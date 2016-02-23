package com.advantage.accountsoap.dto.account;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Binyamin Regev on 21/02/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "success",
                "code",
                "reason"
        })
@XmlRootElement(name = "DemoAppConfigurationResponse", namespace = WebServiceConfig.NAMESPACE_URI)
public class DemoAppConfigurationResponse {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private boolean success;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String reason;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private long code;          //  Additional Success/Error code

    /**
     *
     */
    public DemoAppConfigurationResponse() {
    }

    /**
     *
     * @param success
     * @param reason
     */
    public DemoAppConfigurationResponse(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
        this.code = 0;
    }

    /**
     *
     * @param success
     * @param reason
     * @param code
     */
    public DemoAppConfigurationResponse(boolean success, String reason, long code) {
        this.success = success;
        this.reason = reason;
        this.code = code;
    }

    /**
     *
     * @return
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     *
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     *
     * @return
     */
    public String getReason() {
        return reason;
    }

    /**
     *
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     *
     * @return
     */
    public long getCode() {
        return code;
    }

    /**
     *
     * @param code
     */
    public void setCode(long code) {
        this.code = code;
    }
}
