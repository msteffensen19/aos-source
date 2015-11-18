package com.advantage.online.store.user.dao;

import com.advantage.online.store.user.model.Country;

import java.util.Collection;
import java.util.List;

/**
 * @author Binyamin Regev on 16/11/2015.
 */
public interface CountryRepository {

    Country createCountry(String name, int phonePrefix);
    Country createCountry(String name, String isoName);
    Country createCountry(String name, String isoName, int phonePrefix);

    int deleteCountry(Country country);
    int deleteCountriesbyNames(Collection<String> names);
    int deleteCountriesbyIsoName(Collection<String> isoNames);

    List<Country> getAllCountries();
    List<Country> getCountriesByIsoNames(Collection<String> isoNames);
    List<Country> getCountriesByPartialName(String partialName);

}
