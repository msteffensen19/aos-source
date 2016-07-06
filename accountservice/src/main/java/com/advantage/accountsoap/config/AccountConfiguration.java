package com.advantage.accountsoap.config;

import com.advantage.accountsoap.dto.account.GetAccountConfigurationResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Application User configuration class
 */
@Configuration
public class AccountConfiguration {
    private final String ENV_USER_LOGIN_BLOCKING = "user.login.blocking";
    private final String ENV_NUMBER_OF_LOGIN_TRIES_BEFORE_BLOCKING = "number.of.login.tries.before.blocking";
    private final String ENV_PRODUCT_INSTOCK_DEFAULT_VALUE = "product.inStock.default.value";
    private final String ENV_USER_LOGIN_TIMEOUT = "user.login.timeout";
    private final String ENV_ALLOW_USER_CONFIGURATION = "allow.user.configuration";
    private final String ENV_MAX_CONCURRENT_SESSIONS = "Max.Concurrent.Sessions";


    @Inject
    private Environment env;

    //private AppUserConfig appUserConfig = new AppUserConfig();

    public static int NUMBER_OF_FAILED_LOGIN_ATTEMPTS_BEFORE_BLOCKING;  //numberOfFailedLoginAttemptsBeforeBlocking
    public static long LOGIN_BLOCKING_INTERVAL_IN_SECONDS;              //loginBlockingIntervalInSeconds
    public static int PRODUCT_IN_STOCK_DEFAULT_VALUE;
    public static int USER_LOGIN_TIMEOUT;
    public static String ALLOW_USER_CONFIGURATION;
    public static int MAX_CONCURRENT_SESSIONS;

    //  //  Class that is called must have a method "public void init() throws Exception"
    //@Bean(initMethod = "init")
    //public AppUserConfig initAppUserConfiguration() {
    //    return new AppUserConfig();
    //}

    @Bean
    public int getAppUserConfiguration() {
        this.setNumberOfLoginAttemptsBeforeBlocking(ENV_NUMBER_OF_LOGIN_TRIES_BEFORE_BLOCKING);
        this.setLoginBlockingIntervalInSeconds(ENV_USER_LOGIN_BLOCKING);
        this.setProductInStockDefaultValue(ENV_PRODUCT_INSTOCK_DEFAULT_VALUE);
        this.setUserLoginTimeout(ENV_USER_LOGIN_TIMEOUT);
        this.setAllowUserConfiguration(ENV_ALLOW_USER_CONFIGURATION);
        this.setMaxConcurrentSessions(ENV_MAX_CONCURRENT_SESSIONS);

        System.out.println("Configuration: LOGIN_BLOCKING_INTERVAL_IN_SECONDS=" + this.getLoginBlockingIntervalInSeconds());
        System.out.println("Configuration: NUMBER_OF_FAILED_LOGIN_ATTEMPTS_BEFORE_BLOCKING=" + this.getNumberOfLoginAttemptsBeforeBlocking());
        System.out.println("Configuration: PRODUCT_IN_STOCK_DEFAULT_VALUE=\"" + this.getProductInStockDefaultValue() + "\"");
        System.out.println("Configuration: USER_LOGIN_TIMEOUT=\"" + this.getUserLoginTimeout() + "\"");
        System.out.println("Configuration: ALLOW_USER_CONFIGURATION=\"" + this.getAllowUserConfiguration() + "\"");
        System.out.println("Configuration: MAX_CONCURRENT_SESSIONS=\"" + this.getMaxConcurrentSessions() + "\"");

        return 1;   //  Successful
    }

    /**
     * <ul>Get configuration value:</ul> <br/>
     * Number of unsuccessful login attempts before blocking the user from attempting to login again.
     *
     * @return Number of unsuccessful login attempts.
     */
    public int getNumberOfLoginAttemptsBeforeBlocking() {
        return this.NUMBER_OF_FAILED_LOGIN_ATTEMPTS_BEFORE_BLOCKING;
    }

    private void setNumberOfLoginAttemptsBeforeBlocking(final String parameterKey) {
        String parameterValue = env.getProperty(parameterKey);
        this.NUMBER_OF_FAILED_LOGIN_ATTEMPTS_BEFORE_BLOCKING = (parameterValue != null ? Integer.valueOf(parameterValue) : 0);
    }

    /**
     * <ul>Get configuration value:</ul> <br/>
     * How much time the user is blocked from attempting login again? Data in seconds.
     *
     * @return How much time the user is blocked from attempting login again?
     */
    public long getLoginBlockingIntervalInSeconds() {
        return this.LOGIN_BLOCKING_INTERVAL_IN_SECONDS;
    }

    private void setLoginBlockingIntervalInSeconds(final String parameterKey) {
        String parameterValue = env.getProperty(parameterKey);
        this.LOGIN_BLOCKING_INTERVAL_IN_SECONDS = (parameterValue != null ? Integer.valueOf(parameterValue) : 0);
    }

    public int getProductInStockDefaultValue() {
        return this.PRODUCT_IN_STOCK_DEFAULT_VALUE;
    }

    private void setProductInStockDefaultValue(final String parameterKey) {
        String parameterValue = env.getProperty(parameterKey);
        this.PRODUCT_IN_STOCK_DEFAULT_VALUE = (parameterValue != null ? Integer.valueOf(parameterValue) : 0);
    }

    public int getUserLoginTimeout() {
        return this.USER_LOGIN_TIMEOUT;
    }

    /**
     * User login timeout parameter value in minutes, default 60 minutes.
     */
    public void setUserLoginTimeout(final String parameterKey) {
        String parameterValue = env.getProperty(parameterKey);
        this.USER_LOGIN_TIMEOUT = (parameterValue != null ? Integer.valueOf(parameterValue) : 0);
    }

    public String getAllowUserConfiguration() {
        return this.ALLOW_USER_CONFIGURATION;
    }

    public void setAllowUserConfiguration(final String parameterKey) {
        this.ALLOW_USER_CONFIGURATION = (env.getProperty(parameterKey) != null ? env.getProperty(parameterKey) : "null");
    }

    public int getMaxConcurrentSessions() {
        return MAX_CONCURRENT_SESSIONS;
    }

    public void setMaxConcurrentSessions(final String parameterKey) {
        String parameterValue = env.getProperty(parameterKey);
        this.MAX_CONCURRENT_SESSIONS = (parameterValue != null ? Integer.valueOf(parameterValue) : 0);
    }

    public List<String> getAllAccountParameters() {
        List<String> parameters = new ArrayList<>();

        parameters.add("long,LOGIN_BLOCKING_INTERVAL_IN_SECONDS," + LOGIN_BLOCKING_INTERVAL_IN_SECONDS);
        parameters.add("int,NUMBER_OF_FAILED_LOGIN_ATTEMPTS_BEFORE_BLOCKING," + NUMBER_OF_FAILED_LOGIN_ATTEMPTS_BEFORE_BLOCKING);
        parameters.add("int,PRODUCT_IN_STOCK_DEFAULT_VALUE," + PRODUCT_IN_STOCK_DEFAULT_VALUE);
        parameters.add("int,USER_LOGIN_TIMEOUT," + USER_LOGIN_TIMEOUT);
        parameters.add("string,ALLOW_USER_CONFIGURATION," + ALLOW_USER_CONFIGURATION);
        parameters.add("int,MAX_CONCURRENT_SESSIONS," + MAX_CONCURRENT_SESSIONS);

        return parameters;
    }

    public GetAccountConfigurationResponse getAllConfigurationParameters() {
        GetAccountConfigurationResponse getAccountConfigurationResponse = new GetAccountConfigurationResponse();

        getAccountConfigurationResponse.setNumberOfFailedLoginAttemptsBeforeBlocking(this.getNumberOfLoginAttemptsBeforeBlocking());
        getAccountConfigurationResponse.setLoginBlockingIntervalInSeconds(this.getLoginBlockingIntervalInSeconds());
        getAccountConfigurationResponse.setProductInStockDefaultValue(this.getProductInStockDefaultValue());
        getAccountConfigurationResponse.setUserLoginTimeout(this.getUserLoginTimeout());
        getAccountConfigurationResponse.setAllowUserConfiguration(this.getAllowUserConfiguration().equalsIgnoreCase("yes"));
        getAccountConfigurationResponse.setMaxConcurrentSessions(this.getMaxConcurrentSessions());

        getAccountConfigurationResponse.setUserSecondWsdl(false);   //  MOVED to user-level

        return getAccountConfigurationResponse;
    }
}
