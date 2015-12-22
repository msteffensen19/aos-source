package com.advantage.account.store.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Aspect
public class ApiCallsLoggingAspect {
    @Before("execution(* com.advantage.online.store.api.*.*(..))")
    public void logApiRequest(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = null;
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                request = (HttpServletRequest) arg;
                break;
            }
        }

        if (request != null) logApiRequest(request);
    }

    @AfterReturning(value = "execution(* com.advantage.online.store.api.*.*(..))", returning = "result")
    public void logApiResponse(JoinPoint joinPoint, Object result) {
        Logger logger = getLoggerFactory("HttpResponse");
        String builder = joinPoint.getSignature().getName() +
                " - Response StatusCode: " + ((ResponseEntity) result).getStatusCode();

        logger.info(builder);
    }

    private void logApiRequest(HttpServletRequest request) {
        Logger logger = getLoggerFactory("HttpRequest");
        logger.info(getLoggingRequest(request));
    }

    private Logger getLoggerFactory(String loggerName) {
        return LoggerFactory.getLogger(loggerName);
    }

    private String getLoggingRequest(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder(request.getServletPath());
        Map<String, String[]> params = request.getParameterMap();
        if (!params.isEmpty()) builder.append("?");
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            builder.append(entry.getKey()).append("=");
            for (String s : entry.getValue()) {
                builder.append(s).append("&");
            }
        }
        if (!params.isEmpty()) builder.delete(builder.length() - 1, builder.length());

        return builder.toString();
    }
}
