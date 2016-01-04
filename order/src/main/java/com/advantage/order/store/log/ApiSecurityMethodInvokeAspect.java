package com.advantage.order.store.log;

import com.advantage.root.store.util.ValidationHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

@Aspect
public class ApiSecurityMethodInvokeAspect {
    @Around("execution(* *(..)) && @annotation(com.advantage.order.store.log.AppUserAuthorize)")
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
        if (!ValidationHelper.isAuthorized(request.getSession(), token)) return unAuthorized();

        joinPoint.proceed();
        return null;
    }

    private static ResponseEntity unAuthorized() {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
