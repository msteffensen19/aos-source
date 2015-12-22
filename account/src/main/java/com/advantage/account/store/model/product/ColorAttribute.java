package com.advantage.account.store.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class ColorAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "code")
    private String Code;

    @Column(name = "color")
    private String Color;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = Product.FIELD_ID)
    @JsonIgnore
    private Product product;

    @Column(name = "inStock")
    private int inStock;

    public ColorAttribute() {
    }

    public ColorAttribute(String color) {
        Color = color;
    }

    public ColorAttribute(String color, int inStock) {
        Color = color;
        this.inStock = inStock;
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

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public Product getProduct() {
        return product;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColorAttribute that = (ColorAttribute) o;

        if (Color != null ? !Color.equals(that.Color) : that.Color != null) return false;
        return !(product != null ? !product.equals(that.product) : that.product != null);

    }

    @Override
    public int hashCode() {
        int result = Color != null ? Color.hashCode() : 0;
        result = 31 * result + (product != null ? product.hashCode() : 0);
        return result;
    }
}
