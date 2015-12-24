package com.advantage.catalog.store.dto;

public class ColorAttributeDto {
    private String Code;
    private String Color;
    private int inStock;

    public ColorAttributeDto() {
    }

    public ColorAttributeDto(String code, String color, int inStock) {
        Code = code;
        Color = color;
        this.inStock = inStock;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }
}
