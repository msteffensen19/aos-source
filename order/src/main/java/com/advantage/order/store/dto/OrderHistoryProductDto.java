package com.advantage.order.store.dto;

/**
 * Author Moti Ostrovski on 24/05/2016.
 */
public class OrderHistoryProductDto {

    private long productId;
    private String productName;
    private int productColor;
    private int productColorName;
    private double pricePerItem;
    private int productQuantity;
    private long orderNumber;

    public OrderHistoryProductDto(){
        initFields();
    }

    public OrderHistoryProductDto(long productId, String productName, int productColor,
                                  int productColorName, double pricePerItem, int productQuantity,
                                  long orderNumber) {
        initFields();
        this.productId = productId;
        this.productName = productName;
        this.productColor = productColor;
        this.productColorName = productColorName;
        this.pricePerItem = pricePerItem;
        this.productQuantity = productQuantity;
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

    public int getProductColorName() {
        return productColorName;
    }

    public void setProductColorName(int productColorName) {
        this.productColorName = productColorName;
    }

    public double getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(double pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    private void initFields()
    {
        productId = 0;
        productName="";
        productColor=0;
        productColorName=0;
        pricePerItem=0.0;
        productQuantity=0;
        orderNumber=0;
    }
}
