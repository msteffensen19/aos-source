package com.advantage.online.store.user.api;

import com.advantage.online.store.Constants;
import com.advantage.online.store.user.dto.AppUserDto;
import com.advantage.online.store.user.dto.AppUserResponseStatus;
import com.advantage.online.store.user.model.AppUser;
import com.advantage.online.store.user.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Binyamin Regev on 16/11/2015.
 */
@RestController
@RequestMapping(value = Constants.URI_API)
public class AppUserController {

    //private static final String REQUEST_PARAM_COUNTRY_ID = "country_id";

    @Autowired
    private AppUserService appUserService;

    @RequestMapping(value = "/account/users", method = RequestMethod.GET)
    public ResponseEntity<List<AppUser>> getAllAppUsers(HttpServletRequest request, HttpServletResponse response) {
        List<AppUser> appUsers = appUserService.getAllAppUsers();

        return new ResponseEntity<>(appUsers, HttpStatus.OK);
    }

    @RequestMapping(value = "/account/login", method = RequestMethod.POST)
    public ResponseEntity<AppUserResponseStatus> doLogin(@RequestBody AppUserDto appUser, HttpServletRequest request) {

        final AppUserResponseStatus appUserResponseStatus = appUserService.doLogin(appUser.getLoginUser(),
                appUser.getLoginPassword(),
                appUser.getEmail());

        if (appUserResponseStatus.isSuccess()) {
            HttpSession session = request.getSession();
            session.setAttribute(Constants.UserSession.TOKEN, appUserResponseStatus.getToken());
            session.setAttribute(Constants.UserSession.USER_ID, appUserResponseStatus.getUserId());
            session.setAttribute(Constants.UserSession.IS_SUCCESS, appUserResponseStatus.isSuccess());

            //  Set SessionID to Response Entity
            appUserResponseStatus.setSessionId(session.getId());

            return new ResponseEntity<>(appUserResponseStatus, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(appUserResponseStatus, HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/account/user", method = RequestMethod.POST)
    public ResponseEntity<AppUserResponseStatus> create(@RequestBody AppUser appUser) {

        final AppUserResponseStatus appUserResponseStatus = appUserService.create(appUser.getAppUserType(),
                appUser.getLastName(),
                appUser.getFirstName(),
                appUser.getCityName(),
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
            return new ResponseEntity<>(appUserResponseStatus, HttpStatus.NOT_FOUND);

    }
}
