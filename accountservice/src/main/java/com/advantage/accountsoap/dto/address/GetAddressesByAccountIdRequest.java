package com.advantage.accountsoap.dto.address;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "accountId"
})
@XmlRootElement(name = "GetAddressesByAccountIdRequest", namespace = WebServiceConfig.NAMESPACE_URI)
public class GetAddressesByAccountIdRequest {
    @XmlElement(name = "accountId", namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private int accountId;

    public int getId() {
        return accountId;
    }

    public void setId(int id) {
        this.accountId = id;
    }
}
