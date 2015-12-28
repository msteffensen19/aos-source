package com.advantage.order.store.order.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Binyamin Regev on 19/11/2015.
 */
public class ValidationHelper {
    private static final String COLOR_HEX_PATTERN = "^#([A-Fa-f0-9]{1,6})$";

    private static Pattern pattern;

    public ValidationHelper() {

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

    public static boolean isValidColorHexNumber(final String color) {
        pattern = Pattern.compile(COLOR_HEX_PATTERN);

        //  Validate Color
        final boolean isValid = pattern.matcher(color).matches();

        System.out.println("Color Hex number=" + color + " is valid? " + isValid);

        return isValid;
    }

    public static void main(String[] args) {
        //ValidationHelper validationHelper = new ValidationHelper();

        ValidationHelper.isValidColorHexNumber("FFFF00");   //  true (Yellow)
        ValidationHelper.isValidColorHexNumber("0000FF");   //  true (Blue)
        ValidationHelper.isValidColorHexNumber("FF");       //  true (Blue)
        ValidationHelper.isValidColorHexNumber("C0C0C0");   //  true (Silver)
        ValidationHelper.isValidColorHexNumber("888888");   //  true
        ValidationHelper.isValidColorHexNumber("FFFFFH");   //  false ("H" is not in Hex
        ValidationHelper.isValidColorHexNumber("FFFFFFH");  //  false ("H" is not in Hex and string is longer than 6 characters)
        ValidationHelper.isValidColorHexNumber("C0C0C0C");  //  false (string is longer than 6 characters)

    }
}
