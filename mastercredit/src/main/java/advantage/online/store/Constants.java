package advantage.online.store;

public class Constants {
    /**
     * Keyboard special characters
     */
    public static final char COLON = ':';
    public static final char DASH = '-';
    public static final char SEMICOLON = ';';
    public static final char SLASH = '/';
    public static final char SPACE = ' ';

    /**
     * prefix of REST API
     */
    public static final String URI_API = "/api";

    //	Binyamin Regev: For now it's here, needs T.B.D.
    public static final String URI_SERVER_ACCOUNT = "http://localhost:8080/account" + URI_API + "/v1";
    public static final String URI_SERVER_CATALOG = "http://localhost:8080/catalog" + URI_API + "/v1";
    public static final String URI_SERVER_ORDER = "http://localhost:8080/order" + URI_API + "/v1";
    public static final String URI_SERVER_SERVICE = "http://localhost:8080/service" + URI_API + "/v1";

    public static final String URI_SERVER_MASTER_CREDIT = "https://www.AdvantageOnlineBanking.com"; //  "/MasterCredit" + URI_API + "/v1";
    public static final String URI_SERVER_SAFE_PAY = "https://www.AdvantageOnlineBanking.com";      //  "/SafePay" + URI_API + "/v1";
    //	Binyamin Regev - End

    public static final String URI_PRODUCTS = "/products";

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

}