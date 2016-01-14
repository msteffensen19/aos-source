package com.advantage.accountsoap.dto;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "accountId",
        "lastName",
        "firstName",
        "countryId",
        "stateProvince",
        "cityName",
        "address",
        "zipcode",
        "phoneNumber",
        "email",
        "accountType",
        "allowOffersPromotion"
})
@XmlRootElement(name = "AccountUpdateRequest", namespace = WebServiceConfig.NAMESPACE_URI)
public class AccountUpdateRequest {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    protected String lastName;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    protected String firstName;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    protected long accountId;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    protected Long countryId;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    protected String stateProvince;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    protected String cityName;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    protected String address;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    protected String zipcode;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    protected String phoneNumber;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    protected String email;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    protected Integer accountType;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    protected String allowOffersPromotion;

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

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public Long getCountry() {
        return countryId;
    }

    public void setCountry(Long country) {
        this.countryId = country;
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

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getAllowOffersPromotion() {
        return allowOffersPromotion;
    }

    public void setAllowOffersPromotion(String allowOffersPromotion) {
        this.allowOffersPromotion = allowOffersPromotion;
    }
}
