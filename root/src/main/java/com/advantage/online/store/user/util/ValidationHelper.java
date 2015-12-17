package com.advantage.online.store.user.util;

import com.advantage.online.store.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * @author Binyamin Regev on 19/11/2015.
 */
public class ValidationHelper {
    private static final String COLOR_HEX_PATTERN = "^#([A-Fa-f0-9]{1,6})$";

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

    //  Credit/Debit cards numbers validation patterns
    //  Visa: 13 or 16 digits, starting with 4.
    private static final String VISA_CARD_NUMBER_PATTERN = "^4[0-9]{12}(?:[0-9]{3})?$";
    //  MasterCard: 16 digits, starting with 51 through 55.
    private static final String MASTER_CARD_NUMBER_PATTERN = "^5[1-5][0-9]{14}$";
    //  American Express(AMEX): 15 digits, starting with 34 or 37.
    private static final String AMEX_CARD_NUMBER_PATTERN = "^3[47][0-9]{13}$";
    private static final String CARTE_BLANCHE_CARD_NUMBER_PATTERN = "^389[0-9]{11}$";
    //  Diners Club: 14 digits, starting with 300 through 305, 36, or 38.
    private static final String DINERS_CLUB_CARD_NUMBER_PATTERN = "^3(?:0[0-5]|[68][0-9])[0-9]{11}$";
    //  Discover: 16 digits, starting with 6011 or 65.
    private static final String DISCOVER_CARD_NUMBER_PATTERN = "^65[4-9][0-9]{13}|64[4-9][0-9]{13}|6011[0-9]{12}|(622(?:12[6-9]|1[3-9][0-9]|[2-8][0-9][0-9]|9[01][0-9]|92[0-5])[0-9]{10})$";
    //  JCB: 15 digits, starting with 2131 or 1800, or 16 digits starting with 35.
    private static final String JCB_CARD_NUMBER_PATTERN = "^(?:2131|1800|35\\d{3})\\d{11}$";
    private static final String INSTA_PAYMENT_CARD_NUMBER_PATTERN = "^63[7-9][0-9]{13}$";
    private static final String LAZER_CARD_NUMBER_PATTERN = "^(6304|6706|6709|6771)[0-9]{12,15}$";
    private static final String MAESTRO_CARD_NUMBER_PATTERN = "^(5018|5020|5038|6304|6759|6761|6763)[0-9]{8,15}$";
    private static final String SOLO_CARD_NUMBER_PATTERN = "^(6334|6767)[0-9]{12}|(6334|6767)[0-9]{14}|(6334|6767)[0-9]{15}$";
    private static final String SWITCH_CARD_NUMBER_PATTERN = "^(4903|4905|4911|4936|6333|6759)[0-9]{12}|(4903|4905|4911|4936|6333|6759)[0-9]{14}|(4903|4905|4911|4936|6333|6759)[0-9]{15}|564182[0-9]{10}|564182[0-9]{12}|564182[0-9]{13}|633110[0-9]{10}|633110[0-9]{12}|633110[0-9]{13}$";
    private static final String UNION_PAY_CARD_NUMBER_PATTERN = "^(62[0-9]{14,17})$";
    private static final String KOREAN_LOCAL_CARD_NUMBER_PATTERN = "^9[0-9]{15}$";
    private static final String BCGLOBAL_CARD_NUMBER_PATTERN = "^(6541|6556)[0-9]{12}$";
    private static final String MESSAGE_UNKNOWN_CREADIT_DEBIT_CARD_COMPANY = "Unknown credit/debit card company";


    private static Pattern pattern;

    public ValidationHelper() {

    }

    /**
     * Checks that {@code userName} is a valid user-name and in compliance with AOS policy.
     * @param userName {@link String} to verify as a valid user-name for login.
     * @return <b>true</b> when {@code userName} is valid, otherwise <b>false</b>.
     */
    public static boolean isValidLogin(final String userName) {
        pattern = Pattern.compile(LOGIN_USER_NAME_PATTERN);

        final boolean isValid = pattern.matcher(userName).matches();
        System.out.println(userName +" : "+ isValid);

        return isValid;

    }

    /**
     * Checks that {@code password} is a valid password and in compliance AOS policy.
     * @param password {@link String} to verify as a password for login.
     * @return <b>true</b> when {@code password} is valid, otherwise <b>false</b>.
     */
    public static boolean isValidPassword(final String password) {
        pattern = Pattern.compile(LOGIN_PASSWORD_PATTERN);

        final boolean isValid = pattern.matcher(password).matches();
        System.out.println(password +" : "+ isValid);

        return isValid;

    }

    public static boolean isValidPhoneNumber(final String phoneNumber) {
        pattern = Pattern.compile(PHONE_PATTERN);

        final boolean isValid = pattern.matcher(phoneNumber).matches();
        System.out.println(phoneNumber +" : "+ isValid);

        return isValid;
    }

    /**
     * Check that {@code e-mail} is a valid e-mail address.
     * @param email {@link String} to verify as a valid e-mail address.
     * @return <b>true</b> when {@code e-mail} is valid, otherwise <b>false</b>.
     */
    public static boolean isValidEmail(final String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);

        final boolean isValid = pattern.matcher(email).matches();
        System.out.println(email +" : "+ isValid);

        return isValid;

    }

    /**
     * Check that {@code time24h} is a valid 24-hours time format.
     * @param time24h {@link String} to verify as a having a valid 24-hours time format.
     * @return <b>true</b> when {@code time24h} is a valid time format, otherwise <b>false</b>.
     */
    public static boolean isValidTime24h(final String time24h) {
        pattern = Pattern.compile(TIME_24HOURS_PATTERN);

        final boolean isValid = pattern.matcher(time24h).matches();
        System.out.println(time24h +" : "+ isValid);

        return isValid;

    }

    /**
     * Check that {@code stringDate} is a valid date format, either EUROPEAN, AMERICAN or SCANDINAVIAN.
     * <br/>
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
            System.out.println(stringDate +" : False");
            return false;
        }

        System.out.println(stringDate +" : True");
        return true;

    }

    public static boolean isValidCreaditDebitCardNumber(final String cardCompanyName, final String cardNumber) {

        //  Determine RegEx pattern by credit/debit card company name
        switch (cardCompanyName.toUpperCase()) {
            case "VISA" :
            case "VISA CARD" :
                pattern = Pattern.compile(VISA_CARD_NUMBER_PATTERN);
                break;
            case "MASTER" :
            case "MasterCard" :
            case "MASTER CARD" :
                pattern = Pattern.compile(MASTER_CARD_NUMBER_PATTERN);
                break;
            case "AMEX" :
            case "AMEX CARD" :
            case "American Express" :
                pattern = Pattern.compile(AMEX_CARD_NUMBER_PATTERN);
                break;
            case "CARTE BLANCHE" :
            case "CARTE BLANCHE CARD" :
                pattern = Pattern.compile(CARTE_BLANCHE_CARD_NUMBER_PATTERN);
                break;
            case "DINERS" :
            case "DINERS CLUB" :
                pattern = Pattern.compile(DINERS_CLUB_CARD_NUMBER_PATTERN);
                break;
            case "DISCOVER" :
            case "DISCOVER CARD" :
                pattern = Pattern.compile(DISCOVER_CARD_NUMBER_PATTERN);
                break;
            case "JCB" :
            case "JCB CARD" :
                pattern = Pattern.compile(JCB_CARD_NUMBER_PATTERN);
                break;
            case "INSTA PAYMENT CARD" :
                pattern = Pattern.compile(INSTA_PAYMENT_CARD_NUMBER_PATTERN);
                break;
            case "LAZER CARD" :
                pattern = Pattern.compile(LAZER_CARD_NUMBER_PATTERN);
                break;
            case "MAESTRO" :
            case "MAESTRO CARD" :
                pattern = Pattern.compile(MAESTRO_CARD_NUMBER_PATTERN);
                break;
            case "SOLO" :
            case "SOLO CARD" :
                pattern = Pattern.compile(SOLO_CARD_NUMBER_PATTERN);
                break;
            case "SWITCH" :
            case "SWITCH CARD" :
                pattern = Pattern.compile(SWITCH_CARD_NUMBER_PATTERN);
                break;
            case "UNION PAY" :
            case "UNION PAY CARD" :
                pattern = Pattern.compile(UNION_PAY_CARD_NUMBER_PATTERN);
                break;
            case "KOREAN LOCAL" :
            case "KOREAN LOCAL CARD" :
                pattern = Pattern.compile(KOREAN_LOCAL_CARD_NUMBER_PATTERN);
                break;
            case "BCGLOBAL" :
            case "BCGLOBAL CARD" :
                pattern = Pattern.compile(BCGLOBAL_CARD_NUMBER_PATTERN);
                break;

            default :
                //  Company not part of known companies - can't validate card number
                System.err.println(MESSAGE_UNKNOWN_CREADIT_DEBIT_CARD_COMPANY);
                return false;
        }

        //  Validate card number
        final boolean isValid = pattern.matcher(cardNumber).matches();

        System.out.println(cardCompanyName + " card number " + cardNumber +" is valid? "+ isValid);

        return isValid;
    }

    public static boolean isValidColorHexNumber(final String color) {
        pattern = Pattern.compile(COLOR_HEX_PATTERN);

        //  Validate Color
        final boolean isValid = pattern.matcher(color).matches();

        System.out.println("Color Hex number=" + color +" is valid? "+ isValid);

        return isValid;
    }

    /**
     * Check the users authorization
     * @param session {@link HttpSession} value  from HTTP request
     * @param token {@link String} token key
     * @return <b>true</b> when {@code token} is a valid
     */
    public static boolean isAuthorized(HttpSession session, String token) {
        return !(session.getAttribute(Constants.UserSession.IS_SUCCESS) == null ||
            session.getAttribute(Constants.UserSession.TOKEN) == null ||
            !(Boolean) session.getAttribute(Constants.UserSession.IS_SUCCESS) ||
            session.getAttribute(Constants.UserSession.TOKEN).toString().compareTo(token) != 0);
    }

    public static void main(String[] args) {
        //ValidationHelper validationHelper = new ValidationHelper();

        ValidationHelper.isValidDate("29.02.2012"); //  valid EUROPEAN date-format
        ValidationHelper.isValidDate("29.02.2011"); //  invalid EUROPEAN date-format: no Feb 29th in 2011

        ValidationHelper.isValidDate("02/29/2012"); //  valid AMERICAN date-format
        ValidationHelper.isValidDate("02/29/2011"); //  invalid AMERICAN date-format: no Feb 29th in 2011

        ValidationHelper.isValidDate("2012-02-29"); //  valid SCANDINAVIAN date-format
        ValidationHelper.isValidDate("2011-02-29"); //  invalid SCANDINAVIAN date-format: no Feb 29th in 2011

        ValidationHelper.isValidLogin("king.david");

        ValidationHelper.isValidEmail("a@b.com");
        ValidationHelper.isValidTime24h("23:59:60");

        ValidationHelper.isValidPhoneNumber("+1 123 456 7890");
        ValidationHelper.isValidPhoneNumber("+972 54 123 4567");
        ValidationHelper.isValidPhoneNumber("+972 54 1234567");
        ValidationHelper.isValidPhoneNumber("+44 123 4567890");
        ValidationHelper.isValidPhoneNumber("+44 1234567890");
        ValidationHelper.isValidPhoneNumber("+441234567890");

        ValidationHelper.isValidPassword("King1david");
        ValidationHelper.isValidPassword("king2David");

        ValidationHelper.isValidCreaditDebitCardNumber("Visa", "4580120780141723");


    }
}
