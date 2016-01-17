package com.advantage.accountsoap.dto.country;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "country"
        })
@XmlRootElement(name = "CountrySearchResponse", namespace = WebServiceConfig.NAMESPACE_URI)
public class CountrySearchResponse {
    @XmlElement(name = "Country", namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private List<CountryDto> country;

    public CountrySearchResponse() {
    }

    public CountrySearchResponse(List<CountryDto> country) {
        this.country = country;
    }

    public List<CountryDto> getCountry() {
        return country;
    }

    public void setCountry(List<CountryDto> country) {
        this.country = country;
    }
}
