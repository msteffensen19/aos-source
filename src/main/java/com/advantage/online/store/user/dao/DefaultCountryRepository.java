package com.advantage.online.store.user.dao;

import com.advantage.online.store.Constants;
import com.advantage.online.store.dao.AbstractRepository;
import com.advantage.online.store.user.dto.CountryResponseStatus;
import com.advantage.online.store.user.model.Country;
import com.advantage.util.ArgumentValidationHelper;
import com.advantage.util.JPAQueryHelper;
import com.advantage.util.fs.FileSystemHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.*;

/**
 * @author Binyamin Regev on 16/11/2015.
 */
@Component
@Qualifier("countryRepository")
@Repository
public class DefaultCountryRepository extends AbstractRepository implements CountryRepository {

    private CountryResponseStatus countryResponseStatus;

    @Override
    public Country createCountry(String name, int phonePrefix) {
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(name, "country name");
        ArgumentValidationHelper.validateNumberArgumentIsPositive(phonePrefix, "phone prefix");

        final Country country = new Country(name, phonePrefix);
        entityManager.persist(country);

        countryResponseStatus = new CountryResponseStatus(true, "New user created successfully.", country.getId());

        return country;
    }

    @Override
    public Country createCountry(String name, String isoName, int phonePrefix) {
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(name, "country name");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(isoName, "ISO name");
        ArgumentValidationHelper.validateNumberArgumentIsPositive(phonePrefix, "phone prefix");

        final Country country = new Country(name, isoName, phonePrefix);

        ArgumentValidationHelper.validateArgumentIsNotNull(country, "Country is null");

        entityManager.persist(country);

        return country;
    }

    @Override
    public CountryResponseStatus create(String name, int phonePrefix) {
        Country country = createCountry(name, phonePrefix);

        if (country == null) {
            //  Country was not created
            return countryResponseStatus;
        }

        return countryResponseStatus;
    }

    @Override
    public CountryResponseStatus create(String name, String isoName, int phonePrefix) {
        Country country = createCountry(name, isoName, phonePrefix);

        if (country == null) {
            //  Country was not created
            return countryResponseStatus;
        }

        return countryResponseStatus;
    }

    /**
     *  For testing the {@code CSV} file is here: <b>C:/Users/regevb/Downloads/countries_20150630.csv</b>.
     *  <b><ul>CSV File Format:</ul></b>
     *  <b>Column A</b> <ul>SKIP</ul>. Country name in Hebrew, need correct code-page.
     *  <b>Column B</b> Country name in English.
     *  <b>Column C</b> Country ISO name, the way it will be in URL address. e.g. &quot;www.gov.il&quot;
     *  <b>Column D</b> <ul>SKIP</ul>. Country international phone prefix. e.g. Israel's 972, Ukraine's 380, etc.
     *  @return number of rows inserted into Country table.
     */
    @Override
    public int fillCountryTable(final String csvFilePath) {

        //Country country;
        String cvsSplitBy = ",";    // use comma as separator

        List<String> countries = FileSystemHelper.readFileCsv("/Users/regevb/Downloads/countries_20150630.csv");
        //List<String> countries = Country.readFileCsv("/Users/regevb/Downloads/countries_20150630.csv");

        if (countries == null) {
            return 0;
        }

        for (String str : countries) {
            String[] substrings = str.split(cvsSplitBy);

            System.out.println("Country: " + substrings[1] +
                    Constants.SPACE + substrings[2] +
                    Constants.SPACE + substrings[3]);

            //country = createCountry(substrings[1], substrings[2], (int)Integer.valueOf(substrings[3]));
            createCountry(substrings[1], substrings[2], (int)Integer.valueOf(substrings[3]));


        }

        return countries.size();
    }

    @Override
    public int deleteCountry(Country country) {
        ArgumentValidationHelper.validateArgumentIsNotNull(country, "country");

        final Integer countryId= country.getId();

        final String hql = JPAQueryHelper.getDeleteByPkFieldQuery(Country.class,
                Country.FIELD_ID,
                countryId);
        final Query query = entityManager.createQuery(hql);

        return query.executeUpdate();
    }

    @Override
    public int deleteCountriesByIds(Collection<Integer> countryIds) {
        ArgumentValidationHelper.validateCollectionArgumentIsNotNullAndNotEmpty(countryIds, "countries IDs");

        for (Integer countryId: countryIds ) {
            final String hql = JPAQueryHelper.getDeleteByPkFieldQuery(Country.class,
                    Country.FIELD_ID,
                    countryId);

            Query query = entityManager.createQuery(hql);

            query.executeUpdate();
        }

        return 1;
    }

    @Override
    public int deleteCountries(Collection<Country> countries) {

        ArgumentValidationHelper.validateCollectionArgumentIsNotNullAndNotEmpty(countries,
                                                                                "countries list");

        final int countriesCount = countries.size();
        final Collection<Integer> countryIds = new ArrayList<Integer>(countriesCount);

        for (final Country country : countries) {
            final Integer countryId = country.getId();
            countryIds.add(countryId);
        }

        return deleteCountriesByIds(countryIds);

    }

    @Override
    public int deleteCountriesByNames(Collection<String> names) {

    	ArgumentValidationHelper.validateCollectionArgumentIsNotNullAndNotEmpty(names,
    			                                                                "countries names");

        final int namesCount = names.size();
        final Collection<Integer> countryIds = new ArrayList<Integer>(namesCount);

        for (final String name : names) {

            final Integer countryId = this.getCountryIdByName(name);
            countryIds.add(countryId);
        }

        return deleteCountriesByIds(countryIds);

    }

    @Override
    public Integer getCountryIdByName(String countryName) {
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(countryName, "country name");

        final Query query = entityManager.createNamedQuery(Country.QUERY_GET_BY_COUNTRY_NAME);

        query.setParameter(Country.PARAM_COUNTRY_NAME, countryName);

        @SuppressWarnings("unchecked")

        List<Country> countries = query.getResultList();

        final Integer countryId;

        if (countries.isEmpty()) {

            countryId = -1;
        } else {

            countryId = countries.get(0).getId();
        }

        return countryId;
    }

    @Override
    public List<Country> getAllCountries() {

        List<Country> countries = entityManager.createNamedQuery(Country.QUERY_GET_ALL, Country.class)
                .setMaxResults(Country.MAX_NUM_OF_COUNTRIES)
                .getResultList();

        return countries.isEmpty() ? null : countries;
    }

    /**
     * Retrieve all countries with name that starts with {@code partialName} and make
     * sure {@code partialName} ends with <i>PERCENT SIGN</i>. <br/>
     * @param partialName Partial county name to match.
     * @return {@link List} or countries ({@link Country} class) with names starting
     * with {@code partialName}.
     */
    @Override
    public List<Country> getCountriesByPartialName(String partialName) {
        StringBuilder partialCountryName = new StringBuilder(partialName);

        //  if partial country name does not end with PERCENT-SIGN then add it. It's MANDATORY for QUERY with "LIKE" keyword.
        if (!partialName.endsWith(String.valueOf('%'))) {
            partialCountryName.append('%');
        }

        List<Country> countries = entityManager.createNamedQuery(Country.QUERY_GET_COUNTRIES_BY_PARTIAL_NAME, Country.class)
                .setParameter(Country.PARAM_COUNTRY_NAME, partialCountryName.toString())
                .setMaxResults(Country.MAX_NUM_OF_COUNTRIES)
                .getResultList();

        return countries.isEmpty() ? null : countries;
    }

    @Override
    public List<Country> getCountriesByPhonePrefix(int phonePrefix) {
        List<Country> countries = entityManager.createNamedQuery(Country.QUERY_GET_COUNTRIES_BY_PHONE_PREFIX, Country.class)
                .setParameter(Country.PARAM_PHONE_PREFIX, phonePrefix)
                .setMaxResults(Country.MAX_NUM_OF_COUNTRIES)
                .getResultList();

        return countries.isEmpty() ? null : countries;
    }

}
