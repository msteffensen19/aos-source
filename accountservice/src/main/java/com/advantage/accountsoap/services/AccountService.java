package com.advantage.accountsoap.services;

import com.advantage.accountsoap.dao.AccountRepository;
import com.advantage.accountsoap.dto.account.AccountDto;
import com.advantage.accountsoap.dto.account.AccountStatusResponse;
import com.advantage.accountsoap.dto.payment.PaymentPreferencesDto;
import com.advantage.accountsoap.model.Account;
import com.advantage.accountsoap.model.Country;
import com.advantage.accountsoap.model.PaymentPreferences;
import com.advantage.accountsoap.util.AccountPassword;
import com.advantage.common.enums.AccountType;
import com.advantage.common.exceptions.token.ContentTokenException;
import com.advantage.common.exceptions.token.VerificationTokenException;
import com.advantage.common.exceptions.token.WrongTokenTypeException;
import com.advantage.common.security.TokenJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AccountService {
    @Autowired
    @Qualifier("accountRepository")
    private AccountRepository accountRepository;

    @Autowired
    private PaymentPreferencesService paymentPreferencesService;

    @Transactional
    public AccountStatusResponse create(final Integer appUserType, final String lastName, final String firstName, final String loginName, final String password, final Long countryId, final String phoneNumber, final String stateProvince, final String cityName, final String address, final String zipcode, final String email, final boolean allowOffersPromotion) {
        return accountRepository.create(appUserType, lastName, firstName, loginName, password, countryId, phoneNumber, stateProvince, cityName, address, zipcode, email, allowOffersPromotion);
    }

    @Transactional(readOnly = true)
    public Account getAppUserByLogin(final String loginUser) {
        return accountRepository.getAppUserByLogin(loginUser);
    }

    @Transactional(readOnly = true)
    public AccountStatusResponse doLogin(final String loginUser, final String loginPassword, final String email) {
        return accountRepository.doLogin(loginUser, loginPassword, email);
    }

    @Transactional(readOnly = true)
    public AccountStatusResponse doLogout(final String loginUser, final String base64Token) {
        return accountRepository.doLogout(loginUser, base64Token);
    }

    @Transactional(readOnly = true)
    public List<Account> getAllAppUsers() {
        return accountRepository.getAll();
    }

    @Transactional(readOnly = true)
    public List<AccountDto> getAllAppUsersDto() {
        return fillAccountsDto(accountRepository.getAll());
    }

    private List<AccountDto> fillAccountsDto(List<Account> accounts) {
        List<AccountDto> dtos = new ArrayList<>();
        for (Account account : accounts) {
            dtos.add(new AccountDto(account.getId(),
                    account.getLastName(),
                    account.getFirstName(),
                    account.getLoginName(),
                    account.getAccountType(),
                    account.getCountry().getId(),
                    account.getCountry().getName(),
                    account.getCountry().getIsoName(),
                    account.getStateProvince(),
                    account.getCityName(),
                    account.getAddress(),
                    account.getZipcode(),
                    account.getPhoneNumber(),
                    account.getEmail(),
                    account.getDefaultPaymentMethodId(),
                    account.isAllowOffersPromotion(), account.getInternalUnsuccessfulLoginAttempts(),
                    account.getInternalUserBlockedFromLoginUntil(),
                    account.getInternalLastSuccesssulLogin()));
        }

        return dtos;
    }

    @Transactional(readOnly = true)
    public boolean isExists(long userId) {
        boolean result = false;

        if (accountRepository.get(userId) != null) { result = true; }

        return result;
    }

    @Transactional
    public AccountStatusResponse updateAccount(long accountId, Integer accountType, String lastName, String firstName,
                                               Long countryId, String phoneNumber, String stateProvince, String cityName,
                                               String address, String zipcode, String email, boolean allowOffersPromotion) {
        return accountRepository.updateAccount(accountId,accountType, lastName, firstName, countryId, phoneNumber,
                stateProvince, cityName, address, zipcode, email, allowOffersPromotion);
    }

    @Transactional
    public AccountStatusResponse updateDefaultPaymentMethod(long accountId, Integer paymentMethodId) {
        Account account = getById(accountId);
        if(account == null) {
            return new AccountStatusResponse(false, "Data not valid", -1);
        }

        if(!paymentPreferencesService.isPaymentPreferencesExist(accountId)) {
            return new AccountStatusResponse(false, "Data not valid", -2);
        }

        account.setDefaultPaymentMethodId(((long) Integer.valueOf(paymentMethodId)));

        return  new AccountStatusResponse(true, "Update default payment method was successful", accountId);
    }

    @Transactional
    public Account getById(long id) {
        return accountRepository.get(id);
    }

    /**
     * Change the password of a registered user. This can happen in 1 of 2 ways: <br/>
     *  1.  Registered user changes his own password (can be USER or ADMIN-USER). <br/>
     *      <ul>NOTE:</ul> In this case the {@param accountId} and {@link TokenJWT#getUserId()} will be same. <br/>
     *      (a) Get the registered user account details by accountId. <br/>
     *      (b) Verify login user name with the one in the {@code token} and old password with argument. <br/>
     *      (c) If <b>VERIFIED</b> then change the user's password, Return <b>Successful</b>with HTTP code 202 (Accepted). <br/>
     *      (d) otherwise, return <b>FAILURE</b> with HTTP code 403 (Forbidden). </p>
     *  <p>2.  Admin-User changes the password of another registered user (not his own). <br/>
     *      <ul>NOTE:</ul> In this case {@link TokenJWT#getAccountType()} must by <i>ADMIN</i>. <br/>
     *      (a) Verify the account type in the token is ADMIN. If NOT then return HTTP code 401 (Unauthorized).
     *      (b) Get the registered user account details by accountId.
     *      (c) Verify old pasword for the registered user.
     *      (d) If <b>VERIFIED</b> then change the user's password, Return <b>Successful</b>with HTTP code 202 (Accepted).
     *      (e) Return <b>Successful</b>with HTTP code 202 (Accepted). </p>
     * @param accountId     user-id to update his password.
     * @param oldPassword   Existing password of the user, for verification.
     * @param newPassword   The password that will replace the old password.
     * @param base64Token   The Base-64 Token that identifies the <b><i>CURRENT USER</i></b>.
     * @return {@link AccountStatusResponse}
     * @throws VerificationTokenException
     * @throws WrongTokenTypeException
     * @throws ContentTokenException
     */
    @Transactional
    public AccountStatusResponse changePassword(long accountId, String oldPassword, String newPassword, String base64Token) {
        //  TODO Benny - Add the argument to XSD file.
        Account account = accountRepository.get(accountId);

        AccountStatusResponse response = new AccountStatusResponse(false, "", -1);

        try {
            TokenJWT tokenJWT = new TokenJWT(base64Token);

            //  Get current user details from Token
            long currentUserId = tokenJWT.getUserId();
            String currentUserLogin = tokenJWT.getLoginName();
            AccountType currentUserAccountType = tokenJWT.getAccountType();

            if (accountId != currentUserId) {
                //  Registered user and current user are not the same
                if (currentUserAccountType.getAccountTypeCode() == AccountType.ADMIN.getAccountTypeCode()) {
                    //  Not the same user and current user is ADMIN
                    String compareToPassword = new AccountPassword(account.getLoginName(), oldPassword)
                            .getEncryptedPassword();

                    if (account.getPassword().equals(compareToPassword)) {
                        //  old password matches registered user password - OK to change password
                        response = accountRepository.changePassword(accountId, newPassword);
                    } else {
                        //  old Password does not match registered user password
                        response = new AccountStatusResponse(false, HttpStatus.FORBIDDEN.getReasonPhrase(), -1);
                    }
                } else {
                    //  Not the same user and current user is not ADMIN
                    response = new AccountStatusResponse(false, HttpStatus.UNAUTHORIZED.getReasonPhrase(), -2);
                }
            }
            else {
                //  Registered user and current user are the same
                String compareToPassword = new AccountPassword(currentUserLogin, oldPassword)
                        .getEncryptedPassword();

                if (account.getPassword().equals(compareToPassword)) {
                    //  old password matches registered user password - OK to change password
                    response = accountRepository.changePassword(accountId, newPassword);
                } else {
                    //  old Password does not match registered user password
                    response = new AccountStatusResponse(false, HttpStatus.FORBIDDEN.getReasonPhrase(), -1);
                }
            }
        } catch (VerificationTokenException e) {
            e.printStackTrace();
        } catch (WrongTokenTypeException e) {
            e.printStackTrace();
        } catch (ContentTokenException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     *  <p>
     *  Reset the registered user's password. Only USER-ADMIN can perform this operation. <br/>
     *  <ul>NOTE:</ul> {@link TokenJWT#getAccountType()} must by <i>ADMIN</i>. <br/>
     *  (a) Verify the account type in the token is ADMIN. If NOT then return HTTP code 401 (Unauthorized). <br/>
     *  (b) Get the registered user account details by accountId. <br/>
     *  (c) Change the user's password, Return <b>Successful</b>with HTTP code 202 (Accepted). <br/>
     *  </p>
     *  @param accountId     user-id to update his password.
     *  @param newPassword   The password that will replace the old password.
     *  @param base64Token   The Base-64 Token that identifies the <b><i>CURRENT USER</i></b>.
     *  @return {@link AccountStatusResponse}
     *  @throws VerificationTokenException
     *  @throws WrongTokenTypeException
     *  @throws ContentTokenException
     */
    @Transactional
    public AccountStatusResponse resetPassword(long accountId, String newPassword, String base64Token) {
        //  TODO Benny - Add the argument to XSD file.
        Account account = accountRepository.get(accountId);

        AccountStatusResponse response = new AccountStatusResponse(false, "", -1);

        try {
            TokenJWT tokenJWT = new TokenJWT(base64Token);

            //  Get current user details from Token
            long currentUserId = tokenJWT.getUserId();
            String currentUserLogin = tokenJWT.getLoginName();
            AccountType currentUserAccountType = tokenJWT.getAccountType();

            if (accountId != currentUserId) {
                //  Registered user and current user are not the same
                if (currentUserAccountType.getAccountTypeCode() == AccountType.ADMIN.getAccountTypeCode()) {
                    //  Not the same user and current user is ADMIN
                    String compareToPassword = new AccountPassword(account.getLoginName(), newPassword)
                            .getEncryptedPassword();

                    //  Update new password into registered user account
                    response = accountRepository.changePassword(accountId, newPassword);
                } else {
                    //  Not the same user and current user is not ADMIN
                    response = new AccountStatusResponse(false, HttpStatus.UNAUTHORIZED.getReasonPhrase(), -2);
                }
            }
        } catch (VerificationTokenException e) {
            e.printStackTrace();
        } catch (WrongTokenTypeException e) {
            e.printStackTrace();
        } catch (ContentTokenException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @Transactional
    public List<PaymentPreferencesDto> getPaymentPreferences(long accountId) {
        Account account = accountRepository.get(accountId);
        if(account == null) return null;

        //return fillPaymentPreferencesDto(account.getPaymentPreferences());
        List<PaymentPreferencesDto> paymentPreferencesDto = paymentPreferencesService.getPaymentPreferencesByUserId(accountId);

        return paymentPreferencesDto;
    }

    @Transactional
    public AccountStatusResponse removePaymentPreferences(long accountId, long preferenceId) {
        return accountRepository.removePaymentPreferences(accountId, preferenceId);
    }

    public AccountStatusResponse dbRestoreFactorySettings() {
        AccountStatusResponse response = accountRepository.dbRestoreFactorySettings();
        return response;
    }

    private List<PaymentPreferencesDto> fillPaymentPreferencesDto(Set<PaymentPreferences> paymentPreferences) {
        List<PaymentPreferencesDto> dtos = new ArrayList<>();
        for (PaymentPreferences item : paymentPreferences) {
            dtos.add(new PaymentPreferencesDto(item.getPaymentMethod(),
                    item.getCardNumber(),
                    item.getExpirationDate(),
                    item.getCvvNumber(),
                    item.getCustomerName(),
                    item.getSafePayUsername(),
                    item.getSafePayPassword(),
                    item.getUserId()));
        }

        return dtos;
    }
}
