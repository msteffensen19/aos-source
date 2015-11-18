package com.advantage.online.store.user.api;

import com.advantage.online.store.Constants;
import com.advantage.online.store.user.model.AppUser;
import com.advantage.online.store.user.services.AppUserService;
import com.advantage.util.ArgumentValidationHelper;
import com.advantage.util.HttpServletHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @author Binyamin Regev on 16/11/2015.
 */
@RestController
@RequestMapping(value = Constants.URI_API)
public class AppUserController {

    private static final String REQUEST_PARAM_COUNTRY_ID = "country_id";

    @Autowired
    private AppUserService appUserService;

    @RequestMapping(value = "appUsers", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllProducts(HttpServletRequest request, HttpServletResponse response) {
        List<AppUser> appUsers = appUserService.getAllAppUsers();
        return new ResponseEntity<Object>(appUsers, HttpStatus.OK);
    }

    @RequestMapping(value = "countryAppUsers", method = RequestMethod.GET)
    public ResponseEntity<Object> getCountryAppUsers(final HttpServletRequest request,
                                                      final HttpServletResponse response) {

        ArgumentValidationHelper.validateArgumentIsNotNull(request, "http servlet request");
        ArgumentValidationHelper.validateArgumentIsNotNull(response, "http servlet response");

        HttpServletHelper.validateParametersExistenceInRequest(request, true,
                                                            AppUserController.REQUEST_PARAM_COUNTRY_ID);

        final String countryIdParameter = request.getParameter(AppUserController.REQUEST_PARAM_COUNTRY_ID);
        final Integer countryId = Integer.valueOf(countryIdParameter);

        final List<AppUser> appUsers = appUserService.getAppUsersByCountry(countryId);

        return new ResponseEntity<Object>(appUsers, HttpStatus.OK);
    }
}
