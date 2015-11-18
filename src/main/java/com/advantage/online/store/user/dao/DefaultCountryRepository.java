package com.advantage.online.store.user.dao;

import com.advantage.online.store.dao.AbstractRepository;
import com.advantage.online.store.user.model.Country;
import com.advantage.util.ArgumentValidationHelper;
import com.advantage.util.JPAQueryHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

/**
 * @author Binyamin Regev on 16/11/2015.
 */
@Component
@Qualifier("countryRepository")
@Repository
public class DefaultCountryRepository extends AbstractRepository implements CountryRepository {

    public static final int MAX_NUM_OF_COUNTRIES = 50;

//    @Autowired
//    protected PlatformTransactionManager transactionManager;
//    final TransactionDefinition transactionDefinition;

//    public DefaultCountryRepository() {
//        transactionDefinition = new DefaultTransactionDefinition();
//    }

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
    public int deleteCountriesByNames(Collection<String> names) {

    	ArgumentValidationHelper.validateCollectionArgumentIsNotNullAndNotEmpty(names,
    			                                                                "countries names");
    	final String hql = JPAQueryHelper.getDeleteByPkFieldsQuery(Country.class,
    			                                                   Country.FIELD_NAME,
    			                                                   Country.PARAM_COUNTRY_NAME);

        final Query query = entityManager.createQuery(hql);
        query.setParameter(Country.PARAM_COUNTRY_NAME, names);
        return query.executeUpdate();

    }

    @Override
    public int deleteCountriesByIsoName(Collection<String> isoNames) {

        ArgumentValidationHelper.validateCollectionArgumentIsNotNullAndNotEmpty(isoNames,
                "countries ISO names");

        final String hql = JPAQueryHelper.getDeleteByPkFieldsQuery(Country.class,
                                                                   Country.FIELD_ISO_NAME,
                                                                   Country.PARAM_ISO_NAME);

        final Query query = entityManager.createQuery(hql);
        query.setParameter(Country.PARAM_ISO_NAME, isoNames);
        return query.executeUpdate();

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

//    public void callCreateCountry() {
//        System.out.println("Create Country ISRAEL - String, String, int");
//
//        final TransactionStatus transactionStatusForCreation = transactionManager.getTransaction(transactionDefinition);
//
//        final Country country = this.createCountry("Israel", "il", 972);
//        transactionManager.commit(transactionStatusForCreation);
//
//        final TransactionStatus transactionStatusForDeletion = transactionManager.getTransaction(transactionDefinition);
//        this.deleteCountry(country);
//        transactionManager.commit(transactionStatusForDeletion);
//    }
//
//    public static void main(String[] args) {
//
//        DefaultCountryRepository defaultCountryRepository = new DefaultCountryRepository();
//
//        defaultCountryRepository.callCreateCountry();
//
//    }
}
