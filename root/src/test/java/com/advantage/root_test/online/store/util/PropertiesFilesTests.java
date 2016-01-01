package com.advantage.root_test.online.store.util;

import com.advantage.root.string_resources.Constants;
import com.advantage.root_test.cfg.AdvantageTestContextConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AdvantageTestContextConfiguration.class})
public class PropertiesFilesTests {
    @Autowired
    private Environment environment;

    @Test
    public void testExternalFile() throws Exception {
        System.out.println("#########TEST ROOT##########");
        String[] properties = {
                "account.hibernate.db.url.host",
                "account.hibernate.db.url.port",
                "account.hibernate.db.name",
                "account.hibernate.db.login",
                "account.hibernate.db.password",
                "account.service.url.host",
                "account.service.url.port",
                "account.service.url.suffix",

                "catalog.hibernate.db.url.host",
                "catalog.hibernate.db.url.port",
                "catalog.hibernate.db.name",
                "catalog.hibernate.db.login",
                "catalog.hibernate.db.password",
                "catalog.service.url.host",
                "catalog.service.url.port",
                "catalog.service.url.suffix",

                "order.hibernate.db.url.host",
                "order.hibernate.db.url.port",
                "order.hibernate.db.name",
                "order.hibernate.db.login",
                "order.hibernate.db.password",
                "order.service.url.host",
                "order.service.url.port",
                "order.service.url.suffix",

                "service.service.url.host",
                "service.service.url.port",
                "service.service.url.suffix",

                "mastercredit.service.url.host",
                "mastercredit.service.url.port",
                "mastercredit.service.url.suffix",

                "shipex.service.url.host",
                "shipex.service.url.port",
                "shipex.service.url.suffix",

                "safepay.service.url.host",
                "safepay.service.url.port",
                "safepay.service.url.suffix"};

        validatePropertiesName(properties, Constants.FILE_PROPERTIES_EXTERNAL);

    }

    private void validatePropertiesName(String[] properties, String fileProperties) throws Exception {
        for (String property : properties) {
            if (!environment.containsProperty(property))
                throw new Exception("Property file " + fileProperties + " doesn't contains property \"" + property + "\"");
        }
    }

}
