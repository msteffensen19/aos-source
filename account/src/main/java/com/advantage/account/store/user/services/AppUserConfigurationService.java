package com.advantage.account.store.user.services;

import com.advantage.account.store.user.config.AppUserConfiguration;
import com.advantage.account.store.user.dto.AppUserConfigurationResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Binyamin Regev on 01/12/2015.
 */
@Service
public class AppUserConfigurationService {
    @Autowired
    @Qualifier("appUserConfiguration")
    public AppUserConfiguration appUserConfiguration;

    @Transactional(readOnly = true)
    public AppUserConfigurationResponseStatus getAllConfigurationParameters() {
        return appUserConfiguration.getAllConfigurationParameters();
    }

}
