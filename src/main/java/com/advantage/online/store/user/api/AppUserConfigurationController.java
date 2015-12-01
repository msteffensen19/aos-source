package com.advantage.online.store.user.api;

import com.advantage.online.store.Constants;
import com.advantage.online.store.user.dto.AppUserConfigurationResponseStatus;
import com.advantage.online.store.user.services.AppUserConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

/**
 * @author Binyamin Regev on 01/12/2015.
 */
@RestController
@RequestMapping(value = Constants.URI_API)
public class AppUserConfigurationController {
    @Autowired
    private AppUserConfigurationService appUserConfigurationService;

    //public ResponseEntity<AppUserConfigurationResponseStatus> getAllConfigurationParameters(HttpServletRequest request, HttpServletResponse response) {

    @RequestMapping(value = "/appUserConfiguration/getAll", method = RequestMethod.GET)
    public ResponseEntity<AppUserConfigurationResponseStatus> getAllConfigurationParameters() {

        AppUserConfigurationResponseStatus appUserConfigurationResponseStatus = appUserConfigurationService.getAllConfigurationParameters();

        return new ResponseEntity<>(appUserConfigurationResponseStatus, (appUserConfigurationResponseStatus != null ? HttpStatus.OK : HttpStatus.NOT_FOUND));

    }


}
