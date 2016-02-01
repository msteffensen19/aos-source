package com.advantage.common.dto;

/**
 * @author Moti Ostrovski on 01/02/2016.
 */
public class CategoryAttributeShowInFilter {
    private Long categoryId;
    private Long attributeId;
    private boolean showInFilter;

    public CategoryAttributeShowInFilter() {
    }

    public CategoryAttributeShowInFilter(Long categoryId, Long attributeId, boolean showInFilter) {
        this.categoryId = categoryId;
        this.attributeId = attributeId;
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

    public boolean isShowInFilter() {
        return showInFilter;
    }

    public void setShowInFilter(boolean showInFilter) {
        this.showInFilter = showInFilter;
    }
}
