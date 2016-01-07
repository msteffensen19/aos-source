package com.advantage.order.store.order.model;

import java.io.Serializable;

/**
 * @author Binyamin Regev on 07/01/2016.
 */
public class OrderHeaderPK implements Serializable {
    private long userId;
    private long orderNumber;

    public OrderHeaderPK() { }

    public OrderHeaderPK(long userId, long orderNumber) {
        this.userId = userId;
        this.orderNumber = orderNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderHeaderPK that = (OrderHeaderPK) o;

        if (userId != that.userId) return false;
        return orderNumber == that.orderNumber;

    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (orderNumber ^ (orderNumber >>> 32));
        return result;
    }
}
