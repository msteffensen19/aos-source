package com.advantage.accountsoap.dao;

import com.advantage.accountsoap.dto.CountryResponse;
import com.advantage.accountsoap.model.Country;

import java.util.Collection;
import java.util.List;

/**
 * @author Binyamin Regev on 16/11/2015.
 */
public interface CountryRepository {

    Country createCountry(String name, int phonePrefix);

    Country createCountry(String name, String isoName, int phonePrefix);

    //  For Country-Management API
    CountryResponse create(String name, int phonePrefix);

    CountryResponse create(String name, String isoName, int phonePrefix);

    int fillCountryTable(final String csvFilePath);

    int deleteCountriesByIds(Collection<Integer> countryIds);

    int deleteCountriesByNames(Collection<String> names);

    Integer getCountryIdByName(String countryName);

    List<Country> getAllCountries();

    List<Country> getCountriesByPartialName(String partialName);

    List<Country> getCountriesByPhonePrefix(int phonePrefix);

}
