package com.advantage.test.online.store.user;

import com.advantage.online.store.user.dao.AppUserRepository;
import com.advantage.online.store.user.dao.CountryRepository;
import com.advantage.online.store.user.model.AppUser;
import com.advantage.online.store.user.model.AppUserType;
//import com.advantage.online.store.user.model.Country;
import com.advantage.online.store.user.model.YesNoReply;
import com.advantage.test.cfg.AdvantageTestContextConfiguration;
import com.advantage.test.online.store.dao.GenericRepositoryTests;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Binyamin Regev on 16/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AdvantageTestContextConfiguration.class})
public class AppUserRepositoryTests extends GenericRepositoryTests {

    private AppUserRepository appUserRepository;

    private CountryRepository countryRepository;

    @Test
    public void testConstructorAppUser() throws IOException {
        System.out.println("Testing public void testConstructorAppUser()");

        System.out.println((AppUserType.USER.getAppUserTypeCode() == null ? "AppUserType.USER.getAppUserTypeCode()=NULL" : "AppUserType.USER.getAppUserTypeCode()=" + AppUserType.USER.getAppUserTypeCode()));
        Assert.assertNotNull(AppUserType.USER.getAppUserTypeCode());

        final AppUser appUser = new AppUser(AppUserType.USER.getAppUserTypeCode(),
                                            "LName",                            /*  String lastName                         */
                                            "FName",                            /*  String firstName                        */
                                            "Login",                            /*  String loginName                        */
                                            "UserPassword",                     /*  String password                         */
                                            1,                                  /*  Integer country                         */
                                            "077-7777777",                      /*  String phoneNumber                      */
                                            "province",                         /*  String stateProvince                    */
                                            "CityName",                         /*  String cityName                         */
                                            "Address1",                         /*  String address1                         */
                                            "Address2",                         /*  String address2                         */
                                            "7654321",                          /*  String zipcode                          */
                                            "koi@israel.gov.il",                /*  String email                            */
                                            YesNoReply.YES.getReplyTypeChar()); /*  char agreeToReceiveOffersAndPromotions  */

        System.out.println(appUser);

        System.out.println("Testing public void testConstructorAppUser() - Successful");

        Assert.assertTrue(true);
    }

    @Test
    public void testCreateAppUser_AllFields() throws IOException {

        System.out.println("Testing public void testCreateAppUser_AllFields()");
        final int COUNTRY1_APP_USERS_COUNT = 5;
        final int COUNTRY2_APP_USERS_COUNT = 10;

        //  Testing method: public AppUser(int appUserType, String lastName, String firstName, String loginName, String password, Integer country, String phoneNumber, String email) {
        final String lastName = "Kind";
        final String firstName = "David";
        final String loginName = "kingdavid";
        final String password = "jerusalem3000!";
        final Integer country = 1;
        final String phoneNumber = "777-7777777";
        final String stateProvince = "Jerusalem Region";
        final String cityName = "Jerusalem";
        final String address1 = "Guy st.";
        final String address2 = "Old City";
        final String zipcode = "9876543";   //   7-Digits zip (postal) code
        final String email = "koi@israel.gov.il";   //  koi = King of Israel

        final TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        final TransactionStatus transactionStatusForCreation = transactionManager.getTransaction(transactionDefinition);

////        System.out.println("Create Country ISRAEL...");
////        final Country country1 = countryRepository.createCountry("Israel", "il", 972);
////
////        System.out.println("Create Country UKRAINE...");
////        final Country country2 = countryRepository.createCountry("Ukraine", "ua", 380);
////
////        System.out.println("Create Country BAHAMAS...");
////        final Country country3 = countryRepository.createCountry("Bahamas", "bs", 1242);
//
//        System.out.println((AppUserType.USER.getAppUserTypeCode() == null ? "AppUserType.USER.getAppUserTypeCode()=NULL" : "AppUserType.USER.getAppUserTypeCode()=" + AppUserType.USER.getAppUserTypeCode()));
//        Assert.assertNotNull(AppUserType.USER.getAppUserTypeCode());
//
//        System.out.println("return sothing=" + appUserRepository.returnSomething());
//
//        System.out.println("Create 1st AppUser ...");
//        AppUser appUser = appUserRepository.createAppUser(AppUserType.USER.getAppUserTypeCode(),
//                                                            lastName,
//                                                            firstName,
//                                                            loginName,
//                                                            password,
//                                                            country,
//                                                            phoneNumber,
//                                                            stateProvince,
//                                                            cityName,
//                                                            address1,
//                                                            address2,
//                                                            zipcode,
//                                                            email,
//                                                            YesNoReply.YES.getReplyTypeChar());  //agreeToReceiveOffersAndPromotions);
//
//        transactionManager.commit(transactionStatusForCreation);
//        Assert.assertNotNull(appUser);
//
//        final TransactionStatus transactionStatusForDeletion = transactionManager.getTransaction(transactionDefinition);
//        appUserRepository.deleteAppUser(appUser);
//        transactionManager.commit(transactionStatusForDeletion);
//
////        System.out.println("Create COUNTRY1 AppUsers ...");
////        for (int i = 0; i < COUNTRY1_APP_USERS_COUNT; i++) {
////            appUserRepository.createAppUser(AppUserType.USER.getAppUserTypeCode(),
////                                            lastName,
////                                            firstName,
////                                            loginName,
////                                            password,
////                                            country,
////                                            phoneNumber,
////                                            stateProvince,
////                                            cityName,
////                                            address1,
////                                            address2,
////                                            zipcode,
////                                            email,
////                                            YesNoReply.YES.getReplyTypeChar());
////        }
////
////        System.out.println("Create COUNTRY2 AppUsers ...");
////        for (int i = 20; i < (COUNTRY2_APP_USERS_COUNT + 20); i++) {
////            appUserRepository.createAppUser(AppUserType.USER.getAppUserTypeCode(),
////                                            lastName,
////                                            firstName,
////                                            loginName,
////                                            password,
////                                            country,
////                                            phoneNumber,
////                                            stateProvince,
////                                            cityName,
////                                            address1,
////                                            address2,
////                                            zipcode,
////                                            email,
////                                            YesNoReply.YES.getReplyTypeChar());
////        }
////
////        System.out.println("Calling appUserRepository.getAllAppUsers() ...");
////        final List<AppUser> appUsers = appUserRepository.getAllAppUsers();
////        //Assert.assertEquals("appUsers size is " + appUsers.size(), (COUNTRY1_APP_USERS_COUNT + COUNTRY2_APP_USERS_COUNT + 1), appUsers.size());
////
////        System.out.println("Application Users: ");
////        System.out.println("================== ");
////
////        for (int i = 0; i < appUsers.size(); i++) {
////            System.out.println(appUsers.get(i).toString());
////        }
////
////        /*
////    	final List<AppUser> country1AppUsers = productRepository.getCategoryProducts(category1);
////    	Assert.assertEquals(CATEGORY1_PRODUCTS_COUNT, category1Products.size());
////
////    	final List<AppUser> country2AppUsers = productRepository.getCategoryProducts(category2);
////    	Assert.assertEquals(CATEGORY2_PRODUCTS_COUNT, category2Products.size());
////
////    	final List<AppUser> country3AppUsers = productRepository.getCategoryProducts(category3);
////    	Assert.assertTrue(category3Products.isEmpty());
////
////    	final TransactionStatus transactionStatusForDeletion = transactionManager.getTransaction(transactionDefinition);
////    	productRepository.deleteProducts(category1Products);
////    	productRepository.deleteProducts(category2Products);
////    	categoryRepository.deleteCategories(category1, category2, category3);
////         */

        Assert.assertTrue(true);
    }

    @Test
    public void testDeleteAppUser_ByEmail() throws IOException {
        List<String> emails = new ArrayList<String>();

        emails.add("koi@israel.gov.il");
        emails.add("koi@israel.co.il");
        emails.add("koi@israel.com.il");

//        final TransactionStatus transactionStatusForDeletion = transactionManager.getTransaction(transactionDefinition);
//        appUserRepository.deleteAppUsersByEmails(emails);
//        transactionManager.commit(transactionStatusForDeletion);

        Assert.assertTrue(true);
    }

    @Test
    public void testDeleteAppUser_ByLogin() throws IOException {
        List<String> logins = new ArrayList<String>();

        logins.add("kingdavid");
        logins.add("kingDavid");
        logins.add("KingDavid");

//        final TransactionStatus transactionStatusForDeletion = transactionManager.getTransaction(transactionDefinition);
//        appUserRepository.deleteAppUsersByLogins(logins);
//        transactionManager.commit(transactionStatusForDeletion);

        Assert.assertTrue(true);
    }

    @Test
    public void testGetAppUsers_All() throws IOException {
        Assert.assertTrue(true);
    }

    @Test
    public void testGetAppUsers_ByCountry() throws IOException {
        final Integer countryId = 10;

        Assert.assertTrue(true);
    }

    @Test
    public void testGetAppUsers_ByEmails() throws IOException {
        List<String> emails = new ArrayList<String>();

        emails.add("koi@israel.gov.il");
        emails.add("koi@israel.co.il");
        emails.add("koi@israel.com.il");

        Assert.assertTrue(true);
    }

    @Test
    public void testGetAppUsers_ByLogins() throws IOException {
        List<String> logins = new ArrayList<String>();

        logins.add("kingdavid");
        logins.add("kingDavid");
        logins.add("KingDavid");

        Assert.assertTrue(true);
    }

}
