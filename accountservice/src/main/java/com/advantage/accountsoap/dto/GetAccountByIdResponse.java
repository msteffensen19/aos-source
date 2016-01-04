package com.advantage.accountsoap.dto;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetAccountByIdResponse",
        namespace = WebServiceConfig.NAMESPACE_URI,
        propOrder = {
                "result"
        })
@XmlRootElement(name = "GetAccountByIdResponse", namespace = WebServiceConfig.NAMESPACE_URI)
public class GetAccountByIdResponse {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private boolean result;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
