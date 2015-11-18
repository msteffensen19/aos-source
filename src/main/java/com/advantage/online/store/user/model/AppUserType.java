package com.advantage.online.store.user.model;

/**
 * @author Binyamin Regev on 15/11/2015.
 */
public enum AppUserType {

    ADMIN (10, "Admin"),
    USER (20, "User");

    private int appUserTypeCode;

    private String appUserTypeName;


    AppUserType(int appUserTypeCode, String appUserTypeName) {
    }

    public int getAppUserTypeCode() {
        return appUserTypeCode;
    }

    public String getAppUserTypeName() {
        return appUserTypeName;
    }
}
