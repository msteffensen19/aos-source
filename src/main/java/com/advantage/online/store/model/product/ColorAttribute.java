package com.advantage.online.store.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class ColorAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "color")
    private String Color;

    @ManyToOne
    @JoinColumn(name = Product.FIELD_ID)
    @JsonIgnore
    private Product product;

    public ColorAttribute() {
    }

    public ColorAttribute(String color) {
        Color = color;
    }

    public Long getId() {
        return id;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
