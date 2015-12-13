package com.advantage.online.store.support.services;

import com.advantage.online.store.support.dto.ContactUsMailDto;
import com.advantage.online.store.support.dto.ContactUsResponseStatus;
import com.advantage.online.store.user.util.ValidationHelper;
import com.advantage.util.ArgumentValidationHelper;
import org.springframework.stereotype.Service;

/**
 * @author Binyamin Regev on 13/12/2015.
 */
@Service
public class ContactUsMailService {

    private static int SUCCESS = 1;
    private static int FAILURE = -1;

    private static String MESSAGE_SUCCESS = "Thank you for contacting Advantage support.";
    private static String MESSAGE_INVALID_EMAIL_ADDRESS = "Invalid e-mail address.";


    public ContactUsResponseStatus sendMail(ContactUsMailDto contactUs) {
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(contactUs.getEmail(), "user e-mail address");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(contactUs.getText(), "e-mail text");

        ContactUsResponseStatus response;

        if (ValidationHelper.isValidEmail(contactUs.getEmail())) {
            response = new ContactUsResponseStatus(true, MESSAGE_SUCCESS, SUCCESS);
        }
        else {
            response = new ContactUsResponseStatus(false, MESSAGE_INVALID_EMAIL_ADDRESS, FAILURE);
        }

        return response;
    }
}
