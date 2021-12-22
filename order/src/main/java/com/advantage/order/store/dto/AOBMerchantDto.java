package com.advantage.order.store.dto;

public class AOBMerchantDto {
    public String id;
    public String name;
    public String type;
    public String subtype;
    public String iban;
    public String bankBic;
    public String bankName;
    public String description;
    public String from;
    public String to;
    public Boolean isSameAmountOnEachTransaction;

    public AOBMerchantDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Boolean getSameAmountOnEachTransaction() {
        return isSameAmountOnEachTransaction;
    }

    public void setSameAmountOnEachTransaction(Boolean sameAmountOnEachTransaction) {
        isSameAmountOnEachTransaction = sameAmountOnEachTransaction;
    }

    public String getBankBic() {
        return bankBic;
    }

    public void setBankBic(String bankBic) {
        this.bankBic = bankBic;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public String toString() {
        return "MerchantDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", subtype='" + subtype + '\'' +
                ", iban='" + iban + '\'' +
                ", bankBic='" + bankBic + '\'' +
                ", bankName='" + bankName + '\'' +
                ", description='" + description + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", isSameAmountOnEachTransaction=" + isSameAmountOnEachTransaction +
                '}';
    }
}

