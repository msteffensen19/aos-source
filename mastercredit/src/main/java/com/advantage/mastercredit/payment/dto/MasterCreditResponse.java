package com.advantage.mastercredit.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Binyamin Regev on 20/12/2015.
 */
public class MasterCreditResponse {

    @JsonProperty("MCTransactionType")
    private String transactionType;

    @JsonProperty("MCResponse")
    private String response;

    @JsonProperty("MCRefNumber")
    private long referenceNumber;       //  10 digits

    @JsonProperty("TransactionDate")
    private String transactionDate; //  DDMMYYYY

    public MasterCreditResponse() {
    }

    public MasterCreditResponse(String transactionType, String response, long referenceNumber, String transactionDate) {
        this.transactionType = transactionType;
        this.response = response;
        this.referenceNumber = referenceNumber;
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public long getReferenceNumber() {
        return this.referenceNumber;
    }

    public void setReferenceNumber(long referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getTransactionDate() {
        return this.transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

}
