package com.advantage.catalog.store.config;

import com.advantage.common.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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

    @Autowired
    Environment env;

    protected Logger logger = LogManager.getLogger("SwaggerConfiguration");

    @Bean
    public Docket api() {
        logger.info("EVG Swagger Configuration");
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                        //.paths(regex(".*" + Constants_catalog.URI_API + "/.*"))
                .paths(regex(Constants.URI_API + "/.*"))
                .build()
                .enableUrlTemplating(false)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        String apiInfoDescription = null;
        try {
            apiInfoDescription = String.format("Git Branch = %s<br/>Last commit revision = %s<br/>Last build time = %s<br/>Build on machine %s",
                    env.getProperty("mvn.scmBranch"), env.getProperty("mvn.commit.revision"), env.getProperty("mvn.buildTime"), env.getProperty("mvn.buildComputerName"));
        } catch (Exception e) {
            //e.printStackTrace();
            apiInfoDescription = "";
        }
        String projectVersion;
        try{
            projectVersion = env.getProperty("mvn.project.version") != null ? env.getProperty("mvn.project.version") : env.getProperty("project.version");
        }catch (Exception e){
            projectVersion = "";
        }
        ApiInfo apiInfo = new ApiInfo(
                "Advantage - " + env.getProperty("mvn.application.name") + ".war REST API",
                apiInfoDescription,
                projectVersion,
                null,
                null,
                null,
                null
        );
        return apiInfo;
    }

}
