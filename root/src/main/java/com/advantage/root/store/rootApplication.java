package com.advantage.root.store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
@ConditionalOnProperty(
        value="aos.gateway.mode",
        havingValue = "true",
        matchIfMissing = false)
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@RestController
public class rootApplication extends SpringBootServletInitializer {

    @Autowired
    private Environment environment;

    private static final Logger logger = LogManager.getLogger(rootApplication.class);

    @RequestMapping("/services.properties")
    @CrossOrigin
    public String getServicesProperties(HttpServletResponse response) {
        logger.info("Writing services properties from spring cloud config");
        Map<String, String> map = new HashMap();
        Gson gson = new GsonBuilder().create();
        String jsonString = "";
        try{
            for (PropertySource<?> propertySource : ((AbstractEnvironment) environment).getPropertySources()){
                if(propertySource.getName().contains("bootstrapProperties-file")){
                    map.putAll((Map<? extends String, ? extends String>) propertySource.getSource());
                }
            }
            jsonString = gson.toJson(map);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        response.setContentType("text/html");
        return jsonString;
    }
}
