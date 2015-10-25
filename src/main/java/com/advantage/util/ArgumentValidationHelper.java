package com.advantage.util;

import org.apache.commons.lang3.StringUtils;

/**
 * A helper class for validations on method arguments.
 */
public abstract class ArgumentValidationHelper {

    private ArgumentValidationHelper() {

        throw new UnsupportedOperationException();
    }

    public static void validateArgumentIsNotNull(final Object argument,
     final String argumentInformativeName) {

        ArgumentValidationHelper.validateArgumentInformativeNameArgument(argumentInformativeName);

        if (argument == null) {

            final String messageString = ArgumentValidationHelper.getNullArgumentMessage(argumentInformativeName);
            throw new IllegalArgumentException(messageString);
        }
    }

    public static void validateStringArgumentIsNotNullAndNotBlank(final String argument,
     final String argumentInformativeName) {

        ArgumentValidationHelper.validateArgumentInformativeNameArgument(argumentInformativeName);
        ArgumentValidationHelper.validateArgumentIsNotNull(argument, argumentInformativeName);

        if (argument.trim().length() == 0) {

            final String messageString = ArgumentValidationHelper.getBlankStringArgumentMessage(argumentInformativeName);
            throw new IllegalArgumentException(messageString);
        }
    }

    private static String getNullArgumentMessage(final String argumentInformativeName) {

        assert StringUtils.isNotBlank(argumentInformativeName);

        final StringBuilder message = new StringBuilder("Could not accept null for argument [");
        message.append(argumentInformativeName);
        message.append("]");
        return message.toString();
    }

    private static void validateArgumentInformativeNameArgument(final String argumentInformativeName) {

        if (argumentInformativeName == null) {

            final String messageString = ArgumentValidationHelper.getNullArgumentMessage("argument informative name");
            throw new IllegalArgumentException(messageString);
        }

        final String trimmedArgumentInformativeName = argumentInformativeName.trim();
        final int trimmedArgumentInformativeNameLength = trimmedArgumentInformativeName.length();

        if (trimmedArgumentInformativeNameLength == 0) {

            final String messageString = ArgumentValidationHelper.getBlankStringArgumentMessage("argument informative name");
            throw new IllegalArgumentException(messageString);
        }
    }

    private static String getBlankStringArgumentMessage(final String argumentInformativeName) {

        final StringBuilder message = new StringBuilder("Could not accept a blank string for argument [");
        message.append(argumentInformativeName);
        message.append("]");
        return message.toString();
    }

    public static void main(String[] args) {

        ArgumentValidationHelper.validateArgumentIsNotNull("kuku", " 7");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(null, "kuku");
    }
}