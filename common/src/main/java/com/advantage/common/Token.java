package com.advantage.common;

import com.advantage.common.dto.AccountType;

import java.security.Key;

/**
 * Created by Evgeney Fiskin on 06-01-2016.
 */
public abstract class Token {
    protected Key key;
    protected String issuer;
    protected String signatureAlgorithmName;

    protected Token() {
        key = SecurityTools.getKey();
        issuer = SecurityTools.getIssuer();
        signatureAlgorithmName = SecurityTools.getSignatureAlgorithmName();
    }

    public abstract AccountType getAppUserType();

    public abstract long getUserId();

    //public abstract String getEmail();

    public abstract String getLoginName();

    public abstract String generateToken();

    //    public boolean validateExpirationTime();

}
