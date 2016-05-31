package com.advantage.common;

/**
 * Created by Evgeney Fiskin on May-2016.
 */
public class SystemParameters {

    public static String getHibernateHbm2ddlAuto(String hbm2ddlAutoMode) {
        String result;
        if (hbm2ddlAutoMode == null || hbm2ddlAutoMode.isEmpty()) {
            result = "validate";
        } else {
            switch (hbm2ddlAutoMode.toLowerCase()) {
                case "create":
                    result = "create";
                    break;
                case "create-drop":
                    result = "create-drop";
                    break;
                case "update":
                    result = "update";
                    break;
                case "validate":
                default:
                    result = "validate";
                    break;
            }
        }
        System.out.println(Constants.ENV_HIBERNATE_HBM2DDL_AUTO + "=" + result + " (from property file)");
        return result;
    }

    public static String getHibernateHbm2ddlAuto() {
        String result;
        String demoapp_db_change = System.getenv("DEMOAPP_DB_CHANGE");
        if (demoapp_db_change == null || demoapp_db_change.isEmpty()) {
            result = "validate";
        } else {
            switch (demoapp_db_change.toLowerCase()) {
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
        System.out.println(Constants.ENV_HIBERNATE_HBM2DDL_AUTO + "=" + result);
        return result;
    }
}
