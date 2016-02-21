package com.advantage.accountsoap.dto.DemoAppConfig;

/**
 * For Requesting a value from &quat;DemoAppConfig.xml&quat; file. <br/>
 * {@link #parameterName} must have the parameter name <b>exactly as it is
 * written in &quat;DemoAppConfig.xml&quat;</b> file. <br/>
 * {@link #attributeTools} can be empty for ALL_TOOLS request. <br/>
 * @author Binyamin Regev on 21/02/2016.
 */
public class DemoAppConfigurationRequest {
    private String parameterName;
    private String attributeTools;      //  e.g. "LeanFT;LoadRunner;UFT...;StormRunner"

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
    public DemoAppConfigurationRequest(String parameterName, String attributeTools) {
        this.parameterName = parameterName;
        this.attributeTools = attributeTools;
    }

    /**
     *
     * @return
     */
    public String getParameterName() {
        return parameterName;
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
        return attributeTools;
    }

    /**
     *
     * @param attributeTools
     */
    public void setAttributeTools(String attributeTools) {
        this.attributeTools = attributeTools;
    }
}
