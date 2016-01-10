package com.advantage.accountsoap.model;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(
                name = ShippingAddress.QUERY_GET_ALL,
                query = "SELECT a FROM ShippingAddress a"
        )/*,
        @NamedQuery(
                name = ShippingAddress.QUERY_GET_BY_ACCOUNT_ID,
                query = "SELECT a FROM ShippingAddress a WHERE a.user_id =: " + Account.FIELD_ID
        )*/
})
public class ShippingAddress {
    public static final String QUERY_GET_ALL = "accountAddress.getAll";
    public static final String FIELD_ID = "id";
    public static final String QUERY_GET_BY_ACCOUNT_ID = "accountAddress.getByAccountId";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "address_line1")
    private String addressLine1;
    @Column(name = "address_line2")
    private String addressLine2;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = Account.FIELD_ID)
    private Account account;
    @Column
    private String city;
    @Column
    private String country;
    @Column
    private String state;
    @Column
    private String postalCode;

    public ShippingAddress() {
    }

    public ShippingAddress(String addressLine1,
                           String addressLine2,
                           Account account,
                           String city,
                           String country,
                           String state,
                           String postalCode) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.account = account;
        this.city = city;
        this.country = country;
        this.state = state;
        this.postalCode = postalCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
