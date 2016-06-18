package com.advantage.accountsoap.config;

import com.advantage.common.SystemParameters;
import com.advantage.common.Constants;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class JpaConfig {
    @Inject
    DataSource dataSource;

//    @Value("${db.driver}")
//    private String DB_DRIVER;

//    @Value("${db.password}")
//    private String DB_PASSWORD;

    //    @Value("${hibernate.dialect}")
    @Value("${" + Constants.ENV_HIBERNATE_DIALECT_PARAMNAME + "}")
    private String HIBERNATE_DIALECT_VALUE;

    //    @Value("${hibernate.show_sql}")
    @Value("${" + Constants.ENV_HIBERNATE_SHOW_SQL_PARAMNAME + "}")
    private String HIBERNATE_SHOW_SQL_VALUE;

    //    @Value("${hibernate.hbm2ddl.auto}")
    @Value("${" + Constants.ENV_HIBERNATE_HBM2DDL_AUTO_PARAMNAME + "}")
    private String HIBERNATE_HBM2DDL_AUTO_VALUE;

    @Value("${account.hibernate.db.hbm2ddlAuto}")
    private String hibernate_db_hbm2ddlAuto;

//    @Value("${entitymanager.packagesToScan}")
//    private String ENTITYMANAGER_PACKAGES_TO_SCAN_VALUE;

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.advantage.accountsoap");
        emf.setPersistenceProvider(new HibernatePersistenceProvider());
        emf.setJpaProperties(jpaProperties());
        return emf;
    }

    private Properties jpaProperties() {
        Properties extraProperties = new Properties();
        extraProperties.put(Constants.ENV_HIBERNATE_HBM2DDL_AUTO_PARAMNAME, SystemParameters.getHibernateHbm2ddlAuto(hibernate_db_hbm2ddlAuto));

        extraProperties.put(Constants.ENV_HIBERNATE_DIALECT_PARAMNAME, HIBERNATE_DIALECT_VALUE);

        return extraProperties;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory().getObject());

        return tm;
    }
}
