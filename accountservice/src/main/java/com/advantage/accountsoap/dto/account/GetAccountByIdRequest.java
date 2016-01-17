package com.advantage.accountsoap.dto.account;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "accountId"
})
@XmlRootElement(name = "GetAccountByIdRequest", namespace = WebServiceConfig.NAMESPACE_URI)
public class GetAccountByIdRequest {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private long accountId;

    public long getId() {
        return accountId;
    }

    public void setId(long id) {
        this.accountId = id;
    }
}
