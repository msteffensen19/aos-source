package com.advantage.catalog.store.config;

import com.advantage.catalog.store.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by dalie on 11/10/2015.
 */
@Configuration
@EnableSwagger2

public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                        //.paths(regex(".*" + Constants.URI_API + "/.*"))
                .paths(regex(Constants.URI_API + "/.*"))
                .build()
                .enableUrlTemplating(false)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Advantage - Catalog.WAR REST API",
                "Description.",
                null,
                null,
                null,
                null,
                null
        );
        return apiInfo;
    }
}
