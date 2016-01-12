package com.advantage.accountsoap.dao;

import com.advantage.accountsoap.dto.CountryStatusResponse;
import com.advantage.common.dao.DefaultCRUDOperations;
import com.advantage.common.dto.CountryResponseDto;
import com.advantage.accountsoap.model.Country;

import java.util.Collection;
import java.util.List;

/**
 * @author Binyamin Regev on 16/11/2015.
 */
public interface CountryRepository extends DefaultCRUDOperations<Country> {

    Country createCountry(String name, int phonePrefix);

    Country createCountry(String name, String isoName, int phonePrefix);

    //  For Country-Management API
    CountryStatusResponse create(String name, int phonePrefix);

    CountryStatusResponse create(String name, String isoName, int phonePrefix);

    int fillCountryTable(final String csvFilePath);

    int deleteCountriesByIds(Collection<Long> countryIds);

    int deleteCountriesByNames(Collection<String> names);

    long getCountryIdByName(String countryName);

    List<Country> getAllCountries();

    List<Country> getCountriesByPartialName(String partialName);

    List<Country> getCountriesByPhonePrefix(int phonePrefix);

}
