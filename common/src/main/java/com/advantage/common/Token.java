package com.advantage.common;

import com.advantage.common.dto.AccountType;

import java.security.Key;

/**
 * Created by Evgeney Fiskin on 06-01-2016.
 */
public abstract class Token {
    protected static final String ROLE_FIELD_NAME = "role";
    protected static final String USER_ID_FIELD_NAME = "userId";
    protected static final String LOGIN_NAME_FIELD_NAME = "loginName";
    protected Key key;
    protected String issuer;
    protected String signatureAlgorithmName;

    protected Token() {
        key = SecurityTools.getKey();
        issuer = SecurityTools.getIssuer();
        signatureAlgorithmName = SecurityTools.getSignatureAlgorithmName();
    }

    public abstract AccountType getAccountType();

    public abstract long getUserId();

    //public abstract String getEmail();

    public abstract String getLoginName();

    public abstract String generateToken();

    //    public boolean validateExpirationTime();

}
