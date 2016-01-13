package com.advantage.accountsoap.dto;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "id",
                "lastName",
                "firstName",
                "loginName",
                "accountType",
                "paymentMethod",
                "country",
                "stateProvince",
                "cityName",
                "address",
                "zipcode",
                "phoneNumber",
                "email",
                "allowOffersPromotion",
                "internalUnsuccessfulLoginAttempts",
                "internalUserBlockedFromLoginUntil",
                "internalLastSuccesssulLogin"
        })
@XmlRootElement(name = "Account", namespace = WebServiceConfig.NAMESPACE_URI)
public class AccountDto {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private long id;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String lastName;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String firstName;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String loginName;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private Integer accountType;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private Integer paymentMethod;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private Integer country;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String stateProvince;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String cityName;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String address;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String zipcode;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String phoneNumber;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String email;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String allowOffersPromotion;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private int internalUnsuccessfulLoginAttempts;  //  Managed Internally
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private long internalUserBlockedFromLoginUntil; //  Managed Internally
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private long internalLastSuccesssulLogin;   //  Managed Internally

    public AccountDto() {
    }

    public AccountDto(long id,
                      String lastName,
                      String firstName,
                      String loginName,
                      Integer accountType,
                      Integer paymentMethod,
                      Integer country,
                      String stateProvince,
                      String cityName,
                      String address,
                      String zipcode,
                      String phoneNumber,
                      String email,
                      String allowOffersPromotion,
                      int internalUnsuccessfulLoginAttempts,
                      long internalUserBlockedFromLoginUntil,
                      long internalLastSuccesssulLogin) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.loginName = loginName;
        this.accountType = accountType;
        this.paymentMethod = paymentMethod;
        this.country = country;
        this.stateProvince = stateProvince;
        this.cityName = cityName;
        this.address = address;
        this.zipcode = zipcode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.allowOffersPromotion = allowOffersPromotion;
        this.internalUnsuccessfulLoginAttempts = internalUnsuccessfulLoginAttempts;
        this.internalUserBlockedFromLoginUntil = internalUserBlockedFromLoginUntil;
        this.internalLastSuccesssulLogin = internalLastSuccesssulLogin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAllowOffersPromotion() {
        return allowOffersPromotion;
    }

    public void setAllowOffersPromotion(String allowOffersPromotion) {
        this.allowOffersPromotion = allowOffersPromotion;
    }

    public int getInternalUnsuccessfulLoginAttempts() {
        return internalUnsuccessfulLoginAttempts;
    }

    public void setInternalUnsuccessfulLoginAttempts(int internalUnsuccessfulLoginAttempts) {
        this.internalUnsuccessfulLoginAttempts = internalUnsuccessfulLoginAttempts;
    }

    public long getInternalUserBlockedFromLoginUntil() {
        return internalUserBlockedFromLoginUntil;
    }

    public void setInternalUserBlockedFromLoginUntil(long internalUserBlockedFromLoginUntil) {
        this.internalUserBlockedFromLoginUntil = internalUserBlockedFromLoginUntil;
    }

    public long getInternalLastSuccesssulLogin() {
        return internalLastSuccesssulLogin;
    }

    public void setInternalLastSuccesssulLogin(long internalLastSuccesssulLogin) {
        this.internalLastSuccesssulLogin = internalLastSuccesssulLogin;
    }
}
