package com.advantage.root.util;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Binyamin Regev on on 22/06/2016.
 */
public abstract class RestApiHelper {
    private static final Logger logger = Logger.getLogger(RestApiHelper.class);

    public RestApiHelper() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String httpGet(URL url, String serviceName) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        logger.debug("HttpURLConnection = " + conn.getURL().toString());
        conn.setRequestProperty(HttpHeaders.USER_AGENT, "AdvantageService/" + serviceName);
        int responseCode = conn.getResponseCode();

        logger.debug("responseCode = " + responseCode);
        String returnValue;
        switch (responseCode) {
            case org.apache.http.HttpStatus.SC_OK:
                // Buffer the result into a string
                InputStreamReader inputStream = new InputStreamReader(conn.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStream);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                returnValue = sb.toString();
                break;
            case org.apache.http.HttpStatus.SC_CONFLICT:
                //  Product not found
                returnValue = "Not found";
                break;
            default:
                IOException e = new IOException(conn.getResponseMessage());
                logger.fatal(e);
                throw e;
        }
        conn.disconnect();
        return returnValue;
    }
}
