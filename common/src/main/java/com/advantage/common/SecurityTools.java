package com.advantage.common;

import com.advantage.common.dto.AccountType;
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
    private static final String ISSUER = "www.adwantageonlineshopping.com";
    private static final String BASE64_CRYPTO_KEY = "0KHQvtGA0L7QuiDRgtGL0YHRj9GHINC+0LHQtdC30YzRj9C9INCyINC20L7Qv9GDINGB0YPQvdGD0LvQuCDQsdCw0L3QsNC9IMKpINChLiDQm9GD0LrRjNGP0L3QtdC90LrQvi4=";

    private static final String signatureAlgorithmName = "HmacSHA512";
    private static final CompressionCodec compressionCodec = null;
    //public static final CompressionCodec compressionCodec = new GzipCompressionCodec();
    private static final Key key = decodeBase64Key(BASE64_CRYPTO_KEY);

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

    public static HttpStatus isAutorized(String authorizationHeader, AccountType... expectedAccountTypes) {
        if (authorizationHeader == null || authorizationHeader.trim().isEmpty()) {
            return HttpStatus.UNAUTHORIZED;
        }


        return null;
    }
}
