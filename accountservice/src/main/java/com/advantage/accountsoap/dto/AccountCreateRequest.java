package com.advantage.accountsoap.dto;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountCreateRequest", propOrder = {
        "account"
})
@XmlRootElement(name = "AccountCreateRequest", namespace = WebServiceConfig.NAMESPACE_URI)
public class AccountCreateRequest {
    @XmlElement(name = "Account",namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private AccountCreateDto account;

    public AccountCreateDto getAccount() {
        return account;
    }

    public void setAccount(AccountCreateDto account) {
        this.account = account;
    }
}
