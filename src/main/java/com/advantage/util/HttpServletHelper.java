package com.advantage.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public abstract class HttpServletHelper {

	private HttpServletHelper() {

		throw new UnsupportedOperationException();
	}

	public static void validateParametersExistanceInRequest(final HttpServletRequest request,
     final boolean considerBlankStringAsNotExist, final String... parameterNames) {

		ArgumentValidationHelper.validateArgumentIsNotNull(request, "http servlet request");
		ArgumentValidationHelper.validateArrayArgumentIsNotNullAndNotZeroLength(parameterNames,
				                                                                "parameter names");
		final Collection<String> nonExistingParameters = new ArrayList<String>();

		for (final String parameterName : parameterNames) {

			final String parameterValue = request.getParameter(parameterName);

			if (parameterValue == null ||
				(considerBlankStringAsNotExist && StringUtils.isBlank(parameterValue))) {

				nonExistingParameters.add(parameterName);
			} 
		}

		if (nonExistingParameters.isEmpty() == false) {

			HttpServletHelper.generateMessageAndThrowError(nonExistingParameters);
		}
	}

	private static void generateMessageAndThrowError(final Collection<String> nonExistingParameters) {

		assert CollectionUtils.isNotEmpty(nonExistingParameters);

		final StringBuilder errorMessage = new StringBuilder("Missing mandatory parameter in HTTP request: [");
		final Iterator<String> nonExistingParametersIterator = nonExistingParameters.iterator();

		while (nonExistingParametersIterator.hasNext()) {

			final String parameterName = nonExistingParametersIterator.next();
			errorMessage.append("{");
			errorMessage.append(parameterName);
			errorMessage.append("}");

			if (nonExistingParametersIterator.hasNext()) {

				errorMessage.append(", ");
			}
		}
		
		errorMessage.append("]");
		final String errorMessageString = errorMessage.toString();
		throw new RuntimeException(errorMessageString);
	}
}