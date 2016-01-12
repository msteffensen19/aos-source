package com.advantage.order.store.log;

import com.advantage.root.util.ValidationHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.security.Principal;
import java.util.Map;

//import org.springframework.ws.transport.context.TransportContext;
//import org.springframework.ws.transport.context.TransportContextHolder;
//import org.springframework.ws.transport.http.HttpServletConnection;

@Aspect
public class ApiSecurityMethodInvokeAspect {

    public ParameterNameDiscoverer discoverer;


    @Around("execution(* *(..)) && @annotation(com.advantage.order.store.log.AppUserAuthorize) && args(userId,..)")
    public ResponseEntity authorize(ProceedingJoinPoint joinPoint, Long userId) throws Throwable {
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

        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorization = httpServletRequest.getHeader("Authorization");
        Principal userPrincipal = httpServletRequest.getUserPrincipal();
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        //EXCEPTION Collection<Part> parts = httpServletRequest.getParts();
        String servletPath = httpServletRequest.getServletPath();


        MethodSignature msignature = (MethodSignature) joinPoint.getSignature();
        Method method = msignature.getMethod();
        Parameter[] parameters = method.getParameters();
        //discoverer=new AspectJAdviceParameterNameDiscoverer("@annotation(com.advantage.order.store.log.AppUserAuthorize)");
        discoverer = new StandardReflectionParameterNameDiscoverer();
        String[] parameterNames = discoverer.getParameterNames(method);

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

//    protected String getHttpHeaderValue(final String headerName) {
//        HttpServletRequest httpServletRequest = getHttpServletRequest();
//        return (null != httpServletRequest) ? httpServletRequest.getHeader(headerName) : null;
//    }
//
//    protected HttpServletRequest getHttpServletRequest() {
//        TransportContext ctx = TransportContextHolder.getTransportContext();
//        return (null != ctx) ? ((HttpServletConnection) ctx.getConnection()).getHttpServletRequest() : null;
//    }


}
