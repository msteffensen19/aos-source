package com.advantage.order.store.config;

import com.advantage.common.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Application User configuration class
 *
 * @author Binyamin Regev on 24/11/2015.
 */
@Configuration
public class AppUserConfiguration {

    @Autowired
    public Environment environment;

    private static final Logger logger = Logger.getLogger(AppUserConfiguration.class);
    private boolean allowUserConfiguration;


//      //  Class that is called must have a method "public void init() throws Exception"
//    @Bean(initMethod = "init")
//    public AppUserConfig initAppUserConfiguration() {
//        return new AppUserConfig();
//    }

    @Bean(initMethod = "init")
    public AppUserConfiguration init() {
        logger.debug("@Bean(initMethod = \"init\")");
        AppUserConfiguration configuration = new AppUserConfiguration();
        configuration.allowUserConfiguration = _isAllowUserConfig();
        logger.debug("allowUserConfiguration = " + configuration.allowUserConfiguration );
        return configuration;   //  Successful
    }

    public AppUserConfiguration() {
        logger.debug("Constructor, objectId="+((Object)this).toString());
    }

    public boolean isAllowUserConfiguration() {
        return allowUserConfiguration;
    }

    private boolean _isAllowUserConfig() {
        if (environment == null) {
            logger.fatal("@Autowired Environment is null");
        }
        String property = environment.getProperty(Constants.ENV_ALLOW_USER_CONFIGURATION);
        logger.debug("Parameter " + Constants.ENV_ALLOW_USER_CONFIGURATION + " = " + (property == null ? "null" : property));
        if (property == null || property.isEmpty()) {
            return false;
        }
        if (property.toLowerCase().equals("yes")) {
            return true;
        } else {
            return false;
        }
    }

}
