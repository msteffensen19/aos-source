package com.advantage.order.store.log;

import com.advantage.root.util.ValidationHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
public class ApiSecurityMethodInvokeAspect {
    @Around("execution(* *(..)) && @annotation(com.advantage.order.store.log.AppUserAuthorize)")
    public ResponseEntity authorize(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = null;
        Signature signature = joinPoint.getSignature();
        String declaringTypeName = signature.getDeclaringTypeName();
        Class declaringType = signature.getDeclaringType();
        String name = signature.getName();
        String token = "";
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                request = (HttpServletRequest) arg;
            }
            if (arg instanceof String) {
                token = (String) arg;
            }
        }

        MethodSignature msignature = (MethodSignature) joinPoint.getSignature();
        Method method = msignature.getMethod();
        Parameter[] parameters = method.getParameters();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint.getTarget().getClass().getDeclaredMethod(joinPoint.getSignature().getName(),
                        method.getParameterTypes());
                int i = 1;
            } catch (final SecurityException exception) {
                //...
            } catch (final NoSuchMethodException exception) {
                //...
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
