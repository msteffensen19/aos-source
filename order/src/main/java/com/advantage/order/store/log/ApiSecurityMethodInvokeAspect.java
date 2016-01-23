package com.advantage.order.store.log;

import com.advantage.common.SecurityTools;
import com.advantage.common.dto.AccountType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//import org.springframework.ws.transport.context.TransportContext;
//import org.springframework.ws.transport.context.TransportContextHolder;
//import org.springframework.ws.transport.http.HttpServletConnection;

@Aspect
public class ApiSecurityMethodInvokeAspect {

    public ParameterNameDiscoverer discoverer;


    @Around("execution(* *(..)) && @annotation(com.advantage.order.store.log.AuthorizeAsUser) && args(userId,..)")
    public ResponseEntity authorizeAsUser(ProceedingJoinPoint joinPoint, Long userId) throws Throwable {

        //final String AUTHORIZATION_PREFIX = "Bearer ";

        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        HttpStatus responseStatus = SecurityTools.isAutorized(authorizationHeader, userId, AccountType.USER);


//
//        if (authorizationHeader == null || authorizationHeader.isEmpty() || !authorizationHeader.startsWith(AUTHORIZATION_PREFIX)) {
//            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//        } else {
//            String token2 = authorizationHeader.substring(AUTHORIZATION_PREFIX.length());
//            try {
//                Token token = new TokenJWT(token2);
//                if (token.getUserId() != userId) {
//
//                    return new ResponseEntity(HttpStatus.FORBIDDEN);
//                }
//                if (!token.getAccountType().equals(AccountType.USER)) {
//                    return new ResponseEntity(HttpStatus.FORBIDDEN);
//                }
//            } catch (Throwable e) {
//                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//            }
//        }

/*

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

        Principal userPrincipal = httpServletRequest.getUserPrincipal();
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        //EXCEPTION Collection<Part> parts = httpServletRequest.getParts();
        String servletPath = httpServletRequest.getServletPath();


        MethodSignature msignature = (MethodSignature) joinPoint.getSignature();
        Method method = msignature.getMethod();
        Parameter[] parameters = method.getParameters();
        //discoverer=new AspectJAdviceParameterNameDiscoverer("@annotation(com.advantage.order.store.log.AuthorizeAsUser)");
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
*/

        ResponseEntity response;

        if (responseStatus != null) {
            response = new ResponseEntity(responseStatus);
        } else {
            response = (ResponseEntity) joinPoint.proceed();
        }
        return response;
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
