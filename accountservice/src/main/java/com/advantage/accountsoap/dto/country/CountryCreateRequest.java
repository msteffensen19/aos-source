package com.advantage.accountsoap.dto.country;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "name",
        "isoName",
        "phonePrefix"
})
@XmlRootElement(name = "CountryCreateRequest", namespace = WebServiceConfig.NAMESPACE_URI)
public class CountryCreateRequest {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String name;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String isoName;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private int phonePrefix;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsoName() {
        return isoName;
    }

    public void setIsoName(String isoName) {
        this.isoName = isoName;
    }

    public int getPhonePrefix() {
        return phonePrefix;
    }

    public void setPhonePrefix(int phonePrefix) {
        this.phonePrefix = phonePrefix;
    }
}
