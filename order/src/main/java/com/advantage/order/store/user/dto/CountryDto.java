package com.advantage.order.store.user.dto;

import com.advantage.order.store.user.model.Country;
import com.advantage.order.util.ArgumentValidationHelper;

/**
 * Created by regevb on 29/11/2015.
 */
public class CountryDto {
    private Integer countryId;
    private String name;
    private String isoName;
    private int phonePrefix;

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsoName() {
        return isoName;
    }

    public void setIsoName(String isoName) {
        this.isoName = isoName;
    }

    public int getPhonePrefix() {
        return phonePrefix;
    }

    public void setPhonePrefix(int phonePrefix) {
        this.phonePrefix = phonePrefix;
    }

    public void applyCountry(final Country country) {
        ArgumentValidationHelper.validateArgumentIsNotNull(country, "country");

        this.setCountryId(country.getId());
        this.setName(country.getName());
        this.setIsoName(country.getIsoName());
        this.setPhonePrefix(country.getPhonePrefix());
    }


}
