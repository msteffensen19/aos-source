package com.advantage.accountsoap.dto.account;
//Modify this class with resources/accountservice.xsd

import com.advantage.accountsoap.config.WebServiceConfig;
import com.advantage.accountsoap.dto.IUserRequest;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.*;

/**
 * @author Binyamin Regev on 02/02/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "loginUser",
        "token"
})
@XmlRootElement(name = "AccountLogoutRequest", namespace = WebServiceConfig.NAMESPACE_URI)
public class AccountLogoutRequest implements IUserRequest {
    //Actually this field is AccountId as a string
    @XmlElement(name = "loginUser", namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String loginUser;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)

    private String token;

    public AccountLogoutRequest() {
    }

    public AccountLogoutRequest(final String loginUser, final String base64Token) {
        this.setAccountId(loginUser);
        this.setToken(base64Token);
    }
    @ApiModelProperty(name = "userId")
    public String getUserId() {
        return this.loginUser;
    }
    @ApiModelProperty(hidden=true)
    public void setAccountId(String accountId) {
        this.loginUser = accountId;
    }
    @ApiModelProperty(name = "Login token", notes = "Use the returned token value from /login request.")
    public void setToken(String base64Token) {
        this.token = base64Token;
    }

    @Override
    public long getAccountId() {
        return Long.valueOf(loginUser);
    }

    @ApiModelProperty(hidden=true)
    @Override
    public String getBase64Token() {
        return token;
    }

    @Override
    public String toString() {
        return "AccountLogoutRequest{" +
                "loginUser='" + loginUser + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
