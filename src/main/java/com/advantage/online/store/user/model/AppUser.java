package com.advantage.online.store.user.model;

import com.advantage.online.store.Constants;
import com.advantage.util.ArgumentValidationHelper;

import javax.persistence.*;

/**
 * @author Binyamin Regev on 15/11/2015.
 */
@Entity
//@Table(name = "AppUser")
@NamedQueries({
        @NamedQuery(
                name = AppUser.QUERY_GET_ALL,
                query = "select u from AppUser u"
        ),
        @NamedQuery(
                name = AppUser.QUERY_GET_USERS_BY_COUNTRY,
                query = "select u from AppUser u where " + AppUser.FIELD_COUNTRY + " = :" + AppUser.PARAM_COUNTRY
)
})
public class AppUser {

    public static final String QUERY_GET_ALL = "appUser.getAll";
    public static final String QUERY_GET_USERS_BY_COUNTRY = "appUser.getAppUsersByCountry";

    public static final String FIELD_ID = "USER_ID";

    public static final String FIELD_EMAIL = "EMAIL";
    public static final String PARAM_EMAIL = "PARAM_USER_EMAIL";

    public static final String FIELD_LOGIN = "LOGIN_NAME";
    public static final String PARAM_LOGIN = "PARAM_USER_LOGIN";

    public static final String FIELD_COUNTRY = "COUNTRY";
    public static final String PARAM_COUNTRY = "PARAM_USER_COUNTRY";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = FIELD_ID)
    private long id;

    @Column(name="LAST_NAME")
    private String lastName;

    @Column(name="FIRST_NAME")
    private String firstName;

    @Column(name= FIELD_LOGIN)
    private String loginName;

    private String password;

    @Column(name="USER_TYPE")
    private Integer appUserType;        //  by enum AppUserType

    @Column(name = FIELD_COUNTRY)
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

    @Column(name = FIELD_EMAIL)
    private String email;

    @Column(name="AGREE_TO_RECEIVE_OFFERS")
    private char agreeToReceiveOffersAndPromotions;  //   'Y' = Yes ; 'N' = No

    public AppUser(Integer appUserType, String lastName, String firstName, String loginName, String password, Integer country, String phoneNumber, String stateProvince, String cityName, String address1, String address2, String zipcode, String email, char agreeToReceiveOffersAndPromotions) {

        //  Validate Numeric Arguments
        ArgumentValidationHelper.validateArgumentIsNotNull(appUserType, "application user type");
        ArgumentValidationHelper.validateNumberArgumentIsPositive(appUserType, "application user type");
        ArgumentValidationHelper.validateNumberArgumentIsPositive(country, "country id");

        //  Validate String Arguments
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(lastName, "last name");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(firstName, "first name");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(loginName, "login name");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(password, "user password");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(phoneNumber, "phone number");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(stateProvince, "state/provice/region");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(cityName, "city name");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(address1, "address line 1");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(address2, "address line 2");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(zipcode, "zipcode");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(String.valueOf(agreeToReceiveOffersAndPromotions), "agree to receive offers and promotions");

        this.setAppUserType(appUserType);
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
        this.setAgreeToReceiveOffersAndPromotions(agreeToReceiveOffersAndPromotions);

    }

    public AppUser(AppUserType appUserType, String lastName, String firstName, String loginName, String password, Integer country, String phoneNumber, String stateProvince, String cityName, String address1, String address2, String zipcode, String email, char agreeToReceiveOffersAndPromotions) {
        this(appUserType.getAppUserTypeCode(), lastName, firstName, loginName, password, country, phoneNumber, stateProvince, cityName, address1, address2, zipcode, email, agreeToReceiveOffersAndPromotions);
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

    public char getAgreeToReceiveOffersAndPromotions() {
        return agreeToReceiveOffersAndPromotions;
    }

    public void setAgreeToReceiveOffersAndPromotions(char agreeToReceiveOffersAndPromotions) {
        this.agreeToReceiveOffersAndPromotions = agreeToReceiveOffersAndPromotions;
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
                "id=" + this.getId() + Constants.SPACE +
                "type=" + this.getAppUserType() + Constants.SPACE +
                "full name=\"" + this.getFirstName() + Constants.SPACE + this.getLastName() + "\" " +
                "login=\"" + this.getLoginName() + "\" " +
                "email=\"" + this.getEmail() + "\" " +
                "phone=\"" + this.getPhoneNumber() + "\" " +
                "country=" + this.getCountry() + Constants.SPACE +
                "state/province/region=\"" + this.getStateProvince() + "\" " +
                "city=\"" + this.getCityName() + "\" " +
                "address 1=\"" + this.getAddress1() + "\" " +
                "address 2=\"" + this.getAddress2() + "\" " +
                "postal code=" + this.getZipcode() + Constants.SPACE +
                "agree to receive offers and promotions=" + this.getAgreeToReceiveOffersAndPromotions();
    }

}
