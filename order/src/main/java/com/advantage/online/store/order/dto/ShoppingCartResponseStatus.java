package com.advantage.online.store.order.dto;

/**
 * @author Binyamin Regev on 03/12/2015.
 */
public class ShoppingCartResponseStatus {

    private boolean success;
    private String reason;
    private long id;

    /**
     *
     * @param success
     * @param reason
     * @param id
     */
    public ShoppingCartResponseStatus(boolean success, String reason, long id) {
        this.setSuccess(success);
        this.setReason(reason);
        this.setId(id);
    }

    /**
     *
     * @return
     */
    public boolean isSuccess() { return success; }

    /**
     *
     * @param success
     */
    public void setSuccess(boolean success) { this.success = success; }

    /**
     *
     * @return
     */
    public String getReason() { return reason; }

    /**
     *
     * @param reason
     */
    public void setReason(String reason) { this.reason = reason; }

    /**
     *
     * @return
     */
    public long getId() { return id; }

    /**
     *
     * @param id
     */
    public void setId(long id) { this.id = id; }
}
