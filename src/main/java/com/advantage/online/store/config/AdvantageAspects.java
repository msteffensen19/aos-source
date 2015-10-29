package com.advantage.online.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.advantage.online.store.log.AdvantageDaoCallsLoggingAspect;

@Configuration
public class AdvantageAspects {

	@Bean
	public AdvantageDaoCallsLoggingAspect getLoggingAspect() {

		return new AdvantageDaoCallsLoggingAspect();
	}
}
