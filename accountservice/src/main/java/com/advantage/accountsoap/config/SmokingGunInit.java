package com.advantage.accountsoap.config;

import com.advantage.accountsoap.init.DataSourceInitByCsv;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmokingGunInit {

    @Autowired
    DataSourceInitByCsv dataSourceInitByCsv;
    private boolean isDirty = false;
    private static final Logger logger = Logger.getLogger(SmokingGunInit.class);
    public void activate() throws Exception{
        if(isDirty)
            return;
        try {
            dataSourceInitByCsv.init(true);
            isDirty = true;
        } catch (Exception e){
            logger.error(e);
            e.printStackTrace();
        }
    }


}
