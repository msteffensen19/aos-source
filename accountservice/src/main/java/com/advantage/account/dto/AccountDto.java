package com.advantage.account.dto;

import com.advantage.account.model.Account;
import com.advantage.account.util.ArgumentValidationHelper;

public class AccountDto {

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

    public void applyAppUser(final Account account) {
        ArgumentValidationHelper.validateArgumentIsNotNull(account, "application user");

        this.setLoginUser(account.getLoginName());
        this.setLoginPassword(account.getPassword());
        this.setEmail(account.getEmail());
    }
}
