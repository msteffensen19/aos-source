package com.advantage.accountsoap.dto.account;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

/**
 * @author Binyamin Regev on 24/02/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "parameterName",
        "attributeTools",
        "parameterNewValue"
})
@XmlRootElement(name = "UpdateDemoAppConfigParameterRequest", namespace = WebServiceConfig.NAMESPACE_URI)
public class UpdateDemoAppConfigParameterRequest {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String parameterName;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String attributeTools;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String parameterNewValue;

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getAttributeTools() {
        return attributeTools;
    }

    public void setAttributeTools(String attributeTools) {
        this.attributeTools = attributeTools;
    }

    public String getParameterNewValue() {
        return parameterNewValue;
    }

    public void setParameterNewValue(String parameterNewValue) {
        this.parameterNewValue = parameterNewValue;
    }
}
