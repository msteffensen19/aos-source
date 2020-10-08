package com.advantage.order.store.config;

import com.advantage.common.Constants;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kubany on 10/11/2015.
 */

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(
        //basePackageClasses = {Constants.class},
        basePackages = "com.advantage.order",
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
        @PropertySource(value = Constants.FILE_PROPERTIES_APP, ignoreResourceNotFound = true),
        //@PropertySource("classpath:/database.properties"),
        @PropertySource(value = Constants.FILE_PROPERTIES_GLOBAL, ignoreResourceNotFound = true),
        @PropertySource(value = Constants.FILE_PROPERTIES_EXTERNAL, ignoreResourceNotFound = true),
        @PropertySource(value = Constants.FILE_PROPERTIES_INTERNAL, ignoreResourceNotFound = true),
        //@PropertySource(Constants.FILE_PROPERTIES_DEMO_APP),
        @PropertySource(Constants.FILE_PROPERTIES_VER_TXT)})
public class AppConfiguration {

}