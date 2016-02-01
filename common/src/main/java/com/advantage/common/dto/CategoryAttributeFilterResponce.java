package com.advantage.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ostrovsm on 01/02/2016.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CategoryAttributeFilterResponce {
    /**
     * Public inner class for Category-Attribute
     */
    public class CategoryAttribute {

        private Long categoryId;
        private Long attributeId;
        private boolean inFilter;

        public CategoryAttribute() {
        }

        public CategoryAttribute(long categoryId, long attributeId, boolean inFilter) {
            this.categoryId = categoryId;
            this.attributeId = attributeId;
            this.inFilter=inFilter;
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

        public boolean isInFilter() {
            return inFilter;
        }

        public void setInFilter(boolean inFilter) {
            this.inFilter = inFilter;
        }




    }

    private List<CategoryAttribute> categoryAttributes=new ArrayList<>();;


    private Long categoryId;
    private Long attributeId;
    private boolean inFilter;

    public CategoryAttributeFilterResponce() {

    }

    public CategoryAttributeFilterResponce(long categoryId, long attributeId, boolean inFilter) {
        this.categoryId = categoryId;
        this.attributeId = attributeId;
        this.inFilter = inFilter;
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

    public boolean isInFilter() {
        return inFilter;
    }

    public void setInFilter(boolean inFilter) {
        this.inFilter = inFilter;
    }


    public List<CategoryAttribute> getCategoryAttributes(){
        return categoryAttributes;
    }
}
