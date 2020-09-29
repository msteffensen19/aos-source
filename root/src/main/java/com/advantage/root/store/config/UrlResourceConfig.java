package com.advantage.root.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UrlResourceConfig extends com.advantage.common.Url_resources {

    public UrlResourceConfig() {
        super();
    }

    @Bean(initMethod = "init")
    public UrlResourceConfig init() {
        return new UrlResourceConfig();
    }

}