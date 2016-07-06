package com.advantage.order.store.config;

import com.advantage.common.Url_resources;
import com.advantage.order.store.listener.SessionCounterListener;
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

    // "SLA: Add delay in add to cart response time (seconds)":
    // 0 = (Default) Disabled; any other positive number = the number of seconds to add as a delay in response time
    private int delayCartResponse;
    //This parameter is enabled only if "SLA: Add delay in add to cart response time (seconds)" is greater than zero.
    // The system will start adding the delay if the number of sessions will be higher than this value and will stop the delay when the number of sessions will go back down.
    // Valid values: 0-n, default=20.
    //For LoadRunner and StormRunner
    private int numberOfSessionsToAddTheDelay;
//      //  Class that is called must have a method "public void init() throws Exception"
//    @Bean(initMethod = "init")
//    public AppUserConfig initAppUserConfiguration() {
//        return new AppUserConfig();
//    }

    public DynamicConfiguration() {
        if (logger.isTraceEnabled()) {
            logger.trace("Constructor, objectId=" + ((Object) this).toString());
        }
        readConfiguration();
    }

    private void readConfiguration() {
        if (AppUserConfiguration.isAllowUserConfiguration()) {
            delayCartResponse = getFromCatalogDelayCartResponse();
            if (delayCartResponse > 0) {
                numberOfSessionsToAddTheDelay = getFromCatalogNumberOfSessions();
            } else {
                numberOfSessionsToAddTheDelay = 0;
            }
        } else {
            delayCartResponse = 0;
            numberOfSessionsToAddTheDelay = 0;
        }
        logger.debug("delayCartResponse = " + delayCartResponse);
        logger.debug("numberOfSessionsToAddTheDelay = " + numberOfSessionsToAddTheDelay);
    }

    private int getFromCatalogDelayCartResponse() {
        String requestPart = "SLA_Add_Delay_In_Add_To_Cart_Response_Time";
        String delay = getCatalogConfigParameter(requestPart);
        try {
            return Integer.parseInt(delay);
        } catch (NumberFormatException e) {
            logger.warn(delay + " is not a number");
            return 0;
        }
    }

    private int getFromCatalogNumberOfSessions() {
        String requestPart = "SLA_Number_Of_Sessions_To_Add_The_Delay";
        String numberOfSessions = getCatalogConfigParameter(requestPart);
        try {
            return Integer.parseInt(numberOfSessions);
        } catch (NumberFormatException e) {
            logger.warn(numberOfSessions + " is not a number");
            return 0;
        }
    }

    private String getCatalogConfigParameter(String requestPart) {
        String value = null;
        URL urlConfig = null;
        try {
            urlConfig = new URL(Url_resources.getUrlCatalog(), "DemoAppConfig/parameters/" + requestPart);
            logger.debug("urlConfig = " + urlConfig);
            HttpURLConnection conn = (HttpURLConnection) urlConfig.openConnection();
            conn.setRequestMethod(HttpMethod.GET.name());
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                String message = "Failed : HTTP error code : " + responseCode;
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
            value = "0";
        } else if (value.isEmpty()) {
            logger.warn("Value is empty");
            value = "0";
        }
        return value;
    }

    public int getNumberOfSessionsToAddTheDelay() {
        return numberOfSessionsToAddTheDelay;
    }

    public int getDelayCartResponse() {
        return delayCartResponse;
    }

    public int getDelay() {
        //DynamicConfiguration dynamicConfiguration = new DynamicConfiguration();
        int delayCartResponse = getDelayCartResponse();
        int numberOfSessionsToAddTheDelay = getNumberOfSessionsToAddTheDelay();
        int activeOrderRequests = SessionCounterListener.getActiveSessionsByRequestListener();
        int result = 0;
        logger.debug("REAL delayCartResponse = " + delayCartResponse);
        logger.debug("REAL numberOfSessionsToAddTheDelay = " + numberOfSessionsToAddTheDelay);
        logger.debug("REAL activeOrderRequests = " + activeOrderRequests);

        if (activeOrderRequests >= numberOfSessionsToAddTheDelay) {
            result = delayCartResponse;
        }
        return result;
    }
}
