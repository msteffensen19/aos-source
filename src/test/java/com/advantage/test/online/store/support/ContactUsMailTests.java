package com.advantage.test.online.store.support;

import com.advantage.test.cfg.AdvantageTestContextConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.advantage.online.store.support.api.ContactUsController;

/**
 * Need to call {@link ContactUsController} {@link URL} of method {@link #sendMail}
 * with parameters to test {@code success} and {@code failure}.
 * @author Binyamin Regev on 13/12/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AdvantageTestContextConfiguration.class})
public class ContactUsMailTests {

    private ContactUsController ContactUsController = new ContactUsController();

    public ContactUsMailTests() {
    }

    @Test
    public void testSendContactUsMail() {
        Assert.assertEquals(true, true);
    }

}
