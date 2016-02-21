package com.advantage.accountsoap.dto.DemoAppConfig;

import java.util.List;

/**
 * Created by regevb on 21/02/2016.
 */
public class DemoAppConfigurationResponse {
    private List<DemoAppConfigParameter> demoAppConfigParameters;
    private boolean success;
    private String reason;
    private long code;          //  Additional Success/Error code

    /**
     *
     */
    public DemoAppConfigurationResponse() {
    }

    /**
     *
     * @param demoAppConfigParameters
     */
    public DemoAppConfigurationResponse(List<DemoAppConfigParameter> demoAppConfigParameters) {
        this.demoAppConfigParameters = demoAppConfigParameters;
    }

    /**
     *
     * @param success
     * @param reason
     */
    public DemoAppConfigurationResponse(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
        this.code = 0;
    }

    /**
     *
     * @param success
     * @param reason
     * @param code
     */
    public DemoAppConfigurationResponse(boolean success, String reason, long code) {
        this.success = success;
        this.reason = reason;
        this.code = code;
    }

    /**
     *
     * @param success
     * @param reason
     * @param demoAppConfigParameters
     */
    public DemoAppConfigurationResponse(boolean success, String reason, List<DemoAppConfigParameter> demoAppConfigParameters) {
        this.success = success;
        this.reason = reason;
        this.code = 0;
        this.demoAppConfigParameters = demoAppConfigParameters;
    }

    /**
     *
     * @param success
     * @param reason
     * @param code
     * @param demoAppConfigParameters
     */
    public DemoAppConfigurationResponse(boolean success, String reason, long code, List<DemoAppConfigParameter> demoAppConfigParameters) {
        this.success = success;
        this.reason = reason;
        this.code = code;
        this.demoAppConfigParameters = demoAppConfigParameters;
    }

    /**
     *
     * @return
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     *
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     *
     * @return
     */
    public String getReason() {
        return reason;
    }

    /**
     *
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     *
     * @return
     */
    public long getCode() {
        return code;
    }

    /**
     *
     * @param code
     */
    public void setCode(long code) {
        this.code = code;
    }

    /**
     *
     * @return
     */
    public List<DemoAppConfigParameter> getDemoAppConfigParameters() {
        return this.demoAppConfigParameters;
    }

    /**
     *
     * @param demoAppConfigParameters
     */
    public void setDemoAppConfigParameters(List<DemoAppConfigParameter> demoAppConfigParameters) {
        this.demoAppConfigParameters = demoAppConfigParameters;
    }

    /**
     *
     * @param parameter
     */
    public void addDemoAppConfigParameter(DemoAppConfigParameter parameter) {
        this.demoAppConfigParameters.add(parameter);
    }
}
