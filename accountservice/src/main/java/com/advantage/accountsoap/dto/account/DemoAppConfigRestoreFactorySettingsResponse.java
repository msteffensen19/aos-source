package com.advantage.accountsoap.dto.account;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

/**
 * @author Binyamin Regev on 25/02/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "response"
        })
@XmlRootElement(name = "DemoAppConfigRestoreFactorySettingsResponse", namespace = WebServiceConfig.NAMESPACE_URI)
public class DemoAppConfigRestoreFactorySettingsResponse {
    @XmlElement(name = "StatusMessage", namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private DemoAppConfigStatusResponse response;

    public DemoAppConfigRestoreFactorySettingsResponse() {
    }

    public DemoAppConfigRestoreFactorySettingsResponse(DemoAppConfigStatusResponse response) {
        this.response = response;
    }

    public DemoAppConfigStatusResponse getResponse() {
        return response;
    }

    public void setResponse(DemoAppConfigStatusResponse response) {
        this.response = response;
    }
}
