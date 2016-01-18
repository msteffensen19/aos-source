package com.advantage.accountsoap.dto.payment;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "id"
})
@XmlRootElement(name = "DeletePaymentPreferenceRequest", namespace = WebServiceConfig.NAMESPACE_URI)
public class DeletePaymentPreferenceRequest {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
