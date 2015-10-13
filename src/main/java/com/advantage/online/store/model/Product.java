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

    public Long getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;


    public Product(String name) {
        this.productName = name;
    }

    public Product() {
    }


    public String getProductName() {
        return productName;
    }
}
