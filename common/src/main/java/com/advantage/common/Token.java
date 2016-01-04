package com.advantage.common;


import com.advantage.common.dto.AccountType;
import io.jsonwebtoken.*;

import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * Created by Evgeney Fiskin on 02-01-2016.
 */
public class Token {
    private Header tokenHeader;
    private Claims tokenClaims;
    private JwtBuilder builder;
    private JwtParser parser;
    private Key key;
    private CompressionCodec compressionCodec;
    private SignatureAlgorithm signatureAlgorithm;
    private String issuer;

    private Token() {
        key = SecurityTools.getKey();
        compressionCodec = SecurityTools.getCompressionCodec();
        signatureAlgorithm = SecurityTools.getSignatureAlgorithm();
        issuer = SecurityTools.getIssuer();
    }

    public Token(long appUserId, AccountType accountType) {
        this(appUserId, accountType, null, null);
    }

    public Token(long appUserId, AccountType accountType, String email, String loginName) {
        this();
        builder = Jwts.builder();
        tokenHeader = Jwts.header();
        tokenHeader.setType(Header.JWT_TYPE);
        tokenClaims = Jwts.claims();
        tokenClaims.setIssuer(issuer);
        tokenClaims.setIssuedAt(new Date());
        tokenClaims.put("userId", appUserId);
        if (loginName != null && !loginName.isEmpty()) {
            tokenClaims.put("loginName", loginName);
        }
        tokenClaims.put("role", accountType);
        if (email != null && !email.isEmpty()) {
            tokenClaims.put("email", email);
        }
        builder.setHeader((Map<String, Object>) tokenHeader);
        builder.setClaims(tokenClaims);
    }

    public Token(String base64Token) {
        this();
        try {
            parser = Jwts.parser();
            parser.setSigningKey(key);
            parser.requireIssuer(issuer);
            Jws<Claims> claimsJws = parser.parseClaimsJws(base64Token);
            tokenClaims = claimsJws.getBody();

        } catch (SignatureException e) {

        } catch (MissingClaimException mce) {
            // the parsed JWT did not have the sub field
        } catch (IncorrectClaimException ice) {
            // the parsed JWT had a sub field, but its value was not equal to 'jsmith'
        } catch (InvalidClaimException ice) {
            // the 'myfield' field was missing or did not have a 'myRequiredValue' value
        }
    }

    public AccountType getAppUserType() {
        AccountType result = (AccountType) tokenClaims.get("role");
        return result;
    }

    public long getUserId() {
        return (Long) tokenClaims.get("userId");
    }

    public String getEmail() {
        return (String) tokenClaims.get("email");
    }

    public String getLoginName() {
        return (String) tokenClaims.get("loginName");
    }

    public String generateToken() {
        builder.signWith(signatureAlgorithm, key);
        if (compressionCodec != null) {
            builder.compressWith(compressionCodec);
        }
        String result = builder.compact();
        return result;
    }
//    public boolean validateExpirationTime(){
//
//    }
}
