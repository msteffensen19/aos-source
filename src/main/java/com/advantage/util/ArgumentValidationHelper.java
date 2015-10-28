package com.advantage.util;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

/**
 * A helper class for validations on method arguments.
 */
public abstract class ArgumentValidationHelper {

    private ArgumentValidationHelper() {

        throw new UnsupportedOperationException();
    }

    /**
     * Validate that the given argument does not reference <b>null</b>.
     * <br/>
     * <b>If no {@link IllegalArgumentException} was throw, than the validation has been
     * successful.</b>
     * @param argument the argument to check if it references <b>null</b>.
     * @param argumentInformativeName the informative name of the argument, to use for the
     * error message, if the validation didn't pass.
     * @throws IllegalArgumentException if the given argument to validate (first argument)
     * references <b>null</b>, or if the given argument-informative-name argument references
     * <b>null</b>, or if it <b>is</b> a blank string.
     */
    public static void validateArgumentIsNotNull(final Object argument,
     final String argumentInformativeName) {

        ArgumentValidationHelper.validateArgumentInformativeNameArgument(argumentInformativeName);

        if (argument == null) {

            final String messageString = ArgumentValidationHelper.getNullArgumentMessage(argumentInformativeName);
            throw new IllegalArgumentException(messageString);
        }
    }

    /**
     * Validate that the given string argument does not reference <b>null</b>, and that it is
     * <b>not</b> a blank string.
     * <br/>
     * <b>If no {@link IllegalArgumentException} was throw, than the validation has been
     * successful.</b>
     * @param argument the string argument to validate that it does not reference
     * <b>null</b>, and that it is <b>not</b> a blank string.
     * @param argumentInformativeName the informative name of the argument, to use for the
     * error message, if the validation didn't pass.
     * @throws IllegalArgumentException if the given argument to validate (first argument)
     * references <b>null</b>, or if it <b>is</b> a blank string, or if the given
     * argument-informative-name argument references <b>null</b>, or if it <b>is</b> a blank
     * string.
     */
    public static void validateStringArgumentIsNotNullAndNotBlank(final String argument,
     final String argumentInformativeName) {

        ArgumentValidationHelper.validateArgumentInformativeNameArgument(argumentInformativeName);
        ArgumentValidationHelper.validateArgumentIsNotNull(argument, argumentInformativeName);
        final String trimmedArgument = argument.trim();
        final int trimmedArgumentLength = trimmedArgument.length();

        if (trimmedArgumentLength == 0) {

            final String messageString = ArgumentValidationHelper.getBlankStringArgumentMessage(argumentInformativeName);
            throw new IllegalArgumentException(messageString);
        }
    }

    /**
     * Validate that the given collection argument does not reference <b>null</b>, and that
     * it is <b>not</b> an empty collection.
     * <br/>
     * <b>If no {@link IllegalArgumentException} was throw, than the validation has been
     * successful.</b>
     * @param argument the collection argument to validate that it does not reference
     * <b>null</b>, and that it is <b>not</b> an empty collection.
     * @param argumentInformativeName the informative name of the argument, to use for the
     * error message, if the validation didn't pass.
     * @throws IllegalArgumentException if the given argument to validate (first argument)
     * references <b>null</b>, or if it <b>is</b> an empty collection, or if the given
     * argument-informative-name argument references <b>null</b>, or if it <b>is</b> a blank
     * string.
     */
    public static void validateCollectionArgumentIsNotNullAndNotEmpty(final Collection<?> argument,
     final String argumentInformativeName) {

    	ArgumentValidationHelper.validateArgumentInformativeNameArgument(argumentInformativeName);
    	ArgumentValidationHelper.validateArgumentIsNotNull(argument, argumentInformativeName);

    	if (argument.isEmpty()) {

    		final StringBuilder message = new StringBuilder("Could not accept an empty collection for argument [");
            message.append(argumentInformativeName);
            message.append("]");
            final String messageString = message.toString();
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
}