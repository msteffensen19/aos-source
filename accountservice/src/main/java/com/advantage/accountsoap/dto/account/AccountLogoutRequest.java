package com.advantage.accountsoap.dto.account;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

/**
 * @author Binyamin Regev on 02/02/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "loginUser",
        "base64Token"
})
@XmlRootElement(name = "AccountLogoutRequest", namespace = WebServiceConfig.NAMESPACE_URI)
public class AccountLogoutRequest {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String loginUser;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String base64Token;

    public AccountLogoutRequest() {
    }

    public AccountLogoutRequest(final String loginUser, final String base64Token) {
        this.setLoginUser(loginUser);
        this.setBase64Token(base64Token);
    }

    public String getLoginUser() {
        return this.loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getBase64Token() {
        return this.base64Token;
    }

    public void setBase64Token(String base64Token) {
        this.base64Token = base64Token;
    }

}
