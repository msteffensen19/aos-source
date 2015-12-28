package com.advantage.order.store.order.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Unified Payment Method names "MasterCredit" and "SafePay"
 * @author Binyamin Regev on 28/12/2015.
 */
public enum PaymentMethodEnum {
    MASTER_CREDIT("MasterCredit"),
    SHIP_EX("ShipEx");

    private String stringCode;

    PaymentMethodEnum(String stringCode) {
        this.stringCode = stringCode;
    }

    public String getStringCode() {
        return this.stringCode;
    }

    /**
     * Return {@link List} of {@link String} with all {@code enum} values.
     *
     * @return {@link List} of {@link String} with all {@code enum} values.
     */
    public static List<String> getAllNames() {
        List<String> values = new ArrayList<>();

        for (PaymentMethodEnum a : PaymentMethodEnum.values()) {
            values.add(a.name());
        }
        return values;
    }

    /**
     * Check if {@code Enum} contains a specific value.
     *
     * @param test {@code Enum} value to check.
     * @return {@code boolean} <b>true</b> when {@code Enum} contains the value,
     * <b>false</b> otherwise.
     */
    public static boolean contains(String test) {

        for (PaymentMethodEnum a : PaymentMethodEnum.values()) {
            if (a.name().equals(test)) {
                return true;
            }
        }

        return false;
    }

}
