package com.advantage.online.store.order.model;

import javax.persistence.*;

/**
 * @author Binyamin Regev on 03/12/2015.
 */
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {

    public static final String FIELD_ID = "cart_item_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = FIELD_ID)
    private Long id;

    @Column(name = "login_name")
    private String loginName;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "managed_image_id")
    private String managedImageId;

    @Column(name = "color_name")
    private String colorName;

    @Column(name = "color_image_url")
    private  String colorImageUrl;

    @Column(name = "original_price")
    private double originalPrice;

    @Column(name = "discount_percent")
    private double discountPercent;

    @Column(name = "price_after_discount")
    private double priceAfterDiscount;

    @Column(name = "quantity")
    private int quantity;

    @Column(name="product_total")
    private double productTotal;

    @Column(name="active")
    private boolean active;

    public ShoppingCart() {
    }

    public ShoppingCart(String loginName, Long productId, String managedImageId, String colorName, String colorImageUrl, double originalPrice, double discountPercent, double priceAfterDiscount, int quantity, double productTotal, boolean active) {
        this.loginName = loginName;
        this.productId = productId;
        this.managedImageId = managedImageId;
        this.colorName = colorName;
        this.colorImageUrl = colorImageUrl;
        this.originalPrice = originalPrice;
        this.discountPercent = discountPercent;
        this.priceAfterDiscount = priceAfterDiscount;
        this.quantity = quantity;
        this.productTotal = productTotal;
        this.active = active;
    }

    public Long getId() { return id; }

    public String getLoginName() { return this.loginName; }

    public void setLoginName(String loginName) { this.loginName = loginName; }

    public Long getProductId() { return productId; }

    public void setProductId(Long productId) { this.productId = productId; }

    public String getManagedImageId() { return managedImageId; }

    public void setManagedImageId(String managedImageId) { this.managedImageId = managedImageId; }

    public String getColorName() { return colorName; }

    public void setColorName(String colorName) { this.colorName = colorName; }

    public String getColorImageUrl() { return colorImageUrl; }

    public void setColorImageUrl(String colorImageUrl) { this.colorImageUrl = colorImageUrl; }

    public double getOriginalPrice() { return originalPrice; }

    public void setOriginalPrice(double originalPrice) { this.originalPrice = originalPrice; }

    public double getDiscountPercent() { return discountPercent; }

    public void setDiscountPercent(double discountPercent) { this.discountPercent = discountPercent; }

    public double getPriceAfterDiscount() { return priceAfterDiscount; }

    public void setPriceAfterDiscount(double priceAfterDiscount) { this.priceAfterDiscount = priceAfterDiscount; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getProductTotal() { return productTotal; }

    public void setProductTotal(double productTotal) { this.productTotal = productTotal; }

    public boolean isActive() { return active; }

    public void setActive(boolean active) { this.active = active; }


}
