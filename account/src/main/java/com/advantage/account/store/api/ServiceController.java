package com.advantage.account.store.api;

import com.advantage.account.store.user.dto.AppUserConfigurationResponseStatus;
import com.advantage.account.store.user.services.AppUserConfigurationService;
import com.advantage.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/service" + Constants.URI_API + "/v1")
public class ServiceController {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private AppUserConfigurationService appUserConfigurationService;
    @RequestMapping(value = "/clientConfiguration", method = RequestMethod.GET)
    public ResponseEntity<AppUserConfigurationResponseStatus> getAllConfigurationParameters(HttpServletRequest request) {

        AppUserConfigurationResponseStatus appUserConfigurationResponseStatus = appUserConfigurationService.getAllConfigurationParameters();

        return new ResponseEntity<>(appUserConfigurationResponseStatus, (appUserConfigurationResponseStatus != null ? HttpStatus.OK : HttpStatus.NOT_FOUND));

    }
}
