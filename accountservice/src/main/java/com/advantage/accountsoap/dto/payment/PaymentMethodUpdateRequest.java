package com.advantage.accountsoap.dto.payment;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "accountId",
        "paymentMethod"
})
@XmlRootElement(name = "PaymentMethodUpdateRequest", namespace = WebServiceConfig.NAMESPACE_URI)
public class PaymentMethodUpdateRequest {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    protected long accountId;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    protected Integer paymentMethod;

    public PaymentMethodUpdateRequest() {
    }

    public PaymentMethodUpdateRequest(long accountId, Integer paymentMethod) {
        this.setAccountId(accountId);
        this.setPaymentMethod(paymentMethod);
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
