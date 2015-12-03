package com.advantage.online.store.order.model;

import com.advantage.online.store.Constants;

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

    @Column(name = "price")
    private double price;

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
        this.price = price;
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

    public double getPrice() { return this.price; }

    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getProductTotal() { return productTotal; }

    public void setProductTotal(double productTotal) { this.productTotal = productTotal; }

    public boolean isActive() { return active; }

    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                " id=" + id +
                ", loginName='" + this.getLoginName() + '\'' +
                ", productId=" + this.getProductId() +
                ", managedImageId='" + this.getManagedImageId() + '\'' +
                ", colorName='" + this.getColorName() + '\'' +
                ", colorImageUrl='" + this.getColorImageUrl()+ '\'' +
                ", price=" + this.getPrice() +
                ", quantity=" + this.getQuantity() +
                ", productTotal=" + this.getProductTotal() +
                ", active=" + this.isActive() + " }";
    }

    @Override
    public boolean equals(Object obj) {

        ShoppingCart compareTo = (ShoppingCart) obj;

        return ((this.getLoginName().equals(compareTo.getLoginName())) &&
                (this.getProductId() == compareTo.getProductId()) &&
                (this.getManagedImageId().equalsIgnoreCase(compareTo.getManagedImageId())) &&
                (this.getColorName().equalsIgnoreCase(compareTo.getColorName())) &&
                (this.getColorImageUrl().equalsIgnoreCase(compareTo.getColorImageUrl())) &&
                (this.getPrice() == compareTo.getPrice()) &&
                (this.getQuantity() == compareTo.getQuantity()) &&
                (this.getProductTotal() == compareTo.getProductTotal())
                );
    }
}
