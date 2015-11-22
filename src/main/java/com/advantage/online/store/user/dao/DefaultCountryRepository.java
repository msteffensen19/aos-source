package com.advantage.online.store.user.dao;

import com.advantage.online.store.dao.AbstractRepository;
import com.advantage.online.store.user.model.Country;
import com.advantage.util.ArgumentValidationHelper;
import com.advantage.util.JPAQueryHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Binyamin Regev on 16/11/2015.
 */
@Component
@Qualifier("countryRepository")
@Repository
public class DefaultCountryRepository extends AbstractRepository implements CountryRepository {

    public static final int MAX_NUM_OF_COUNTRIES = 50;

    @Override
    public Country createCountry(String name, int phonePrefix) {
        final Country country = new Country(name, phonePrefix);
        entityManager.persist(country);
        return country;
    }

    @Override
    public Country createCountry(String name, String isoName) {
        final Country country = new Country(name, isoName);
        entityManager.persist(country);
        return country;
    }

    @Override
    public Country createCountry(String name, String isoName, int phonePrefix) {
        final Country country = new Country(name, isoName, phonePrefix);

        ArgumentValidationHelper.validateArgumentIsNotNull(country, "Country is null");

        entityManager.persist(country);
        //entityManager.persist(new Country(name, isoName, phonePrefix));

        return country;
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

    public int deleteCountriesByIds(Collection<Integer> countryIds) {
        return 0;
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
                .setMaxResults(MAX_NUM_OF_COUNTRIES)
                .getResultList();

        return countries.isEmpty() ? null : countries;
    }

    @Override
    public List<Country> getCountriesByIsoNames(Collection<String> isoNames) {

        return null;
    }

    @Override
    public List<Country> getCountriesByPartialName(String partialName) {

        return null;
    }

}
