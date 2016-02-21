package com.advantage.accountsoap.services;

import com.advantage.accountsoap.config.DemoAppConfigurationXml;
import com.advantage.accountsoap.dto.DemoAppConfigParameter;
import com.advantage.accountsoap.dto.DemoAppConfigurationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This service manages &quat;DemoAppConfig.xml&quat; parameters file. There is no
 * database involved and no transaction management reuired.
 * @author Binyamin Regev on 21/02/2016.
 */
public class DemoAppConfigService {
    @Autowired
    @Qualifier("demoAppConfigurationXml")
    private DemoAppConfigurationXml demoAppConfigurationXml;

    @Transactional(readOnly = true)
    public DemoAppConfigurationResponse getAllConfigurationParameters() {
        List<DemoAppConfigParameter> parameters = demoAppConfigurationXml.getAllDemoAppConfigParameters();
        return new DemoAppConfigurationResponse(parameters);
    }

    @Transactional(readOnly = true)
    public DemoAppConfigurationResponse getAllParametersByTool(String toolName) {
        List<DemoAppConfigParameter> parameters = demoAppConfigurationXml.getAllParametersByTool(toolName);
        return new DemoAppConfigurationResponse(parameters);
    }

    @Transactional(readOnly = true)
    public DemoAppConfigurationResponse updateParameterValue(String parameterName, String parameterNewValue) {
        DemoAppConfigurationResponse response = demoAppConfigurationXml.updateParameterValue(parameterName, parameterNewValue);
        return response;
    }
}
