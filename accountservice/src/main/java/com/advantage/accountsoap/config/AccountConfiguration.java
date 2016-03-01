package com.advantage.accountsoap.config;

import com.advantage.accountsoap.dto.account.AccountConfigurationStatusResponse;
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
    private final String ENV_ADD_EMAIL_FIELD_TO_LOGIN = "email.address.in.login";
    private final String ENV_NUMBER_OF_LOGIN_TRIES_BEFORE_BLOCKING = "number.of.login.tries.before.blocking";
    private final String ENV_PRODUCT_INSTOCK_DEFAULT_VALUE = "product.inStock.default.value";
    private final String ENV_USER_SECOND_WSDL_VALUE = "user.second.wsdl";
    private final String ENV_USER_LOGIN_TIMEOUT = "user.login.timeout";
    private final String ENV_ADMIN_DEMO_APP_CONFIG_ALLOWED = "admin.demo.app.config.Allowed";

    @Inject
    private Environment env;

    //private AppUserConfig appUserConfig = new AppUserConfig();

    public static int NUMBER_OF_FAILED_LOGIN_ATTEMPTS_BEFORE_BLOCKING;  //numberOfFailedLoginAttemptsBeforeBlocking
    public static long LOGIN_BLOCKING_INTERVAL_IN_MILLISECONDS;         //loginBlockingIntervalInMilliSeconds
    public static String EMAIL_ADDRESS_IN_LOGIN;                        //emailAddressInLogin
    public static int PRODUCT_IN_STOCK_DEFAULT_VALUE;
    public static String USER_SECOND_WSDL_VALUE;
    public static int USER_LOGIN_TIMEOUT;
    public static String ADMIN_DEMO_APP_CONFIG_ALLOWED;

    //  //  Class that is called must have a method "public void init() throws Exception"
    //@Bean(initMethod = "init")
    //public AppUserConfig initAppUserConfiguration() {
    //    return new AppUserConfig();
    //}

    @Bean
    public int getAppUserConfiguration() {
        this.setNumberOfLoginAttemptsBeforeBlocking(ENV_NUMBER_OF_LOGIN_TRIES_BEFORE_BLOCKING);
        this.setLoginBlockingIntervalInMilliseconds(ENV_USER_LOGIN_BLOCKING);
        this.setEmailAddressInLogin(ENV_ADD_EMAIL_FIELD_TO_LOGIN);
        this.setProductInStockDefaultValue(ENV_PRODUCT_INSTOCK_DEFAULT_VALUE);
        this.setUserSecondWsdlValue(ENV_USER_SECOND_WSDL_VALUE);
        this.setUserLoginTimeout(ENV_USER_LOGIN_TIMEOUT);
        this.setAdminDemoAppConfigAllowed(ENV_ADMIN_DEMO_APP_CONFIG_ALLOWED);

        System.out.println("Configuration: LOGIN_BLOCKING_INTERVAL_IN_MILLISECONDS=" + this.getLoginBlockingIntervalInMilliseconds());
        System.out.println("Configuration: NUMBER_OF_FAILED_LOGIN_ATTEMPTS_BEFORE_BLOCKING=" + this.getNumberOfLoginAttemptsBeforeBlocking());
        System.out.println("Configuration: EMAIL_ADDRESS_IN_LOGIN=\"" + this.getEmailAddressInLogin() + "\"");
        System.out.println("Configuration: PRODUCT_IN_STOCK_DEFAULT_VALUE=\"" + this.getProductInStockDefaultValue() + "\"");
        System.out.println("Configuration: USER_SECOND_WSDL_VALUE=\"" + this.getUserSecondWsdlValue() + "\"");
        System.out.println("Configuration: USER_LOGIN_TIMEOUT=\"" + this.getUserLoginTimeout() + "\"");
        System.out.println("Configuration: ADMIN_DEMO_APP_CONFIG_ALLOWED=\"" + this.getAdminDemoAppConfigAllowed() + "\"");

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
     * How much time the user is blocked from attempting login again? Data in milli-seconds.
     *
     * @return How much time the user is blocked from attempting login again?
     */
    public long getLoginBlockingIntervalInMilliseconds() {
        return this.LOGIN_BLOCKING_INTERVAL_IN_MILLISECONDS;
    }

    private void setLoginBlockingIntervalInMilliseconds(final String parameterKey) {
        String parameterValue = env.getProperty(parameterKey);
        this.LOGIN_BLOCKING_INTERVAL_IN_MILLISECONDS = (parameterValue != null ? Integer.valueOf(parameterValue) : 0);
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

    private void setEmailAddressInLogin(final String parameterKey) {
        String parameterValue = env.getProperty(parameterKey);
        this.EMAIL_ADDRESS_IN_LOGIN = (parameterValue.isEmpty() ? "" : parameterValue );
    }

    public int getProductInStockDefaultValue() {
        return this.PRODUCT_IN_STOCK_DEFAULT_VALUE;
    }

    private void setProductInStockDefaultValue(final String parameterKey) {
        String parameterValue = env.getProperty(parameterKey);
        this.PRODUCT_IN_STOCK_DEFAULT_VALUE = (parameterValue != null ? Integer.valueOf(parameterValue) : 0);
    }

    public void setUserSecondWsdlValue(String parameterKey) {
        this.USER_SECOND_WSDL_VALUE = env.getProperty(parameterKey);
    }

    public String getUserSecondWsdlValue() {
        return this.USER_SECOND_WSDL_VALUE;
    }

    /**
     * User login timeout parameter value in minutes, default 60 minutes.
     */
    public void setUserLoginTimeout(final String parameterKey) {
        String parameterValue = env.getProperty(parameterKey);
        this.USER_LOGIN_TIMEOUT = (parameterValue != null ? Integer.valueOf(parameterValue) : 0);
    }

    public int getUserLoginTimeout() {
        return this.USER_LOGIN_TIMEOUT;
    }

    public void setAdminDemoAppConfigAllowed(final String parameterKey) {
        this.ADMIN_DEMO_APP_CONFIG_ALLOWED = env.getProperty(parameterKey);
    }

    public String getAdminDemoAppConfigAllowed() {
        return this.ADMIN_DEMO_APP_CONFIG_ALLOWED;
    }

    public List<String> getAllAccountParameters() {
        List<String> parameters = new ArrayList<>();

        parameters.add("boolean,EMAIL_ADDRESS_IN_LOGIN," + (EMAIL_ADDRESS_IN_LOGIN.toUpperCase() == "YES" ? true : false));
        parameters.add("long,LOGIN_BLOCKING_INTERVAL_IN_MILLISECONDS," + LOGIN_BLOCKING_INTERVAL_IN_MILLISECONDS);
        parameters.add("int,NUMBER_OF_FAILED_LOGIN_ATTEMPTS_BEFORE_BLOCKING," + NUMBER_OF_FAILED_LOGIN_ATTEMPTS_BEFORE_BLOCKING);
        parameters.add("int,PRODUCT_IN_STOCK_DEFAULT_VALUE," + PRODUCT_IN_STOCK_DEFAULT_VALUE);
        parameters.add("string,USER_SECOND_WSDL_VALUE," + USER_SECOND_WSDL_VALUE);
        parameters.add("int,USER_LOGIN_TIMEOUT," + USER_LOGIN_TIMEOUT);
        parameters.add("string,ADMIN_DEMO_APP_CONFIG_ALLOWED," + ADMIN_DEMO_APP_CONFIG_ALLOWED);

        return parameters;
    }

    public AccountConfigurationStatusResponse getAllConfigurationParameters() {
        return new AccountConfigurationStatusResponse(this.getNumberOfLoginAttemptsBeforeBlocking(),
                this.getLoginBlockingIntervalInMilliseconds(),
                this.getEmailAddressInLogin().equalsIgnoreCase("yes"),
                this.getProductInStockDefaultValue(),
                this.getUserSecondWsdlValue().equalsIgnoreCase("yes"),
                this.getUserLoginTimeout(),
                this.getAdminDemoAppConfigAllowed().equalsIgnoreCase("yes"));

    }
}
