package com.advantage.accountsoap.model;

import com.advantage.accountsoap.dto.payment.PaymentMethodEnum;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(
                name = PaymentPreferences.QUERY_GET_ALL,
                query = "SELECT p FROM PaymentPreferences p"
        )
})
public class PaymentPreferences {
    public static final String QUERY_GET_ALL = "query.getAll";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int paymentMethod;
    private String cardNumber;
    private String expirationDate;
    private String cvvNumber;
    private String customerName;
    private String safePayUsername;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = Account.FIELD_ID)
    private Account account;

    public PaymentPreferences(String cardNumber, String expirationDate, String cvvNumber, String customerName) {
        this.paymentMethod = PaymentMethodEnum.MasterCredit.getPaymentTypeCode();
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvvNumber = cvvNumber;
        this.customerName = customerName;
    }

    public PaymentPreferences(String safePayUsername) {
        this.safePayUsername = safePayUsername;
        this.paymentMethod = PaymentMethodEnum.SafePay.getPaymentTypeCode();
    }

    public long getId() {
        return id;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
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

    public String getSafePayUsername() {
        return safePayUsername;
    }

    public void setSafePayUsername(String safePayUsername) {
        this.safePayUsername = safePayUsername;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
