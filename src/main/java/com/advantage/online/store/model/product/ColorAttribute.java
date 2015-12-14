package com.advantage.online.store.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class ColorAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "color")
    private String Color;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = Product.FIELD_ID)
    @JsonIgnore
    private Product product;

    @Column(name = "quantity", nullable = false, columnDefinition = "int default 10")
    private int quantity;

    public ColorAttribute() {
    }

    public ColorAttribute(String color) {
        Color = color;
    }

    public ColorAttribute(String color, int quantity) {
        Color = color;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
