package com.advantage.account.dto;

import com.advantage.account.model.AppUser;
import com.advantage.account.util.ArgumentValidationHelper;

public class AppUserDto {

    private String loginUser;
    private String loginPassword;
    private String email;

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void applyAppUser(final AppUser appUser) {
        ArgumentValidationHelper.validateArgumentIsNotNull(appUser, "application user");

        this.setLoginUser(appUser.getLoginName());
        this.setLoginPassword(appUser.getPassword());
        this.setEmail(appUser.getEmail());
    }
}
