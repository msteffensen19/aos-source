package com.advantage.catalog.store.dto;

import com.advantage.catalog.store.model.product.ProductAttributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Describes the product attribute in the ProductDTO
 */
public class AttributeItem {
    private String attributeName;
    private String attributeValue;

    public AttributeItem() {
    }

    public AttributeItem(String attributeName, String attributeValue) {
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    /**
     * Convert ProductAttributes collection to AttributeItem DTO
     *
     * @param attributes - ProductAttributes collection
     * @return AttributeItem DTO collection
     */
    public static List<AttributeItem> productAttributesToAttributeValues(Collection<ProductAttributes> attributes) {
        List<AttributeItem> items = new ArrayList<>();
        for (ProductAttributes attribute : attributes) {
            String name = attribute.getAttribute().getName();
            String value = attribute.getAttributeValue();
            items.add(new AttributeItem(name, value));
        }

        return items;
    }
}
