package com.advantage.test.online.store.user;

import com.advantage.online.store.Constants;
import com.advantage.online.store.user.dao.CountryRepository;
import com.advantage.online.store.user.dao.DefaultCountryRepository;
import com.advantage.online.store.user.model.Country;
import com.advantage.test.cfg.AdvantageTestContextConfiguration;
import com.advantage.test.online.store.dao.GenericRepositoryTests;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;

import java.io.IOException;

/**
 * @author Binyamin Regev on 16/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AdvantageTestContextConfiguration.class})
public class CountryRepositoryTests extends GenericRepositoryTests {

    private final String name = "Israel";

    @Autowired
    CountryRepository countryRepository;

    /**
     * Test {@link Country} class constructor <@code Country(Sting, String)}
     * @throws IOException
     */
//    @Test
//    public void testConstructorCountry_CountryAndIsoNames() throws IOException {
//
//        System.out.println("Testing public void testConstructorCountry_CountryAndIsoNames()");
//        String isoName = "il";
//        final Country country = new Country(name, isoName);
//        Assert.assertNotNull(country);
//        System.out.println(country);
//
//        System.out.println(Constants.SPACE);
//
//        Assert.assertTrue(true);
//
//    }
//
//    /**
//     * Test {@link Country} class constructor <@code Country(Sting, int)}
//     * @throws IOException
//     */
//    @Test
//    public void testConstructorCountry_CountryAndPhonePrefix() throws IOException {
//
//        System.out.println("Testing public void testConstructorCountry_CountryAndPhonePrefix()");
//        int phonePrefix = 972;
//        final Country country = new Country(name, phonePrefix);
//        Assert.assertNotNull(country);
//        System.out.println(country);
//
//        System.out.println(Constants.SPACE);
//
//        Assert.assertTrue(true);
//
//    }
//
//    /**
//     * Test {@link Country} class constructor <@code Country(Sting, String, int)}
//     * @throws IOException
//     */
//    @Test
//    public void testConstructorCountry_AllFields() throws IOException {
//
//        System.out.println("Testing public void testConstructorCountry_AllFields()");
//        final Country country = new Country("Israel", "il", 972);
//        Assert.assertNotNull(country);
//        System.out.println(country);
//
//        System.out.println(Constants.SPACE);
//
//        Assert.assertTrue(true);
//
//    }

//    @Test
//    public void testCreateCountry_CountryAndIsoNames() throws IOException {
//
//        System.out.println("Create Country ISRAEL - Str1ing, String");
//        final TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
//        final TransactionStatus transactionStatusForCreation = transactionManager.getTransaction(transactionDefinition);
//
//        final Country country = countryRepository.createCountry("Israel", "il");
//        transactionManager.commit(transactionStatusForCreation);
//        Assert.assertNotNull(country);
//
//        final TransactionStatus transactionStatusForDeletion = transactionManager.getTransaction(transactionDefinition);
//        countryRepository.deleteCountry(country);
//        transactionManager.commit(transactionStatusForDeletion);
//
//        Assert.assertTrue(true);
//
//    }

//    @Test
//    public void testCreateCountry_CountryAndPhonePrefix() throws IOException {
//
//        System.out.println("Create Country ISRAEL - String, int");
//        final TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
//        final TransactionStatus transactionStatusForCreation = transactionManager.getTransaction(transactionDefinition);
//
//        final Country country = countryRepository.createCountry("Israel", 972);
//        transactionManager.commit(transactionStatusForCreation);
//        Assert.assertNotNull(country);
//
//        final TransactionStatus transactionStatusForDeletion = transactionManager.getTransaction(transactionDefinition);
//        countryRepository.deleteCountry(country);
//        transactionManager.commit(transactionStatusForDeletion);
//
//        Assert.assertTrue(true);
//
//    }

    @Test
    public void testCreateCountry_AllFields() throws IOException {

        System.out.println("Create Country ISRAEL - String, String, int");

        final TransactionStatus transactionStatusForCreation = transactionManager.getTransaction(transactionDefinition);

        Country country = countryRepository.createCountry("Israel", "il", 972);
        transactionManager.commit(transactionStatusForCreation);
        Assert.assertNotNull(country);

        final TransactionStatus transactionStatusForDeletion = transactionManager.getTransaction(transactionDefinition);
        countryRepository.deleteCountry(country);
        transactionManager.commit(transactionStatusForDeletion);

        Assert.assertTrue(true);

    }
}
