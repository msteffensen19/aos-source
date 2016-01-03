package com.advantage.common;

/**
 * Created by Evgeney Fiskin on 31-12-2015.
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import java.net.URL;

@Configuration
@PropertySources(value = {@PropertySource(Constants.FILE_PROPERTIES_EXTERNAL)})
public class Url_resources {

    private static URL urlPrefixAccount;
    private static URL urlPrefixCatalog;
    private static URL urlPrefixMasterCredit;
    private static URL urlPrefixOrder;
    private static URL urlPrefixSafePay;
    private static URL urlPrefixService;
    private static URL urlPrefixShipEx;

    @Inject
    private Environment environment;

    @Bean
    public int setConfiguration() {
        urlPrefixAccount = generateUrlPrefix("Account");
        urlPrefixCatalog = generateUrlPrefix("Catalog");
        urlPrefixMasterCredit = generateUrlPrefix("MasterCredit");
        urlPrefixOrder = generateUrlPrefix("Order");
        urlPrefixSafePay = generateUrlPrefix("SafePay");
        urlPrefixService = generateUrlPrefix("Service");
        urlPrefixShipEx = generateUrlPrefix("ShipEx");

        System.out.println("Url_resources: ");
        System.out.println("   Account=\'" + this.getUrlAccount() + "\'");
        System.out.println("   Catalog=\'" + this.getUrlCatalog() + "\'");
        System.out.println("   MasterCredit=\'" + this.getUrlMasterCredit() + "\'");
        System.out.println("   Order=\'" + this.getUrlOrder() + "\'");
        System.out.println("   SafePay=\'" + this.getUrlSafePay() + "\'");
        System.out.println("   Service=\'" + this.getUrlService() + "\'");
        System.out.println("   ShipEx=\'" + this.getUrlShipEx() + "\'");

        return 1;
    }

    public URL generateUrlPrefix(String serviceName) {
        URL url = null;

        try {
            String scheme = Constants.URI_SCHEME;
            String host = environment.getProperty(serviceName.toLowerCase() + ".service.url.host");
            int port = Integer.parseInt(environment.getProperty(serviceName.toLowerCase() + ".service.url.port"));
            String suffix = '/' + environment.getProperty(serviceName.toLowerCase() + ".service.url.suffix") + Constants.URI_API + "/v1/";

            url = new URL(scheme, host, port, suffix);

        } catch (Throwable e) {
            System.err.println("Config file wrong");
            e.printStackTrace();
        }

        return url;

    }

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

    public static URL getUrlAccount() {
        return urlPrefixAccount;
    }

    public static URL getUrlCatalog() {
        return urlPrefixCatalog;
    }

    public static URL getUrlMasterCredit() {
        return urlPrefixMasterCredit;
    }

    public static URL getUrlOrder() {
        return urlPrefixOrder;
    }

    public static URL getUrlSafePay() { return urlPrefixSafePay; }

    public static URL getUrlService() {
        return urlPrefixService;
    }

    public static URL getUrlShipEx() {
        return urlPrefixShipEx;
    }

    @Deprecated
    private URL getUrlPrefix(String serviceName) {
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

//    @Ignore
//    @Test
//    public void ttt() throws URISyntaxException, MalformedURLException {
//        String scheme = "http";
//        String userInfo = null;
//        String host = "www.advantageonlineshopping.com";
//        int port = 8080;
//        String service_path = "/account/";
//        String query = null;
//        String fragment = null;
//
//        URI uri = new URI(scheme, userInfo, host, port, service_path, query, fragment);
//
//
//        URL url = new URL("http", host, port, service_path);
//        URL url2 = new URL(url, "kuku2");
//        URL url3 = new URL(url2, "kuku3/kuku3_1");
//        URL url4 = new URL(url2, "kuku4/kuku4_1/");
//        URL url5 = new URL(url4, "file.wsdl");
//        URL url6 = new URL(url3, "file.wsdl");
//
//        environment.getProperty("evg.fisk.test.property.catalog");
//    }
}
