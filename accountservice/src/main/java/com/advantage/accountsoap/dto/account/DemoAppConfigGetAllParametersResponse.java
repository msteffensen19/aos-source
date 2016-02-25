package com.advantage.accountsoap.dto.account;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Binyamin Regev on 25/02/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "parameters"
        })
@XmlRootElement(name = "DemoAppConfigGetAllParametersResponse", namespace = WebServiceConfig.NAMESPACE_URI)
public class DemoAppConfigGetAllParametersResponse {
    @XmlElement(name = "parameters",namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    protected List<DemoAppConfigParameter> parameters;

    public DemoAppConfigGetAllParametersResponse() {
    }

    public List<DemoAppConfigParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<DemoAppConfigParameter> parameters) {
        this.parameters = parameters;
    }
}
