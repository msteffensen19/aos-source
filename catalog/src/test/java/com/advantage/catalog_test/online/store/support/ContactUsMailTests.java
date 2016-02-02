package com.advantage.catalog_test.online.store.support;

import com.advantage.catalog.store.services.ContactUsMailService;
import com.advantage.catalog_test.cfg.AdvantageTestContextConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Need to call method{@link ContactUsMailService#sendMail}
 * with parameters to test {@code success} and {@code failure}.
 * @author Binyamin Regev on 02/02/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AdvantageTestContextConfiguration.class})
public class ContactUsMailTests {

    private ContactUsMailService contactUsMailService = new ContactUsMailService();

    public ContactUsMailTests() {
    }

    @Test
    public void testSendContactUsMail_EmailAndTextOnly() {
        Assert.assertEquals(true, true);
    }

    @Test
    public void testSendContactUsMail_CategoryEmailAndText() {
        Assert.assertEquals(true, true);
    }

    @Test
    public void testSendContactUsMail_CategoryProductEmailAndText() {
        Assert.assertEquals(true, true);
    }

}
