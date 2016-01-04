package com.advantage.accountsoap.dto;

import com.advantage.accountsoap.config.WebServiceConfig;
import com.advantage.accountsoap.model.Country;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetCountriesResponse",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "country"
        })
@XmlRootElement(name = "GetCountriesResponse", namespace = WebServiceConfig.NAMESPACE_URI)
public class GetCountriesResponse {
    private List<Country> country;

    public List<Country> getCountry() {
        return country;
    }

    public void setCountry(List<Country> country) {
        this.country = country;
    }
}
