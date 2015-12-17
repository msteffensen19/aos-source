package com.advantage.online.store.config;

import com.advantage.online.store.log.ApiCallsLoggingAspect;
import com.advantage.online.store.log.ApiSecurityMethodInvokeAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.advantage.online.store.log.AdvantageDaoCallsLoggingAspect;

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
