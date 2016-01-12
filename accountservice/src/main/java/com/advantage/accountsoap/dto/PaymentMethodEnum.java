package com.advantage.accountsoap.dto;

import java.util.ArrayList;
import java.util.List;

public enum PaymentMethodEnum {
    SafePay(10),
    MasterCredit(20);

    private int paymentTypeCode;

    PaymentMethodEnum(int paymentTypeCode) {
        this.paymentTypeCode = paymentTypeCode;
    }

    public int getPaymentTypeCode() {
        return paymentTypeCode;
    }

    public static List<String> getAllNames() {
        List<String> values = new ArrayList<>();

        for (PaymentMethodEnum a : PaymentMethodEnum.values()) {
            values.add(a.name());
        }
        return values;
    }

    public static boolean contains(String test) {
        for (PaymentMethodEnum a : PaymentMethodEnum.values()) {
            if (a.name().equals(test)) {
                return true;
            }
        }

        return false;
    }
}
