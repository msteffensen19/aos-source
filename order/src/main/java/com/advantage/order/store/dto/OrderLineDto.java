package com.advantage.order.store.dto;

import com.advantage.common.enums.ColorPalletEnum;
import com.advantage.order.store.model.ShoppingCart;

/**
 * @author Binyamin Regev on on 25/07/2016.
 */
public class OrderLineDto {
    private long userId;

    private long orderNumber;

    private Long productId;             //  From Product table in CATALOG schema
    private String productName;         //  From Product table in CATALOG schema
    private int productColor;           //  RGB decimal value
    private String productColorName;    //  Product Color Name
    private double pricePerItem;        //  From Product table in CATALOG schema
    private int quantity;               //  From ShoppingCart table in ORDER schema

    public OrderLineDto() {
    }

    public OrderLineDto(long userId, long orderNumber, Long productId, String productName, int productColor, double pricePerItem, int quantity) {
        this.userId = userId;
        this.orderNumber = orderNumber;
        this.productId = productId;
        this.productName = productName;
        this.productColor = productColor;
        this.productColorName = ColorPalletEnum.getColorByCode(ShoppingCart.convertIntColorToHex(productColor)).getColorName();
        this.pricePerItem = pricePerItem;
        this.quantity = quantity;
    }

    public OrderLineDto(long userId, long orderNumber, Long productId, String productName, int productColor, String productColorName, double pricePerItem, int quantity) {
        this.userId = userId;
        this.orderNumber = orderNumber;
        this.productId = productId;
        this.productName = productName;
        this.productColor = productColor;
        this.productColorName = productColorName;
        this.pricePerItem = pricePerItem;
        this.quantity = quantity;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductColor() {
        return productColor;
    }

    public void setProductColor(int productColor) {
        this.productColor = productColor;
    }

    public String getProductColorName() {
        return productColorName;
    }

    public void setProductColorName(String productColorName) {
        this.productColorName = productColorName;
    }

    public double getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(double pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
