package com.advantage.order.store.order.dto;

/**
 * @author Binyamin Regev on 06/01/2016.
 */
public class OrderPurchaseResponse {
    private boolean success;
    private String reason;
    private int code;

    public OrderPurchaseResponse() {  }

    public OrderPurchaseResponse(boolean success) {
        this.success = success;
        this.reason = "";
        this.code = -1;
    }

    public OrderPurchaseResponse(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
        this.code = -1;
    }

    public OrderPurchaseResponse(boolean success, String reason, int code) {
        this.success = success;
        this.reason = reason;
        this.code = code;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
