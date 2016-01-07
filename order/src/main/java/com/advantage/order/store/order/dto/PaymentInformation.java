package com.advantage.order.store.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Binyamin Regev on 07/01/2016.
 *
 *  TransactionType, 							# Both (Payment / Refund)
 *  customerPhone,								# Both
 *  TransactionDate, 							# Both
 *  ReceivingCard.AccountNumber, 				# Both
 *  ReceivingAmount.Value,						# Both
 *  ReceivingAmount.Currency					# Both
 *
 *  ------------------------------------------
 *  MasterCreditInformation:
 *  ------------------------------------------
 *  cardNumber, 								# MasterCredit ONLY
 *  expirationDate, 							# MasterCredit ONLY
 *  customerName, 							# MasterCredit ONLY
 *  cvvNumber, 								# MasterCredit ONLY
 *
 *  ------------------------------------------
 *  SafePayPaymentInformation:
 *  ------------------------------------------
 *  SafePay.username, 							# SafePay ONLY
 *  SafePay.password, 							# SafePay ONLY
 */
public class PaymentInformation {
    @JsonProperty("Transaction.Type")
    private String transactionType;     //  by TransactioTypeEnum

    @JsonProperty("Transaction.CustomerPhone")
    private String customerPhone;

    @JsonProperty("Transaction.TransactionDate")
    private String transactionDate;

    @JsonProperty("Transaction.AccountNumber")
    private String accountNumber;

    @JsonProperty("Transaction.Amount")
    private double amount;

    @JsonProperty("Transaction.Currency")
    private String currency;

    @JsonProperty("Transaction.MasterCredit.CardNumber")
    private String cardNumber;

    @JsonProperty("Transaction.MasterCredit.ExpirationDate")
    private String expirationDate;

    @JsonProperty("Transaction.MasterCredit.CustomerName")
    private String customerName;

    @JsonProperty("Transaction.MasterCredit.CVVNumber")
    private String cvvNumber;

    @JsonProperty("Transaction.SafePay.UserName")
    private String username;

    @JsonProperty("Transaction.SafePay.Password")
    private String password;

    public PaymentInformation() { }

    /**
     * Constructor for <b><i>SafePay</i></b> Payment or Refund
     * @param transactionType
     * @param customerPhone
     * @param transactionDate
     * @param accountNumber
     * @param amount
     * @param currency
     * @param cardNumber
     * @param expirationDate
     * @param customerName
     * @param cvvNumber
     */
    public PaymentInformation(String transactionType, String customerPhone, String transactionDate, String accountNumber, double amount, String currency, String cardNumber, String expirationDate, String customerName, String cvvNumber) {
        this.transactionType = transactionType;
        this.customerPhone = customerPhone;
        this.transactionDate = transactionDate;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.currency = currency;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.customerName = customerName;
        this.cvvNumber = cvvNumber;

        // SafePay information
        this.username = null;
        this.password = null;
    }

    /**
     * Constructor for <b><i>SafePay</i></b> Payment or Refund
     * @param transactionType
     * @param customerPhone
     * @param transactionDate
     * @param accountNumber
     * @param amount
     * @param currency
     * @param username
     * @param password
     */
    public PaymentInformation(String transactionType, String customerPhone, String transactionDate, String accountNumber, double amount, String currency, String username, String password) {
        this.transactionType = transactionType;
        this.customerPhone = customerPhone;
        this.transactionDate = transactionDate;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.currency = currency;
        this.username = username;
        this.password = password;

        // MasterCredit information
        this.cardNumber = null;
        this.expirationDate = null;
        this.customerName = null;
        this.cvvNumber = null;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCvvNumber() {
        return cvvNumber;
    }

    public void setCvvNumber(String cvvNumber) {
        this.cvvNumber = cvvNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentInformation that = (PaymentInformation) o;

        if (Double.compare(that.getAmount(), getAmount()) != 0) return false;
        if (!getTransactionType().equals(that.getTransactionType())) return false;
        if (!getCustomerPhone().equals(that.getCustomerPhone())) return false;
        if (!getTransactionDate().equals(that.getTransactionDate())) return false;
        if (!getAccountNumber().equals(that.getAccountNumber())) return false;
        if (!getCurrency().equals(that.getCurrency())) return false;
        if (getCardNumber() != null ? !getCardNumber().equals(that.getCardNumber()) : that.getCardNumber() != null)
            return false;
        if (getExpirationDate() != null ? !getExpirationDate().equals(that.getExpirationDate()) : that.getExpirationDate() != null)
            return false;
        if (getCustomerName() != null ? !getCustomerName().equals(that.getCustomerName()) : that.getCustomerName() != null)
            return false;
        if (getCvvNumber() != null ? !getCvvNumber().equals(that.getCvvNumber()) : that.getCvvNumber() != null)
            return false;
        if (getUsername() != null ? !getUsername().equals(that.getUsername()) : that.getUsername() != null)
            return false;
        return !(getPassword() != null ? !getPassword().equals(that.getPassword()) : that.getPassword() != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getTransactionType().hashCode();
        result = 31 * result + getCustomerPhone().hashCode();
        result = 31 * result + getTransactionDate().hashCode();
        result = 31 * result + getAccountNumber().hashCode();
        temp = Double.doubleToLongBits(getAmount());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getCurrency().hashCode();
        result = 31 * result + (getCardNumber() != null ? getCardNumber().hashCode() : 0);
        result = 31 * result + (getExpirationDate() != null ? getExpirationDate().hashCode() : 0);
        result = 31 * result + (getCustomerName() != null ? getCustomerName().hashCode() : 0);
        result = 31 * result + (getCvvNumber() != null ? getCvvNumber().hashCode() : 0);
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        return result;
    }
}
