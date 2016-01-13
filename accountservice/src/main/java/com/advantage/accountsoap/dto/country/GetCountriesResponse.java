package com.advantage.accountsoap.dto.country;

import com.advantage.accountsoap.config.WebServiceConfig;
import com.advantage.accountsoap.model.Country;

import javax.xml.bind.annotation.*;
import java.util.List;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetCountriesResponse",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "country"
        })
@XmlRootElement(name = "GetCountriesResponse", namespace = WebServiceConfig.NAMESPACE_URI)
public class GetCountriesResponse {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    public List<CountryDto> country;

    public List<CountryDto> getCountry() {
        return country;
    }

    public void setCountry(List<CountryDto> country) {
        this.country = country;
    }
}
