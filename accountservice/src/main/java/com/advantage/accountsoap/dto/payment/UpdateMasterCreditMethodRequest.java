package com.advantage.accountsoap.dto.payment;

import com.advantage.accountsoap.config.WebServiceConfig;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "userId",
        "cardNumber",
        "expirationDate",
        "cvvNumber",
        "customerName",
        "referenceId"
})
@XmlRootElement(name = "UpdateMasterCreditMethodRequest", namespace = WebServiceConfig.NAMESPACE_URI)
public class UpdateMasterCreditMethodRequest {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private long userId;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String cardNumber;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String expirationDate;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String cvvNumber;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String customerName;
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private long referenceId;

    public UpdateMasterCreditMethodRequest() {
    }

    public UpdateMasterCreditMethodRequest(String cardNumber, String expirationDate, String cvvNumber, String customerName, long referenceId) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvvNumber = cvvNumber;
        this.customerName = customerName;
        this.referenceId = referenceId;
    }

    public UpdateMasterCreditMethodRequest(long userId, String cardNumber, String expirationDate, String cvvNumber, String customerName, long referenceId) {
        this.userId = userId;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvvNumber = cvvNumber;
        this.customerName = customerName;
        this.referenceId = referenceId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvvNumber() {
        return cvvNumber;
    }

    public void setCvvNumber(String cvvNumber) {
        this.cvvNumber = cvvNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(long referenceId) {
        this.referenceId = referenceId;
    }
}
