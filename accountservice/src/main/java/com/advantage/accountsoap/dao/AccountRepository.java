package com.advantage.accountsoap.dao;

import com.advantage.accountsoap.dto.AccountStatusResponse;
import com.advantage.accountsoap.model.Account;
import com.advantage.common.dao.DefaultCRUDOperations;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountRepository extends DefaultCRUDOperations<Account> {

    Account createAppUser(Integer appUserType, String lastName, String firstName, String loginName,
                          String password, Long country, String phoneNumber, String stateProvince,
                          String cityName, String address, String zipcode, String email,
                          String agreeToReceiveOffersAndPromotions);

    //  For User-Management API
    @Transactional
    AccountStatusResponse create(Integer appUserType, String lastName, String firstName, String loginName,
                                 String password, Long country, String phoneNumber, String stateProvince,
                                 String cityName, String address, String zipcode, String email,
                                 String agreeToReceiveOffersAndPromotions);

    Account addUnsuccessfulLoginAttempt(Account account);

    String getBlockedUntilTimestamp(long milliSeconds);

    Account updateAppUser(Account account);

    AccountStatusResponse updateAccount(long acccountId, Integer appUserType, String lastName, String firstName,Long country,
                                        String phoneNumber, String stateProvince, String cityName, String address,
                                        String zipcode, String email, String agreeToReceiveOffersAndPromotions);

    String getFailureMessage();

    int deleteAppUser(Account account);
//    int deleteAppUsersByEmails(Collection<String> emails);
//    int deleteAppUsersByLogins(Collection<String> logins);


    Account getAppUserByLogin(String login);

    AccountStatusResponse doLogin(String login, String password, String email);

    List<Account> getAppUsersByCountry(Integer countryId);

    AccountStatusResponse updatePaymentMethod(long accountId, int paymentMethod);

    AccountStatusResponse changePassword(long accountId, String newPassword);

}
