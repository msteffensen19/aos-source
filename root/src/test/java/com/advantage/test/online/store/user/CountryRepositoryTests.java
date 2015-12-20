package com.advantage.test.online.store.user;

import com.advantage.online.store.user.dao.CountryRepository;
import com.advantage.online.store.user.model.Country;
import com.advantage.test.cfg.AdvantageTestContextConfiguration;
import com.advantage.test.online.store.dao.GenericRepositoryTests;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Binyamin Regev on 16/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AdvantageTestContextConfiguration.class})
public class CountryRepositoryTests extends GenericRepositoryTests {

    private final String name = "Israel";
    private final String isoName = "il";
    private final int phonePrefix = 972;

    @Autowired
    @Qualifier("countryRepository")
    CountryRepository countryRepository;

    @Test
    public void testCreateCountry_CountryAndPhonePrefix() throws IOException {
        final String name = "Israel";
        final int phonePrefix = 972;

        final TransactionStatus transactionStatusForCreation = transactionManager.getTransaction(transactionDefinition);
        final Country country = countryRepository.createCountry(name, phonePrefix);
        transactionManager.commit(transactionStatusForCreation);

        Assert.assertNotNull(country);

        //final TransactionStatus transactionStatusForDeletion = transactionManager.getTransaction(transactionDefinition);
        //countryRepository.deleteCountry(country);
        //transactionManager.commit(transactionStatusForDeletion);
        //Assert.assertTrue(true);

    }

    @Test
    public void testCreateCountry_AllFields() throws IOException {
        final TransactionStatus transactionStatusForCreation = transactionManager.getTransaction(transactionDefinition);
        Country country = countryRepository.createCountry(name, isoName, phonePrefix);
        transactionManager.commit(transactionStatusForCreation);

        Assert.assertNotNull(country);

        //final TransactionStatus transactionStatusForDeletion = transactionManager.getTransaction(transactionDefinition);
        //countryRepository.deleteCountry(country);
        //transactionManager.commit(transactionStatusForDeletion);
        //Assert.assertTrue(true);

    }

    @Test
    public void testGetCountryIdByCountryName() {
        final TransactionStatus transactionStatusForCreation = transactionManager.getTransaction(transactionDefinition);
        Country country = new Country();

        country = countryRepository.createCountry("Austria", "at", 43);
        country = countryRepository.createCountry("Australia", "au", 61);
        country = countryRepository.createCountry("Cayman Islands", "ky", 1345);
        country = countryRepository.createCountry("Bahamas", "bs", 1242);
        country = countryRepository.createCountry("Uruguay", "uy", 598);
        country = countryRepository.createCountry("Solomon Islands", "sb", 677);
        country = countryRepository.createCountry("Falkland Islands", "fk", 500);
        country = countryRepository.createCountry("Ukraine", "ua", 380);
        country = countryRepository.createCountry("Cook Islands", "ck", 682);
        country = countryRepository.createCountry("Israel", "il", 972);
        country = countryRepository.createCountry("Canada", "ca", 1);
        country = countryRepository.createCountry("Russia", "ru", 7);
        country = countryRepository.createCountry("United Kingdom", "uk", 44);
        country = countryRepository.createCountry("United States", "us", 1);
        country = countryRepository.createCountry("Iceland", "is", 354);
        country = countryRepository.createCountry("Uzbekistan", "uz", 998);

        transactionManager.commit(transactionStatusForCreation);

        final String countryName = "Israel";
        final Integer countryId = countryRepository.getCountryIdByName(countryName);
        System.out.println("The Country-ID of " + countryName + " is " + countryId);

        Assert.assertTrue("[CountryId] is Negative or 0", countryId > 0);

        final List<Country> countries = countryRepository.getAllCountries();

        final Collection<Integer> countryIds = new ArrayList<Integer>(countries.size());

        for (final Country c : countries) {
            final Integer id = c.getId();
            countryIds.add(id);
        }

    }
}
