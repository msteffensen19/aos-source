package com.advantage.order.store.dto;

import com.advantage.common.enums.ColorPalletEnum;
import com.advantage.order.store.model.ShoppingCart;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Single line in order history.
 * @author Binyamin Regev on on 25/07/2016.
 */
public class OrderLineDto {

    @JsonProperty("UserId")
    private long userId;
    @JsonProperty("OrderNumber")
    private long orderNumber;
    @JsonProperty("ProductID")
    private Long productId;             //  From Product table in CATALOG schema
    @JsonProperty("ProductName")
    private String productName;         //  From Product table in CATALOG schema
    @JsonProperty("ProductColorCode")
    private int productColor;           //  RGB decimal value
    @JsonProperty("ProductColorName")
    private String productColorName;    //  Product Color Name
    @JsonProperty("PricePerUnit")
    private double pricePerItem;        //  From Product table in CATALOG schema
    @JsonProperty("Quantity")
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
