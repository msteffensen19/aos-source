package com.advantage.root.store.config;

import com.advantage.common.Constants;
import com.advantage.common.SystemParameters;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
public class JpaConfiguration {

    private static final Logger log = LoggerFactory.getLogger(JpaConfiguration.class);

    @Inject
    private Environment env;

    @Inject
    private DataSource dataSource;

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.advantage.root.store");
        emf.setPersistenceProvider(new HibernatePersistenceProvider());
        emf.setJpaProperties(jpaProperties());
        return emf;
    }

    private Properties jpaProperties() {
        Properties extraProperties = new Properties();
//        extraProperties.put(ENV_HIBERNATE_FORMAT_SQL, env.getProperty(ENV_HIBERNATE_FORMAT_SQL));
//        extraProperties.put(ENV_HIBERNATE_SHOW_SQL, env.getProperty(ENV_HIBERNATE_SHOW_SQL));
        extraProperties.put(Constants.ENV_HIBERNATE_HBM2DDL_AUTO, SystemParameters.getHibernateHbm2ddlAuto());
        if (log.isDebugEnabled()) {
            log.debug(" hibernate.dialect @" + env.getProperty(Constants.ENV_HIBERNATE_DIALECT));
        }
        if (env.getProperty(Constants.ENV_HIBERNATE_DIALECT) != null) {
            extraProperties.put(Constants.ENV_HIBERNATE_DIALECT, env.getProperty(Constants.ENV_HIBERNATE_DIALECT));
        }
        return extraProperties;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory().getObject());

        return tm;
    }
}
