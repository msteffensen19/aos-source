package com.advantage.account.store.user.services;

import com.advantage.account.store.user.dao.AppUserRepository;
import com.advantage.account.store.user.dto.AppUserResponseStatus;
import com.advantage.account.store.user.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Binyamin Regev on 16/11/2015.
 */
@Service
public class AppUserService {

    @Autowired
    @Qualifier("appUserRepository")
    public AppUserRepository appUserRepository;


    @Transactional
    public AppUserResponseStatus create(final Integer appUserType, final String lastName, final String firstName, final String loginName, final String password, final Integer country, final String phoneNumber, final String stateProvince, final String cityName, final String address, final String zipcode, final String email, final char allowOffersPromotion) {
        return appUserRepository.create(appUserType, lastName, firstName, loginName, password, country, phoneNumber, stateProvince, cityName, address, zipcode, email, allowOffersPromotion);
    }

    @Transactional(readOnly = true)
    public AppUser getAppUserByLogin(final String loginUser) {
        return appUserRepository.getAppUserByLogin(loginUser);
    }

    @Transactional(readOnly = true)
    public AppUserResponseStatus doLogin(final String loginUser, final String loginPassword, final String email) {
        return appUserRepository.doLogin(loginUser, loginPassword, email);
    }

    @Transactional(readOnly = true)
    public List<AppUser> getAllAppUsers() {
        return appUserRepository.getAll();
    }

}
