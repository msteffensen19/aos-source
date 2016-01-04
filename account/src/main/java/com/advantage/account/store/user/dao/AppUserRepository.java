package com.advantage.account.store.user.dao;

import com.advantage.account.store.dao.DefaultCRUDOperations;
import com.advantage.common.dto.AppUserResponseDto;
import com.advantage.account.store.user.model.AppUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Binyamin Regev on 16/11/2015.
 */
public interface AppUserRepository extends DefaultCRUDOperations<AppUser> {

    AppUser createAppUser(Integer appUserType, String lastName, String firstName, String loginName,
                          String password, Integer country, String phoneNumber, String stateProvince,
                          String cityName, String address, String zipcode, String email,
                          char agreeToReceiveOffersAndPromotions);

    //  For User-Management API
    @Transactional
    AppUserResponseDto create(Integer appUserType, String lastName, String firstName, String loginName,
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

    AppUserResponseDto doLogin(String login, String password, String email);

    List<AppUser> getAppUsersByCountry(Integer countryId);

}
