package com.advantage.online.store.model.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Created by kubany on 10/15/2015.
 */
@Entity
@Table(name = "CATEGORY")
//, uniqueConstraints = {@UniqueConstraint(columnNames = "CATEGORY_NAME")})
@NamedQueries({
        @NamedQuery(
                name = Category.QUERY_GET_ALL,
                query = "select c from Category c"
        )
})
public class Category {

    public static final String QUERY_GET_ALL = "category.getAll";

    public static final String FIELD_CATEGORY_ID = "categoryId";

    public static final String PARAM_CATEGORY_ID = "CATEGORY_PARAM_CATEGORY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "managed_image_id")
    private String managedImageId;

    //@OneToMany(mappedBy="category")
    //private Set<Product> products;

    public Category(final String categoryName, final String managedImageId) {

        this.categoryName = categoryName.toUpperCase();
        this.managedImageId = managedImageId.toUpperCase();
    }

    public Category(){

    }

    public String getCategoryName() {
        return categoryName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

     public void setManagedImageId(final String managedImageId) {
    	
    	this.managedImageId = managedImageId;
    }

    public String getManagedImageId() {

    	return managedImageId;
    }
}