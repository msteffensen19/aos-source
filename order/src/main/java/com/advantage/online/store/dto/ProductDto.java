package com.advantage.online.store.dto;

import com.advantage.online.store.model.product.ColorAttribute;
import com.advantage.online.store.model.product.ImageAttribute;
import com.advantage.online.store.model.product.Product;
import com.advantage.online.store.model.product.ProductAttributes;

import java.util.*;

public class ProductDto {
    private Long productId;
    private Long categoryId;
    private String productName;
    private double price;
    private String description;
    private String imageUrl;
    private List<AttributeItem> attributes;
    private List<ColorAttribute> colors;
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
        this.colors = new ArrayList<>(product.getColors());
        this.images = fillImages(product.getImages());
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

    public List<ColorAttribute> getColors() {
        return colors;
    }

    public void setColors(List<ColorAttribute> colors) {
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
     * Fill all products DTO parameters
     *
     * @param products Collection of products
     * @return {@link List} ProductDto collection
     */
    public static List<ProductDto> fillProducts(Collection<Product> products) {
        if (products == null) return null;
        List<ProductDto> productDtos = new ArrayList<>(products.size());

        for (Product product : products) {
            ProductDto productDto = new ProductDto(product);
            productDtos.add(productDto);
        }

        return productDtos;
    }

    /**
     * Fill default products DTO parameters
     *
     * @param products Collection products
     * @return {@link List} ProductDto collection
     */
    public static List<ProductDto> fillPureProducts(Collection<Product> products) {
        if (products == null) return null;
        List<ProductDto> productDtos = new ArrayList<>(products.size());

        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.productId = product.getId();
            productDto.categoryId = product.getCategory().getCategoryId();
            productDto.productName = product.getProductName();
            productDto.price = product.getPrice();
            productDto.imageUrl = product.getManagedImageId();

            productDtos.add(productDto);
        }

        return productDtos;
    }

    /**
     * Build AttributeItem collection from Products attributes
     *
     * @param product - Product object
     * @return {@link List} collection of attributes
     */
    private static List<AttributeItem> fillAttributes(Product product) {
        Set<ProductAttributes> productAttributes = product.getProductAttributes();
        List<AttributeItem> items = new ArrayList<>();
        for (ProductAttributes attribute : productAttributes) {
            items.add(new AttributeItem(attribute.getAttribute().getName(), attribute.getAttributeValue()));
        }

        return items;
    }

    /**
     * Build images IDs collection
     *
     * @param imageAttributes {@link Set} ImageAttribute collection
     * @return {@link List} images
     */
    private List<String> fillImages(Set<ImageAttribute> imageAttributes) {
        List<String> images = new ArrayList<>(imageAttributes.size());
        for (ImageAttribute image : imageAttributes) {
            images.add(image.getImageUrl());
        }

        return images;
    }
}