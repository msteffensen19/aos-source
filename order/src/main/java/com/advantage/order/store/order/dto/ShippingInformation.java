package com.advantage.order.store.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Binyamin Regev on 07/01/2016.
 */
public class ShippingInformation {

    @JsonProperty("Shipping.Cost")
    private double shippingCost;

    @JsonProperty("Shipping.TrackingNumber")
    private long trackingNumber;    //  10 digits

    @JsonProperty("Shipping.Address.Address")
    private String address;         //  0-100 characters

    @JsonProperty("Shipping.Address.City")
    private String city;            //  City name

    @JsonProperty("Shipping.Address.PostalCode")
    private String postalCode;      //  0-10 digis

    @JsonProperty("Shipping.Address.State")
    private String state;           //  0-10 characters

    @JsonProperty("Shipping.Address.CountryCode")
    private String countryCode;     //  2 characters, by ISO3166

    public ShippingInformation() { }

    public ShippingInformation(String address, String city, String postalCode, String state, String countryCode) {
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.state = state;
        this.countryCode = countryCode;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShippingInformation that = (ShippingInformation) o;

        if (Double.compare(that.getShippingCost(), getShippingCost()) != 0) return false;
        if (!getAddress().equals(that.getAddress())) return false;
        if (!getCity().equals(that.getCity())) return false;
        if (!getPostalCode().equals(that.getPostalCode())) return false;
        if (getState() != null ? !getState().equals(that.getState()) : that.getState() != null) return false;
        return getCountryCode().equals(that.getCountryCode());

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getShippingCost());
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + getAddress().hashCode();
        result = 31 * result + getCity().hashCode();
        result = 31 * result + getPostalCode().hashCode();
        result = 31 * result + (getState() != null ? getState().hashCode() : 0);
        result = 31 * result + getCountryCode().hashCode();
        return result;
    }
}
