package com.advantage.catalog.store.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

import static com.advantage.common.util.ValidationHelper.isAuthorized;

@Aspect
public class ApiSecurityMethodInvokeAspect {
    @Around("execution(* *(..)) && @annotation(com.advantage.account.store.log.AppUserAuthorize)")
    public ResponseEntity authorize(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = null;
        String token = "";
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                request = (HttpServletRequest) arg;
            }
            if (arg instanceof String) {
                token = (String) arg;
            }
        }

        assert request != null;
        if (!isAuthorized(request.getSession(), token)) return unAuthorized();

        joinPoint.proceed();
        return null;
    }

    private static ResponseEntity unAuthorized() {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
