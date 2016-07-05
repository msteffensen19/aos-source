package com.advantage.order.store.filters;

import org.apache.log4j.Logger;
import org.springframework.jmx.support.ObjectNameManager;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.management.MBeanServer;
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

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        ServletContext servletContext = this.getServletContext();
        String contextPath = servletContext.getContextPath();
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();

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

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
