package com.advantage.online.store.model.attribute;

import com.advantage.online.store.model.product.ProductAttributes;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ATTRIBUTE")
public class Attribute {

    public static final String FIELD_ID = "attribute_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = FIELD_ID, nullable = false)
    private Long id;
    @Column(name = "NAME", length = 150, nullable = false)
    private String name;

    @OneToMany(mappedBy = "primaryKey.attribute", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ProductAttributes> productAttributes = new HashSet<>();

    public Long getId() {

        return id;
    }

    public void setId(final Long id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(final String name) {

        this.name = name;
    }


    public Set<ProductAttributes> getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(Set<ProductAttributes> products) {
        this.productAttributes = products;
    }
}