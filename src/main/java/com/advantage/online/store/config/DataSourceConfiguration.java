package com.advantage.online.store.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.advantage.online.store.init.DataSourceInit;

import liquibase.integration.spring.SpringLiquibase;

/**
 * Created by kubany on 10/11/2015.
 */
@Configuration
public class DataSourceConfiguration {

    public static final String ENV_HIBERNATE_DB_LOGIN = "hibernate.db.login";
    public static final String ENV_HIBERNATE_DB_PASSWORD = "hibernate.db.password";
    public static final String ENV_HIBERNATE_DB_DRIVER_CLASSNAME = "hibernate.db.driver_classname";
    public static final String ENV_HIBERNATE_DB_URL = "hibernate.db.url";
    @Inject
    private Environment env;

    @Bean(initMethod = "init")
    public DataSourceInit initTestData() {
        return new DataSourceInit();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty(ENV_HIBERNATE_DB_DRIVER_CLASSNAME));
        dataSource.setUrl(env.getProperty(ENV_HIBERNATE_DB_URL));
        dataSource.setUsername(env.getProperty(ENV_HIBERNATE_DB_LOGIN));
        dataSource.setPassword(env.getProperty(ENV_HIBERNATE_DB_PASSWORD));

        return dataSource;
    }

    @Bean
    public SpringLiquibase liquibase()  {
        SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setDataSource(dataSource());
        liquibase.setChangeLog("classpath:db.changelog-master-test.xml");

        return liquibase;
    }
}
