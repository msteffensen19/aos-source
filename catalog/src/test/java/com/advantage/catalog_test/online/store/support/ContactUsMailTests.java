package com.advantage.catalog_test.online.store.support;

import com.advantage.catalog.store.services.ContactSupportService;
import com.advantage.catalog_test.cfg.AdvantageTestContextConfiguration;
import com.advantage.common.dto.ContactUsMailRequest;
import com.advantage.common.dto.ContactUsResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Need to call method{@link ContactSupportService#sendMail}
 * with parameters to test {@code success} and {@code failure}.
 * @author Binyamin Regev on 02/02/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AdvantageTestContextConfiguration.class})
public class ContactUsMailTests {

    @Autowired
    private ContactSupportService contactSupportService;

    public ContactUsMailTests() {
    }

    @Test
    public void testSendContactUsMail_EmailAndTextOnly() {
        ContactUsMailRequest request = new ContactUsMailRequest("a@b.com", "I love your products");
        ContactUsResponse response = contactSupportService.sendMail(request);
        Assert.assertEquals(true, response.isSuccess());

        /*  Has to fail - invalid email address */
        request = new ContactUsMailRequest("a#d@b.com", "I love your products");
        response = contactSupportService.sendMail(request);
        Assert.assertEquals(false, response.isSuccess());
    }

    @Test
    public void testSendContactUsMail_CategoryEmailAndText() {
        ContactUsMailRequest request = new ContactUsMailRequest("a@b.com", 1L, 0L, "Cool products in this category.");
        ContactUsResponse response = contactSupportService.sendMail(request);
        Assert.assertEquals(true, response.isSuccess());

        /*  Has to fail - invalid email address */
        request = new ContactUsMailRequest("a@b.com", 1L, 0L, "Cool products in this category.");
        response = contactSupportService.sendMail(request);
        Assert.assertEquals(false, response.isSuccess());
    }

    @Test
    public void testSendContactUsMail_CategoryProductEmailAndText() {
        ContactUsMailRequest request = new ContactUsMailRequest("a@b.com", 1L, 1L, "Awesome product.");
        ContactUsResponse response = contactSupportService.sendMail(request);
        Assert.assertEquals(true, response.isSuccess());

        /*  Has to fail - invalid email address */
        request = new ContactUsMailRequest("a@b.com", 1L, 1L, "Awesome product.");
        response = contactSupportService.sendMail(request);
        Assert.assertEquals(false, response.isSuccess());
    }
}
