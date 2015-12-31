package com.advantage.account.dao;

import com.advantage.account.dto.AccountResponseStatus;
import com.advantage.account.model.Account;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountRepository extends DefaultCRUDOperations<Account> {

    Account createAppUser(Integer appUserType, String lastName, String firstName, String loginName,
                          String password, Integer country, String phoneNumber, String stateProvince,
                          String cityName, String address, String zipcode, String email,
                          char agreeToReceiveOffersAndPromotions);

    //  For User-Management API
    @Transactional
    AccountResponseStatus create(Integer appUserType, String lastName, String firstName, String loginName,
                                 String password, Integer country, String phoneNumber, String stateProvince,
                                 String cityName, String address, String zipcode, String email,
                                 char agreeToReceiveOffersAndPromotions);

    Account addUnsuccessfulLoginAttempt(Account account);

    String getBlockedUntilTimestamp(long milliSeconds);

    Account updateAppUser(Account account);

    String getFailureMessage();

    int deleteAppUser(Account account);
//    int deleteAppUsersByEmails(Collection<String> emails);
//    int deleteAppUsersByLogins(Collection<String> logins);


    Account getAppUserByLogin(String login);

    AccountResponseStatus doLogin(String login, String password, String email);

    List<Account> getAppUsersByCountry(Integer countryId);

}
