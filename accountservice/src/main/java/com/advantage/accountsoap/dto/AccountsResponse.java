package com.advantage.accountsoap.dto;

import com.advantage.accountsoap.config.WebServiceConfig;
import com.advantage.accountsoap.model.Account;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountsResponse",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "account"
        })
@XmlRootElement(name = "AccountsResponse", namespace = WebServiceConfig.NAMESPACE_URI)
public class AccountsResponse {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    protected List<Account> account;

    public List<Account> getAccount() {
        if (account == null) {
            return new ArrayList<>();
        }

        return this.account;
    }

    public void setAccount(List<Account> account) {
        this.account = account;
    }
}
