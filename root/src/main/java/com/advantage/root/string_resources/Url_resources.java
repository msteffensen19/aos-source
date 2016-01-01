package com.advantage.root.string_resources;

/**
 * Created by Evgeney Fiskin on 31-12-2015.
 */

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@PropertySources(value = {@PropertySource(Constants.PROPERTIES_EXTERNAL)})
public class Url_resources {

    @Autowired
    private static Environment environment;

    //TODO-EVG change to properties
    @Deprecated
    public static String getUrlPrefixCatalog() {
        return Constants.URI_SERVER_CATALOG;
    }

    @Deprecated
    public static String getUrlPrefixAccount() {
        return Constants.URI_SERVER_ACCOUNT;
    }

    @Deprecated
    public static String getUrlPrefixOrder() {
        return Constants.URI_SERVER_ORDER;
    }

    @Deprecated
    public static String getUrlPrefixMasterCredit() {
        return "";
    }

    @Deprecated
    public static String getUrlPrefixSafePay() {
        return "";
    }

    @Deprecated
    public static String getUrlPrefixShipEx() {
        return Constants.URI_SERVER_SHIP_EX;
    }

    public static URL getUrlCatalog() {
        return getUrlPrefix("catalog");
    }

    public static URL getUrlAccount() {
        return getUrlPrefix("account");
    }

    public static URL getUrlOrder() {
        return getUrlPrefix("order");
    }

    public static URL getUrlService() {
        return getUrlPrefix("service");
    }

    public static URL getUrlMasterCredit() {
        return getUrlPrefix("mastercredit");
    }

    public static URL getUrlSafePay() {
        return getUrlPrefix("safepay");
    }

    public static URL getUrlShipEx() {
        return getUrlPrefix("shipex");
    }

    private static URL getUrlPrefix(String serviceName) {
        URL url = null;
        try {
            String scheme = Constants.URI_SCHEME;
            String host = environment.getProperty(serviceName + ".service.url.host");
            int port = Integer.parseInt(environment.getProperty(serviceName + ".service.url.port"));
            String suffix = '/' + environment.getProperty(serviceName + ".service.url.suffix") + Constants.URI_API + "/v1/";
            url = new URL(scheme, host, port, suffix);
        } catch (Throwable e) {
            System.err.println("Config file wrong");
            e.printStackTrace();
        }
        return url;
    }

    @Ignore
    @Test
    public void ttt() throws URISyntaxException, MalformedURLException {
        String scheme = "http";
        String userInfo = null;
        String host = "www.advantageonlineshopping.com";
        int port = 8080;
        String service_path = "/account/";
        String query = null;
        String fragment = null;

        URI uri = new URI(scheme, userInfo, host, port, service_path, query, fragment);


        URL url = new URL("http", host, port, service_path);
        URL url2 = new URL(url, "kuku2");
        URL url3 = new URL(url2, "kuku3/kuku3_1");
        URL url4 = new URL(url2, "kuku4/kuku4_1/");
        URL url5 = new URL(url4, "file.wsdl");
        URL url6 = new URL(url3, "file.wsdl");

        environment.getProperty("evg.fisk.test.property.catalog");
    }
}
