package com.advantage.order.store.config;

import accountservice.store.online.advantage.com.AccountServicePort;
import accountservice.store.online.advantage.com.AccountServicePortService;
import accountservice.store.online.advantage.com.GetAccountConfigurationRequest;
import accountservice.store.online.advantage.com.GetAccountConfigurationResponse;
import com.advantage.common.Url_resources;
import org.apache.log4j.Logger;

public class DemoAppConfiguration {
    private static final Logger logger = Logger.getLogger(DemoAppConfiguration.class);
    private static boolean isFirstRead = true;
    private static boolean allowUserConfiguration;

    public static boolean isAllowUserConfiguration() {
        readAccountDemoAppConfig();
        return allowUserConfiguration;
    }

    private static void readAccountDemoAppConfig() {
        if (isFirstRead) {
            AccountServicePortService accountServicePortService = new AccountServicePortService(Url_resources.getUrlSoapAccount());
            AccountServicePort accountServicePortSoap11 = accountServicePortService.getAccountServicePortSoap11();
            GetAccountConfigurationResponse getAccountConfigurationResponse = accountServicePortSoap11.getAccountConfiguration(new GetAccountConfigurationRequest());
            if (getAccountConfigurationResponse == null) {
                logger.fatal("Can't get AccountConfiguration - getAccountConfigurationResponse is null");
            } else {
                allowUserConfiguration = getAccountConfigurationResponse.isAllowUserConfiguration();
                logger.info("getAccountConfigurationResponse = " + allowUserConfiguration);
            }
            logger.debug("First run: allowUserConfiguration = " + allowUserConfiguration);
            isFirstRead = false;
        } else {
            logger.debug("allowUserConfiguration = " + allowUserConfiguration);
        }
    }
}
