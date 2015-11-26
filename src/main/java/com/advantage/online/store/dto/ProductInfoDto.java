package com.advantage.online.store.dto;

import com.advantage.online.store.model.product.ColorAttribute;
import com.advantage.online.store.model.product.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * ProductInfo data transfer object is using to represent the product in the Product API
 */
public class ProductInfoDto {
    private Long id;
    private String productName;
    private String productAlias;
    private int price;
    private String description;
    private String imageUrl;
    private List<ColorAttribute> colors;
    private List<AttributeItem> attributes;

    public ProductInfoDto(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.productAlias = product.getCategory().getCategoryName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.imageUrl = product.getManagedImageId();
        this.colors = new ArrayList<>(product.getColors());
        this.attributes = AttributeItem.productAttributesToAttributeValues(product.getProductAttributes());
    }

    public ProductInfoDto(Long id, String productName, String productAlias, int price, String description,
                          String imageUrl, List<ColorAttribute> colors, List<AttributeItem> attributes) {
        this.id = id;
        this.productName = productName;
        this.productAlias = productAlias;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.colors = colors;
        this.attributes = attributes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductAlias() {
        return productAlias;
    }

    public void setProductAlias(String productAlias) {
        this.productAlias = productAlias;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<ColorAttribute> getColors() {
        return colors;
    }

    public void setColors(List<ColorAttribute> colors) {
        this.colors = colors;
    }

    public List<AttributeItem> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeItem> attributes) {
        this.attributes = attributes;
    }


}
