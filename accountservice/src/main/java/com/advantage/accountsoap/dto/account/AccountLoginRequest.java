package com.advantage.accountsoap.dto.account;

import com.advantage.accountsoap.config.WebServiceConfig;
import com.advantage.accountsoap.model.Account;
import com.advantage.accountsoap.util.ArgumentValidationHelper;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "loginUser",
        "email",
        "loginPassword"
})
@XmlRootElement(name = "AccountLoginRequest", namespace = WebServiceConfig.NAMESPACE_URI)
public class AccountLoginRequest {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String loginUser;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String loginPassword;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
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
