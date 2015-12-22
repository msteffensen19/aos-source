package com.advantage.account.store.config;

import com.advantage.account.store.log.AdvantageDaoCallsLoggingAspect;
import com.advantage.account.store.log.ApiCallsLoggingAspect;
import com.advantage.account.store.log.ApiSecurityMethodInvokeAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdvantageAspects {

    @Bean
    public AdvantageDaoCallsLoggingAspect getLoggingAspect() {

        return new AdvantageDaoCallsLoggingAspect();
    }

    @Bean
    public ApiSecurityMethodInvokeAspect getAppUserAuthorization() {
        return new ApiSecurityMethodInvokeAspect();
    }

    @Bean
    public ApiCallsLoggingAspect getApiLoggingAspect() {
        return new ApiCallsLoggingAspect();
    }
}
