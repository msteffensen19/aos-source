package com.advantage.accountsoap.dto.account;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

/**
 * @author Binyamin Regev on 24/02/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "response"
        })
@XmlRootElement(name = "UpdateDemoAppConfigParameterResponse", namespace = WebServiceConfig.NAMESPACE_URI)
public class UpdateDemoAppConfigParameterResponse {
    @XmlElement(name = "StatusMessage", namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private DemoAppConfigStatusResponse response;

    public UpdateDemoAppConfigParameterResponse() {
    }

    public UpdateDemoAppConfigParameterResponse(DemoAppConfigStatusResponse response) {
        this.response = response;
    }

    public DemoAppConfigStatusResponse getResponse() {
        return response;
    }

    public void setResponse(DemoAppConfigStatusResponse response) {
        this.response = response;
    }
}
