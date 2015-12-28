package com.advantage.safepay.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Binyamin Regev on 19/11/2015.
 */
public class ValidationHelper {

    private static final String FULL_NAME_PATTERN = "^[\\p{L} .'-]+$";

    private static final String MASTER_CREDIT_CVV_NUMBER_PATTERN = "([0-9]{3})";

    private static final String MASTER_CREDIT_CARD_NUMBER_PATTERN = "([0-9]{16})";
    private static final String MASTER_CREDIT_ACCOUNT_NUMBER_PATTERN = "([0-9]{12})";

    //private static final String PHONE_PATTERN = "^\\+([0-9]{1,3})?[-.\\s]\\(?([0-9]{1,3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
    //private static final String PHONE_PATTERN = "((\\+([1-9]{1}[0-9]{0,3})|00[1-9]{3})[-.\\s]?)?\\(?([0-9]{1,3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
    private static final String PHONE_PATTERN = "((\\+([1-9]{1}[0-9]{0,3}))?[-.\\s]?)\\(?([0-9]{1,3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";

    private static final String TIME_24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";

    //  EUROPEAN_DATE_FORMAT = "dd.MM.yyyy" ; AMERICAN_DATE_FORMAT = "MM/dd/yyyy" ; SCANDINAVIAN_DATE_FORMAT = "yyyy-MM-dd"
    private static final String AMERICAN_DATE_PATTERN = "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)";
    private static final String EUROPEAN_DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01]).(0?[1-9]|1[012]).((19|20)\\d\\d)";
    private static final String SCANDINAVIAN_DATE_PATTERN = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])"; //YYYY-MM-DD
    private static final String SAFE_PAY_ACCOUNT_NUMBER = "843200971";

    private static Pattern pattern;

    public ValidationHelper() {

    }

    public static boolean isValidPhoneNumber(final String phoneNumber) {
        pattern = Pattern.compile(PHONE_PATTERN);

        final boolean isValid = pattern.matcher(phoneNumber).matches();
        System.out.println(phoneNumber + " : " + isValid);

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

//    /**
//     * Validates <b>MasterCredit</b> card number.
//     *
//     * @param cardNumber <b>MasterCredit</b> card number to validate.
//     * @return {@code boolean} <b>true</b> when valid, <b>false</b> otherwise.
//     */
//    public static boolean isValidMasterCreditCardNumber(final String cardNumber) {
//        pattern = Pattern.compile(MASTER_CREDIT_CARD_NUMBER_PATTERN);
//
//        //  Validate MasterCredit CVV number
//        final boolean isValid = pattern.matcher(cardNumber).matches();
//
//        System.out.println("MasterCredit card number=" + cardNumber + " is valid? " + isValid);
//
//        return isValid;
//    }

//    /**
//     * Validates <b>MasterCredit</b> CVV number.
//     *
//     * @param cvvNumber <b>MasterCredit</b> CVV number to validate.
//     * @return {@code boolean} <b>true</b> when valid, <b>false</b> otherwise.
//     */
//    public static boolean isValidMasterCreditCVVNumber(final String cvvNumber) {
//        pattern = Pattern.compile(MASTER_CREDIT_CVV_NUMBER_PATTERN);
//
//        //  Validate MasterCredit CVV number
//        final boolean isValid = pattern.matcher(cvvNumber).matches();
//
//        System.out.println("MasterCredit CVV number=" + cvvNumber + " is valid? " + isValid);
//
//        return isValid;
//    }

//    /**
//     * Validates <b>MasterCredit</b> card holder full name.
//     *
//     * @param fullName <b>MasterCredit</b> card holder full name to validate.
//     * @return {@code boolean} <b>true</b> when valid, <b>false</b> otherwise.
//     */
//    public static boolean isValidFullName(final String fullName) {
//        pattern = Pattern.compile(FULL_NAME_PATTERN);
//
//        //  Validate Full Name
//        final boolean isValid = pattern.matcher(fullName).matches();
//
//        System.out.println("Full name=\"" + fullName + "\" is valid? " + isValid);
//
//        return isValid;
//    }

    /**
     * Validates <b>MasterCredit</b> account number.
     *
     * @param accountNumber <b>MasterCredit</b> account number to validate.
     * @return {@code boolean} <b>true</b> when valid, <b>false</b> otherwise.
     */
    public static boolean isValidSafePayAccountNumber(final String accountNumber) {
        final boolean isValid = accountNumber.equals(SAFE_PAY_ACCOUNT_NUMBER);

        System.out.println("SafePay account number=" + accountNumber + " is valid? " + isValid);

        return isValid;
    }

    /**
     * Validate currency code. For now only <i>USD</i> is a valid currency code.
     * @param currency Currency code to validate
     * @return <b>true</b> when valid currency code, <b>false</b> otherwise.
     */
    public static boolean isValidCurrency(final String currency) {
        List<String> currencies = new ArrayList<>();

        currencies.add("USD");

        for (String test : currencies) {
            if (test.equalsIgnoreCase(test)) { return true; }
        }

        return false;
    }

}
