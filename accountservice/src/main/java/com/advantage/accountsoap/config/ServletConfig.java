package com.advantage.accountsoap.config;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
@Configuration
public class ServletConfig {

    @Inject
    Environment env;

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
        return (container -> {
            container.setPort(Integer.parseInt(env.getProperty("account.soapservice.url.port")));
        });
    }
}
