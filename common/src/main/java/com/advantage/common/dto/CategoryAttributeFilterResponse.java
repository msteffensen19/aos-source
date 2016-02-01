package com.advantage.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ostrovsm on 01/02/2016.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CategoryAttributeFilterResponse {
    /**
     * Public inner class for Category-Attribute
     */

    private List<CategoryAttributeShowInFilter> categoriesAttributes = new ArrayList<>();

    public CategoryAttributeFilterResponse() {
    }

    public CategoryAttributeFilterResponse(List<CategoryAttributeShowInFilter> categoriesAttributes) {
        this.categoriesAttributes = categoriesAttributes;
    }

    public List<CategoryAttributeShowInFilter> getCategoriesAttributes() {
        return categoriesAttributes;
    }

    public void setCategoriesAttributes(List<CategoryAttributeShowInFilter> categoriesAttributes) {
        this.categoriesAttributes = categoriesAttributes;
    }


}
