package com.advantage.account.test.online.store.user;

import com.advantage.account.test.cfg.AdvantageTestContextConfiguration;
import com.advantage.account.test.online.store.dao.GenericRepositoryTests;
import com.advantage.account.util.UserPassword;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Binyamin Regev on 17/11/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AdvantageTestContextConfiguration.class})
public class UserPasswordTests extends GenericRepositoryTests {

    @Test
    public void testEncryptAndDecryptText() {
        final UserPassword userPassword = new UserPassword();

        final String originalText = "myPassword1!";

        //  Encrypt the original text
        final String encryptedText = userPassword.encryptText(originalText);

        //  Decrypt the encrypted original text
        final String decryptedText = userPassword.decryptText(encryptedText);

        System.out.println("encryptText: \"" + originalText + "\" to: " + encryptedText);

        System.out.println("Decrypt Text \"" + encryptedText + "\" to \"" + decryptedText + "\"");

        //  Expecting Decrypted-Text to be IDENTICAL to Original-Text
        Assert.assertEquals(originalText, decryptedText);
    }
}
