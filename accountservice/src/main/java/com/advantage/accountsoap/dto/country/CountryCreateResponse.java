package com.advantage.accountsoap.dto.country;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "response"
        })
@XmlRootElement(name = "CountryStatusResponse", namespace = WebServiceConfig.NAMESPACE_URI)
public class CountryCreateResponse {
    @XmlElement(name = "StatusMessage", namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private CountryStatusResponse response;

    public CountryCreateResponse() {
    }

    public CountryCreateResponse(CountryStatusResponse response) {
        this.response = response;
    }

    public CountryStatusResponse getResponse() {
        return response;
    }

    public void setResponse(CountryStatusResponse response) {
        this.response = response;
    }
}
