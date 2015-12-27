package com.advantage.catalog.store.config;

import com.advantage.root.string_resources.Constants;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(
       // basePackageClasses = {Constants.class},
        basePackages = {"com.advantage.catalog"},
        excludeFilters = {
                @Filter(
                        type = FilterType.ANNOTATION,
                        value = {
                                RestController.class,
                                ControllerAdvice.class,
                                Configuration.class
                        }
                )
        }
)
@PropertySources(value = {
        @PropertySource("classpath:/app.properties"),
        //@PropertySource("classpath:/database.properties"),
        @PropertySource("classpath:/global.properties"),
        @PropertySource("classpath:/services.properties"),
        @PropertySource("classpath:/DemoApp.properties")
        //,@PropertySource("classpath:/mainconfiguration.properties")
})
public class AppConfiguration {

}