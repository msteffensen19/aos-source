package com.advantage.common.utils;

/**
 * Created by Evgeney Fiskin on Jun-2016.
 */
public class LoggerUtils {
    public static String getMinThrowableDescription(Object message,Throwable e){
        StringBuilder sb=new StringBuilder(message.toString());
        sb.append("\n");
        sb.append(e.getClass().getName()).append(": ");
        sb.append(e.getMessage());
        return sb.toString();
    }
}
