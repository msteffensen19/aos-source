package com.advantage.order.store.listener;

/**
 * Created by fiskine on 7/5/2016.
 */

import org.apache.log4j.Logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

public class SessionCounterListener implements ServletRequestListener {
    private static AtomicInteger activeSessionsByRequestListener = new AtomicInteger(0);
    private static final Logger requestLogger = Logger.getLogger("RequestListener");

    public static int getActiveSessionsByRequestListener() {
        requestLogger.trace("Call static getActiveSessionsByRequestListener");
        return activeSessionsByRequestListener.get();
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        requestLogger.trace("requestInitialized");
        if (requestLogger.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("\tServletRequest.isAsyncSupported() = ").append(servletRequestEvent.getServletRequest().isAsyncSupported()).append(System.lineSeparator());

            Enumeration<String> attributeNames = servletRequestEvent.getServletRequest().getAttributeNames();
            sb.append("\tServletRequest.getAttributeNames()").append(System.lineSeparator());
            while (attributeNames.hasMoreElements()) {
                sb.append("\t\t").append(attributeNames.nextElement()).append(System.lineSeparator());
            }

            sb.append("\tServletRequest.getLocalAddr() = ").append(servletRequestEvent.getServletRequest().getLocalAddr()).append(System.lineSeparator());
            sb.append("\tServletRequest.getLocalName() = ").append(servletRequestEvent.getServletRequest().getLocalName()).append(System.lineSeparator());
            //sb.append("\tServletRequest.() = ").append(servletRequestEvent.getServletRequest().).append(System.lineSeparator());

            requestLogger.debug(sb.toString());
        }
        activeSessionsByRequestListener.incrementAndGet();

        if (requestLogger.isInfoEnabled()) {
            requestLogger.info("activeSessionsByRequestListener = " + activeSessionsByRequestListener);
        }
    }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        requestLogger.trace("requestDestroyed");
        if (activeSessionsByRequestListener.get() > 0) {
            activeSessionsByRequestListener.decrementAndGet();
        }
        if (requestLogger.isInfoEnabled()) {
            requestLogger.info("activeSessionsByRequestListener = " + activeSessionsByRequestListener);
        }
    }

}