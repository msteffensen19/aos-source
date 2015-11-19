package com.advantage.online.store.user.services;

import com.advantage.online.store.user.dao.AppUserRepository;
import com.advantage.online.store.user.model.AppUser;
import com.advantage.util.ArgumentValidationHelper;
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

    @Transactional(readOnly = true)
    public List<AppUser> getAllAppUsers() {
        return appUserRepository.getAllAppUsers();
    }


    @Transactional(readOnly = true)
    public List<AppUser> getAppUsersByCountry(final Integer countryId) {
        ArgumentValidationHelper.validateArgumentIsNotNull(countryId, "country id");
        return appUserRepository.getAppUsersByCountry(countryId);
    }

}
