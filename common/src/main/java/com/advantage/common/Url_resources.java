package com.advantage.common;

/**
 * @author Evgeney Fiskin on 31-12-2015.
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

    private static URL urlPrefixCatalog;
    private static URL urlPrefixMasterCredit;
    private static URL urlPrefixOrder;
    private static URL urlPrefixSafePay;

    private static URL urlPrefixSoapAccount;
    private static URL urlPrefixSoapShipEx;

    @Inject
    private Environment environment;

    @Bean
    public int setConfiguration() {

        urlPrefixCatalog = generateUrlPrefix("Catalog");
        urlPrefixMasterCredit = generateUrlPrefix("MasterCredit");
        urlPrefixOrder = generateUrlPrefix("Order");
        urlPrefixSafePay = generateUrlPrefix("SafePay");
        //urlPrefixService = generateUrlPrefix("Service");

        urlPrefixSoapAccount = generateUrlSoapPrefix("Account");
        urlPrefixSoapShipEx = generateUrlSoapPrefix("ShipEx");

        System.out.println("Url_resources: ");
        System.out.println("   Catalog=\'" + getUrlCatalog() + "\'");
        System.out.println("   MasterCredit=\'" + getUrlMasterCredit() + "\'");
        System.out.println("   Order=\'" + getUrlOrder() + "\'");
        System.out.println("   SafePay=\'" + getUrlSafePay() + "\'");
        //System.out.println("   Service=\'" + getUrlService() + "\'");
        System.out.println("   Account (SOAP)=\'" + getUrlSoapAccount() + "\'");
        System.out.println("   ShipEx (SOAP)=\'" + getUrlSoapShipEx() + "\'");

        return 1;
    }

    public URL generateUrlPrefix(String serviceName) {
        URL url = null;

        try {
            String scheme = Constants.URI_SCHEMA;
            String host = environment.getProperty(serviceName.toLowerCase() + ".service.url.host");
            int port = Integer.parseInt(environment.getProperty(serviceName.toLowerCase() + ".service.url.port"));
            String suffix = '/' + environment.getProperty(serviceName.toLowerCase() + ".service.url.suffix") + "/";

            url = new URL(scheme, host, port, suffix);

        } catch (Throwable e) {
            System.err.println("Config file wrong");
            e.printStackTrace();
        }

        return url;

    }

    public URL generateUrlSoapPrefix(String serviceName) {

        URL urlWithWsdl = null;

        try {
            String schema = Constants.URI_SCHEMA;
            String host = environment.getProperty(serviceName.toLowerCase() + ".soapservice.url.host");
            int port = Integer.parseInt(environment.getProperty(serviceName.toLowerCase() + ".soapservice.url.port"));
            String suffix = environment.getProperty(serviceName.toLowerCase() + ".soapservice.url.suffix");
            String wsdl = environment.getProperty(serviceName.toLowerCase() + ".soapservice.url.wsdl");
            if (! wsdl.contains("/")) { suffix += '/'; }
            urlWithWsdl = new URL(new URL(schema, host, port, suffix), suffix + wsdl);

        } catch (Throwable e) {
            System.err.println("Config file wrong");
            e.printStackTrace();
        }

        return urlWithWsdl;

    }

    public static URL getUrlSoapAccount() { return urlPrefixSoapAccount; }

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

    public static URL getUrlSoapShipEx() { return urlPrefixSoapShipEx; }


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
