package com.advantage.accountsoap.dto.account;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

/**
 * For Requesting a value from &quat;DemoAppConfig.xml&quat; file. <br/>
 * {@link #parameterName} must have the parameter name <b>exactly as it is
 * written in &quat;DemoAppConfig.xml&quat;</b> file. <br/>
 * {@link #attributeTools} can be empty for ALL_TOOLS request. <br/>
 * @author Binyamin Regev on 21/02/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "parameterName",
        "attributeTools",
        "parameterNewValue"
})
@XmlRootElement(name = "DemoAppConfigurationRequest", namespace = WebServiceConfig.NAMESPACE_URI)
public class DemoAppConfigurationRequest {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = false)
    private String parameterName;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = false)
    private String attributeTools;      //  e.g. "LeanFT;LoadRunner;UFT...;StormRunner"
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = false)
    private String parameterNewValue;

    /**
     *
     */
    public DemoAppConfigurationRequest() {
    }

    /**
     *
     * @param parameterName
     * @param attributeTools
     */
    public DemoAppConfigurationRequest(String parameterName, String attributeTools, String parameterNewValue) {
        this.parameterName = parameterName;
        this.attributeTools = attributeTools;
        this.parameterNewValue = parameterNewValue;
    }

    /**
     *
     * @return
     */
    public String getParameterName() {
        return this.parameterName;
    }

    /**
     *
     * @param parameterName
     */
    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    /**
     *
     * @return
     */
    public String getAttributeTools() {
        return this.attributeTools;
    }

    /**
     *
     * @param attributeTools
     */
    public void setAttributeTools(String attributeTools) {
        this.attributeTools = attributeTools;
    }

    /**
     *
     * @return
     */
    public String getParameterNewValue() {
        return parameterNewValue;
    }

    /**
     *
     * @param parameterNewValue
     */
    public void setParameterNewValue(String parameterNewValue) {
        this.parameterNewValue = parameterNewValue;
    }
}
