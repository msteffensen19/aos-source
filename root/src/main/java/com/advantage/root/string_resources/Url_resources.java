package com.advantage.root.string_resources;

/**
 * Created by Evgeney Fiskin on 31-12-2015.
 */

import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@PropertySources(value = {@PropertySource(Constants.PROPERTIES_EXTERNAL)})
public class Url_resources {
    //TODO-EVG change to properties
    public static String getUrlPrefixCatalog() {
        return Constants.URI_SERVER_CATALOG;
    }

    public static String getUrlPrefixAccount() {
        return Constants.URI_SERVER_ACCOUNT;
    }

    public static String getUrlPrefixOrder() {
        return Constants.URI_SERVER_ORDER;
    }

    public static String getUrlPrefixMasterCredit() {
        return "";
    }

    public static String getUrlPrefixSafePay() {
        return "";
    }

    public static String getUrlPrefixShipEx() {
        return Constants.URI_SERVER_SHIP_EX;
    }
}
