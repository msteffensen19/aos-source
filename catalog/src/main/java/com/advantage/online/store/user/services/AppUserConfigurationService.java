package com.advantage.online.store.user.services;

import com.advantage.online.store.Constants;
import com.advantage.online.store.user.config.AppUserConfiguration;
import com.advantage.online.store.user.dto.AppUserConfigurationResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
