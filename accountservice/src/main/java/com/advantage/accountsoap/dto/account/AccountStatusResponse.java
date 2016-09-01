package com.advantage.accountsoap.dto.account;
//Modify this class with resources/accountservice.xsd

import com.advantage.accountsoap.config.WebServiceConfig;
import com.advantage.common.enums.AccountType;

import javax.xml.bind.annotation.*;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "success",
                "userId",
                "reason",
                "token",
                "sessionId",
                "T_Authorization",
                "accountType"
        })
@XmlRootElement(name = "AccountStatusResponse", namespace = WebServiceConfig.NAMESPACE_URI)
public class AccountStatusResponse {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private boolean success;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private long userId;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String reason;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String token;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String sessionId;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String T_Authorization;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private Integer accountType;

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

    @Override
    public String toString() {
        return "AccountStatusResponse{" +
                "success=" + success +
                ", userId=" + userId +
                ", reason='" + reason + '\'' +
                ", token='" + token + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", T_Authorization='" + T_Authorization +
                ", role='" + accountType +
                '}';
    }

    public Integer getAccountType(){
        return  accountType;
    }
    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getT_Authorization() {
        return T_Authorization;
    }

    public void setT_Authorization(String t_Authorization) {
        T_Authorization = t_Authorization;
    }
}
