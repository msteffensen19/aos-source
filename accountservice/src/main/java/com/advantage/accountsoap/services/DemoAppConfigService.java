package com.advantage.accountsoap.services;

import com.advantage.accountsoap.config.DemoAppConfigurationXml;
import com.advantage.accountsoap.dto.account.DemoAppConfigParameter;
import com.advantage.accountsoap.dto.account.DemoAppConfigurationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Node;

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

    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public DemoAppConfigurationResponse getAllConfigurationParameters() {
        List<DemoAppConfigParameter> parameters = demoAppConfigurationXml.getAllDemoAppConfigParameters();
        return new DemoAppConfigurationResponse(parameters);
    }

    /**
     *
     * @param toolName
     * @return
     */
    @Transactional(readOnly = true)
    public DemoAppConfigurationResponse getParametersByTool(String toolName) {
        List<DemoAppConfigParameter> parameters = demoAppConfigurationXml.getAllParametersByTool(toolName);
        return new DemoAppConfigurationResponse(parameters);
    }

    /**
     *
     * @param parameterName
     * @param parameterNewValue
     * @return
     */
    @Transactional(readOnly = true)
    public DemoAppConfigurationResponse updateParameterValue(String parameterName, String parameterNewValue) {
        DemoAppConfigurationResponse response = demoAppConfigurationXml.updateParameterValue(parameterName, parameterNewValue);
        return response;
    }
}
