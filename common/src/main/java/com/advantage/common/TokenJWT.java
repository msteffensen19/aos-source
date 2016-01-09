package com.advantage.common;


import com.advantage.common.dto.AccountType;
import com.advantage.common.exceptions.token.TokenUnsignedException;
import com.advantage.common.exceptions.token.SignatureAlgorithmException;
import com.advantage.common.exceptions.token.WrongTokenTypeException;
import io.jsonwebtoken.*;

import java.util.Map;

/**
 * Created by Evgeney Fiskin on 02-01-2016.
 */
public class TokenJWT extends Token {

    private Claims tokenClaims;
    private Header tokenHeader;
    private JwtBuilder builder;
    private JwtParser parser;
    //private CompressionCodec compressionCodec;
    private SignatureAlgorithm signatureAlgorithm;

    private TokenJWT() throws SignatureAlgorithmException {
        super();
        //compressionCodec = SecurityTools.getCompressionCodec();
        convertSignatureAlgorithm();
    }

    public TokenJWT(long appUserId, String loginName, AccountType accountType) throws SignatureAlgorithmException {
        this();
        builder = Jwts.builder();
        tokenHeader = Jwts.header();
        tokenHeader.setType(Header.JWT_TYPE);
        tokenClaims = Jwts.claims();
        tokenClaims.setIssuer(issuer);
        //tokenClaims.setIssuedAt(new Date());
        tokenClaims.put(USER_ID_FIELD_NAME, appUserId);
        if (loginName != null && !loginName.isEmpty()) {
            tokenClaims.put(LOGIN_NAME_FIELD_NAME, loginName);
        }
        tokenClaims.put(ROLE_FIELD_NAME, accountType);
//        if (email != null && !email.isEmpty()) {
//            tokenClaims.put("email", email);
//        }
        builder.setHeader((Map<String, Object>) tokenHeader);
        builder.setClaims(tokenClaims);
    }

    public TokenJWT(String base64Token) throws TokenUnsignedException, SignatureAlgorithmException, WrongTokenTypeException {
        this();
//        try {
        parser = Jwts.parser();
        if (!parser.isSigned(base64Token)) {
            throw new TokenUnsignedException();
        }
        parser.setSigningKey(key);
        parser.requireIssuer(issuer);
        Jws<Claims> claimsJws = parser.parseClaimsJws(base64Token);
        tokenClaims = claimsJws.getBody();
        JwsHeader jwsHeader = claimsJws.getHeader();

        if (!jwsHeader.getType().equals(Header.JWT_TYPE)) {
            throw new WrongTokenTypeException("Wrong token type");
        }
        if (!jwsHeader.getAlgorithm().equals(signatureAlgorithm.name())) {
            throw new SignatureAlgorithmException(String.format("The token signed by %s algorithm, but nust be signed with %s (%s)", jwsHeader.getAlgorithm(), signatureAlgorithm.name(), signatureAlgorithmName));
        }

//        }  catch (SignatureException e) {
//
//        } catch (MissingClaimException mce) {
//            // the parsed JWT did not have the sub field
//        } catch (IncorrectClaimException ice) {
//            // the parsed JWT had a sub field, but its value was not equal to 'jsmith'
//        } catch (InvalidClaimException ice) {
//            // the 'myfield' field was missing or did not have a 'myRequiredValue' value
//        }
    }

    @Override
    public AccountType getAccountType() {
        String role = (String) tokenClaims.get(ROLE_FIELD_NAME);
        AccountType result = AccountType.valueOf(role);
        return result;
    }

    @Override
    public long getUserId() {
        long result = 0;
        try {
            Number userId = (Number) tokenClaims.get(USER_ID_FIELD_NAME);
            result = userId.longValue();
        } catch (ClassCastException | NumberFormatException e) {

        }
        return result;
    }

//    @Override
//    public String getEmail() {
//        return (String) tokenClaims.get("email");
//    }

    @Override
    public String getLoginName() {
        return (String) tokenClaims.get(LOGIN_NAME_FIELD_NAME);
    }

    @Override
    public String generateToken() {
        builder.signWith(signatureAlgorithm, key);
//        if (compressionCodec != null) {
//            builder.compressWith(compressionCodec);
//        }
        String result = builder.compact();
        return result;
    }

    private void convertSignatureAlgorithm() throws SignatureAlgorithmException {
        for (SignatureAlgorithm sa : SignatureAlgorithm.values()) {
            String saname = (sa.getJcaName() == null) ? "" : sa.getJcaName();
            if (saname.equalsIgnoreCase(signatureAlgorithmName)) {
                if (!sa.isJdkStandard()) {
                    throw new SignatureAlgorithmException("io.jsonwebtoken: Unsupported signature algorithm:" + signatureAlgorithmName);
                } else {
                    signatureAlgorithm = sa;
                    return;
                }
            }
        }
        throw new SignatureAlgorithmException("io.jsonwebtoken: Unknown signature algorithm:" + signatureAlgorithmName);
    }

}
