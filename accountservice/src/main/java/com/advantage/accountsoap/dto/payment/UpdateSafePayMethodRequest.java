package com.advantage.accountsoap.dto.payment;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "userId",
        "safePayUsername",
        "safePayPassword",
        "referenceId"
})
@XmlRootElement(name = "UpdateSafePayMethodRequest", namespace = WebServiceConfig.NAMESPACE_URI)
public class UpdateSafePayMethodRequest {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private long userId;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String safePayUsername;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String safePayPassword;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private long referenceId;

    public UpdateSafePayMethodRequest() {
    }

    public UpdateSafePayMethodRequest(String safePayUsername, String safePayPassword, long referenceId) {
        this.safePayUsername = safePayUsername;
        this.safePayPassword = safePayPassword;
        this.referenceId = referenceId;
    }

    public UpdateSafePayMethodRequest(long userId, String safePayUsername, String safePayPassword, long referenceId) {
        this.userId = userId;
        this.safePayPassword = safePayPassword;
        this.safePayUsername = safePayUsername;
        this.referenceId = referenceId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getSafePayUsername() {
        return safePayUsername;
    }

    public void setSafePayUsername(String safePayUsername) {
        this.safePayUsername = safePayUsername;
    }

    public String getSafePayPassword() {
        return this.safePayPassword;
    }

    public void setSafePayPassword(String safePayPassword) {
        this.safePayPassword = safePayPassword;
    }

    public long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(long referenceId) {
        this.referenceId = referenceId;
    }
}
