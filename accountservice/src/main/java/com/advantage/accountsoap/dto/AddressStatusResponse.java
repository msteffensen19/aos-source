package com.advantage.accountsoap.dto;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "success",
                "userId",
                "reason",
        })
@XmlRootElement(name = "AddressStatusResponse", namespace = WebServiceConfig.NAMESPACE_URI)
public class AddressStatusResponse {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    boolean success;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    long userId;        //  -1 = Invalid user login name
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    String reason;

    public AddressStatusResponse() {
    }

    public AddressStatusResponse(boolean success, long userId, String reason) {
        this.success = success;
        this.userId = userId;
        this.reason = reason;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
