package com.advantage.accountsoap.dto.address;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "addresses",
        "accountId"
})
@XmlRootElement(name = "AddAddressesRequest", namespace = WebServiceConfig.NAMESPACE_URI)
public class AddAddressesRequest {
    @XmlElement(name = "address", namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private List<AddAddressDto> addresses;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private long accountId;

    public List<AddAddressDto> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddAddressDto> addresses) {
        this.addresses = addresses;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
