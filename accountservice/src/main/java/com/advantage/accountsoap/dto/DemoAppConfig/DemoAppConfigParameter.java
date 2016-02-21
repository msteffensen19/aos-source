package com.advantage.accountsoap.dto.DemoAppConfig;

/**
 * @author Binyamin Regev on 21/02/2016.
 */
public class DemoAppConfigParameter {
    private String parameterName;
    private String attributeTools;
    private String parameterValue;

    /**
     *
     */
    public DemoAppConfigParameter() {
    }

    /**
     *
     * @param parameterName
     * @param parameterValue
     */
    public DemoAppConfigParameter(String parameterName, String parameterValue) {
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }

    /**
     *
     * @param parameterName
     * @param attributeTools
     * @param parameterValue
     */
    public DemoAppConfigParameter(String parameterName, String attributeTools, String parameterValue) {
        this.parameterName = parameterName;
        this.attributeTools = attributeTools;
        this.parameterValue = parameterValue;
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

    /**
     *
     * @return
     */
    public String getParameterValue() {
        return parameterValue;
    }

    /**
     *
     * @param parameterValue
     */
    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }
}
