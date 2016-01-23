package com.advantage.accountsoap.util;

public class AccountPassword {
    private String salt;
    private String password;

    public AccountPassword(String userName, String password) {
        this.salt = setSalt(userName);
        this.password = password;
    }

    public String getEncryptedPassword() throws Exception {
        return SHA1(this.salt + SHA1(this.password));
    }

    private String setSalt(String salt) {
        return new StringBuilder(salt).reverse().toString();
    }

    public static String SHA1(String value) throws Exception {
        java.security.MessageDigest d = null;
        d = java.security.MessageDigest.getInstance("SHA-1");
        d.reset();
        d.update(value.getBytes());
        byte[] digest = d.digest();
        String hexStr = "";
        for (byte aDigest : digest) {
            hexStr += Integer.toString((aDigest & 0xff) + 0x100, 16).substring(1);
        }

        return hexStr;
    }
}
