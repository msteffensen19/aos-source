package com.advantage.order.store.filters;

import com.advantage.order.store.listener.SessionCounterListener;
import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.management.*;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Enumeration;

/**
 * Created by fiskine on 7/4/2016.
 */
public class DelayFilter extends OncePerRequestFilter {

    private final static Logger logger = Logger.getLogger(DelayFilter.class);
    private final static Logger loggerDev = Logger.getLogger("dev");
    private static final Logger requestLogger = Logger.getLogger("RequestLogger");
    private static final Logger sessionLogger = Logger.getLogger("SessionLogger");

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        requestLogger.debug("SessionCounterListener.getActiveSessionsByRequestListener() = " + SessionCounterListener.getActiveSessionsByRequestListener());
        sessionLogger.debug("SessionCounterListener.getActiveSessionsBySessionListener() = " + SessionCounterListener.getActiveSessionsBySessionListener());
        ServletContext servletContext = this.getServletContext();
        String contextPath = servletContext.getContextPath();
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();

        try {
            ObjectName objectName = new ObjectName("Catalina:type=Manager,context=" + contextPath + ",host=localhost");
            logger.trace("objectName.getKeyPropertyListString() = " + objectName.getKeyPropertyListString());
            MBeanInfo mBeanInfo = platformMBeanServer.getMBeanInfo(objectName);
            logger.trace("platformMBeanServer.getMBeanInfo(objectName).getDescription() = " + mBeanInfo.getDescription());
            int activeSessions = (int) platformMBeanServer.getAttribute(objectName, "activeSessions");
            logger.debug("platformMBeanServer.getAttribute(objectName,\"activeSessions\") = " + activeSessions);
            loggerDev.debug("platformMBeanServer.getAttribute(objectName,\"activeSessions\") = " + activeSessions);

            int ererer = 434;
        } catch (MalformedObjectNameException e) {
            logger.fatal(e);
        } catch (AttributeNotFoundException e) {
            logger.fatal(e);
        } catch (InstanceNotFoundException e) {
            logger.fatal(e);
        } catch (ReflectionException e) {
            logger.fatal(e);
        } catch (MBeanException e) {
            logger.fatal(e);
        } catch (IntrospectionException e) {
            logger.fatal(e);
        }

        if (logger.isTraceEnabled()) {
            String[] domains = platformMBeanServer.getDomains();
            StringBuilder sb = new StringBuilder("platformMBeanServer.getDomains():").append(System.lineSeparator());
            for (int i = 0; i < domains.length; i++) {
                sb.append("\t").append(domains[i]).append(System.lineSeparator());
            }
            logger.trace(sb.toString());
        }

        logger.trace("servletContext.getServerInfo() = " + servletContext.getServerInfo());
        logger.trace("servletContext.getContextPath() = " + contextPath);
        logger.trace("servletContext.getVirtualServerName() = " + servletContext.getVirtualServerName());
        logger.trace("servletContext.getServletContextName() = " + servletContext.getServletContextName());
        if (logger.isTraceEnabled()) {
            Enumeration<String> attributeNames = servletContext.getAttributeNames();
            StringBuilder sb = new StringBuilder("servletContext.getAttributeNames():").append(System.lineSeparator());
            while (attributeNames.hasMoreElements()) {
                sb.append("\t").append(attributeNames.nextElement()).append(System.lineSeparator());
            }
            logger.trace(sb.toString());
        }
        //servletContext.setAttribute("CURRENT_SESSIONS",);

        if (logger.isTraceEnabled() || loggerDev.isDebugEnabled()) {
            logger.trace("Start sleep: thread " + Thread.currentThread().getId());
            loggerDev.trace("Start sleep: thread " + Thread.currentThread().getId());
        }
        try {
            Thread.sleep(3000);
            logger.trace("End sleep: thread " + Thread.currentThread().getId());
            loggerDev.trace("End sleep: thread " + Thread.currentThread().getId());
        } catch (InterruptedException e) {
            logger.error(e);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
