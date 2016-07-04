package com.advantage.order.store.config;

import CatalogRestServiceClient.CatalogClient;
import com.advantage.common.Constants;
import com.advantage.common.Url_resources;
import com.advantage.order.store.dto.CatalogConfigurationDelayResponse;
import com.advantage.order.store.dto.SafePayResponse;
import com.advantage.root.util.JsonHelper;
import com.google.common.net.HttpHeaders;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Application User configuration class
 *
 * @author Binyamin Regev on 24/11/2015.
 */
@Configuration
public class AppUserConfiguration {

    private static AppUserConfiguration instance;
    private static final Logger logger = Logger.getLogger(AppUserConfiguration.class);
    @Autowired
    public Environment environment;

    private boolean allowUserConfiguration;
    // "SLA: Add delay in add to cart response time (seconds)":
    // 0 = (Default) Disabled; any other positive number = the number of seconds to add as a delay in response time
    private int delayCartResponse;
    //This parameter is enabled only if "SLA: Add delay in add to cart response time (seconds)" is greater than zero.
    // The system will start adding the delay if the number of sessions will be higher than this value and will stop the delay when the number of sessions will go back down.
    // Valid values: 0-n, default=20.
    //For LoadRunner and StormRunner
    private int numberOfSessionsToAddTheDelay;
    private CatalogClient catalog;
//      //  Class that is called must have a method "public void init() throws Exception"
//    @Bean(initMethod = "init")
//    public AppUserConfig initAppUserConfiguration() {
//        return new AppUserConfig();
//    }

    @Bean(initMethod = "init")
    public AppUserConfiguration init() {
        logger.trace("@Bean(initMethod = \"init\")");
        return getInstance();
    }

    public AppUserConfiguration getInstance() {
        if (instance == null) {
            instance = new AppUserConfiguration();
            instance.allowUserConfiguration = _isAllowUserConfig();
            logger.debug("allowUserConfiguration = " + instance.allowUserConfiguration);
            instance.delayCartResponse = getFromCatalogDelayCartResponse();
            logger.debug("delayCartResponse = " + instance.delayCartResponse);
            if (delayCartResponse > 0) {
//                instance.numberOfSessionsToAddTheDelay = getFromCatalogNumberOfSessions();
                instance.numberOfSessionsToAddTheDelay = 123;
            } else {
                instance.numberOfSessionsToAddTheDelay = 0;
            }
            logger.debug("numberOfSessionsToAddTheDelay = " + instance.numberOfSessionsToAddTheDelay);

        }
        return instance;
    }

    private int getFromCatalogDelayCartResponse() {
        String delay = null;

        URL urlConfigDelay = null;

//        CatalogConfigurationDelayResponse catalogConfigurationDelayResponse = new CatalogConfigurationDelayResponse();

        try {
            urlConfigDelay = new URL(Url_resources.getUrlCatalog(), "DemoAppConfig/parameters/SLA_Add_Delay_In_Add_To_Cart_Response_Time");
            logger.debug("urlConfigDelay = " + urlConfigDelay);
            HttpURLConnection conn = (HttpURLConnection) urlConfigDelay.openConnection();
            if (logger.isTraceEnabled()) {
                logger.trace(conn.getRequestMethod());
            }
//            conn.setDoOutput(true);
//            if (logger.isTraceEnabled()){
//                logger.trace(conn.getRequestMethod());
//            }
            conn.setRequestMethod(HttpMethod.GET.name());
            if (logger.isTraceEnabled()) {
                logger.trace("After set method before flush. " + conn.getRequestMethod());
            }
//            conn.setRequestProperty(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//            if (logger.isTraceEnabled()){
//                logger.trace(conn.getRequestMethod());
//            }
//            OutputStream os = conn.getOutputStream();
//            if (logger.isTraceEnabled()){
//                logger.trace("After get output stream. "+conn.getRequestMethod());
//            }
//            os.flush();
//            if (logger.isTraceEnabled()){
//                logger.trace(conn.getRequestMethod());
//            }
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                String message = "Failed : HTTP error code : " + responseCode;
                logger.fatal(message);
                throw new RuntimeException(message);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuilder sb = new StringBuilder();

            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                sb.append(output);
                System.out.println(output);
            }

            Map<String, Object> jsonMap = JsonHelper.jsonStringToMap(sb.toString());

//            catalogConfigurationDelayResponse.setParameterName((String) jsonMap.get("parameterName"));
//            catalogConfigurationDelayResponse.setDatatype((String) jsonMap.get("datatype"));
//            catalogConfigurationDelayResponse.setDescription((String) jsonMap.get("description"));
//            catalogConfigurationDelayResponse.setAttributeTools((String) jsonMap.get("attributeTools"));
//            catalogConfigurationDelayResponse.setParameterValue((String) jsonMap.get("parameterValue"));
            delay = ((String) jsonMap.get("parameterValue"));

            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (delay == null) {
            logger.fatal("delay is null");
            return 0;
        } else if (delay.isEmpty()) {
            logger.fatal("delay is empty");
            return 0;
        } else {
            try {
                return Integer.parseInt(delay);
            } catch (NumberFormatException e) {
                logger.fatal(delay + " is not a number");
                return 0;
            }
        }
    }

    public AppUserConfiguration() {
        if (logger.isTraceEnabled()) {
            logger.trace("Constructor, objectId=" + ((Object) this).toString());
        }
    }

    public boolean isAllowUserConfiguration() {
        return allowUserConfiguration;
    }

    private boolean _isAllowUserConfig() {
        if (environment == null) {
            logger.fatal("@Autowired Environment is null");
        }
        String property = environment.getProperty(Constants.ENV_ALLOW_USER_CONFIGURATION);
        logger.debug("Parameter " + Constants.ENV_ALLOW_USER_CONFIGURATION + " = " + (property == null ? "null" : property));
        if (property == null || property.isEmpty()) {
            return false;
        }
        if (property.toLowerCase().equals("yes")) {
            return true;
        } else {
            return false;
        }
    }

}
