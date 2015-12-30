package com.advantage.account.dao;

import com.advantage.account.dto.AppUserResponseStatus;
import com.advantage.account.model.AppUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AppUserRepository extends DefaultCRUDOperations<AppUser> {

    AppUser createAppUser(Integer appUserType, String lastName, String firstName, String loginName,
                          String password, Integer country, String phoneNumber, String stateProvince,
                          String cityName, String address, String zipcode, String email,
                          char agreeToReceiveOffersAndPromotions);

    //  For User-Management API
    @Transactional
    AppUserResponseStatus create(Integer appUserType, String lastName, String firstName, String loginName,
                                 String password, Integer country, String phoneNumber, String stateProvince,
                                 String cityName, String address, String zipcode, String email,
                                 char agreeToReceiveOffersAndPromotions);

    AppUser addUnsuccessfulLoginAttempt(AppUser appUser);

    String getBlockedUntilTimestamp(long milliSeconds);

    AppUser updateAppUser(AppUser appUser);

    String getFailureMessage();

    int deleteAppUser(AppUser appUser);
//    int deleteAppUsersByEmails(Collection<String> emails);
//    int deleteAppUsersByLogins(Collection<String> logins);


    AppUser getAppUserByLogin(String login);

    AppUserResponseStatus doLogin(String login, String password, String email);

    List<AppUser> getAppUsersByCountry(Integer countryId);

}
