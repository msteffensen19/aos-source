import com.advantage.online.store.dao.CategoryRepository;
import com.advantage.online.store.init.DataSourceInit;
import com.advantage.online.store.model.Category;
import com.advantage.online.store.services.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by kubany on 10/14/2015.
 */


@RunWith(SpringJUnit4ClassRunner.class)
// ApplicationContext will be loaded from the static inner ContextConfiguration class
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class ProductRestWebServiceTest {

    @Configuration
    @ComponentScan({"com.advantage.online.store.services", "com.advantage.online.store.dao",
            "com.advantage.online.store.init"})
    static class ContextConfiguration {

//        @Bean(initMethod = "init")
//        public DataSourceInit initTestData() {
//
//            return new DataSourceInit();
//        }

        @Bean(name = "transactionManager")
        public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory,
                                                             DriverManagerDataSource dataSource) {
            JpaTransactionManager transactionManager = new JpaTransactionManager();
            transactionManager.setEntityManagerFactory(entityManagerFactory);
            transactionManager.setDataSource(dataSource);
            return transactionManager;
        }

        @Bean(name = "datasource")
        public DriverManagerDataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(org.hsqldb.jdbcDriver.class.getName());
            dataSource.setUrl("jdbc:hsqldb:mem:mydb");
            dataSource.setUsername("sa");
            dataSource.setPassword("jdbc:hsqldb:mem:mydb");
            return dataSource;
        }

        @Bean(name = "entityManagerFactory")
        public LocalContainerEntityManagerFactoryBean entityManagerFactory(DriverManagerDataSource dataSource) {

            LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
            entityManagerFactoryBean.setDataSource(dataSource);
            entityManagerFactoryBean.setPackagesToScan(new String[]{"com.advantage.online.store.model"});
            entityManagerFactoryBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
            entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

            Map<String, Object> jpaProperties = new HashMap<String, Object>();
            jpaProperties.put("hibernate.hbm2ddl.auto", "create");
            jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
            jpaProperties.put("hibernate.show_sql", "true");
            jpaProperties.put("hibernate.format_sql", "true");
            jpaProperties.put("hibernate.use_sql_comments", "true");
            entityManagerFactoryBean.setJpaPropertyMap(jpaProperties);

            return entityManagerFactoryBean;
        }
    }



    @Autowired
    CategoryRepository c;

    @Test
    public void testFindUserByUsername() {
        List<Category> cat = c.getAllCategories();
        assertTrue("Unexpected user ", cat.isEmpty());
    }
}