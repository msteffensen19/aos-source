package com.advantage.catalog.store.model.category;

import javax.persistence.*;

/**
 * Created by ostrovsm on 31/01/2016.
 */

@Entity
@Table(name = "category_attribute_filter")
@IdClass(CategoryAttributeFilterPK.class)
@NamedQueries({
        @NamedQuery(
                name = CategoryAttributeFilter.QUERY_GET_ALL,
                query = "select c from CategoryAttributeFilter c"
        )
})
public class CategoryAttributeFilter {

    public static final String FIELD_CATEGORY_ID = "CATEGORY_ID";;
    public static final String FIELD_ATTRIBUTE_ID = "ATTRIBUTE_ID";
    public static final String FIELD_INFILTER = "INFILTER";
    public static final String QUERY_GET_ALL = "categoryAttributeFilter.getAll";


    @Id
    @Column(name = FIELD_CATEGORY_ID)
    private long categoryId;

    @Id
    @Column(name = FIELD_ATTRIBUTE_ID)
    private long attributeId;

    @Column(name = FIELD_INFILTER)
    private boolean inFilter;




    public CategoryAttributeFilter() {    }

    public CategoryAttributeFilter(long categoryId, long attributeId) {
        this.categoryId = categoryId;
        this.attributeId = attributeId;
        this.inFilter=true;
    }

    public CategoryAttributeFilter(long categoryId, long attributeId, boolean inFilter) {
        this.categoryId = categoryId;
        this.attributeId = attributeId;
        this.inFilter = inFilter;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(long attributeId) {
        this.attributeId = attributeId;
    }

    public boolean isInFilter() {
        return inFilter;
    }

    public void setInFilter(boolean inFilter) {
        this.inFilter = inFilter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryAttributeFilter that = (CategoryAttributeFilter) o;

        if (categoryId != that.categoryId) return false;
        return attributeId == that.attributeId;

    }

    @Override
    public int hashCode() {
        int result = (int) (categoryId ^ (categoryId >>> 32));
        result = 31 * result + (int) (attributeId ^ (attributeId >>> 32));
        return result;
    }
}
