package com.advantage.accountsoap.config;

import com.advantage.accountsoap.init.DataSourceInitByCsv;
import com.advantage.accountsoap.util.RestApiHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmokingGunInit {

    @Autowired
    DataSourceInitByCsv dataSourceInitByCsv;

    public void init() throws Exception{
        String runSmokingGunScenario = RestApiHelper.getDemoAppConfigParameterValue("duplicate_countries");
        dataSourceInitByCsv.setRunSmokingGunScenario(runSmokingGunScenario.equals("Yes"));
        dataSourceInitByCsv.init();
    }
}
