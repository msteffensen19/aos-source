package com.advantage.common.util;

import com.advantage.common.Constants;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * @author Binyamin Regev on 19/11/2015.
 */
public class ValidationHelper {
    private static final String COLOR_HEX_PATTERN = "^#([A-Fa-f0-9]{1,6})$";

    private static final String FULL_NAME_PATTERN = "^[\\p{L} .'-]+$";

    private static final String MASTER_CREDIT_CVV_NUMBER_PATTERN = "([0-9]{3})";

    private static final String MASTER_CREDIT_CARD_NUMBER_PATTERN = "([0-9]{16})";
    private static final String MASTER_CREDIT_ACCOUNT_NUMBER_PATTERN = "([0-9]{12})";

    //private static final String PHONE_PATTERN = "^\\+([0-9]{1,3})?[-.\\s]\\(?([0-9]{1,3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
    //private static final String PHONE_PATTERN = "((\\+([1-9]{1}[0-9]{0,3})|00[1-9]{3})[-.\\s]?)?\\(?([0-9]{1,3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
    private static final String PHONE_PATTERN = "((\\+([1-9]{1}[0-9]{0,3}))?[-.\\s]?)\\(?([0-9]{1,3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    //  Contain 3-15 characters of: Digits 0-9, UPPER case (A-Z), lower case (a-z), underscore (_), dot (.) and hyphen (-)
    private static final String LOGIN_USER_NAME_PATTERN = "^[A-Za-z0-9_.-]{3,15}$";

    /**
     * <ul><code>Password</code> must be compliant with <b>AOS policy</b></ul>:
     * Password length must be 5 to 10 characters.
     * Password must contain one or more numerical digits and
     * both UPPER-CASE and lower-case characters (case sensitivity)
     */
    //private static final String LOGIN_PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%\\.\\-\\+\\*]).{6,20})";
    private static final String LOGIN_PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,10})";

    private static final String TIME_24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";

    //  EUROPEAN_DATE_FORMAT = "dd.MM.yyyy" ; AMERICAN_DATE_FORMAT = "MM/dd/yyyy" ; SCANDINAVIAN_DATE_FORMAT = "yyyy-MM-dd"
    private static final String AMERICAN_DATE_PATTERN = "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)";
    private static final String EUROPEAN_DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01]).(0?[1-9]|1[012]).((19|20)\\d\\d)";
    private static final String SCANDINAVIAN_DATE_PATTERN = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])"; //YYYY-MM-DD
    //private static final String SAFE_PAY_ACCOUNT_NUMBER = "843200971";
    private static final String SAFE_PAY_ACCOUNT_NUMBER_PATTERN = "([0-9]{9})";
    private static final String CURRENCY_PATTERN = "[A-Z]{3}";

    private static Pattern pattern;

    public ValidationHelper() {
    }

    /**
     * Checks that {@code userName} is a valid user-name and in compliance with AOS policy.
     */
    public static boolean isValidLogin(final String userName) {
        return isValidByRegExpPattern(LOGIN_USER_NAME_PATTERN, userName);
    }

    /**
     * Checks that {@code password} is a valid password and in compliance AOS policy.
     */
    public static boolean isValidPassword(final String password) {
        return isValidByRegExpPattern(LOGIN_PASSWORD_PATTERN, password);
    }

    public static boolean isValidPhoneNumber(final String phoneNumber) {
        return isValidByRegExpPattern(PHONE_PATTERN, phoneNumber);
    }

    /**
     * Check that {@code e-mail} is a valid e-mail address.
     */
    public static boolean isValidEmail(final String email) {
        return isValidByRegExpPattern(EMAIL_PATTERN, email);
    }

    /**
     * Check that {@code time24h} is a valid 24-hours time format.
     */
    public static boolean isValidTime24h(final String time24h) {
        return isValidByRegExpPattern(TIME_24HOURS_PATTERN, time24h);
    }

    private static boolean isValidByRegExpPattern(String regExp, String string) {
        Pattern pattern = Pattern.compile(regExp);

        final boolean isValid = pattern.matcher(string).matches();
        System.out.println(string + " : " + isValid);

        return isValid;
    }
    /**
     * Check that {@code stringDate} is a valid date format, either EUROPEAN, AMERICAN or SCANDINAVIAN.
     */
    public static boolean isValidDate(final String stringDate) {
        SimpleDateFormat dateFormat;

        if (Pattern.compile(AMERICAN_DATE_PATTERN).matcher(stringDate).matches()) {
            dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        } else if (Pattern.compile(EUROPEAN_DATE_PATTERN).matcher(stringDate).matches()) {
            dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        } else if (Pattern.compile(SCANDINAVIAN_DATE_PATTERN).matcher(stringDate).matches()) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        } else {
            //  invalid date format
            return false;
        }

        dateFormat.setLenient(false);
        try {
            dateFormat.parse(stringDate.trim());
        } catch (ParseException pe) {
            System.out.println(stringDate + " : False");
            return false;
        }

        System.out.println(stringDate + " : True");
        return true;
    }

    /**
     * Validates <b>MasterCredit</b> card number.
     */
    public static boolean isValidMasterCreditCardNumber(final String cardNumber) {
        pattern = Pattern.compile(MASTER_CREDIT_CARD_NUMBER_PATTERN);

        final boolean isValid = pattern.matcher(cardNumber).matches();
        System.out.println("MasterCredit card number=" + cardNumber + " is valid? " + isValid);
        return isValid;
    }

    /**
     * Validates <b>MasterCredit</b> CVV number.
     */
    public static boolean isValidMasterCreditCVVNumber(final String cvvNumber) {
        pattern = Pattern.compile(MASTER_CREDIT_CVV_NUMBER_PATTERN);
        final boolean isValid = pattern.matcher(cvvNumber).matches();
        System.out.println("MasterCredit CVV number=" + cvvNumber + " is valid? " + isValid);
        return isValid;
    }

    /**
     * Validates <b>MasterCredit</b> card holder full name.
     */
    public static boolean isValidFullName(final String fullName) {
        pattern = Pattern.compile(FULL_NAME_PATTERN);
        final boolean isValid = pattern.matcher(fullName).matches();
        System.out.println("Full name=\"" + fullName + "\" is valid? " + isValid);
        return isValid;
    }

    /**
     * Validates <b>MasterCredit</b> account number.
     */
    public static boolean isValidMasterCreditAccountNumber(final String accountNumber) {
        //  Validate MasterCredit account number
        pattern = Pattern.compile(MASTER_CREDIT_ACCOUNT_NUMBER_PATTERN);

        final boolean isValid = pattern.matcher(accountNumber).matches();

        System.out.println("MasterCredit account number=" + accountNumber + " is valid? " + isValid);

        return isValid;
    }

    /**
     * Validates <b>MasterCredit</b> account number.
     */
    public static boolean isValidSafePayAccountNumber(final String accountNumber) {
        pattern = Pattern.compile(SAFE_PAY_ACCOUNT_NUMBER_PATTERN);

        final boolean isValid = pattern.matcher(accountNumber).matches();
        System.out.println("SafePay account number=" + accountNumber + " is valid? " + isValid);

        return isValid;
    }

    /**
     * Validate currency code. For now only <i>USD</i> is a valid currency code.
     */
    public static boolean isValidCurrency(final String currency) {
        return isValidByRegExpPattern(CURRENCY_PATTERN,currency);
    }

    /**
     * Check the users authorization
     *
     * @param session {@link HttpSession} value  from HTTP request
     * @param token   {@link String} token key
     * @return <b>true</b> when {@code token} is a valid
     */
    public static boolean isAuthorized(HttpSession session, String token) {
        return !(session.getAttribute(Constants.UserSession.IS_SUCCESS) == null ||
                session.getAttribute(Constants.UserSession.TOKEN) == null ||
                !(Boolean) session.getAttribute(Constants.UserSession.IS_SUCCESS) ||
                session.getAttribute(Constants.UserSession.TOKEN).toString().compareTo(token) != 0);
    }

    public static void main(String[] args) {

        //  Date
        ValidationHelper.isValidDate("29.02.2012");         //  valid EUROPEAN date-format
        ValidationHelper.isValidDate("29.02.2011");         //  invalid EUROPEAN date-format: no Feb 29th in 2011

        ValidationHelper.isValidDate("02/29/2012");         //  valid AMERICAN date-format
        ValidationHelper.isValidDate("02/29/2011");         //  invalid AMERICAN date-format: no Feb 29th in 2011

        ValidationHelper.isValidDate("2012-02-29");         //  valid SCANDINAVIAN date-format
        ValidationHelper.isValidDate("2011-02-29");         //  invalid SCANDINAVIAN date-format: no Feb 29th in 2011

        //  24-hours time
        ValidationHelper.isValidTime24h("11:11:11");        //  true
        ValidationHelper.isValidTime24h("12:34:56");        //  true
        ValidationHelper.isValidTime24h("23:59:59");        //  true
        ValidationHelper.isValidTime24h("23:59:60");        //  false
        ValidationHelper.isValidTime24h("24:00:00");        //  false

        //  login username
        ValidationHelper.isValidLogin("king_david");        //  true
        ValidationHelper.isValidLogin("king.david");        //  true
        ValidationHelper.isValidLogin("king7david");        //  true
        ValidationHelper.isValidLogin("king david");        //  false. SPACE is not allowed

        //  login user password
        ValidationHelper.isValidPassword("King1david");     //  true
        ValidationHelper.isValidPassword("king2David");     //  true
        ValidationHelper.isValidPassword("kingDavid12");    //  false. Password too long

        //  e-mail address
        ValidationHelper.isValidEmail("a#b.com");           //  false, "#" is invalid character
        ValidationHelper.isValidEmail("a@b.com");           //  true
        ValidationHelper.isValidEmail("king.david@gov.il"); //  true

        //  International phone number
        ValidationHelper.isValidPhoneNumber("+1 123 456 7890");
        ValidationHelper.isValidPhoneNumber("+972 54 123 4567");
        ValidationHelper.isValidPhoneNumber("+972 54 1234567");
        ValidationHelper.isValidPhoneNumber("+44 123 4567890");
        ValidationHelper.isValidPhoneNumber("+44 1234567890");
        ValidationHelper.isValidPhoneNumber("+441234567890");

        //  All are public figures
        ValidationHelper.isValidFullName("King David");
        ValidationHelper.isValidFullName("Solomon Ben-David");
        ValidationHelper.isValidFullName("Ben E. King");
        ValidationHelper.isValidFullName("James T. Kirk");
        ValidationHelper.isValidFullName("G'kar");

        ValidationHelper.isValidMasterCreditCVVNumber("404");   //  "0" (zero)
        ValidationHelper.isValidMasterCreditCVVNumber("4O4");   //  "O" (UPPER case) is not numeric
        ValidationHelper.isValidMasterCreditCVVNumber("777");
    }
}
