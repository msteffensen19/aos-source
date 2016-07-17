package com.advantage.catalog.store.listener;

/**
 * Created by fiskine on 7/5/2016.
 */

import org.apache.log4j.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

public class SessionCounterServletRequestListener implements ServletRequestListener {
    private static AtomicInteger activeSessionsByRequestListener = new AtomicInteger(0);
    private static final Logger requestLogger = Logger.getLogger("RequestListener");
    private static final Logger logger = Logger.getLogger(SessionCounterServletRequestListener.class);

    public static int getActiveSessionsByRequestListener() {
        requestLogger.trace("Call static getActiveSessionsByRequestListener");
        return activeSessionsByRequestListener.get();
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        requestLogger.trace("requestInitialized");
        if (requestLogger.isDebugEnabled() || logger.isDebugEnabled()) {
            ServletRequest servletRequest = servletRequestEvent.getServletRequest();
            boolean isRequestIsHttpRequest = servletRequest instanceof HttpServletRequest;
            if (isRequestIsHttpRequest) {
                HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
                if (logger.isDebugEnabled() && isRequestIsHttpRequest) {
                    Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
                    StringBuffer sbf = new StringBuffer("The request headers are:").append(System.lineSeparator());
                    while (headerNames.hasMoreElements()) {
                        String headerName = headerNames.nextElement();
                        sbf.append(headerName).append(": ").append(httpServletRequest.getHeader(headerName)).append(System.lineSeparator());
                    }
                    logger.debug(sbf.toString());
                }

                if (requestLogger.isDebugEnabled()) {
                    StringBuffer sb = new StringBuffer();
                    sb.append("\tServletRequest.isAsyncSupported() = ").append(servletRequest.isAsyncSupported()).append(System.lineSeparator());

                    Enumeration<String> attributeNames = servletRequest.getAttributeNames();
                    sb.append("\tServletRequest.getAttributeNames()").append(System.lineSeparator());
                    while (attributeNames.hasMoreElements()) {
                        sb.append("\t\t").append(attributeNames.nextElement()).append(System.lineSeparator());
                    }

                    sb.append("\tServletRequest.getLocalAddr() = ").append(servletRequest.getLocalAddr()).append(System.lineSeparator());
                    sb.append("\tServletRequest.getLocalName() = ").append(servletRequest.getLocalName()).append(System.lineSeparator());
                    //sb.append("\tServletRequest.() = ").append(servletRequestEvent.getServletRequest().).append(System.lineSeparator());

                    requestLogger.debug(sb.toString());
                }
            }
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