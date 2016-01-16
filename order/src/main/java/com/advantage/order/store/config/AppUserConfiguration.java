package com.advantage.order.store.config;

import com.advantage.common.Constants;
import com.advantage.common.dto.AppUserConfigurationResponseStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Application User configuration class
 *
 * @author Binyamin Regev on 24/11/2015.
 */
@Configuration
public class AppUserConfiguration {

    @Inject
    private Environment env;

    //private AppUserConfig appUserConfig = new AppUserConfig();

    public static int NUMBER_OF_FAILED_LOGIN_ATTEMPTS_BEFORE_BLOCKING;  //numberOfFailedLoginAttemptsBeforeBlocking
    public static long LOGIN_BLOCKING_INTERVAL_IN_MILLISECONDS;         //loginBlockingIntervalInMilliSeconds
    public static String EMAIL_ADDRESS_IN_LOGIN;                        //emailAddressInLogin
    public static int PRODUCT_IN_STOCK_DEFAULT_VALUE;

//      //  Class that is called must have a method "public void init() throws Exception"
//    @Bean(initMethod = "init")
//    public AppUserConfig initAppUserConfiguration() {
//        return new AppUserConfig();
//    }

    @Bean
    public int getAppUserConfiguration() {
        this.setNumberOfLoginAttemptsBeforeBlocking(Constants.ENV_NUMBER_OF_LOGIN_TRIES_BEFORE_BLOCKING);
        this.setLoginBlockingIntervalInMilliseconds(Constants.ENV_USER_LOGIN_BLOCKING);
        this.setEmailAddressInLogin(Constants.ENV_ADD_EMAIL_FIELD_TO_LOGIN);
        this.setProductInStockDefaultValue(Constants.ENV_PRODUCT_INSTOCK_DEFAULT_VALUE);

        System.out.println("Configuration: LOGIN_BLOCKING_INTERVAL_IN_MILLISECONDS=" + this.getLoginBlockingIntervalInMilliseconds());
        System.out.println("Configuration: NUMBER_OF_FAILED_LOGIN_ATTEMPTS_BEFORE_BLOCKING=" + this.getNumberOfLoginAttemptsBeforeBlocking());
        System.out.println("Configuration: getEmailAddressInLogin=\"" + this.getEmailAddressInLogin() + "\"");

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

    private void setNumberOfLoginAttemptsBeforeBlocking(final String environmentKey) {
        this.NUMBER_OF_FAILED_LOGIN_ATTEMPTS_BEFORE_BLOCKING = (env.getProperty(environmentKey) != null ? Integer.valueOf(env.getProperty(environmentKey)) : 0);
    }

    /**
     * <ul>Get configuration value:</ul> <br/>
     * How much time the user is blocked from attempting login again? Data in milli-seconds.
     *
     * @return How much time the user is blocked from attempting login again?
     */
    public long getLoginBlockingIntervalInMilliseconds() {
        return this.LOGIN_BLOCKING_INTERVAL_IN_MILLISECONDS;
    }

    private void setLoginBlockingIntervalInMilliseconds(final String environmentKey) {
        this.LOGIN_BLOCKING_INTERVAL_IN_MILLISECONDS = (env.getProperty(environmentKey) != null ? Integer.valueOf(env.getProperty(environmentKey)) : 0);
    }

    /**
     * <ul>Get configuration value:</ul> <br/>
     * Whether {@code e-mail} field is displayed in user login page. Valid values are &quat;YES&quat; or &quat;NO&quat;.
     * In case of &quat;YES&quat; {@code e-mail} becomes mandatory field in creating a new user.
     *
     * @return Whether {@code e-mail} field is displayed in user login page.
     */
    public String getEmailAddressInLogin() {
        return this.EMAIL_ADDRESS_IN_LOGIN;
    }

    private void setEmailAddressInLogin(final String environmentKey) {
        this.EMAIL_ADDRESS_IN_LOGIN = (env.getProperty(environmentKey) != null ? env.getProperty(environmentKey) : "");
    }

    public int getProductInStockDefaultValue() {
        return this.PRODUCT_IN_STOCK_DEFAULT_VALUE;
    }

    private void setProductInStockDefaultValue(final String environmentKey) {
        this.PRODUCT_IN_STOCK_DEFAULT_VALUE = (env.getProperty(environmentKey) != null ? Integer.valueOf(env.getProperty(environmentKey)) : 0);
    }

    public List<String> getAllParameters() {
        List<String> parameters = new ArrayList<String>();

        parameters.add("boolean,EMAIL_ADDRESS_IN_LOGIN," + (EMAIL_ADDRESS_IN_LOGIN.toUpperCase() == "YES" ? true : false));
        parameters.add("long,LOGIN_BLOCKING_INTERVAL_IN_MILLISECONDS," + LOGIN_BLOCKING_INTERVAL_IN_MILLISECONDS);
        parameters.add("int,NUMBER_OF_FAILED_LOGIN_ATTEMPTS_BEFORE_BLOCKING," + NUMBER_OF_FAILED_LOGIN_ATTEMPTS_BEFORE_BLOCKING);
        parameters.add("int,PRODUCT_IN_STOCK_DEFAULT_VALUE," + PRODUCT_IN_STOCK_DEFAULT_VALUE);

        return parameters;
    }

    public AppUserConfigurationResponseStatus getAllConfigurationParameters() {
        return new AppUserConfigurationResponseStatus(this.getNumberOfLoginAttemptsBeforeBlocking(),
                this.getLoginBlockingIntervalInMilliseconds(),
                (this.getEmailAddressInLogin().toUpperCase() == "YES"),
                this.getProductInStockDefaultValue());

    }

}
