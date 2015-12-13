package com.advantage.online.store.support.dto;

/**
 * @author Binyamin Regev on 13/12/2015.
 */
public class ContactUsMailDto {
    private String email;
    private Long categoryId;
    private Long productId;
    private String text;

    /**
     * Default constructor
     */
    public ContactUsMailDto() {
    }

    /**
     * Constructor for <b>MANDATORY</b> fields.
     * @param email
     * @param text
     */
    public ContactUsMailDto(String email, String text) {
        this.email = email;
        this.text = text;
    }

    /**
     * Constructor for all fields. {@code categiryId} and/or {@code productId} can be {@code null}.
     * @param email
     * @param categoryId
     * @param productId
     * @param text
     */
    public ContactUsMailDto(String email, Long categoryId, Long productId, String text) {
        this.email = email;
        this.categoryId = categoryId;
        this.productId = productId;
        this.text = text;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
