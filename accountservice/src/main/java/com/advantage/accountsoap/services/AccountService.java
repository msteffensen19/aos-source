package com.advantage.accountsoap.services;

import com.advantage.accountsoap.dao.AccountRepository;
import com.advantage.accountsoap.dto.AccountStatusResponse;
import com.advantage.accountsoap.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    @Qualifier("accountRepository")
    public AccountRepository accountRepository;

    /*@Qualifier("accountRepository")
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(DefaultAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }*/

    @Transactional
    public AccountStatusResponse create(final Integer appUserType, final String lastName, final String firstName, final String loginName, final String password, final Integer country, final String phoneNumber, final String stateProvince, final String cityName, final String address, final String zipcode, final String email, final char allowOffersPromotion) {
        return accountRepository.create(appUserType, lastName, firstName, loginName, password, country, phoneNumber, stateProvince, cityName, address, zipcode, email, allowOffersPromotion);
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
    public boolean isExists(long userId) {
        boolean result = false;

        if (accountRepository.get(userId) != null) { result = true; }

        return result;
    }

}
