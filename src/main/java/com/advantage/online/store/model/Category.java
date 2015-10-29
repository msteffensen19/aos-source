package com.advantage.online.store.model;

import javax.persistence.*;
import java.util.Set;

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

    private byte[] image;

    @OneToMany(mappedBy="category")
    private Set<Product> products;

    public Category(String categoryName, byte[] photoBytes) {
        this.image = photoBytes;
        this.categoryName = categoryName;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
