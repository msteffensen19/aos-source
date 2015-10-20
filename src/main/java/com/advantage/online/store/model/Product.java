package com.advantage.online.store.model;

/**
 * Created by kubany on 10/11/2015.
 */
import javax.persistence.*;

@Entity
@Table(name = "PRODUCT")
@NamedQueries({
        @NamedQuery(
                name = Product.GET_ALL,
                query = "select p from Product p"
        )
})
public class Product {

    public static final String GET_ALL = "product.getAll";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name")
    private String productName;
    private int price;
    private String description;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    public Product() {
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

}
