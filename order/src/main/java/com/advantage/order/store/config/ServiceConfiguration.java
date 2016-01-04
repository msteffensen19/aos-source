package com.advantage.order.store.config;

import com.advantage.common.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;

/**
 * To get and contain Services servers configuration. <br/>
 * e.g. URI service server domain, port and suffix.
 * @see Environment
 *
 * @author Binyamin Regev on 24/12/2015.
 */
@Configuration
public class ServiceConfiguration {

    private static final String SERVICE_URL_FORMAT = "http://%s:%s/%s" + Constants.URI_API + "/v1";

    private static String uriServerAccount;
    private static String uriServerCatalog;
    private static String uriServerMasterCredit;
    private static String uriServerOrder;
    private static String uriServerService;
    private static String uriServerSafePay;

    @Inject
    private Environment env;

    @Bean
    public int setConfiguration() {
        this.setUriServiceServer("Account");
        this.setUriServiceServer("Catalog");
        this.setUriServiceServer("Master_credit");
        this.setUriServiceServer("Order");
        this.setUriServiceServer("Safe_Pay");
        this.setUriServiceServer("Service");
        this.setUriServiceServer("Ship_Ex");

        System.out.println("URIs of services servers: ");
        System.out.println("   Account=\'" + this.getUriServerAccount() + "\'");
        System.out.println("   Catalog=\'" + this.getUriServerCatalog() + "\'");
        System.out.println("   MasterCredit=\'" + this.getUriServerMasterCredit() + "\'");
        System.out.println("   Order=\'" + this.getUriServerOrder() + "\'");
        System.out.println("   SafePay=\'" + this.getUriServerSafePay() + "\'");
        System.out.println("   Service=\'" + this.getUriServerService() + "\'");
        //System.out.println("   ShipEx=\'" + this.get);

        return 1;
    }

    public void setUriServiceServer(String service) {
        switch (service.toLowerCase()) {
            case "account":
                uriServerAccount = String.format(SERVICE_URL_FORMAT,
                        env.getProperty("account.service.url.host"),
                        env.getProperty("account.service.url.port"),
                        env.getProperty("account.service.url.suffix"));
                break;

            case "catalog":
                uriServerCatalog = String.format(SERVICE_URL_FORMAT,
                        env.getProperty("catalog.service.url.host"),
                        env.getProperty("catalog.service.url.port"),
                        env.getProperty("catalog.service.url.suffix"));
                break;

            case "master_credit":
                uriServerMasterCredit = String.format(SERVICE_URL_FORMAT,
                        env.getProperty("mastercredit.service.url.host"),
                        env.getProperty("mastercredit.service.url.port"),
                        env.getProperty("mastercredit.service.url.suffix"));
                break;

            case "order":
                uriServerOrder = String.format(SERVICE_URL_FORMAT,
                        env.getProperty("order.service.url.host"),
                        env.getProperty("order.service.url.port"),
                        env.getProperty("order.service.url.suffix"));
                break;

            case "safe_pay":
                uriServerSafePay = String.format(SERVICE_URL_FORMAT,
                        env.getProperty("safepay.service.url.host"),
                        env.getProperty("safepay.service.url.port"),
                        env.getProperty("safepay.service.url.suffix"));
                break;

            case "service":
                uriServerService = String.format(SERVICE_URL_FORMAT,
                        env.getProperty("service.service.url.host"),
                        env.getProperty("service.service.url.port"),
                        env.getProperty("service.service.url.suffix"));
                break;

            case "ship_ex":

                break;

        }
    }

    public static String getUriServerAccount() { return uriServerAccount; }

    public static String getUriServerCatalog() { return uriServerCatalog; }

    public static String getUriServerOrder() { return uriServerOrder; }

    public static String getUriServerService() { return uriServerService; }

    public static String getUriServerSafePay() { return uriServerSafePay; }

    public static String getUriServerMasterCredit() { return uriServerMasterCredit; }
}
