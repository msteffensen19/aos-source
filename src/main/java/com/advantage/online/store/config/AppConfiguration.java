package com.advantage.online.store.config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kubany on 10/11/2015.
 */
import com.advantage.online.store.Constants;
import com.advantage.online.store.dao.CategoryRepository;

@Configuration
@ComponentScan(
        basePackageClasses = {Constants.class},
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
        @PropertySource("classpath:/database.properties")})
public class AppConfiguration {

	/*@Bean
	public EntityManagerFactory entityManagerFactory() {
		
		return Persistence.createEntityManagerFactory("com.advantage.online.store.dao");
	}*/
}