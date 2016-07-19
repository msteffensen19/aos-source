package com.advantage.order.store.config;

import com.advantage.common.Url_resources;
import com.advantage.order.store.listener.SessionCounterServletRequestListener;
import com.advantage.root.util.JsonHelper;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class DynamicConfiguration {

    private static final Logger logger = Logger.getLogger(DynamicConfiguration.class);
    private static final String XML_SLA_ADD_DELAY_SESSIONS = "SLA_add_delay_sessions";
    private static final String XML_SLA_ADD_DELAY_TIME = "SLA_add_delay_time";
    private static final int DEFAULT_DELAY = 0;
    private static final int DEFAULT_NUMBER_OF_SESSIONS = 20;

    // "SLA: Add delay in add to cart response time (seconds)":
    // 0 = (Default) Disabled; any other positive number = the number of seconds to add as a delay in response time
    private int delayCartResponse;

    //This parameter is enabled only if "SLA: Add delay in add to cart response time (seconds)" is greater than zero.
    // The system will start adding the delay if the number of sessions will be higher than this value and will stop the delay when the number of sessions will go back down.
    // Valid values: 0-n, default=20.
    //For LoadRunner and StormRunner
    private int numberOfSessionsToAddTheDelay;

    public DynamicConfiguration() {
        if (logger.isTraceEnabled()) {
            logger.trace("Constructor, objectId=" + ((Object) this).toString());
        }
        readConfiguration();
    }

    public int getDelay() {
        //DynamicConfiguration dynamicConfiguration = new DynamicConfiguration();
        int activeOrderRequests = SessionCounterServletRequestListener.getActiveSessionsByRequestListener();
        int result = DEFAULT_DELAY;

        if (logger.isDebugEnabled()) {
            logger.debug("REAL delayCartResponse = " + delayCartResponse + " sec.");
            logger.debug("REAL numberOfSessionsToAddTheDelay = " + numberOfSessionsToAddTheDelay);
            logger.debug("REAL activeOrderRequests = " + activeOrderRequests);
        }

        if (activeOrderRequests > numberOfSessionsToAddTheDelay) {
            result = delayCartResponse;
        }
        logger.info("REAL actualDelay = " + result + " sec.");
        return result;
    }

    private void readConfiguration() {
        if (DemoAppConfiguration.isAllowUserConfiguration()) {
            delayCartResponse = getFromCatalogDelayCartResponse();
            if (delayCartResponse > 0) {
                numberOfSessionsToAddTheDelay = getFromCatalogNumberOfSessions();
            } else {
                numberOfSessionsToAddTheDelay = 0;
            }
        } else {
            delayCartResponse = DEFAULT_DELAY;
            numberOfSessionsToAddTheDelay = DEFAULT_NUMBER_OF_SESSIONS;
        }
        logger.debug("delayCartResponse = " + delayCartResponse);
        logger.debug("numberOfSessionsToAddTheDelay = " + numberOfSessionsToAddTheDelay);
    }

    private int getFromCatalogDelayCartResponse() {
        String delay = getCatalogConfigParameter(XML_SLA_ADD_DELAY_TIME);
        if (delay == null) {
            return DEFAULT_DELAY;
        }
        try {
            return Integer.parseInt(delay);
        } catch (NumberFormatException e) {
            logger.warn(delay + " is not a number");
            return DEFAULT_DELAY;
        }
    }

    private int getFromCatalogNumberOfSessions() {
        String numberOfSessions = getCatalogConfigParameter(XML_SLA_ADD_DELAY_SESSIONS);
        if (numberOfSessions == null) {
            return DEFAULT_NUMBER_OF_SESSIONS;
        }
        try {
            return Integer.parseInt(numberOfSessions);
        } catch (NumberFormatException e) {
            logger.warn(numberOfSessions + " is not a number");
            return DEFAULT_NUMBER_OF_SESSIONS;
        }
    }

    private String getCatalogConfigParameter(String requestPart) {
        String value = null;
        URL urlConfig;
        try {
            urlConfig = new URL(Url_resources.getUrlCatalog(), "DemoAppConfig/parameters/" + requestPart);
            logger.debug("urlConfig = " + urlConfig);
            HttpURLConnection conn = (HttpURLConnection) urlConfig.openConnection();
            conn.setRequestMethod(HttpMethod.GET.name());
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                String message = "Failed connect to catalog service: HTTP error code : " + responseCode;
                logger.fatal(message);
                throw new RuntimeException(message);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuilder sb = new StringBuilder();

            logger.info("Output from Server .... " + System.lineSeparator());
            while ((output = br.readLine()) != null) {
                sb.append(output);
                logger.trace(output);
            }
            conn.disconnect();
            logger.debug("Disconnected");

            Map<String, Object> jsonMap = JsonHelper.jsonStringToMap(sb.toString());
            value = ((String) jsonMap.get("parameterValue"));
        } catch (MalformedURLException e) {
            logger.fatal(e);
        } catch (IOException e) {
            logger.fatal(e);
        }
        if (value == null) {
            logger.warn("Value is null");
        } else if (value.isEmpty()) {
            logger.warn("Value is empty");
            value = null;
        }
        return value;
    }

}
