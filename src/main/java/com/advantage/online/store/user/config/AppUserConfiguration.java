package com.advantage.online.store.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;

/**
 * =================================================
 * Testing Application User Configuration
 * =================================================
 * Application User configuration class
 * @author Binyamin Regev on 24/11/2015.
 */
@Configuration
public class AppUserConfiguration {
    private final String ENV_USER_LOGIN_BLOCKING = "user.login.blocking";
    private final String ENV_ADD_EMAIL_FIELD_TO_LOGIN = "email.address.in.login";
    private final String ENV_NUMBER_OF_LOGIN_TRIES_BEFORE_BLOCKING = "number.of.login.tries.before.blocking";

    @Inject
    private Environment env;

    private AppUserConfig appUserConfig = new AppUserConfig();

    //  In milliseconds
    private int loginBlockingInMilliseconds;
    private int numberOfLoginTriesBeforeBlocking;
    private String emailAddressInLogin;             //  in UPPER case: YES / NO

    public AppUserConfiguration() {


//        stringValue = env.getProperty(ENV_ADD_EMAIL_FIELD_TO_LOGIN);
//        if (stringValue != null) {
//            stringValue = stringValue.toUpperCase();
//            this.setEmailAddressInLogin(stringValue);
//        }
    }

    public void getAppUserConfiguration() {
        this.loginBlockingInMilliseconds = appUserConfig.getLoginBlockingInMilliseconds();
        this.numberOfLoginTriesBeforeBlocking = appUserConfig.getNumberOfLoginTriesBeforeBlocking();
        this.emailAddressInLogin = appUserConfig.getEmailAddressInLogin();
    }

//    public int getLoginBlockingInMilliseconds() {
//        return loginBlockingInMilliseconds;
//    }
//
//    public void setLoginBlockingInMilliseconds(int loginBlockingInMilliseconds) {
//        this.loginBlockingInMilliseconds = loginBlockingInMilliseconds;
//    }
//
//    public int getNumberOfLoginTriesBeforeBlocking() {
//        return numberOfLoginTriesBeforeBlocking;
//    }
//
//    public void setNumberOfLoginTriesBeforeBlocking(int numberOfLoginTriesBeforeBlocking) {
//        this.numberOfLoginTriesBeforeBlocking = numberOfLoginTriesBeforeBlocking;
//    }
//
//    public String getLoginBlockingIntervalString() {
//        return loginBlockingIntervalString;
//    }
//
//    public void setLoginBlockingIntervalString(String loginBlockingIntervalString) {
//        this.loginBlockingIntervalString = loginBlockingIntervalString;
//    }
//
//    public String getEmailAddressInLogin() { return emailAddressInLogin; }
//
//    public void setEmailAddressInLogin(String emailAddressInLogin) {
//        this.emailAddressInLogin = emailAddressInLogin;
//    }
//
//    private String convertMillisecondsToStringInterval(int milliSeconds) {
//        final int SECONDS_IN_A_MINUTE = 60;
//        final int SECONDS_IN_A_HOUR = 3600;
//        final int SECONDS_IN_A_DAY = 86400;
//
//        int seconds = milliSeconds / 1000;
//        int numberOfDays = 0;
//        int numberOfHours = 0;
//        int numberOfMinutes = 0;
//
//        if (seconds >= SECONDS_IN_A_DAY) {
//            numberOfDays = seconds / SECONDS_IN_A_DAY;
//            seconds %= SECONDS_IN_A_DAY;
//        }
//
//        if (seconds >= SECONDS_IN_A_HOUR) {
//            numberOfHours = seconds / SECONDS_IN_A_HOUR;
//            seconds %= SECONDS_IN_A_HOUR;
//        }
//
//        if (seconds >= SECONDS_IN_A_MINUTE) {
//            numberOfMinutes = seconds / SECONDS_IN_A_MINUTE;
//            seconds %= SECONDS_IN_A_MINUTE;
//        }
//
//        return (numberOfDays > 0 ? numberOfDays + " days " : "") +
//                (numberOfHours > 0 ? numberOfHours + " hours " : "") +
//                (numberOfMinutes > 0 ? numberOfMinutes + " minutes" : "") +
//                (seconds > 0 ? seconds + " seconds" : "");
//    }




//    public static void main(String[] args) {
//        AppUserConfiguration appUserConfiguration = new AppUserConfiguration();
//
//        System.out.println("loginBlockingInMilliseconds=" + appUserConfiguration.getLoginBlockingInMilliseconds());
//        System.out.println("numberOfLoginTriesBeforeBlocking=" + appUserConfiguration.getNumberOfLoginTriesBeforeBlocking());
//        System.out.println("loginBlockingIntervalString=\"" + appUserConfiguration.getLoginBlockingIntervalString() + "\"");
//    }
}
