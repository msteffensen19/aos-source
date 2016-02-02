package com.advantage.accountsoap.dto.account;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "numberOfFailedLoginAttemptsBeforeBlocking",
                "loginBlockingIntervalInMilliSeconds",
                "emailAddressInLogin",
                "productInStockDefaultValue",
                "userSecondWsdl",
                "userLoginTimeout"
        })
@XmlRootElement(name = "AccountConfigurationStatusResponse", namespace = WebServiceConfig.NAMESPACE_URI)
public class AccountConfigurationStatusResponse {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private int numberOfFailedLoginAttemptsBeforeBlocking;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private long loginBlockingIntervalInMilliSeconds;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private boolean emailAddressInLogin;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private int productInStockDefaultValue;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private boolean userSecondWsdl;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private int userLoginTimeout;

    public AccountConfigurationStatusResponse() {
    }

    public AccountConfigurationStatusResponse(int numberOfFailedLoginAttemptsBeforeBlocking,
                                              long loginBlockingIntervalInMilliSeconds,
                                              boolean emailAddressInLogin,
                                              int productInStockDefaultValue,
                                              boolean userSecondWsdl,
                                              int userLoginTimeout) {
        this.numberOfFailedLoginAttemptsBeforeBlocking = numberOfFailedLoginAttemptsBeforeBlocking;
        this.loginBlockingIntervalInMilliSeconds = loginBlockingIntervalInMilliSeconds;
        this.emailAddressInLogin = emailAddressInLogin;
        this.productInStockDefaultValue = productInStockDefaultValue;
        this.userSecondWsdl = userSecondWsdl;
        this.userLoginTimeout = userLoginTimeout;
    }

    public int getNumberOfFailedLoginAttemptsBeforeBlocking() {
        return numberOfFailedLoginAttemptsBeforeBlocking;
    }

    public void setNumberOfFailedLoginAttemptsBeforeBlocking(int numberOfFailedLoginAttemptsBeforeBlocking) {
        this.numberOfFailedLoginAttemptsBeforeBlocking = numberOfFailedLoginAttemptsBeforeBlocking;
    }

    public long getLoginBlockingIntervalInMilliSeconds() {
        return loginBlockingIntervalInMilliSeconds;
    }

    public void setLoginBlockingIntervalInMilliSeconds(long loginBlockingIntervalInMilliSeconds) {
        this.loginBlockingIntervalInMilliSeconds = loginBlockingIntervalInMilliSeconds;
    }

    public boolean isEmailAddressInLogin() {
        return emailAddressInLogin;
    }

    public void setEmailAddressInLogin(boolean emailAddressInLogin) {
        this.emailAddressInLogin = emailAddressInLogin;
    }

    public int getProductInStockDefaultValue() {
        return this.productInStockDefaultValue;
    }

    public void setProductInStockDefaultValue(int productInStockDefaultValue) {
        this.productInStockDefaultValue = productInStockDefaultValue;
    }

    public boolean isUserSecondWsdl() {
        return userSecondWsdl;
    }

    public void setUserSecondWsdl(boolean userSecondWsdl) {
        this.userSecondWsdl = userSecondWsdl;
    }

    public int getUserLoginTimeout() {
        return this.userLoginTimeout;
    }

    public void setUserLoginTimeout(int userLoginTimeout) {
        this.userLoginTimeout = userLoginTimeout;
    }
}
