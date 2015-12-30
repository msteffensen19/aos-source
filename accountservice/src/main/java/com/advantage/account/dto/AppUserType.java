package com.advantage.account.dto;

import java.util.ArrayList;
import java.util.List;

public enum AppUserType {

    ADMIN(10),
    USER(20),
    GUEST(30);

    private Integer appUserTypecode;

    AppUserType(Integer appUserTypecode) {
        this.appUserTypecode = appUserTypecode;
    }

    public Integer getAppUserTypeCode() {
        return appUserTypecode;
    }

    public static List<String> getAllNames() {
        List<String> values = new ArrayList<>();

        for (AppUserType a : AppUserType.values()) {
            values.add(a.name());
        }
        return values;
    }

    public static boolean contains(String test) {

        for (AppUserType a : AppUserType.values()) {
            if (a.name().equals(test)) {
                return true;
            }
        }

        return false;
    }

}
