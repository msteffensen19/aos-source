package com.advantage.online.store.user.model;

import com.advantage.online.store.Constants;

import javax.persistence.*;

/**
 * @author Binyamin Regev on 15/11/2015.
 */
@Entity
@Table(name = "APP_USER")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID")
    private long id;

    @Column(name="LAST_NAME")
    private String lastName;

    @Column(name="FIRST_NAME")
    private String firstName;

    @Column(name="LOGIN_NAME")
    private String loginName;

    private String password;

    @Column(name="USER_TYPE")
    private int appUserType;        //  by enum AppUserType

    private Integer country;                //  by Country

    @Column(name="STATE_PROVINCE")
    private String stateProvince;

    @Column(name="CITY_NAME")
    private String cityName;

    private String address1;

    private String address2;

    private String zipcode;

    @Column(name="PHONE_NUMBER")
    private String phoneNumber;

    private String email;

    public AppUser(int appUserType, String lastName, String firstName, String loginName, String password, Integer country, String phoneNumber, String email) {
        this.appUserType = appUserType;
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setLoginName(loginName);
        this.setPassword(password);
        this.setCountry(country);
        this.setPhoneNumber(phoneNumber);
        this.setEmail(email);
    }

    public AppUser(AppUserType appUserType, String lastName, String firstName, String loginName, String password, Integer country, String phoneNumber, String email) {
        this(appUserType.getAppUserTypeCode(), lastName, firstName, loginName, password, country, phoneNumber, email);
    }

    public AppUser(int appUserType, String lastName, String firstName, String loginName, String password, Integer country, String phoneNumber, String stateProvince, String cityName, String address1, String address2, String zipcode, String email) {
        this.appUserType = appUserType;
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setLoginName(loginName);
        this.setPassword(password);
        this.setCountry(country);
        this.setPhoneNumber(phoneNumber);
        this.setStateProvince(stateProvince);
        this.setCityName(cityName);
        this.setAddress1(address1);
        this.setAddress2(address2);
        this.setZipcode(zipcode);
        this.setEmail(email);
    }

    public AppUser(AppUserType appUserType, String lastName, String firstName, String loginName, String password, Integer country, String phoneNumber, String stateProvince, String cityName, String address1, String address2, String zipcode, String email) {
        this(appUserType.getAppUserTypeCode(), lastName, firstName, loginName, password, country, phoneNumber, stateProvince, cityName, address1, address2, zipcode, email);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAppUserType() {
        return appUserType;
    }

    public void setAppUserType(Integer appUserType) {
        this.appUserType = appUserType;
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

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
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

    @Override
    public boolean equals(Object obj) {
        AppUser compareTo = (AppUser) obj;

        return (
                    (this.getAppUserType() == compareTo.getAppUserType()) &&
                    (this.getFirstName() == compareTo.getFirstName()) &&
                    (this.getLastName() == compareTo.getLastName()) &&
                    (this.getLoginName() == compareTo.getLoginName()) &&
                    (this.getCountry() == compareTo.getCountry()) &&
                    (this.getStateProvince() == compareTo.getStateProvince()) &&
                    (this.getCityName() == compareTo.getCityName()) &&
                    (this.getAddress1() == compareTo.getAddress1()) &&
                    (this.getAddress2() == compareTo.getAddress2()) &&
                    (this.getZipcode() == compareTo.getZipcode()) &&
                    (this.getPhoneNumber() == compareTo.getPhoneNumber()) &&
                    (this.getEmail() == compareTo.getEmail())
                );
    }

    @Override
    public String toString() {
        return "User Information: " +
                "id=" + this.getId() + 
                "type=" + this.getAppUserType() +
                "full name=\"" + this.getFirstName() + Constants.SPACE + this.getLastName() + "\" " +
                "login=\"" + this.getLoginName() + "\" " +
                "email=\"" + this.getEmail() + "\" " +
                "phone=\"" + this.getPhoneNumber() + "\" " +
                "country=\"" + this.getCountry() + "\" " +
                "state/province/region=\"" + this.getStateProvince() + "\" " +
                "city=\"" + this.getCityName() + "\" " +
                "address 1=\"" + this.getAddress1() + "\" " +
                "address 2=\"" + this.getAddress2() + "\" " +
                "postal code=" + this.getZipcode();
    }
}
