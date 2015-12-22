package com.advantage.account.store.dto;

/**
 * Created by dalie on 11/23/2015.
 */
public class ProductResponseStatus {
    boolean success;
    long id;        //  -1 = Invalid
    String reason;
    String imageId;

    public ProductResponseStatus(boolean success, long id, String reason) {
        this.success = success;
        this.id = id;
        this.reason = reason;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
