package com.advantage.online.store.user.dao;

import com.advantage.online.store.user.dto.AppUserResponseStatus;
import com.advantage.online.store.user.model.AppUser;

import java.util.Collection;
import java.util.List;

/**
 * @author Binyamin Regev on 16/11/2015.
 */
public interface AppUserRepository {

    AppUser createAppUser(Integer appUserType, String lastName, String firstName, String loginName,
                          String password, Integer country, String phoneNumber, String stateProvince,
                          String cityName, String address1, String address2, String zipcode, String email,
                          char agreeToReceiveOffersAndPromotions);

    int deleteAppUser(AppUser appUser);
    int deleteAppUsersByEmails(Collection<String> emails);
    int deleteAppUsersByLogins(Collection<String> logins);


    AppUserResponseStatus doLogin(String login, String password, String email);
    AppUser getAppUserByLogin(String login);


    List<AppUser> getAllAppUsers();
    List<AppUser> getAppUsersByCountry(Integer countryId);

}
