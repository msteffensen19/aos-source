package com.advantage.online.store.user.model;

/**
 * @author Binyamin Regev on 15/11/2015.
 */
public enum AppUserType {

    ADMIN(Integer.valueOf(10)),
    USER(Integer.valueOf(20));

    private final Integer appUserTypeCode;

    private AppUserType(final Integer appUserTypeCode) {

        assert appUserTypeCode != null;

        this.appUserTypeCode = appUserTypeCode;
    }

    public Integer getAppUserTypeCode() {

        return appUserTypeCode;
    }


}
