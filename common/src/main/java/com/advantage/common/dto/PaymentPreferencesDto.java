package com.advantage.common.dto;

public class PaymentPreferencesDto {
    private final int paymentMethod;
    private final String cvvNumber;
    private final String cardNumber;
    private final String expirationDate;

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public String getCvvNumber() {
        return cvvNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    private final String customerName;

    public PaymentPreferencesDto(Integer paymentMethod, String cardNumber, String expirationDate, String cvvNumber, String customerName) {

        this.paymentMethod = paymentMethod;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvvNumber = cvvNumber;
        this.customerName = customerName;

    }


}
