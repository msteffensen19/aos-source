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

public class SessionCounterListener implements ServletRequestListener, HttpSessionListener {
    private static int activeSessionsByRequestListener = 0;
    @Deprecated
    private static int activeSessionsBySessionListener = 0;
    private static final Logger requestLogger = Logger.getLogger("RequestLogger");
    @Deprecated
    private static final Logger sessionLogger = Logger.getLogger("SessionLogger");

    public static int getActiveSessionsByRequestListener() {
        requestLogger.trace("Call static getActiveSessionsByRequestListener");
        return activeSessionsByRequestListener;
    }

    @Deprecated
    public static int getActiveSessionsBySessionListener() {
        sessionLogger.trace("Call static getActiveSessionsByRequestListener");
        return activeSessionsBySessionListener;
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
        activeSessionsByRequestListener++;

        if (requestLogger.isInfoEnabled()) {
            requestLogger.info("activeSessionsByRequestListener = " + activeSessionsByRequestListener);
        }
    }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        requestLogger.trace("requestDestroyed");
        if (activeSessionsByRequestListener > 0) {
            activeSessionsByRequestListener--;
        }
        if (requestLogger.isInfoEnabled()) {
            requestLogger.info("activeSessionsByRequestListener = " + activeSessionsByRequestListener);
        }
    }

    @Override
    @Deprecated
    public void sessionCreated(HttpSessionEvent se) {
        boolean isNewSession = se.getSession().isNew();
        sessionLogger.debug("Session new = " + isNewSession);
        activeSessionsBySessionListener++;
        if (sessionLogger.isDebugEnabled()) {
            String sessionId = se.getSession().getId();
            sessionLogger.debug("HttpSessionId = " + sessionId);
            sessionLogger.debug("activeSessionsBySessionListener = " + activeSessionsBySessionListener);
        }

    }

    @Override
    @Deprecated
    public void sessionDestroyed(HttpSessionEvent se) {
        if (activeSessionsBySessionListener > 0) {
            activeSessionsBySessionListener--;
        }
        if (sessionLogger.isDebugEnabled()) {
            String sessionId = se.getSession().getId();
            sessionLogger.debug("HttpSessionId = " + sessionId);
            sessionLogger.debug("activeSessionsBySessionListener = " + activeSessionsBySessionListener);
        }
    }
}