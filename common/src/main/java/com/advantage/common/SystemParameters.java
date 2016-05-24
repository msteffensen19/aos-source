package com.advantage.common;

/**
 * Created by Evgeney Fiskin on May-2016.
 */
public class SystemParameters {

    public static String getHibernateHbm2ddlAuto() {
        String result;
        String db_create_or_update = System.getenv("DB_CHANGE").toLowerCase();
        if (db_create_or_update == null && db_create_or_update.isEmpty()) {
            result = "validate";
        } else {
            switch (db_create_or_update) {
                case "new":
                    result = "create";
                    break;
                case "renew":
                    result = "create-drop";
                    break;
                case "update":
                    result = "update";
                    break;
                case "current":
                default:
                    result = "validate";
                    break;
            }
        }
        return result;
    }
}
