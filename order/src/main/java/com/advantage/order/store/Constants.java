package com.advantage.order.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

@PropertySources(value = {@PropertySource("classpath:/services.properties")})
public class Constants {
    @Autowired
    private static Environment environment;

    /**
     * Keyboard special characters
     */
    public static final char COLLON = ':';

    public static final char DASH = '-';

    public static final char SEMI_COLLON = ';';

    public static final char SLASH = '/';

    public static final char SPACE = ' ';


    /**
     * prefix of REST API
     */
    public static final String URI_API = "/api";

    //	Binyamin Regev: For now it's here, needs T.B.D.
    public static final String SERVICE_URL_FORMAT = "http://%s:%s/%s" + URI_API + "/v1";
    //	Binyamin Regev - End

    public static final String URI_PRODUCTS = "/products";

    public static class UserSession {
        public static final String TOKEN = "token";
        public static final String IS_SUCCESS = "isSuccess";
        public static final String USER_ID = "userId";

    }

    public static class AttributeNames {
        public static final String ATTRIBUTE_PRICE = "Price";
        public static final String ATTRIBUTE_CUSTOMIZATION = "Customization";
        public static final String ATTRIBUTE_OPERATING_SYSTEM = "Operating System";
        public static final String ATTRIBUTE_PROCESSOR = "Processor";
        public static final String ATTRIBUTE_MEMORY = "Memory";
        public static final String ATTRIBUTE_COLOR = "Color";
        public static final String ATTRIBUTE_DISPLAY = "Display";
    }
}
