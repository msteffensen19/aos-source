package com.advantage.root.string_resources;

public class Constants {
    public static final char SPACE = ' ';

    public static final String URI_API = "/api";

    public static final String PROPERTIES_INTERNAL = "classpath:/services.properties";
    public static final String PROPERTIES_EXTERNAL = "classpath:/services.properties";

    //TODO-EVG remove
    public static final String URI_SERVER_ORDER = "http://localhost:8080/order" + URI_API + "/v1";
    public static final String URI_SERVER_SERVICE = "http://localhost:8080/service" + URI_API + "/v1";

    //TODO-EVG remove
    /* CATALOG Service related constants - BEGIN    */
    public static final String URI_SERVER_CATALOG = "http://localhost:8080/catalog" + URI_API + "/v1";
    public static final String CATALOG_GET_PRODUCT_BY_ID_URI = "/products/{product_id}";
    /* CATALOG Service related constants - END      */

    //TODO-EVG remove
    /* ACCOUNT URI and transactions types - BEGIN   */
    public static final String URI_SERVER_ACCOUNT = "http://localhost:8080/account" + URI_API + "/v1";
    public static final String ACCOUNT_GET_APP_USER_BY_ID_URI = "/users/{user_id}";
    /* ACCOUNT URI and transactions types - END     */

    //TODO-EVG remove
    /* ShipEx URI and transactions types - BEGIN    */
    public static final String URI_SERVER_SHIP_EX = "http://localhost:8080/ShipEx";
    public static final String TRANSACTION_TYPE_SHIPPING_COST = "SHIPPINGCOST";
    public static final String TRANSACTION_TYPE_PLACE_SHIPPING_ORDER = "PlaceShippingOrder";
    public static final String SHIP_EX_RESPONSE_STATUS_OK = "SUCCESS";
    /* ShipEx URI and transactions names - END      */

    public static final String PROPERTY_IMAGE_MANAGEMENT_REPOSITORY = "advantage.imageManagement.repository";

    public static final String ENV_HIBERNATE_DIALECT = "hibernate.dialect";
    public static final String ENV_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String ENV_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String ENV_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";

    public static class UserSession {
        public static final String TOKEN = "token";
        public static final String IS_SUCCESS = "isSuccess";
        public static final String USER_ID = "userId";
    }

    public static class AppInitializer {
        private static final String LOCATION = "C:/temp/"; // Temporary location where files will be stored
        private static final long MAX_FILE_SIZE = 5242880; // 5MB : Max file size.
        // Beyond that size spring will throw exception.
        private static final long MAX_REQUEST_SIZE = 20971520; // 20MB : Total request size containing Multi part.
        private static final int FILE_SIZE_THRESHOLD = 0; // Size threshold after which files will be written to disk
    }

    public static class Payments {
        public static final String URI_SERVER_MASTER_CREDIT = "https://www.AdvantageOnlineBanking.com"; //  "/MasterCredit" + URI_API + "/v1";
        public static final String URI_SERVER_SAFE_PAY = "https://www.AdvantageOnlineBanking.com";      //  "/SafePay" + URI_API + "/v1";
    }
}
