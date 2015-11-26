package com.advantage.online.store.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;

/**
 * =================================================
 * Testing Application User Configuration
 * =================================================
 * User configuration
 * @author Binyamin Regev on 26/11/2015.
 */
@Configuration
public class AppUserConfig {
    private final String ENV_USER_LOGIN_BLOCKING = "user.login.blocking";
    private final String ENV_EMAIL_ADDRESS_IN_LOGIN = "email.address.in.login";
    private final String ENV_NUMBER_OF_LOGIN_TRIES_BEFORE_BLOCKING = "number.of.login.tries.before.blocking";

    @Inject
    private Environment env;

    private String stringValue;

    /**
     * Get value of {@link String} {@code stringValue} private variable.
     * @return Value of {@code stringValue} private variable.
     */
    public String getStringValue() { return stringValue; }

    /**
     * Set private {@link String} variable.
     * @param stringValue {@link String} value to set as value of {@code stringValue} private variable.
     */
    public void setStringValue(String stringValue) { this.stringValue = stringValue; }

    /**
     * <ul>Get configuration value:</ul> <br/>
     * How much time the user is blocked from attempting login again? Data in milli-seconds.
     * @return How much time the user is blocked from attempting login again?
     */
    public int getLoginBlockingInMilliseconds() {
        this.setStringValue(env.getProperty(ENV_USER_LOGIN_BLOCKING));
        if (this.getStringValue() != null) {
            return Integer.valueOf(this.getStringValue());
        }
        else {
            return 0;
        }
    }

    /**
     * <ul>Get configuration value:</ul> <br/>
     * Number of unsuccessful login attempts before blocking the user from attempting to login again.
     * @return Number of unsuccessful login attempts.
     */
    public int getNumberOfLoginTriesBeforeBlocking() {
        this.setStringValue(env.getProperty(ENV_NUMBER_OF_LOGIN_TRIES_BEFORE_BLOCKING));
        if (this.getStringValue() == null) {
            return 0;
        }
        return Integer.valueOf(this.getStringValue());
    }

    /**
     * <ul>Get configuration value:</ul> <br/>
     * Whether {@code e-mail} field is displayed in user login page. Valid values are &quat;YES&quat; or &quat;NO&quat;.
     * In case of &quat;YES&quat; {@code e-mail} becomes mandatory field in creating a new user.
     * @return Whether {@code e-mail} field is displayed in user login page.
     */
    public String getEmailAddressInLogin() {
        this.setStringValue(env.getProperty(ENV_EMAIL_ADDRESS_IN_LOGIN));

        return (this.getStringValue() != null ? this.getStringValue() : null);
    }

}
