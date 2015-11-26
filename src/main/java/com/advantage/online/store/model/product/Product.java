package com.advantage.online.store.model.product;

import javax.persistence.*;

import com.advantage.online.store.model.category.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PRODUCT")
@NamedQueries({
    @NamedQuery(
            name = Product.QUERY_GET_ALL,
            query = "select p from Product p"
    )
})
public class Product {
    public static final String QUERY_GET_ALL = "product.getAll";

    public static final String FIELD_ID = "product_id";
    public static final String FIELD_CATEGORY_ID = "category.categoryId";

    public static final String PARAM_ID = "PARAM_PRODUCT_ID";
    public static final String PARAM_CATEGORY_ID = "PARAM_PRODUCT_CATEGORY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = FIELD_ID)
    private Long id;

    @Column(name = "product_name")
    private String productName;
    private int price;
    private String description;

    @Column(name = "managed_image_id")
    private String managedImageId;
    
    @ManyToOne
    @JoinColumn(name="category_id")
    @JsonIgnore
    private Category category;

    @OneToMany(mappedBy = "primaryKey.product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ProductAttributes> productAttributes = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<ColorAttribute> colors = new HashSet<>();

    public Product() {
    }

    public Product(String name, String description, int price) {
        this.productName = name;
        this.description = description;
        this.price = price;
    }

    public Product(String name, String description, int price, Category category) {
        this.productName = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Product(String name) {
        this.productName = name;
    }

    public String getProductName() {

        return productName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManagedImageId() {
        return managedImageId;
    }

    public void setManagedImageId(String managedImageId) {
        this.managedImageId = managedImageId;
    }

    public Set<ColorAttribute> getColors() {
        return colors;
    }

    public void setColors(Set<ColorAttribute> colors) {
        this.colors = colors;
    }

    @JsonIgnore
    public Set<ProductAttributes> getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(Set<ProductAttributes> attributes) {
        this.productAttributes = attributes;
    }
}