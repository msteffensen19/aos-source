package com.advantage.common.dto;

/**
 * @author Moti Ostrovski on 01/02/2016.
 */
public class CategoryAttributeShowInFilter {
    private Long categoryId;
    private Long attributeId;
    private String attributeName;
    private boolean showInFilter;

    public CategoryAttributeShowInFilter() {
    }

    public CategoryAttributeShowInFilter(Long categoryId, Long attributeId, boolean showInFilter) {
        this.categoryId = categoryId;
        this.attributeId = attributeId;
        this.attributeName = null;
        this.showInFilter = showInFilter;
    }

    public CategoryAttributeShowInFilter(Long categoryId, Long attributeId, String attributeName, boolean showInFilter) {
        this.categoryId = categoryId;
        this.attributeId = attributeId;
        this.attributeName = attributeName;
        this.showInFilter = showInFilter;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public boolean isShowInFilter() {
        return showInFilter;
    }

    public void setShowInFilter(boolean showInFilter) {
        this.showInFilter = showInFilter;
    }
}
