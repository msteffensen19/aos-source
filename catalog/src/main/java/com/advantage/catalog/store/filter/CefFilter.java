package com.advantage.catalog.store.filter;

import com.advantage.common.cef.CefHttpModel;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by fiskine on 7/14/2016.
 */
public class CefFilter implements Filter {

    private static final Logger cefLogger = Logger.getLogger("CEF");
    private static final Logger logger = Logger.getLogger(CefFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.trace("Start " + filterConfig.getFilterName() + " Init");
        if (logger.isDebugEnabled()) {
            StringBuffer sb = new StringBuffer("\tFilter ").append(filterConfig.getFilterName()).append(" config init parameters:").append(System.lineSeparator());
            Enumeration<String> initParameterNames = filterConfig.getInitParameterNames();
            while (initParameterNames.hasMoreElements()) {
                String initParameterName = initParameterNames.nextElement();
                sb.append("\t\t").append(initParameterName).append(" = ").append(filterConfig.getInitParameter(initParameterName)).append(System.lineSeparator());
            }
            logger.debug(sb.toString());
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (cefLogger.isInfoEnabled()) {
            logger.trace("Start");
            boolean isRequestIsHttpRequest = servletRequest instanceof HttpServletRequest;
            if (isRequestIsHttpRequest) {
                CefHttpModel cefData = new CefHttpModel("catalog", "HC-1.0.-SNAPSHOT");
                HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
                cefData.setRequestData(httpServletRequest);
                servletRequest.setAttribute("cefData", cefData);

                logger.trace("Before chain doFilter: cefDataId=" + cefData.toString());
                filterChain.doFilter(httpServletRequest, servletResponse);
                logger.trace("After chain doFilter: cefDataId=" + cefData.toString());

                HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
                cefData.setStatusCode(HttpStatus.valueOf(httpServletResponse.getStatus()));

                try {
                    cefLogger.info(cefData.cefFomatMessage());
                } catch (Exception e) {
                    logger.error("Incorrect cefData " + cefData.toString());
                }
            } else {
                logger.fatal("Its not a HTTPRequest");
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        logger.trace("CefFilter destroy");
    }
}
