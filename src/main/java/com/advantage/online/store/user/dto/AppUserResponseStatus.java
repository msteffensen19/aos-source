package com.advantage.online.store.user.dto;

/**
 * @author Binyamin Regev on 19/11/2015.
 */
public class AppUserResponseStatus {
    boolean success;
    long userId;        //  -1 = Invalid user login name
    String reason;

    public AppUserResponseStatus(boolean success, String reason, long userId) {
        this.setUserId(userId);
        this.setReason(reason);
        this.setSuccess(success);
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
