package com.advantage.accountsoap.dto.payment;


import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "accountId"
})
@XmlRootElement(name = "GetAccountPaymentPreferencesRequest", namespace = WebServiceConfig.NAMESPACE_URI)
public class GetAccountPaymentPreferencesRequest {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private long accountId;

    public long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
