package com.advantage.online.store.dto;

import java.util.List;
import java.util.Set;

import com.advantage.online.store.model.category.Category;
import com.advantage.util.ArgumentValidationHelper;

public class CategoryDto {
    private Long categoryId;
    private String categoryName;
    private String categoryImageId;
    private PromotedProductDto promotedProduct;
    private List<AttributeDto> attributes;
    private List<ProductDto> products;
    private Set<String> colors;
    private String minPrice;
    private String maxPrice;


    public List<AttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeDto> attributeItems) {
        this.attributes = attributeItems;
    }

    public Long getCategoryId() {

        return categoryId;
    }

    public void setCategoryId(final Long categoryId) {

        this.categoryId = categoryId;
    }

    public String getCategoryName() {

        return categoryName;
    }

    public void setCategoryName(String categoryName) {

        this.categoryName = categoryName;
    }

    public void applyCategory(final Category category) {
        ArgumentValidationHelper.validateArgumentIsNotNull(category, "category");
        setCategoryId(category.getCategoryId());
        setCategoryName(category.getCategoryName());
        setCategoryImageId(category.getManagedImageId());

    }

    public String getCategoryImageId() {

        return categoryImageId;
    }

    public void setCategoryImageId(String managedImageId) {

        this.categoryImageId = managedImageId;
    }

    public List<ProductDto> getProducts() {

        return products;
    }

    public void setProducts(final List<ProductDto> products) {

        this.products = products;
    }

    public PromotedProductDto getPromotedProduct() {
        return promotedProduct;
    }

    public void setPromotedProduct(PromotedProductDto promotedProduct) {
        this.promotedProduct = promotedProduct;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Set<String> getColors() {
        return colors;
    }

    public void setColors(Set<String> colors) {
        this.colors = colors;
    }
}

