package com.advantage.accountsoap.dto;

import com.advantage.accountsoap.config.WebServiceConfig;
import com.advantage.accountsoap.model.Account;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "id",
                "addressLine1",
                "addressLine2"
        })
@XmlRootElement(name = "Address", namespace = WebServiceConfig.NAMESPACE_URI)
public class AddressDto {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private long id;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String addressLine1;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String addressLine2;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }
}
