package com.advantage.order.store.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthHelper {

    public static String decodeBasicAuth(String authToken) throws Exception{
        String token = authToken.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(token);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        return credentials;
    }
}
