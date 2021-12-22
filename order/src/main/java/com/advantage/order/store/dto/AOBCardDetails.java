package com.advantage.order.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * Card
 */
public class AOBCardDetails {
    @JsonProperty("card_id")
    private UUID cardId = null;

    @JsonProperty("image")
    private String image = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("card_type")

    private String cardType = null;

    @JsonProperty("interest_rate")
    private Float interestRate = null;

    @JsonProperty("currency")
    private String currency = null;

    @JsonProperty("user_fullname")
    private String userFullname = null;

    @JsonProperty("card_number")
    private long cardNumber;

    @JsonProperty("last_four_digits")
    private String lastFourDigits = null;

    @JsonProperty("validity_year")
    private BigDecimal validityYear = null;

    @JsonProperty("validity_month")
    private BigDecimal validityMonth = null;

    @JsonProperty("status")
    private String status = null;

    @JsonProperty("bank_account")
    private String bankAccountNumber = null;

    @JsonProperty("credit_limit")
    private BigDecimal creditLimit = null;

    private BigDecimal availableCredit = null;

    @JsonProperty("current_balance")
    private BigDecimal currentBalance = null;

    @JsonProperty("limit_pos")
    private BigDecimal limitPos = null;

    @JsonProperty("limit_atm")
    private BigDecimal limitAtm = null;

    public AOBCardDetails() {
    }

    public AOBCardDetails(UUID cardId, String image, String description, String cardType, Float interestRate, String currency, String userFullname, BigDecimal validityYear, BigDecimal validityMonth, String status, String bankAccountNumber, BigDecimal creditLimit, BigDecimal availableCredit, BigDecimal currentBalance, BigDecimal limitPos, BigDecimal limitAtm) {
        this.cardId = cardId;
        this.image = image;
        this.description = description;
        this.cardType = cardType;
        this.interestRate = interestRate;
        this.currency = currency;
        this.userFullname = userFullname;
        this.validityYear = validityYear;
        this.validityMonth = validityMonth;
        this.status = status;
        this.bankAccountNumber = bankAccountNumber;
        this.creditLimit = creditLimit;
        this.availableCredit = availableCredit;
        this.currentBalance = currentBalance;
        this.limitPos = limitPos;
        this.limitAtm = limitAtm;
        setLastFourDigits();
    }

    public UUID getCardId() {
        return cardId;
    }

    public void setCardId(UUID cardId) {
        this.cardId = cardId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Float interestRate) {
        this.interestRate = interestRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUserFullname() {
        return userFullname;
    }

    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }

    public String getLastFourDigits() {
        return lastFourDigits;
    }

    public void setLastFourDigits() {
        String cardNumberString = Long.toString(cardNumber);
        this.lastFourDigits = cardNumberString.substring(cardNumberString.length() - 4);
    }

    public BigDecimal getValidityYear() {
        return validityYear;
    }

    public void setValidityYear(BigDecimal validityYear) {
        this.validityYear = validityYear;
    }

    public BigDecimal getValidityMonth() {
        return validityMonth;
    }

    public void setValidityMonth(BigDecimal validityMonth) {
        this.validityMonth = validityMonth;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getAvailableCredit() {
        return availableCredit;
    }

    public void setAvailableCredit(BigDecimal availableCredit) {
        this.availableCredit = availableCredit;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public BigDecimal getLimitPos() {
        return limitPos;
    }

    public void setLimitPos(BigDecimal limitPos) {
        this.limitPos = limitPos;
    }

    public BigDecimal getLimitAtm() {
        return limitAtm;
    }

    public void setLimitAtm(BigDecimal limitAtm) {
        this.limitAtm = limitAtm;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
        setLastFourDigits();
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardId=" + cardId +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", cardType='" + cardType + '\'' +
                ", interestRate=" + interestRate +
                ", currency='" + currency + '\'' +
                ", userFullname='" + userFullname + '\'' +
                ", lastFourDigits='" + lastFourDigits + '\'' +
                ", validityYear=" + validityYear +
                ", validityMonth=" + validityMonth +
                ", status='" + status + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", creditLimit=" + creditLimit +
                ", availableCredit=" + availableCredit +
                ", currentBalance=" + currentBalance +
                ", limitPos=" + limitPos +
                ", limitAtm=" + limitAtm +
                '}';
    }
}
