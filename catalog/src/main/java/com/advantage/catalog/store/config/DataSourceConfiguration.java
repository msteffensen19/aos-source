package com.advantage.catalog.store.config;

import com.advantage.catalog.store.init.DataSourceInit4Json;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.inject.Inject;
import javax.sql.DataSource;

/**
 * Created by kubany on 10/11/2015.
 */
@Configuration
public class DataSourceConfiguration {

    public static final String ENV_DB_URL_HOST = "catalog.hibernate.db.url.host";
    public static final String ENV_DB_URL_PORT = "catalog.hibernate.db.url.port";
    public static final String ENV_DB_URL_NAME = "catalog.hibernate.db.name";

    public static final String ENV_DB_URL_PREFIX = "db.url.prefix";
    public static final String ENV_DB_URL_QUERY = "db.url.query";

    public static final String ENV_HIBERNATE_DB_LOGIN = "catalog.hibernate.db.login";
    public static final String ENV_HIBERNATE_DB_PASSWORD = "catalog.hibernate.db.password";
    public static final String ENV_HIBERNATE_DB_DRIVER_CLASSNAME = "hibernate.db.driver_classname";

    @Inject
    private Environment env;

    /*@Bean(initMethod = "init")
    public DataSourceInit initTestData() {
        return new DataSourceInit();
    }*/

    @Bean(initMethod = "init")
    public DataSourceInit4Json initTestData() {
        return new DataSourceInit4Json();
    }

    @Bean
    public DataSource dataSource() {
        String ENV_HIBERNATE_DB_URL = String.format("%s://%s:%s/%s?%s", env.getProperty(ENV_DB_URL_PREFIX), env.getProperty(ENV_DB_URL_HOST), env.getProperty(ENV_DB_URL_PORT), env.getProperty(ENV_DB_URL_NAME), env.getProperty(ENV_DB_URL_QUERY));
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty(ENV_HIBERNATE_DB_DRIVER_CLASSNAME));
        dataSource.setUrl(ENV_HIBERNATE_DB_URL);
        dataSource.setUsername(env.getProperty(ENV_HIBERNATE_DB_LOGIN));
        dataSource.setPassword(env.getProperty(ENV_HIBERNATE_DB_PASSWORD));

        return dataSource;
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setDataSource(dataSource());
        liquibase.setChangeLog("classpath:db.changelog-master-test.xml");

        return liquibase;
    }
}
