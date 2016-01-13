package com.advantage.accountsoap.dto;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "address"
})
@XmlRootElement(name = "AddressUpdateRequest", namespace = WebServiceConfig.NAMESPACE_URI)
public class AddressUpdateRequest {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private AddressDto address;

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }
}
