package com.advantage.online.store.dto;

import com.advantage.online.store.model.product.ColorAttribute;
import com.advantage.online.store.model.product.ImageAttribute;
import com.advantage.online.store.model.product.Product;
import com.advantage.online.store.model.product.ProductAttributes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ProductDto {
    private Long productId;
    private Long categoryId;
    private String productName;
    private double price;
    private String description;
    private String imageUrl;
    private List<AttributeItem> attributes;
    private List<String> colors;
    private List<String> images;

    public ProductDto() {
    }

    public ProductDto(Product product) {
        this.productId = product.getId();
        this.categoryId = product.getCategory().getCategoryId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.imageUrl = product.getManagedImageId();
        this.attributes = fillAttributes(product);
        this.colors = fillColors(product.getColors());
        this.images = fillImages(product.getImages());
    }

    private List<String> fillColors(Set<ColorAttribute> colorAttributes) {
        List<String> colors = new ArrayList<>(colorAttributes.size());
        for (ColorAttribute color : colorAttributes) {
            colors.add(color.getColor())  ;
        }
        return colors;
    }

    private List<String> fillImages(Set<ImageAttribute> imageAttributes) {
        List<String> images = new ArrayList<>(imageAttributes.size());
        for (ImageAttribute image : imageAttributes) {
            images.add(image.getImageUrl());
        }

        return images;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
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

    /**
     * Build AttributeItem collection from Products attributes
     * @param product - Product object
     * @return
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