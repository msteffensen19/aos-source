package com.advantage.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * @author Binyamin Regev on 22/03/2016.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class DemoAppConfigParameter {
    @JsonProperty("parameterName")
    private String parameterName;
    @JsonProperty("attributeTools")
    private String attributeTools;
    @JsonProperty("parameterValue")
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
