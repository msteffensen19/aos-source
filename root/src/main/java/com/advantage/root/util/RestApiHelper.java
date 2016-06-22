package com.advantage.root.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Binyamin Regev on on 22/06/2016.
 */
public abstract class RestApiHelper {
    public RestApiHelper() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String httpGet(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        int responseCode = conn.getResponseCode();

        String returnValue;
        switch (responseCode) {
            case org.apache.http.HttpStatus.SC_OK: {
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
            }
            case org.apache.http.HttpStatus.SC_CONFLICT:
                //  Product not found
                returnValue = "Not found";
                break;

            default:
                System.out.println("httpGet -> responseCode=" + responseCode);
                throw new IOException(conn.getResponseMessage());
        }

        conn.disconnect();

        return returnValue;
    }
}
