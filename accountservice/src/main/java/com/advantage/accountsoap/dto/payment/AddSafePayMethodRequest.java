package com.advantage.accountsoap.dto.payment;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "accountId",
        "safePayUsername"
})
@XmlRootElement(name = "AddSafePayMethodRequest", namespace = WebServiceConfig.NAMESPACE_URI)
public class AddSafePayMethodRequest {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String safePayUsername;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private long accountId;

    public AddSafePayMethodRequest() {
    }

    public AddSafePayMethodRequest(String safePayUsername, long accountId) {
        this.safePayUsername = safePayUsername;
        this.accountId = accountId;
    }

    public String getSafePayUsername() {
        return safePayUsername;
    }

    public void setSafePayUsername(String safePayUsername) {
        this.safePayUsername = safePayUsername;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
