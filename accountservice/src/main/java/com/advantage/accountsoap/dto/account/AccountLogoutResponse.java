package com.advantage.accountsoap.dto.account;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Binyamin Regev on 02/02/2016.
 */
@XmlType(name = "",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "response"
        })
@XmlRootElement(name = "AccountLogoutResponse", namespace = WebServiceConfig.NAMESPACE_URI)
public class AccountLogoutResponse {
    @XmlElement(name = "StatusMessage", namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private AccountStatusResponse response;

    public AccountLogoutResponse() {
    }

    public AccountLogoutResponse(AccountStatusResponse response) {
        this.response = response;
    }

    public AccountStatusResponse getResponse() {
        return response;
    }

    public void setResponse(AccountStatusResponse response) {
        this.response = response;
    }
}
