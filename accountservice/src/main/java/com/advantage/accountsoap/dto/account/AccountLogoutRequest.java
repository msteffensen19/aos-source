package com.advantage.accountsoap.dto.account;

import com.advantage.accountsoap.config.WebServiceConfig;
import com.advantage.accountsoap.model.Account;
import com.advantage.accountsoap.util.ArgumentValidationHelper;

import javax.xml.bind.annotation.*;

/**
 * @author Binyamin Regev on 02/02/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "loginUser",
        "loginPassword"
})
@XmlRootElement(name = "AccountLogoutRequest", namespace = WebServiceConfig.NAMESPACE_URI)
public class AccountLogoutRequest {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String loginUser;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String loginPassword;

    public AccountLogoutRequest() {
    }

    public AccountLogoutRequest(final String loginUser, final String loginPassword) {
        this.setLoginUser(loginUser);
        this.setLoginPassword(loginPassword);
    }

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

}
