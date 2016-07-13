package com.advantage.common.security;

import com.advantage.common.enums.AccountType;
import com.advantage.common.exceptions.authorization.AuthorizationException;
import com.advantage.common.exceptions.token.ContentTokenException;
import com.advantage.common.exceptions.token.VerificationTokenException;
import com.advantage.common.exceptions.token.WrongTokenTypeException;
import io.jsonwebtoken.CompressionCodec;
import org.springframework.http.HttpStatus;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.Key;
import java.util.Base64;

/**
 * Created by Evgeney Fiskin on 02-01-2016.
 */
public class SecurityTools {
    private static final String ISSUER = "www.advantageonlineshopping.com";
    private static final String BASE64_CRYPTO_KEY = "0KHQvtGA0L7QuiDRgtGL0YHRj9GHINC+0LHQtdC30YzRj9C9INCyINC20L7Qv9GDINGB0YPQvdGD0LvQuCDQsdCw0L3QsNC9IMKpINChLiDQm9GD0LrRjNGP0L3QtdC90LrQvi4=";

    private static final String signatureAlgorithmName = "HmacSHA256";
    //    private static final String signatureAlgorithmName = "HmacSHA512";
    private static final CompressionCodec compressionCodec = null;
    //public static final CompressionCodec compressionCodec = new GzipCompressionCodec();
    private static final Key key = decodeBase64Key(BASE64_CRYPTO_KEY);
    //    public static final String SWAGGER_NOTE = "For authorization as USER with ID = 1 (\"avinu.avraham\", password \"Avraham1\", email \"a@b.com\") use token  \"<span style=\"font-family:Courier New;font-size:0.75em\">Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ3d3cuYWR3YW50YWdlb25saW5lc2hvcHBpbmcuY29tIiwidXNlcklkIjoxLCJzdWIiOiJhdmludS5hdnJhaGFtIiwicm9sZSI6IlVTRVIifQ.sadgAYdH5xlqqNFlA_eVoV-ttyL5hgHLdmF5ScMoWEw</span>\".<br/>For authorization as ADMIN with ID = 13 (\"admin\", password \"adm1n\", email \"admin@admin.ad\") use token  \"<span style=\"font-family:Courier New;font-size:0.75em\">Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ3d3cuYWR3YW50YWdlb25saW5lc2hvcHBpbmcuY29tIiwidXNlcklkIjoxMywic3ViIjoiYWRtaW4iLCJyb2xlIjoiQURNSU4ifQ.XssBjww5LFdYt6ONUYCcJDvdIJinQN1TI_ehyhcdylA</span>\"<br/>Or just generate your ouwn in page <a href=\"http://jwt.io/\">jwt.io</a> with base64 secret key \"<span style=\"font-family:Courier New;font-size:0.75em\">" + BASE64_CRYPTO_KEY + "</span>\"";
    public static final String SWAGGER_NOTE = "Create your own JSON Web Token with <a href=\"http://jwt.io/\" target=\"_blank\">jwt.io site</a> with your Base64 secret key with this template: <br/>{" +
            "<ul style=\"list-style-type:none;margin: 0 0 0 0;\">" +
            "  <li>\"typ\": \"JWT\",</li>" +
            "  <li>\"alg\": \"HS256\"</li>" +
            "</ul>}" +
            "<br/>{" +
            "<ul style=\"list-style-type:none;margin: 0 0 0 0;\">" +
            "<li>\"iss\": \"www.advantageonlineshopping.com\",</li>" +
            "<li>  \"userId\": [user id],</li>" +
            "<li>  \"sub\": \"[user name]\",</li>" +
            "<li>  \"role\": \"USER\" / \"ADMIN\"</li>" +
            "</ul>}";
    public static final String AUTHORIZATION_HEADER_PREFIX = "Bearer";

    public static String getSignatureAlgorithmName() {
        return signatureAlgorithmName;
    }

    private static String encodeBase64Key(Key key) {
        byte[] keyEncoded = key.getEncoded();
        String result = Base64.getEncoder().encodeToString(keyEncoded);
        return result;
    }

    private static Key decodeBase64Key(String base64Key) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        Key result = new SecretKeySpec(decodedKey, signatureAlgorithmName);
        return result;
    }

    private static String decodeBase64(String base64) {
        byte[] decodedKey = Base64.getDecoder().decode(base64);
        return new String(decodedKey, Charset.forName("UTF-8"));
    }

    public static Key getKey() {
        return key;
    }

    public static CompressionCodec getCompressionCodec() {
        return compressionCodec;
    }

    public static String getIssuer() {
        return ISSUER;
    }

    public static boolean isAuthorized(String authorizationHeader, AccountType... expectedAccountTypes) throws AuthorizationException {
        if (authorizationHeader == null || authorizationHeader.trim().isEmpty()) {
            throw new AuthorizationException("Authorization header is missing", HttpStatus.UNAUTHORIZED);
        } else {
            // remove schema from token
            if (authorizationHeader.indexOf(AUTHORIZATION_HEADER_PREFIX) == -1) {
                throw new AuthorizationException("Authorization header is wrong", HttpStatus.UNAUTHORIZED);
            } else {
                Token token = getTokenFromAuthorizationHeader(authorizationHeader);
                AccountType actualAccountType = token.getAccountType();
                for (AccountType at : expectedAccountTypes) {
                    if (at.equals(actualAccountType)) {
                        return true;
                    }
                }
                throw new VerificationTokenException("Wrong account type (" + actualAccountType.toString() + ")");
            }
        }
    }

    public static boolean isAuthorized(String authorizationHeader, long expectedUserId, AccountType... expectedAccountTypes) throws AuthorizationException {
        if (authorizationHeader == null || authorizationHeader.trim().isEmpty()) {
            throw new AuthorizationException("Authorization header is missing", HttpStatus.UNAUTHORIZED);
        } else {
            // remove schema from token
            if (authorizationHeader.indexOf(AUTHORIZATION_HEADER_PREFIX) == -1) {
                throw new AuthorizationException("Authorization header is wrong", HttpStatus.UNAUTHORIZED);
            } else {
                Token token = getTokenFromAuthorizationHeader(authorizationHeader);
                AccountType actualAccountType = token.getAccountType();
                long actualUserId = token.getUserId();
                if (actualUserId != expectedUserId) {
                    throw new VerificationTokenException("You authenticated with user Id (" + actualUserId + "), but request is for user (" + expectedUserId + ")");
                }
                for (AccountType at : expectedAccountTypes) {
                    if (at.equals(actualAccountType)) {
                        return true;
                    }
                }
                throw new VerificationTokenException("Wrong account type (" + actualAccountType.toString() + ")");
            }
        }
    }

    public static boolean isAuthorized(String authorizationHeader, String expectedUserName, AccountType... expectedAccountTypes) throws AuthorizationException {
        if (authorizationHeader == null || authorizationHeader.trim().isEmpty()) {
            throw new AuthorizationException("Authorization header is missing", HttpStatus.UNAUTHORIZED);
        } else {
            // remove schema from token
            if (authorizationHeader.indexOf(AUTHORIZATION_HEADER_PREFIX) == -1) {
                throw new AuthorizationException("Authorization header is wrong", HttpStatus.UNAUTHORIZED);
            } else {
                Token token = getTokenFromAuthorizationHeader(authorizationHeader);
                AccountType actualAccountType = token.getAccountType();
                String actualUserName = token.getLoginName();
                if (!actualUserName.equals(expectedUserName)) {
                    throw new VerificationTokenException("Wrong user name (" + actualUserName + "), but the request is for user (" + expectedUserName + ")");
                }
                for (AccountType expectedAccountType : expectedAccountTypes) {
                    if (expectedAccountType.equals(actualAccountType) && actualUserName.equals(expectedUserName)) {
                        return true;
                    }
                }
                throw new VerificationTokenException("Wrong account type (" + actualAccountType.toString() + ")");
            }
        }
    }

    public static Token getTokenFromAuthorizationHeader(String authorizationHeader) throws VerificationTokenException, WrongTokenTypeException, ContentTokenException {
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            return null;
        }
        String stringToken = authorizationHeader.substring(AUTHORIZATION_HEADER_PREFIX.length()).trim();
        return TokenJWT.parseToken(stringToken);
    }
}
