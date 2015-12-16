package com.advantage.online.store;

public class Constants {
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
