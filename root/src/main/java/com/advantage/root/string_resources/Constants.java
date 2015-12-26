package com.advantage.root.string_resources;

public class Constants {
    public static final char COLLON = ':';
    public static final char DASH = '-';
    public static final char SEMI_COLLON = ';';
    public static final char SLASH = '/';
    public static final char SPACE = ' ';

    public static final String URI_API = "/api";

    public static final String URI_SERVER_ACCOUNT = "http://localhost:8080/account" + URI_API + "/v1";
    public static final String URI_SERVER_CATALOG = "http://localhost:8080/catalog" + URI_API + "/v1";
    public static final String URI_SERVER_ORDER = "http://localhost:8080/order" + URI_API + "/v1";
    public static final String URI_SERVER_SERVICE = "http://localhost:8080/service" + URI_API + "/v1";

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

    public static class AttributeNames {
        public static final String ATTRIBUTE_CUSTOMIZATION = "Customization";
        public static final String ATTRIBUTE_OPERATING_SYSTEM = "Operating System";
        public static final String ATTRIBUTE_PROCESSOR = "Processor";
        public static final String ATTRIBUTE_MEMORY = "Memory";
        public static final String ATTRIBUTE_DISPLAY = "Display";
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
        //	Binyamin Regev - End
    }
}
