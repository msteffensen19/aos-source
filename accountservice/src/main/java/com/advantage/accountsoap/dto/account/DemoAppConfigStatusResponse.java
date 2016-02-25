package com.advantage.accountsoap.dto.account;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

/**
 * @author Binyamin Regev on 25/02/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "success",
                "reason",
                "code"
        })
@XmlRootElement(name = "DemoAppConfigStatusResponse", namespace = WebServiceConfig.NAMESPACE_URI)
public class DemoAppConfigStatusResponse {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private boolean success;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String reason;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private long code;

    public DemoAppConfigStatusResponse() {
    }

    public DemoAppConfigStatusResponse(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    public DemoAppConfigStatusResponse(boolean success, String reason, long code) {
        this.success = success;
        this.reason = reason;
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }
}
