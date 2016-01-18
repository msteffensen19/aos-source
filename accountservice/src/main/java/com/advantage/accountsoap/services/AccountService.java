package com.advantage.accountsoap.services;

import com.advantage.accountsoap.dao.AccountRepository;
import com.advantage.accountsoap.dto.account.AccountDto;
import com.advantage.accountsoap.dto.account.AccountStatusResponse;
import com.advantage.accountsoap.dto.payment.PaymentPreferencesStatusResponse;
import com.advantage.accountsoap.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    @Qualifier("accountRepository")
    private AccountRepository accountRepository;

    @Autowired
    private PaymentPreferencesService paymentPreferencesService;

    @Transactional
    public AccountStatusResponse create(final Integer appUserType, final String lastName, final String firstName, final String loginName, final String password, final Long countryId, final String phoneNumber, final String stateProvince, final String cityName, final String address, final String zipcode, final String email, final String allowOffersPromotion) {
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
                    account.getAllowOffersPromotion(), account.getInternalUnsuccessfulLoginAttempts(),
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
    public AccountStatusResponse updateAccount(long accountId, Integer accountType, String lastName, String firstName, Long countryId, String phoneNumber, String stateProvince, String cityName, String address, String zipcode, String email, String allowOffersPromotion) {
        return accountRepository.updateAccount(accountId,accountType, lastName, firstName, countryId, phoneNumber, stateProvince, cityName, address, zipcode, email, allowOffersPromotion);
    }

    @Transactional
    public AccountStatusResponse updateDefaultPaymentMethod(long accountId, Integer paymentMethodId) {
        Account account = getById(accountId);
        if(account == null || !paymentPreferencesService.isPyamentPreferencesExist(paymentMethodId)) {
            return new AccountStatusResponse(false, "Data not valid", -1);
        }

        account.setDefaultPaymentMethodId(paymentMethodId);

        return  new AccountStatusResponse(true, "", accountId);
    }


    @Transactional
    public Account getById(long id) {
        return accountRepository.get(id);
    }

    @Transactional
    public AccountStatusResponse changePassword(long accountId, String newPassword) {
        return accountRepository.changePassword(accountId, newPassword);
    }
}
