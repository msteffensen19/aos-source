package com.advantage.root.common;

import io.jsonwebtoken.CompressionCodec;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.Key;
import java.util.Base64;

/**
 * Created by Evgeney Fiskin on 02-01-2016.
 */
public class SecurityTools {
    public static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
    public static final String ISSUER = "www.adwantageonlineshopping.com";
    static final String BASE64_CRYPTO_KEY = "0KHQvtGA0L7QuiDRgtGL0YHRj9GHINC+0LHQtdC30YzRj9C9INCyINC20L7Qv9GDINGB0YPQvdGD0LvQuCDQsdCw0L3QsNC9IMKpINChLiDQm9GD0LrRjNGP0L3QtdC90LrQvi4=";
    public static final String signatureAlgorithmName = signatureAlgorithm.getJcaName();
    public static final CompressionCodec compressionCodec = null;
    //public static final CompressionCodec compressionCodec = new GzipCompressionCodec();
    public static final Key key = decodeBase64Key(BASE64_CRYPTO_KEY);

    private static String encodeBase64Key(Key key) {
        byte[] keyEncoded = key.getEncoded();
        String result = Base64.getEncoder().encodeToString(keyEncoded);
        return result;
    }

    static Key decodeBase64Key(String base64Key) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        //Key result = new SecretKeySpec(decodedKey, "Hmac512");
        Key result = new SecretKeySpec(decodedKey, signatureAlgorithmName);
        return result;
    }

    private static String decodeBase64(String base64) {
        byte[] decodedKey = Base64.getDecoder().decode(base64);
        return new String(decodedKey, Charset.forName("UTF-8"));
    }
}
