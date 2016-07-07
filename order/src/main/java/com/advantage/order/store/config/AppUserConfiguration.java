package com.advantage.order.store.config;

import com.advantage.common.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppUserConfiguration {

    private static final Logger logger = Logger.getLogger(AppUserConfiguration.class);

    @Autowired
    private Environment environment;

    private static boolean allowUserConfiguration;
    private static boolean isFirstRead = true;

    //Call by AppInitializer
    protected AppUserConfiguration() {
        if (logger.isTraceEnabled()) {
            logger.trace("Constructor, objectId=" + ((Object) this).toString());
        }
        if (environment == null) {
            logger.fatal("!!!!!!!!!!! @Autowired Environment is null");
        } else {
            logger.trace("!!!!!!!!!!! @Autowired Environment is not null");
        }
    }

    //  Class that is called must have a method "public void init() throws Exception"
    @Bean(initMethod = "init")
    public AppUserConfiguration init() {
        if (environment == null) {
            logger.fatal("!!!!!!!!!!! @Autowired Environment is null");
        } else {
            logger.trace("!!!!!!!!!!! @Autowired Environment is not null");
        }
        logger.trace("@Bean(initMethod = \"init\")");
        isAllowUserConfig();
        return this;
    }

    public static boolean isAllowUserConfiguration() {
        return allowUserConfiguration;
    }

    private void isAllowUserConfig() {
        if (isFirstRead) {
            if (environment == null) {
                logger.fatal("!!!!!!!!!!! @Autowired Environment is null");
            }
            String property = environment.getProperty(Constants.ENV_ALLOW_USER_CONFIGURATION);
            if (logger.isDebugEnabled()) {
                logger.debug("Parameter " + Constants.ENV_ALLOW_USER_CONFIGURATION + " = " + (property == null ? "null" : property));
            }
            if (property == null || property.isEmpty()) {
                allowUserConfiguration = false;
            }
            if (property.toLowerCase().equals("yes")) {
                allowUserConfiguration = true;
            }
            logger.debug("First run: allowUserConfiguration = " + allowUserConfiguration);
            isFirstRead = false;
        } else {
            logger.debug("allowUserConfiguration = " + allowUserConfiguration);
        }
    }
}
