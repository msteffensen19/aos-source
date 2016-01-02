package com.advantage.root.common;

import com.advantage.root.store.user.dto.AppUserType;
import io.jsonwebtoken.*;

import java.security.Key;

/**
 * Created by Evgeney Fiskin on 02-01-2016.
 */
public class Token {
    private Header tokenHeader;
    private Claims tokenBody;
    private JwtBuilder builder;
    private JwtParser parser;
    private Key key;
    private CompressionCodec compressionCodec;
    private SignatureAlgorithm signatureAlgorithm;
    private String issuer;

    private Token() {
        key = SecurityTools.decodeBase64Key(SecurityTools.BASE64_CRYPTO_KEY);
        compressionCodec = SecurityTools.compressionCodec;
        signatureAlgorithm = SecurityTools.signatureAlgorithm;
        issuer = SecurityTools.ISSUER;
    }

    public Token(String base64Token) {
        this();
        try {
            parser = Jwts.parser();
            parser.setSigningKey(key);
            parser.requireIssuer(issuer);
            Jws<Claims> claimsJws = parser.parseClaimsJws(base64Token);
            tokenBody = claimsJws.getBody();

        } catch (SignatureException e) {

        } catch (MissingClaimException mce) {
            // the parsed JWT did not have the sub field
        } catch (IncorrectClaimException ice) {
            // the parsed JWT had a sub field, but its value was not equal to 'jsmith'
        } catch (InvalidClaimException ice) {
            // the 'myfield' field was missing or did not have a 'myRequiredValue' value
        }
    }

    public Token(long appUserId, AppUserType appUserType) {
        this(appUserId, appUserType, null);
    }

    public Token(long appUserId, AppUserType appUserType, String email) {
        this();
        builder = Jwts.builder();
        tokenBody = Jwts.claims();
        tokenBody.setIssuer(issuer);
        tokenBody.put("userId", appUserId);
        tokenBody.put("role", appUserType);
        if (email != null && !email.isEmpty()) {
            tokenBody.put("email", email);
        }
        builder.setClaims(tokenBody);
    }

    public AppUserType getAppUserType() {
        AppUserType result = (AppUserType) tokenBody.get("role");
        return result;
    }

    public long getUserId() {
        return (Long) tokenBody.get("userId");
    }

    public String getEmail() {
        return (String) tokenBody.get("email");
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
