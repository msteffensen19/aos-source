package com.advantage.online.store.order.dto;

import com.advantage.online.store.order.model.ShoppingCart;

/**
 * Used to send data for REQUEST to BACK END of <b>only one</b> {@link ShoppingCart} product,
 * identified by unique combination of {@code userId}, {@code productId} and {@code color}.
 * @author Binyamin Regev on 13/12/2015.
 */
public class ShoppingCartProductDto extends ShoppingCartDto{

    private long userId;

    /**
     * Default constructor
     */
    public ShoppingCartProductDto() {
    }

    /**
     * Constructor for <b>required</b> unique primary key fields.
     * @param userId
     * @param productId
     * @param hexColor
     */
    public ShoppingCartProductDto(long userId, Long productId, String hexColor) {
        this.userId = userId;
        this.setProductId(productId);
        this.setHexColor(hexColor);
    }

    public ShoppingCartProductDto(long userId, Long productId, String hexColor, int quantity) {
        this.userId = userId;
        this.setProductId(productId);
        this.setHexColor(hexColor);
        this.setQuantity(quantity);
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}
