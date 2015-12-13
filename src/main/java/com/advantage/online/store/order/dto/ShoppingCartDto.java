package com.advantage.online.store.order.dto;

import java.util.List;

/**
 * @author Binyamin Regev on 03/12/2015.
 */
public class ShoppingCartDto {

    private class CartProduct {
        private Long productId;
        private String hexColor;
        private int quantity;

        public CartProduct() {
        }

        public CartProduct(Long productId, String hexColor, int quantity) {
            this.productId = productId;
            this.hexColor = hexColor;
            this.quantity = quantity;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public String getHexColor() {
            return hexColor;
        }

        public void setHexColor(String hexColor) {
            this.hexColor = hexColor;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }   //  End of private class CartProduct

    private long userId;
    private List<CartProduct> cartProductProducts;

    public ShoppingCartDto() {
    }

    public ShoppingCartDto(long userId, List<CartProduct> cartProductProducts) {
        this.userId = userId;
        this.cartProductProducts = cartProductProducts;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<CartProduct> getCartProductProducts() {
        return cartProductProducts;
    }

    public void setCartProductProducts(List<CartProduct> cartProductProducts) {
        this.cartProductProducts = cartProductProducts;
    }
}
