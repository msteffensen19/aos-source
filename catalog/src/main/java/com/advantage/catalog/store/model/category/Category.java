package com.advantage.catalog.store.model.category;

import com.advantage.catalog.store.model.product.Product;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kubany on 10/15/2015.
 */
@Entity
@Table(name = "CATEGORY")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name="getCategoryName",
                resultClasses = String.class,
                procedureName="get_category_name",
                parameters={
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "category_code", type = Integer.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "category_name", type = String.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name="getAllCategories01",
                resultClasses = Category.class,
                procedureName="get_all_categories_01",
                parameters={
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "ref", type = void.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name="getAllCategories02",
                resultClasses = String.class,
                procedureName="get_all_categories_02",
                parameters={
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_int", type = Integer.class)
                }
        )
})
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

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Product> products = new HashSet<>();

    public Category(final String categoryName, final String managedImageId) {

        this.categoryName = categoryName.toUpperCase();
        this.managedImageId = managedImageId.toUpperCase();
    }

    public Category() {

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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}