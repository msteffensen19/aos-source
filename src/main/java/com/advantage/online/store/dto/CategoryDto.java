package com.advantage.online.store.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.advantage.online.store.model.category.Category;
import com.advantage.util.ArgumentValidationHelper;

public class CategoryDto {

    private Long categoryId;
    private String categoryName;
    private String managedImageId;
    private PromotedProductDto promotedProduct;
    private List<AttributeDto> attributes;
    private List<ProductDto> products;

    public List<AttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Set<String>> attributes) {
        List<AttributeDto> attributeItems = new ArrayList<>();
        for (Map.Entry<String, Set<String>> item : attributes.entrySet()) {
            attributeItems.add(new AttributeDto(item.getKey(), item.getValue()));
        }

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
        setManagedImageId(category.getManagedImageId());

    }

    public String getManagedImageId() {

        return managedImageId;
    }

    public void setManagedImageId(String managedImageId) {

        this.managedImageId = managedImageId;
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

