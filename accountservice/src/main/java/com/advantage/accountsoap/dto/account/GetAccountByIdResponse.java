package com.advantage.accountsoap.dto.account;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "account"
        })
@XmlRootElement(name = "GetAccountByIdResponse", namespace = WebServiceConfig.NAMESPACE_URI)
public class GetAccountByIdResponse {
    @XmlElement(name = "AccountResponse", namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private AccountDto account;

    public GetAccountByIdResponse() {
    }

    public GetAccountByIdResponse(AccountDto account) {
        this.account = account;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }
}
