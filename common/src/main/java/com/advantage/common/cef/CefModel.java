package com.advantage.common.cef;

import com.advantage.common.exceptions.token.ContentTokenException;
import com.advantage.common.exceptions.token.VerificationTokenException;
import com.advantage.common.exceptions.token.WrongTokenTypeException;
import com.advantage.common.security.Token;
import com.advantage.common.security.TokenJWT;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by fiskine on 7/12/2016.
 */

public class CefModel {
    private static int version = 0;
    private static String deviceVendor = "Advantage";
    private String deviceProduct;//Current service (account, order, SafePay etc)
    private String deviceVersion;
    private String deviceEventClassId;
    private String name;
    private int severity;
    private String app = "HTTP";
    private String destinationServiceName; //Service than requested by current service (accountservice, catalog,order,ShipEx,SafePay etc)
    private String outcome;
    private String request;
    private String requestContext;
    private String requestCookies;
    private String requestClientApplication;
    private String requestMethod;
    private Integer spt;
    private String src;
    private Long suid;

    /**
     * @param deviceProduct
     * @param deviceVersion
     */
    public void setServiceValues(String deviceProduct, String deviceVersion) {
        this.deviceProduct = deviceProduct;
        this.deviceVersion = deviceVersion;
    }

    public void setEventValues(String deviceEventClassId, String name, int severity) {
        this.deviceEventClassId = deviceEventClassId;
        this.name = name;
        this.severity = severity;
    }

    public void setDestinationServiceName(String destinationServiceName) {
        this.destinationServiceName = destinationServiceName;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public void setRequestData(HttpServletRequest httpServletRequest) {
        request = httpServletRequest.getRequestURI();
//        requestContext = convertEnumerationToString(httpServletRequest.getHeaders(HttpHeaders.REFERER));
        requestContext = httpServletRequest.getHeader(HttpHeaders.REFERER);
        requestCookies = convertCookiesToString(httpServletRequest.getCookies());
        requestClientApplication = httpServletRequest.getHeader(HttpHeaders.USER_AGENT);
        requestMethod = httpServletRequest.getMethod();
        spt = httpServletRequest.getRemotePort();
        src = httpServletRequest.getRemoteAddr();

    }

    public void setUserId(String base64Token) throws VerificationTokenException, WrongTokenTypeException, ContentTokenException {
        Token token = new TokenJWT(base64Token);
        setUserId(token);
    }

    public void setUserId(Token token) throws ContentTokenException {
        suid = token.getUserId();
    }

    private String convertCookiesToString(Cookie[] cookies) {
        String result = null;
        if (cookies != null && cookies.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cookies.length; i++) {
                sb.append(cookies[i].toString());
                if (i < cookies.length - 1) {
                    sb.append(";");
                }
            }
            result = sb.toString();
        }
        return result;
    }

    private String convertEnumerationToString(Enumeration<String> referers) {
        String result = null;
        if (referers != null && referers.hasMoreElements()) {
            StringBuilder sb = new StringBuilder();
            while (referers.hasMoreElements()) {
                sb.append(referers.nextElement());
                if (referers.hasMoreElements()) {
                    sb.append(";");
                }
            }
            result = sb.toString();
        }
        return result;
    }


    public String cefFomatMessage() {
        String cefHeader = String.format("CEF:%d|%s|%s|%s|%s|%s|%d|",
                version,
                escapeHeaderValueSigns(deviceVendor),
                escapeHeaderValueSigns(deviceProduct),
                escapeHeaderValueSigns(deviceVersion),
                escapeHeaderValueSigns(deviceEventClassId),
                escapeHeaderValueSigns(name),
                severity);

        StringBuilder sb = new StringBuilder(cefHeader);
        sb.append(convertToExtensionPair("app", app));
        sb.append(convertToExtensionPair("destinationServiceName", destinationServiceName));
        sb.append(convertToExtensionPair("outcome", outcome));
        sb.append(convertToExtensionPair("request", request));
        sb.append(convertToExtensionPair("requestContext", requestContext));
        sb.append(convertToExtensionPair("requestCookies", requestCookies));
        sb.append(convertToExtensionPair("requestClientApplication", requestClientApplication));
        sb.append(convertToExtensionPair("requestMethod", requestMethod));
        sb.append(convertToExtensionPair("spt", spt));
        sb.append(convertToExtensionPair("src", src));
        sb.append(convertToExtensionPair("suid", suid));
        return sb.toString();
    }

    private StringBuilder convertToExtensionPair(String cefExtensionKey, Number cefExtensionValue) {
        if (cefExtensionValue != null) {
            StringBuilder sb = new StringBuilder(cefExtensionKey).append('=');
            sb.append(cefExtensionValue).append(' ');
            return sb;
        } else {
            return new StringBuilder();
        }

    }

    private StringBuilder convertToExtensionPair(String cefExtensionKey, String cefExtensionValue) {
        if (cefExtensionValue != null && !cefExtensionValue.isEmpty()) {
            StringBuilder sb = new StringBuilder(cefExtensionKey).append('=');
            sb.append(escapeExtensionValueSigns(cefExtensionValue)).append(' ');
            return sb;
        } else {
            return new StringBuilder();
        }
    }

    private String escapeExtensionValueSigns(String value) {
        return value.replace("\\", "\\\\").replace("=", "\\=");
    }

    private String escapeHeaderValueSigns(String value) {
        return value.replace("\\", "\\\\").replace("|", "\\|");
    }
}
