package com.advantage.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Author Moti Ostrovski on 03/02/2016.
 */
public enum ProductStatus {
    //product block by moderator
    BLOCK("Block"),

    //product out of stock
    OUT_OF_STOCK("OutOfStock"),

    //product avalible with
    ACTIVE("Active");

    private String stringCode;
    ProductStatus(String stringCode) {this.stringCode=stringCode; }

    public String getStringCode() {
        return this.stringCode;
    }
    /**
     *
     * @return list of enum
     */
    public static List<String> getAllNames() {
        List<String> values = new ArrayList<>();

        for (ProductStatus a : ProductStatus.values()) {
            values.add(a.name());
        }
        return values;
    }

    public static boolean contains(String test) {

        for (ProductStatus a : ProductStatus.values()) {
            if (a.getStringCode().equals(test)) {
                return true;
            }
        }

        return false;
    }

}
