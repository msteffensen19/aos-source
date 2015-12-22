package com.advantage.mastercredit.payment.log;

import com.advantage.mastercredit.util.ArgumentValidationHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An aspect class for logging data access object methods calls.
 */
@Aspect
public class AdvantageDaoCallsLoggingAspect {

    @Before("execution(* com.advantage.online.store.dao.*.*(..))")
    public void logBeforeDaoCall(final JoinPoint joinPoint) {

        ArgumentValidationHelper.validateArgumentIsNotNull(joinPoint, "join point");
        logDaoCall(joinPoint, true);
    }

    @After("execution(* com.advantage.online.store.dao.*.*(..))")
    public void logAfterDaoCall(final JoinPoint joinPoint) {

        ArgumentValidationHelper.validateArgumentIsNotNull(joinPoint, "join point");
        logDaoCall(joinPoint, false);
    }

    private void logDaoCall(final JoinPoint joinPoint, final boolean before) {

        assert joinPoint != null;

        final Signature signature = joinPoint.getSignature();
        final Class<?> cls = signature.getDeclaringType();
        final Logger logger = LoggerFactory.getLogger(cls);
        final String daoMethodName = signature.getName();
        final StringBuilder info;

        if (before) {

            info = new StringBuilder("Before");
        } else {

            info = new StringBuilder("After");
        }

        info.append(" DAO call: ");
        info.append(daoMethodName);
        final String infoString = info.toString();
        logger.info(infoString);
    }
}