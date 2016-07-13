package com.advantage.common.security;


import com.advantage.common.enums.AccountType;
import com.advantage.common.exceptions.token.*;
import io.jsonwebtoken.*;
import org.apache.log4j.Logger;

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

    private static final Logger logger = Logger.getLogger(TokenJWT.class);

    private TokenJWT() {
        super();
        //compressionCodec = SecurityTools.getCompressionCodec();
        convertSignatureAlgorithm();
    }

    public static TokenJWT createToken(long appUserId, String loginName, AccountType accountType) {
        TokenJWT result = new TokenJWT();
        result.builder = Jwts.builder();
        result.tokenHeader = Jwts.header();
        result.tokenHeader.setType(Header.JWT_TYPE);
        result.tokenClaims = Jwts.claims();
        result.tokenClaims.setIssuer(result.issuer);
        //tokenClaims.setIssuedAt(new Date());
        result.tokenClaims.put(USER_ID_FIELD_NAME, appUserId);
        if (loginName != null && !loginName.isEmpty()) {
            result.tokenClaims.setSubject(loginName);
        }
        result.tokenClaims.put(ROLE_FIELD_NAME, accountType);
//        if (email != null && !email.isEmpty()) {
//            tokenClaims.put("email", email);
//        }
        result.builder.setHeader((Map<String, Object>) result.tokenHeader);
        result.builder.setClaims(result.tokenClaims);
        return result;
    }

    public static TokenJWT convertToToken(String base64Token) throws VerificationTokenException, WrongTokenTypeException, ContentTokenException {
        TokenJWT result = new TokenJWT();
        try {
            result.parser = Jwts.parser();
            if (!result.parser.isSigned(base64Token)) {
                TokenUnsignedException e = new TokenUnsignedException("Token is unsigned");
                logger.error("Token is unsigned", e);
                throw e;
            }
            result.parser.setSigningKey(result.key);
            result.parser.requireIssuer(result.issuer);
            Jws<Claims> claimsJws = result.parser.parseClaimsJws(base64Token);
            result.tokenClaims = claimsJws.getBody();
            JwsHeader jwsHeader = claimsJws.getHeader();

            if (!jwsHeader.getType().equals(Header.JWT_TYPE)) {
                WrongTokenTypeException e = new WrongTokenTypeException("Wrong token type");
                logger.error("Wrong token type", e);
                throw e;
            }
            if (!jwsHeader.getAlgorithm().equals(result.signatureAlgorithm.name())) {
                String m = String.format("The token signed by %s algorithm, but must be signed with %s (%s)", jwsHeader.getAlgorithm(), signatureAlgorithm.name(), signatureAlgorithmJdkName);
                SignatureAlgorithmException e = new SignatureAlgorithmException(m);
                logger.error(m, e);
                throw e;
            }

        } catch (ClaimJwtException | RequiredTypeException e) {
            throw new VerificationTokenException(e.getMessage());
        } catch (SignatureException e) {
            throw new SignatureAlgorithmException(e.getMessage());
        } catch (MalformedJwtException | CompressionException | UnsupportedJwtException e) {
            throw new ContentTokenException(e.getMessage());
        }
        return result;
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
        Object o = "null";

        try {
            o = tokenClaims.get(USER_ID_FIELD_NAME);
            if (o == null) {
                throw new ContentTokenException("The token must contains " + USER_ID_FIELD_NAME + " field");
            }
            Number userId = (Number) o;
            result = userId.longValue();
        } catch (ClassCastException | NumberFormatException e) {
            throw new ContentTokenException("User id have wrong number: " + o.toString());
        }
        return result;
    }

//    @Override
//    public String getEmail() {
//        return (String) tokenClaims.get("email");
//    }

    @Override
    public String getLoginName() {
        return (String) tokenClaims.getSubject();
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

    @Override
    public Map<String, Object> getClaims() {
        return tokenClaims;
    }

    private void convertSignatureAlgorithm() {
        for (SignatureAlgorithm sa : SignatureAlgorithm.values()) {
            String saname = (sa.getJcaName() == null) ? "" : sa.getJcaName();
            if (saname.equalsIgnoreCase(signatureAlgorithmJdkName)) {
                if (!sa.isJdkStandard()) {
                    throw new SignatureException("io.jsonwebtoken: Unsupported signature algorithm:" + signatureAlgorithmJdkName);
                } else {
                    signatureAlgorithm = sa;
                    return;
                }
            }
        }
        throw new SignatureException("io.jsonwebtoken: Unknown signature algorithm:" + signatureAlgorithmJdkName);
    }

}
