package com.advantage.common;

public class Constants {


    public static final char AT_SIGN    = '@';
    public static final char COMMA      = ',';
    public static final char DOT        = '.';
    public static final char MODULU     = '%';
    public static final char POWER      = '^';
    public static final char SPACE      = ' ';
    public static final char TAB        = '\t';
    public static final char UNDERSCORE = '_';

    public static final String DOUBLE_SPACES    = "  ";
    public static final String EMPTY_STRING     = "";
    public static final String TRIPLE_SPACES    = "   ";
    public static final String NEW_LINE         = "\n\r";
    public static final String NOT_FOUND        = "NOT FOUND";

    public static final String URI_API = "/api";

    public static final String FILE_PROPERTIES_INTERNAL = "classpath:properties/internal_config_for_env.properties";
    public static final String FILE_PROPERTIES_EXTERNAL = "classpath:properties/services.properties";
    public static final String FILE_PROPERTIES_GLOBAL = "classpath:properties/global.properties";
    public static final String FILE_PROPERTIES_DEMO_APP = "classpath:/DemoApp.properties";
    public static final String FILE_PROPERTIES_APP = "classpath:/app.properties";
    public static final String FILE_DEMO_APP_CONFIG_XML = "classpath:/DemoAppConfig.xml";
    public static final String FILE_PROPERTIES_LIQUIBASE_AND_HIBERNATE = "classpath:properties/lb_hib.properties";

    public final static String ENV_USER_LOGIN_BLOCKING = "user.login.blocking";
    public final static String ENV_ADD_EMAIL_FIELD_TO_LOGIN = "email.address.in.login";
    public final static String ENV_NUMBER_OF_LOGIN_TRIES_BEFORE_BLOCKING = "number.of.login.tries.before.blocking";
    public final static String ENV_PRODUCT_INSTOCK_DEFAULT_VALUE = "product.inStock.default.value";

    //TODO-EVG remove
    @Deprecated
    public static final String URI_SERVER_ORDER = "http://localhost:8080/order" + URI_API + "/v1";
    @Deprecated
    public static final String URI_SERVER_SERVICE = "http://localhost:8080/service" + URI_API + "/v1";

    //TODO-EVG remove
    /* CATALOG Service related constants - BEGIN    */
    @Deprecated
    public static final String URI_SERVER_CATALOG = "http://localhost:8080/catalog" + URI_API + "/v1";
    public static final String CATALOG_GET_PRODUCT_BY_ID_URI = "/products/{product_id}";
    /* CATALOG Service related constants - END      */

    //TODO-EVG remove
    @Deprecated
    public static final String URI_SERVER_ACCOUNT = "http://localhost:8080/account" + URI_API + "/v1";
    public static final String ACCOUNT_GET_APP_USER_BY_ID_URI = "/users/{user_id}";

    //TODO-EVG remove
    @Deprecated
    public static final String URI_SERVER_SHIP_EX = "http://localhost:8080/ShipEx";
    public static final String TRANSACTION_TYPE_SHIPPING_COST = "SHIPPINGCOST";
    public static final String TRANSACTION_TYPE_PLACE_SHIPPING_ORDER = "PlaceShippingOrder";

    public static final String PROPERTY_IMAGE_MANAGEMENT_REPOSITORY = "advantage.imageManagement.repository";

    @Deprecated
    public static final String PART_DB_URL_HOST_PARAMNAME = "hibernate.db.url.host";
    @Deprecated
    public static final String PART_DB_URL_PORT_PARAMNAME = "hibernate.db.url.port";
    @Deprecated
    public static final String PART_DB_URL_NAME_PARAMNAME = "hibernate.db.name";

    public static final String ENV_DB_URL_PREFIX_PARAMNAME = "db.url.prefix";
    public static final String ENV_DB_URL_QUERY_PARAMNAME = "db.url.query";

    public static final String PART_HIBERNATE_DB_LOGIN_PARAMNAME = "hibernate.db.login";
    public static final String PART_HIBERNATE_DB_PASSWORD_PARAMNAME = "hibernate.db.password";

    @Deprecated
    public static final String ENV_HIBERNATE_DIALECT = "hibernate.dialect";
    public static final String ENV_HIBERNATE_DIALECT_PARAMNAME = "hibernate.dialect";
    public static final String ENV_HIBERNATE_HBM2DDL_AUTO_PARAMNAME = "hibernate.hbm2ddl.auto";
    @Deprecated
    public static final String ENV_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    public static final String ENV_HIBERNATE_SHOW_SQL_PARAMNAME = "hibernate.show_sql";
    @Deprecated
    public static final String ENV_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    public static final String ENV_HIBERNATE_FORMAT_SQL_PARAMNAME = "hibernate.format_sql";

    public static final String URI_SCHEMA = "http";
    public static final String FILE_PROPERTIES_VER_TXT = "classpath:/ver.txt";

    public static final String ENV_HIBERNATE_DB_DRIVER_CLASSNAME_PARAMNAME = "hibernate.db.driver_classname";
    public static final String ENV_LIQUIBASE_FILE_CHANGELOG_PARAMNAME = "liquibase.file.changelog";


    public static class UserSession {
        public static final String TOKEN = "token";
        public static final String IS_SUCCESS = "isSuccess";
        public static final String USER_ID = "userId";
    }

    public static class AppInitializer {
        // USE System.Temp private static final String LOCATION = "C:/temp/"; // Temporary location where files will be stored
        private static final long MAX_FILE_SIZE = 5242880; // 5MB : Max file size.
        // Beyond that size spring will throw exception.
        private static final long MAX_REQUEST_SIZE = 20971520; // 20MB : Total request size containing Multi part.
        private static final int FILE_SIZE_THRESHOLD = 0; // Size threshold after which files will be written to disk
    }

    public static String paramNameAsParam(String paramName) {
        StringBuilder sb = new StringBuilder("${");
        sb.append(paramName);
        sb.append("}");
        return sb.toString();
    }

}
