package com.advantage.online.store.support.dto;

/**
 * @author Binyamin Regev on 13/12/2015.
 */
public class ContactUsResponseStatus {
    private boolean success;
    private String reason;
    private long returnCode;

    public ContactUsResponseStatus() {
    }

    public ContactUsResponseStatus(boolean success, String reason, long returnCode) {
        this.success = success;
        this.reason = reason;
        this.returnCode = returnCode;
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

    public long getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(long returnCode) {
        this.returnCode = returnCode;
    }
}
