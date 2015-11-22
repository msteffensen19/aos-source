package com.advantage.online.store.user.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Binyamin Regev on 19/11/2015.
 */
public class ValidationHelper {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                                                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    //  Contain 3-15 characters of: Digits 0-9, UPPER case (A-Z), lower case (a-z), underscore (_) and hyphen (-)
    private static final String LOGIN_PATTERN = "^[A-Za-z0-9_.-]{3,15}$";

    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

    private static final String TIME_24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";

    private static final String DATE_YYYYMMDD_PATTERN = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";

    //  EUROPEAN_DATE_FORMAT = "dd.MM.yyyy" ; AMERICAN_DATE_FORMAT = "MM/dd/yyyy" ; SCANDINAVIAN_DATE_FORMAT = "yyyy-MM-dd"
    private static final String AMERICAN_DATE_PATTERN = "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)";
    private static final String EUROPEAN_DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01]).(0?[1-9]|1[012]).((19|20)\\d\\d)";
    private static final String SCANDINAVIAN_DATE_PATTERN = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])"; //YYYY-MM-DD

    private static Pattern pattern;

    private static Matcher matcher;

    public ValidationHelper() {

    }

    public static boolean isValidEmail(final String email) {

        pattern = Pattern.compile(EMAIL_PATTERN);

        matcher = pattern.matcher(email);

        final boolean isValid = matcher.matches();
        System.out.println(email +" : "+ isValid);

        return isValid;

    }

    public static boolean isValidLogin(final String login) {

        pattern = Pattern.compile(LOGIN_PATTERN);

        matcher = pattern.matcher(login);

        final boolean isValid = matcher.matches();
        System.out.println(login +" : "+ isValid);

        return isValid;

    }

    public static boolean isValidPassword(final String password) {

        pattern = Pattern.compile(PASSWORD_PATTERN);

        matcher = pattern.matcher(password);

        final boolean isValid = matcher.matches();
        System.out.println(password +" : "+ isValid);

        return isValid;

    }

    public static boolean isValidTime24h(final String time24h) {

        pattern = Pattern.compile(TIME_24HOURS_PATTERN);

        matcher = pattern.matcher(time24h);

        final boolean isValid = matcher.matches();
        System.out.println(time24h +" : "+ isValid);

        return isValid;

    }

    public static boolean isValidDate(final String stringDate) {
        SimpleDateFormat dateFormat;

        if (Pattern.compile(AMERICAN_DATE_PATTERN).matcher(stringDate).matches()) {
            dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        } else if (Pattern.compile(EUROPEAN_DATE_PATTERN).matcher(stringDate).matches()) {
            dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        } else {
            // (Pattern.compile(SCANDINAVIAN_DATE_PATTERN).matcher(stringDate).matches())
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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

    public static boolean isValidDateYyyymmdd(final String yyyymmdd) {

        pattern = Pattern.compile(DATE_YYYYMMDD_PATTERN);

        Matcher matcher = pattern.matcher(yyyymmdd);

        final boolean isValid = matcher.matches();
        if (isValid) {
            try {
                DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT); // YYYY-MM-DD
                System.out.println(df);

                df.setLenient(false);   // this is important!
                df.parse(yyyymmdd);
            }
            catch (ParseException e) {
                System.out.println(yyyymmdd +" : DateFormat not valid");
                return false;
            }
            catch (IllegalArgumentException e) {
                System.out.println(yyyymmdd +" : DateFormat not valid");
                return false;
            }
            System.out.println(yyyymmdd +" : DateFormat is valid");
            return true;
        }

        return isValid;

    }

    public static void main(String[] args) {
        //ValidationHelper validationHelper = new ValidationHelper();

        ValidationHelper.isValidDate("29.02.2012");
        ValidationHelper.isValidDate("29.02.2011");

        ValidationHelper.isValidDate("02/29/2012");
        ValidationHelper.isValidDate("02/29/2011");

        ValidationHelper.isValidDate("2012-02-29");
        ValidationHelper.isValidDate("2011-02-29");

        ValidationHelper.isValidEmail("a@b.com");
        ValidationHelper.isValidLogin("king.david");
        ValidationHelper.isValidTime24h("23:59:60");
        ValidationHelper.isValidPassword("kingdavid");
        ValidationHelper.isValidDateYyyymmdd("2011-02-28");
    }
}
