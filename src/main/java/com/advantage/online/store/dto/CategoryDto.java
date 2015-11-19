package com.advantage.online.store.dto;

import java.util.List;

import com.advantage.online.store.model.category.Category;
import com.advantage.util.ArgumentValidationHelper;

public class CategoryDto {
    private Long id;
    private String categoryName;
    private String catImageUrl;
    private PromotedProductDto promotedProduct;
    private List<AttributeDto> attributes;
    private List<ProductDto> products;

    public List<AttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeDto> attributeItems) {
        this.attributes = attributeItems;
    }

    public Long getId() {

        return id;
    }

    public void setId(final Long categoryId) {

        this.id = categoryId;
    }

    public String getCategoryName() {

        return categoryName;
    }

    public void setCategoryName(String categoryName) {

        this.categoryName = categoryName;
    }

    public void applyCategory(final Category category) {
        ArgumentValidationHelper.validateArgumentIsNotNull(category, "category");
        setId(category.getCategoryId());
        setCategoryName(category.getCategoryName());
        setManagedImageId(category.getManagedImageId());

    }

    public String getManagedImageId() {

        return catImageUrl;
    }

    public void setManagedImageId(String managedImageId) {

        this.catImageUrl = managedImageId;
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
}

