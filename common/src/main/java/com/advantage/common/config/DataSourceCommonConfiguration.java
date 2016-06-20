package com.advantage.common.config;

import com.advantage.common.Constants;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by Evgeney Fiskin on May-2016.
 */
public abstract class DataSourceCommonConfiguration {
    private String propertyPrefix;

    private Logger logger;

    @Autowired
    private Environment environment;

    public DataSourceCommonConfiguration(String propertyPrefix) {
        logger = Logger.getLogger(DataSourceCommonConfiguration.class);
        this.propertyPrefix = propertyPrefix;
    }

    @Bean
    public DataSource dataSource() {
        logger.debug("Start DataSource Bean");
//        if (logger.isDebugEnabled()) {
//            logger.debug(compileLog());
//        }
        String ENV_HIBERNATE_DB_URL = String.format("%s://%s:%s/%s?%s",
                environment.getProperty(Constants.ENV_DB_URL_PREFIX_PARAMNAME),
                environment.getProperty(propertyPrefix + Constants.PART_DB_URL_HOST_PARAMNAME),
                environment.getProperty(propertyPrefix + Constants.PART_DB_URL_PORT_PARAMNAME),
                environment.getProperty(propertyPrefix + Constants.PART_DB_URL_NAME_PARAMNAME),
                environment.getProperty(Constants.ENV_DB_URL_QUERY_PARAMNAME)
        );
        logger.debug("ENV_HIBERNATE_DB_URL = " + ENV_HIBERNATE_DB_URL);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty(Constants.ENV_HIBERNATE_DB_DRIVER_CLASSNAME_PARAMNAME));
        dataSource.setUrl(ENV_HIBERNATE_DB_URL);
        dataSource.setUsername(environment.getProperty(propertyPrefix + Constants.PART_HIBERNATE_DB_LOGIN_PARAMNAME));
        dataSource.setPassword(environment.getProperty(propertyPrefix + Constants.PART_HIBERNATE_DB_PASSWORD_PARAMNAME));
        return dataSource;
    }

    @Bean //TODO-EVG apply changelog
    public SpringLiquibase liquibase() {
        logger.debug("Start LIQUIBASE Bean");
        if (logger.isTraceEnabled()) {
            logger.trace(compileLog());
        }
        SpringLiquibase liquibase = new SpringLiquibase();
        try {
            DataSource dataSource = dataSource();
            //liquibase.setDropFirst();
            liquibase.setDataSource(dataSource);
            liquibase.setIgnoreClasspathPrefix(true);
            logger.trace("liquibase.setIgnoreClasspathPrefix(true)");
            liquibase.setChangeLog("classpath:" + environment.getProperty(Constants.ENV_LIQUIBASE_FILE_CHANGELOG_PARAMNAME));
            liquibase.setDropFirst(Boolean.parseBoolean(environment.getProperty("liquibase.dropFirst")));
            logger.trace(dataSource);

            if (logger.isTraceEnabled()) {
                String databaseProductName = liquibase.getDatabaseProductName();
                boolean dropFirst = liquibase.isDropFirst();
                //liquibase.afterPropertiesSet();
                String tag = liquibase.getTag();
                String contexts = liquibase.getContexts();
                String beanName = liquibase.getBeanName();
                String labels = liquibase.getLabels();
                String defaultSchema = liquibase.getDefaultSchema();
                String catalog = liquibase.getDataSource().getConnection().getCatalog();

                StringBuilder sb = new StringBuilder("SpringLiquibase object");
                sb.append("liquibase.dropFirst = ").append(dropFirst);
                sb.append("Set changelog file = classpath:").append(environment.getProperty(Constants.ENV_LIQUIBASE_FILE_CHANGELOG_PARAMNAME));
                sb.append("liquibase.databaseProductName = ").append(databaseProductName);
                sb.append("liquibase.tag = ").append(tag);
                sb.append("liquibase.contexts = ").append(contexts == null ? "null" : contexts);
                sb.append("liquibase.beanName = ").append(beanName);
                sb.append("liquibase.labels = ").append(labels);
                sb.append("liquibase.defaultSchema = ").append(defaultSchema);
                sb.append("liquibase.getDataSource().getConnection().getCatalog() = ").append(catalog);
                logger.trace(sb.toString());

            }
        } catch (SQLException e) {
            logger.debug(e);
        } catch (DatabaseException e) {
            logger.debug(e);
        } catch (LiquibaseException e) {
            logger.debug(e);
        }
        return liquibase;
    }

    private String compileLog() {
        StringBuilder sb = new StringBuilder("Read properties");
        sb.append(logParam(environment, "liquibase.properties.description"));
        sb.append(logParam(environment, "liquibase.file.changelog"));
        sb.append(logParam(environment, "liquibase.diffTypes"));
        sb.append(logParam(environment, "liquibase.dropFirst"));
        sb.append(logParam(environment, "hibernate.format_sql"));
        sb.append(logParam(environment, "hibernate.show_sql"));
        sb.append(logParam(environment, "hibernate.hbm2ddl.auto"));
        return sb.toString();
    }

    private static String logParam(Environment environment, String s) {
        if (environment == null) {
            return "Environment is null\n";
        } else {
            return s + " = " + (environment.getProperty(s) == null ? "null" : environment.getProperty(s)) + System.lineSeparator();
        }
    }
}
