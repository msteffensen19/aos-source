package com.advantage.accountsoap.dto;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountCreateDto", propOrder = {
        "lastName",
        "firstName",
        "loginName",
        "country",
        "stateProvince",
        "cityName",
        "address",
        "zipcode",
        "phoneNumber",
        "email",
        "password",
        "accountType",
        "allowOffersPromotion"
})
public class AccountCreateDto {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String lastName;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String firstName;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String loginName;
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
    private String password;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private Integer accountType;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private char allowOffersPromotion;

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

    public String getPhoneNumber() {
        return phoneNumber;
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public char getAllowOffersPromotion() {
        return allowOffersPromotion;
    }

    public void setAllowOffersPromotion(char allowOffersPromotion) {
        this.allowOffersPromotion = allowOffersPromotion;
    }
}
