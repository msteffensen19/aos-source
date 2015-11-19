package com.advantage.online.store.dto;

import com.advantage.online.store.model.product.Product;
import com.advantage.online.store.model.product.ProductAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductDto {
    private Long id;
    private String productName;
    private int price;
    private String description;
    private String imageUrl;
    private List<AttributeItem> attributes;

    public ProductDto() {
    }

    public ProductDto(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.imageUrl = product.getManagedImageId();
        this.attributes = fillAttributes(product);
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



    public List<AttributeItem> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeItem> attributes) {
        this.attributes = attributes;
    }

   /* public void setAttributes(Product product) {
        this.attributes = fillAttributes(product);
    }
*/
    private List<AttributeItem> fillAttributes(Product product) {
        Set<ProductAttributes> productAttributes = product.getProductAttributes();
        List<AttributeItem> items = new ArrayList<>();
        for (ProductAttributes attribute : productAttributes) {
            items.add(new AttributeItem(attribute.getAttribute().getName(), attribute.getAttributeValue()));
        }

        return items;
    }

}