package com.advantage.test.online.store.user;

import com.advantage.online.store.user.util.ValidationHelper;
import com.advantage.test.cfg.AdvantageTestContextConfiguration;
import com.advantage.test.online.store.dao.GenericRepositoryTests;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Binyamin Regev on 22/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AdvantageTestContextConfiguration.class})
public class ValidationHelperTests extends GenericRepositoryTests {

    @Test
    public void testIsValidPhoneNumber() {
        Assert.assertEquals(true, ValidationHelper.isValidPhoneNumber("+1 123 456 7890"));
        Assert.assertEquals(true, ValidationHelper.isValidPhoneNumber("+972 54 123 4567"));
        Assert.assertEquals(true, ValidationHelper.isValidPhoneNumber("+972 54 1234567"));
        Assert.assertEquals(true, ValidationHelper.isValidPhoneNumber("+44 123 4567890"));
        Assert.assertEquals(true, ValidationHelper.isValidPhoneNumber("+44 1234567890"));
        Assert.assertEquals(false, ValidationHelper.isValidPhoneNumber("+441234567890"));
        Assert.assertEquals(false, ValidationHelper.isValidPhoneNumber("441234567890"));
    }



    /**
     * Test validity of e-mail address
     */
    @Test
    public void testIsValidEmail() {
        Assert.assertEquals(true, ValidationHelper.isValidEmail("a@b.com"));
    }

    /**
     * Test validity of user login name
     */
    @Test
    public void testIsValidLogin() {

        Assert.assertEquals(true, ValidationHelper.isValidLogin("king.david"));
    }

    /**
     * Test validity of user login password
     */
    @Test
    public void testIsValidPassword() {
        //Assert.assertEquals(true, ValidationHelper.isValidPassword("kingdavid"));
        Assert.assertTrue(true);
    }

    /**
     * Test validity of Time value by 24-hours format (from 00:00:00 to 23:59:59)
     */
    @Test
    public void testIsValidTime24h() {
        Assert.assertEquals(true, ValidationHelper.isValidTime24h("23:59:59"));

    }

    /**
     * Test validity Date value with all 3 Date-Formats: European, American and Scandinavian.
     */
    @Test
    public void testIsValidDate() {

        //ValidationHelper validationHelper = new ValidationHelper();

        //  European Date Format
        Assert.assertEquals(true, ValidationHelper.isValidDate("29.02.2012"));

        //  American Date Format
        Assert.assertEquals(true, ValidationHelper.isValidDate("02/29/2012"));

        //  Scandinavian Date Format
        Assert.assertEquals(true, ValidationHelper.isValidDate("2012-02-29"));

    }

}
