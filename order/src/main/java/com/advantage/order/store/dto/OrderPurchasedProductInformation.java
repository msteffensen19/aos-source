package com.advantage.order.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Binyamin Regev on 07/01/2016.
 */
public class OrderPurchasedProductInformation {

    @JsonProperty("PurchasedProduct.Id")
    private Long productId;

    @JsonProperty("PurchasedProduct.Name")
    private String productName;

    @JsonProperty("PurchasedProduct.Color.Code")
    private String hexColor;

    @JsonProperty("PurchasedProduct.Color.Name")
    private String colorName;

    @JsonProperty("PurchasedProduct.PricePerItem")
    private double pricePerItem;

    @JsonProperty("PurchasedProduct.Quantity")
    private int quantity;

    @JsonProperty("PurchasedProduct.ProductImageUrl")
    private String productImageUrl;

    public String getWarrantyNumber() {
        return warrantyNumber;
    }

    public void setWarrantyNumber(String warrantyNumber) {
        this.warrantyNumber = warrantyNumber;
    }

    public boolean isHasWarranty() {
        return hasWarranty;
    }

    public void setHasWarranty(boolean hasWarranty) {
        this.hasWarranty = hasWarranty;
    }

    @JsonProperty("PurchasedProduct.HasWarranty")
    private boolean hasWarranty;

    @JsonProperty("PurchasedProduct.WarrantyNumber")
    private String warrantyNumber;

    public OrderPurchasedProductInformation() { }

    public OrderPurchasedProductInformation(Long productId,
                                            String productName,
                                            String hexColor, String colorName,
                                            double pricePerItem,
                                            int quantity,
                                            String productImageUrl, boolean hasWarranty, String warrantyNumber) {
        this.productId = productId;
        this.productName = productName;
        this.hexColor = hexColor;
        this.colorName = colorName;
        this.pricePerItem = pricePerItem;
        this.quantity = quantity;
        this.productImageUrl = productImageUrl;
        this.hasWarranty = hasWarranty;
        this.warrantyNumber = warrantyNumber;

    }

    public Long getProductId() { return productId; }

    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }

    public void setProductName(String productName) { this.productName = productName; }

    public String getHexColor() { return hexColor; }

    public void setHexColor(String hexColor) { this.hexColor = hexColor; }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public double getPricePerItem() { return pricePerItem; }

    public void setPricePerItem(double pricePerItem) { this.pricePerItem = pricePerItem; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getProductImageUrl() {return productImageUrl;}

    public void setProductImageUrl(String productImageUrl) {this.productImageUrl = productImageUrl;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderPurchasedProductInformation that = (OrderPurchasedProductInformation) o;

        if (Double.compare(that.getPricePerItem(), getPricePerItem()) != 0) return false;
        if (getQuantity() != that.getQuantity()) return false;
        if (getProductImageUrl() != that.getProductImageUrl()) return false;
        if (!getProductId().equals(that.getProductId())) return false;
        return getProductName().equals(that.getProductName());

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getProductId().hashCode();
        result = 31 * result + getProductName().hashCode();
        temp = Double.doubleToLongBits(getPricePerItem());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getQuantity();
        return result;
    }
}
