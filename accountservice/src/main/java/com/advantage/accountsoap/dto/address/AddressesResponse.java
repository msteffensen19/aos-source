package com.advantage.accountsoap.dto.address;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "shippingAddress"
        })
@XmlRootElement(name = "ShippingAddresses", namespace = WebServiceConfig.NAMESPACE_URI)
public class AddressesResponse {
    @XmlElement(name = "ShippingAddress",namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private List<AddressDto> shippingAddress;

    public AddressesResponse() {
    }

    public AddressesResponse(List<AddressDto> shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<AddressDto> getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(List<AddressDto> shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
