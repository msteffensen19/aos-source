package com.advantage.online.store.api;

import com.advantage.online.store.Constants;
import com.advantage.online.store.user.dto.AppUserDto;
import com.advantage.online.store.user.dto.AppUserResponseStatus;
import com.advantage.online.store.user.dto.CountryResponseStatus;
import com.advantage.online.store.user.model.AppUser;
import com.advantage.online.store.user.model.Country;
import com.advantage.online.store.user.services.AppUserService;
import com.advantage.online.store.user.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Binyamin Regev on 16/11/2015.
 */
@RestController
@RequestMapping(value = Constants.URI_API + "/account")
public class AccountController {

    //private static final String REQUEST_PARAM_COUNTRY_ID = "country_id";

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private CountryService countryService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<AppUser>> getAllAppUsers(HttpServletRequest request, HttpServletResponse response) {
        List<AppUser> appUsers = appUserService.getAllAppUsers();

        return new ResponseEntity<>(appUsers, HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<AppUserResponseStatus> doLogin(@RequestBody AppUserDto appUser, HttpServletRequest request, HttpServletResponse response) {

        final AppUserResponseStatus appUserResponseStatus = appUserService.doLogin(appUser.getLoginUser(),
                appUser.getLoginPassword(),
                appUser.getEmail());

        if (appUserResponseStatus.isSuccess()) {
            HttpSession session = request.getSession();
            session.setAttribute(Constants.UserSession.TOKEN, appUserResponseStatus.getToken());
            session.setAttribute(Constants.UserSession.USER_ID, appUserResponseStatus.getUserId());
            session.setAttribute(Constants.UserSession.IS_SUCCESS, appUserResponseStatus.isSuccess());

            //  Set SessionID to Response Entity
            //response.getHeader().
            appUserResponseStatus.setSessionId(session.getId());


            return new ResponseEntity<>(appUserResponseStatus, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(appUserResponseStatus, HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<AppUserResponseStatus> create(@RequestBody AppUser appUser) {

        final AppUserResponseStatus appUserResponseStatus = appUserService.create(
                appUser.getAppUserType(),
                appUser.getLastName(),
                appUser.getFirstName(),
                appUser.getLoginName(),
                appUser.getPassword(),
                appUser.getCountry(),
                appUser.getPhoneNumber(),
                appUser.getStateProvince(),
                appUser.getCityName(),
                appUser.getAddress(),
                appUser.getZipcode(),
                appUser.getEmail(),
                appUser.getAllowOffersPromotion());

        if (appUserResponseStatus.isSuccess())
            return new ResponseEntity<>(appUserResponseStatus, HttpStatus.OK);
        else
            return new ResponseEntity<>(appUserResponseStatus, HttpStatus.CONFLICT);

    }

    @RequestMapping(value = "/countries", method = RequestMethod.POST)
    public ResponseEntity<CountryResponseStatus> create(@RequestBody Country country) {

        final CountryResponseStatus countryResponseStatus = countryService.create(country.getName(),
                country.getIsoName(),
                country.getPhonePrefix());

        if (countryResponseStatus.isSuccess())
            return new ResponseEntity<>(countryResponseStatus, HttpStatus.OK);
        else
            return new ResponseEntity<>(countryResponseStatus, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/countries", method = RequestMethod.GET)
    public ResponseEntity<List<Country>> getCountries(@RequestParam(value = "phonePrefix", required = false) Integer internationalPhonePrefix,
                                                      @RequestParam(value = "nameStartFrom", required = false) String startOfName) {
        List<Country> countries;

        if (internationalPhonePrefix == null && startOfName == null) {
            countries = countryService.getAllCountries();
        } else if (internationalPhonePrefix != null && startOfName != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (internationalPhonePrefix != null) {
            countries = countryService.getCountriesByPhonePrefix(internationalPhonePrefix);
        } else {//nameStartFrom!=null
            countries = countryService.getCountriesByPartialName(startOfName);
        }
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

}
