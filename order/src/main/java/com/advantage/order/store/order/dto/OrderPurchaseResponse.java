package com.advantage.order.store.order.dto;

/**
 * @author Binyamin Regev on 06/01/2016.
 */
public class OrderPurchaseResponse {
    private boolean success;
    private String reason;
    private long userId;
    private long orderNumber;

    public OrderPurchaseResponse() {  }

    public OrderPurchaseResponse(boolean success) {
        this.success = success;
        this.reason = "";
        this.userId = -1;
        this.orderNumber = 0;
    }

    public OrderPurchaseResponse(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
        this.userId = -1;
        this.orderNumber = 0;
    }

    public OrderPurchaseResponse(boolean success, String reason, long userId) {
        this.success = success;
        this.reason = reason;
        this.userId = userId;
        this.orderNumber = 0;
    }

    public OrderPurchaseResponse(boolean success, String reason, long userId, long orderNumber) {
        this.success = success;
        this.reason = reason;
        this.userId = userId;
        this.orderNumber = orderNumber;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }
}
