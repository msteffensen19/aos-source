package com.advantage.online.store.user.model;

/**
 * @author Binyamin Regev on 15/11/2015.
 */
public enum AppUserType {

    ADMIN(10),
    USER(20),
    GUEST(30);

    private Integer appUserTypecode;

    AppUserType(Integer appUserTypecode) { this.appUserTypecode = appUserTypecode;}

    public Integer getAppUserTypeCode() { return appUserTypecode; }

}
