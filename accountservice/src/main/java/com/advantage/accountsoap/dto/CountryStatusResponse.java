package com.advantage.accountsoap.dto;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CountryStatusResponse",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "success",
                "countryId",
                "reason"
        })
@XmlRootElement(name = "CountryStatusResponse", namespace = WebServiceConfig.NAMESPACE_URI)
public class CountryStatusResponse {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    boolean success;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    Integer countryId;        //  -1 = Invalid country name
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    String reason;

    public CountryStatusResponse() {
    }

    /**
     * Create Country Response Entity with given values.
     *
     * @param success
     * @param reason
     * @param countryId
     */
    public CountryStatusResponse(boolean success, String reason, Integer countryId) {
        this.setSuccess(success);
        this.setCountryId(countryId);
        this.setReason(reason);
    }

    /**
     * Get {@code success} value
     *
     * @return {@code boolean} <i>true</i> or <i>false</i>
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Set {@code success} value
     *
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Get {@link Integer} {@code countryId} value
     *
     * @return
     */
    public Integer getCountryId() {
        return countryId;
    }

    /**
     * Set {@code countryId} {@link Integer} value
     *
     * @param countryId
     */
    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    /**
     * Get {@link String} {@code reason} value
     *
     * @return
     */
    public String getReason() {
        return reason;
    }

    /**
     * Get {@code reason} {@link String} value
     *
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }
}
