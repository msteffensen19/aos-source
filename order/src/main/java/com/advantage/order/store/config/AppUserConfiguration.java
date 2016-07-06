package com.advantage.order.store.config;

import com.advantage.common.Constants;
import com.advantage.common.Url_resources;
import com.advantage.root.util.JsonHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Configuration
public class AppUserConfiguration {

    private static final Logger logger = Logger.getLogger(AppUserConfiguration.class);

    @Autowired
    private Environment environment;

    private static boolean allowUserConfiguration;
    private static boolean isFirstRead = true;

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
    protected AppUserConfiguration init() {
        if (environment == null) {
            logger.fatal("!!!!!!!!!!! @Autowired Environment is null");
        } else {
            logger.trace("!!!!!!!!!!! @Autowired Environment is not null");
        }
        logger.trace("@Bean(initMethod = \"init\")");
        //AppUserConfiguration result = new AppUserConfiguration();
        isAllowUserConfig();
        return this;
    }

    public boolean isAllowUserConfiguration() {
//        if (isFirstRead) {
//            allowUserConfiguration = isAllowUserConfig();
//            isFirstRead = false;
//        }
//        logger.debug("allowUserConfiguration = " + allowUserConfiguration);
        return allowUserConfiguration;
    }

    private void isAllowUserConfig() {
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
    }
}
