package com.advantage.root.store.user.util;

import com.advantage.root.string_resources.Constants;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * @author Binyamin Regev on 19/11/2015.
 */
public class ValidationHelper {
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


    private static Pattern pattern;

    public ValidationHelper() {

    }

    /**
     * Checks that {@code userName} is a valid user-name and in compliance with AOS policy.
     *
     * @param userName {@link String} to verify as a valid user-name for login.
     * @return <b>true</b> when {@code userName} is valid, otherwise <b>false</b>.
     */
    public static boolean isValidLogin(final String userName) {
        pattern = Pattern.compile(LOGIN_USER_NAME_PATTERN);

        final boolean isValid = pattern.matcher(userName).matches();
        System.out.println(userName + " : " + isValid);

        return isValid;

    }

    /**
     * Checks that {@code password} is a valid password and in compliance AOS policy.
     *
     * @param password {@link String} to verify as a password for login.
     * @return <b>true</b> when {@code password} is valid, otherwise <b>false</b>.
     */
    public static boolean isValidPassword(final String password) {
        pattern = Pattern.compile(LOGIN_PASSWORD_PATTERN);

        final boolean isValid = pattern.matcher(password).matches();
        System.out.println(password + " : " + isValid);

        return isValid;

    }

    public static boolean isValidPhoneNumber(final String phoneNumber) {
        pattern = Pattern.compile(PHONE_PATTERN);

        final boolean isValid = pattern.matcher(phoneNumber).matches();
        System.out.println(phoneNumber + " : " + isValid);

        return isValid;
    }

    /**
     * Check that {@code e-mail} is a valid e-mail address.
     *
     * @param email {@link String} to verify as a valid e-mail address.
     * @return <b>true</b> when {@code e-mail} is valid, otherwise <b>false</b>.
     */
    public static boolean isValidEmail(final String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);

        final boolean isValid = pattern.matcher(email).matches();
        System.out.println(email + " : " + isValid);

        return isValid;

    }

    /**
     * Check that {@code time24h} is a valid 24-hours time format.
     *
     * @param time24h {@link String} to verify as a having a valid 24-hours time format.
     * @return <b>true</b> when {@code time24h} is a valid time format, otherwise <b>false</b>.
     */
    public static boolean isValidTime24h(final String time24h) {
        pattern = Pattern.compile(TIME_24HOURS_PATTERN);

        final boolean isValid = pattern.matcher(time24h).matches();
        System.out.println(time24h + " : " + isValid);

        return isValid;

    }

    /**
     * Check that {@code stringDate} is a valid date format, either EUROPEAN, AMERICAN or SCANDINAVIAN.
     * <br/>
     *
     * @param stringDate {@link String} verify as having a valid date format.
     * @return <b>true</b> when {@code stringDate} is a valid date format, otherwise <b>false</b>.
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

    }
}
