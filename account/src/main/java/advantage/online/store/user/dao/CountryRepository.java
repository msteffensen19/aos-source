package advantage.online.store.user.dao;

import com.advantage.online.store.user.dto.CountryResponseStatus;
import com.advantage.online.store.user.model.Country;

import java.util.Collection;
import java.util.List;

/**
 * @author Binyamin Regev on 16/11/2015.
 */
public interface CountryRepository {

    Country createCountry(String name, int phonePrefix);

    Country createCountry(String name, String isoName, int phonePrefix);

    //  For Country-Management API
    CountryResponseStatus create(String name, int phonePrefix);

    CountryResponseStatus create(String name, String isoName, int phonePrefix);

    int fillCountryTable(final String csvFilePath);

    int deleteCountriesByIds(Collection<Integer> countryIds);

    int deleteCountriesByNames(Collection<String> names);

    Integer getCountryIdByName(String countryName);

    List<Country> getAllCountries();

    List<Country> getCountriesByPartialName(String partialName);

    List<Country> getCountriesByPhonePrefix(int phonePrefix);

}
