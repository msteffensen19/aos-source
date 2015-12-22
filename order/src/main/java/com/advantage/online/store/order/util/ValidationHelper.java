package com.advantage.online.store.order.util;

import com.advantage.online.store.Constants;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * @author Binyamin Regev on 19/11/2015.
 */
public class ValidationHelper {
    private static final String COLOR_HEX_PATTERN = "^#([A-Fa-f0-9]{1,6})$";

    private static Pattern pattern;

    public ValidationHelper() {

    }

    public static boolean isValidColorHexNumber(final String color) {
        pattern = Pattern.compile(COLOR_HEX_PATTERN);

        //  Validate Color
        final boolean isValid = pattern.matcher(color).matches();

        System.out.println("Color Hex number=" + color +" is valid? "+ isValid);

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
