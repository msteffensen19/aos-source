package com.advantage.accountsoap.dto.account;

import com.advantage.accountsoap.config.WebServiceConfig;
import com.advantage.common.Token;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "success",
                "userId",
                "reason",
                "token",
                "sessionId"
        })
@XmlRootElement(name = "AccountStatusResponse", namespace = WebServiceConfig.NAMESPACE_URI)
public class AccountStatusResponse {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    boolean success;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    long userId;        //  -1 = Invalid user login name
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    String reason;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    String token;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    String sessionId;

    public AccountStatusResponse() {
    }

    /**
     * @param success
     * @param reason
     * @param userId
     */
    public AccountStatusResponse(boolean success, String reason, long userId) {
        this.setUserId(userId);
        this.setReason(reason);
        this.setSuccess(success);
    }

    /**
     * @param success
     * @param reason
     * @param userId
     * @param token
     */
    public AccountStatusResponse(boolean success, String reason, long userId, String token) {
        this.setUserId(userId);
        this.setReason(reason);
        this.setSuccess(success);
        this.setToken(token);
    }

    /**
     * @return
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
