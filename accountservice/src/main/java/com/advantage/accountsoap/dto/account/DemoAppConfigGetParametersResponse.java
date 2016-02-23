package com.advantage.accountsoap.dto.account;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Binyamin Regev on 21/02/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "parameter"
        })
@XmlRootElement(name = "GetAllDemoAppConfigParametersResponse", namespace = WebServiceConfig.NAMESPACE_URI)
public class DemoAppConfigGetParametersResponse {
    @XmlElement(name = "Parameter", namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private List<DemoAppConfigParameter> parameter;

    public DemoAppConfigGetParametersResponse() {
    }

    public List<DemoAppConfigParameter> getParameter() {
        if (this.parameter == null) {
            return new ArrayList<>();
        }
        return parameter;
    }

    public void setParameter(List<DemoAppConfigParameter> parameter) {
        this.parameter = parameter;
    }
}
