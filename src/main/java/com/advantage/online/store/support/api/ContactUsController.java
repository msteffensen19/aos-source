package com.advantage.online.store.support.api;

import com.advantage.online.store.Constants;
import com.advantage.online.store.support.dto.ContactUsMailDto;
import com.advantage.online.store.support.dto.ContactUsResponseStatus;
import com.advantage.online.store.support.services.ContactUsMailService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Binyamin Regev on 13/12/2015.
 */
@RestController
@RequestMapping(value = "/support" + Constants.URI_API + "/v1")
public class ContactUsController {

    @Autowired
    private ContactUsMailService mailService;

    @RequestMapping(value="/contactus/email", method= RequestMethod.PUT)
    @ApiOperation(value = "Contact support by email")
    public ContactUsResponseStatus sendMail(@RequestBody ContactUsMailDto contactUs) {
        ContactUsResponseStatus response = mailService.sendMail(contactUs);
        return response;
    }
}
