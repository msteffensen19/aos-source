package com.advantage.common.dto;

import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

/**
 * @author Binyamin Regev on 19/11/2015.
 */
public class UserDetailsDto {

    private long id;
    private String lastName;
    private String firstName;
    private String loginName;
    private Integer accountType;
    private Long countryId;
    private String countryName;
    private String countryIsoName;
    private String stateProvince;
    private String cityName;
    private String address;
    private String zipcode;
    private String phoneNumber;
    private String email;
    private long defaultPaymentMethodId;
    private boolean allowOffersPromotion;
    private int internalUnsuccessfulLoginAttempts;  //  Managed Internally
    private long internalUserBlockedFromLoginUntil; //  Managed Internally
    private long internalLastSuccesssulLogin;   //  Managed Internally
    private String loginPassword;
    private boolean aobUser;

    public UserDetailsDto(long id,
                          String lastName,
                          String firstName,
                          String loginName,
                          Integer accountType,
                          Long countryId,
                          String countryName,
                          String countryIsoName,
                          String stateProvince,
                          String cityName,
                          String address,
                          String zipcode,
                          String phoneNumber,
                          String email,
                          long defaultPaymentMethodId,
                          boolean allowOffersPromotion,
                          int internalUnsuccessfulLoginAttempts,
                          long internalUserBlockedFromLoginUntil,
                          long internalLastSuccesssulLogin,
                          String loginPassword,
                          boolean aobUser) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.loginName = loginName;
        this.accountType = accountType;
        this.countryId = countryId;
        this.countryName = countryName;
        this.countryIsoName = countryIsoName;
        this.stateProvince = stateProvince;
        this.cityName = cityName;
        this.address = address;
        this.zipcode = zipcode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.defaultPaymentMethodId = defaultPaymentMethodId;
        this.allowOffersPromotion = allowOffersPromotion;
        this.internalUnsuccessfulLoginAttempts = internalUnsuccessfulLoginAttempts;
        this.internalUserBlockedFromLoginUntil = internalUserBlockedFromLoginUntil;
        this.internalLastSuccesssulLogin = internalLastSuccesssulLogin;
        this.loginPassword = loginPassword;
        this.aobUser = aobUser;
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

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryIsoName() {
        return countryIsoName;
    }

    public void setCountryIsoName(String countryIsoName) {
        this.countryIsoName = countryIsoName;
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

    public long getDefaultPaymentMethodId() {
        return defaultPaymentMethodId;
    }

    public void setDefaultPaymentMethodId(long defaultPaymentMethodId) {
        this.defaultPaymentMethodId = defaultPaymentMethodId;
    }

    public boolean isAllowOffersPromotion() {
        return allowOffersPromotion;
    }

    public void setAllowOffersPromotion(boolean allowOffersPromotion) {
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

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public boolean isAobUser() {
        return aobUser;
    }

    public void setAobUser(boolean aobUser) {
        this.aobUser = aobUser;
    }

    @Override
    public String toString() {
        return "UserDetailsDto{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", loginName='" + loginName + '\'' +
                ", accountType=" + accountType +
                ", countryId=" + countryId +
                ", countryName='" + countryName + '\'' +
                ", countryIsoName='" + countryIsoName + '\'' +
                ", stateProvince='" + stateProvince + '\'' +
                ", cityName='" + cityName + '\'' +
                ", address='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", defaultPaymentMethodId=" + defaultPaymentMethodId +
                ", allowOffersPromotion=" + allowOffersPromotion +
                ", internalUnsuccessfulLoginAttempts=" + internalUnsuccessfulLoginAttempts +
                ", internalUserBlockedFromLoginUntil=" + internalUserBlockedFromLoginUntil +
                ", internalLastSuccesssulLogin=" + internalLastSuccesssulLogin +
                ", loginPassword='" + loginPassword + '\'' +
                ", aobUser=" + aobUser +
                '}';
    }
}
