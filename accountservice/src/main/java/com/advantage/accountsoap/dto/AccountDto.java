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
}
