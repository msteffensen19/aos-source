package com.advantage.online.store.order.dto;

import com.advantage.online.store.order.model.ShoppingCart;
import com.advantage.online.store.model.product.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Return {@code userId} and user {@link ShoppingCart} with {@link Product} details. <br/>
 * Contains inner classes {@link CartProduct} and {@link CartProduct.ProductColor}. <br/>
 * To add a product to user cart use: <br/>
 * {@link #addCartProduct(Long, String, double, int, String, String, String, int)} <br/>
 * <b>or</b> <br/>
 * {@link #addCartProduct(Long, String, double, int, String, String, String, int, boolean)}
 * @author Binyamin Regev on 16/12/2015.
 */
public class ShoppingCartResponseDto {

    /**
     * Public inner class for products in user cart.
     */
    public class CartProduct {

        /**
         * Private inner class for color attribute of product in user cart.
         */
        private class ProductColor {

            /*  private class ProductColor - properties  */
            private String code;
            private String name;
            private int inStock;

            /*  private class ProductColor - Constructors    */
            public ProductColor(String code, String name, int inStock) {
                this.code = code;
                this.name = name;
                this.inStock = inStock;
            }

            /*  private class ProductColor - Getters and Setters */
            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getInStock() {
                return inStock;
            }

            public void setInStock(int inStock) {
                this.inStock = inStock;
            }
        }

        /*  private class CartProduct - properties  */
        private Long productId;
        private String productName;
        private double pricePerItem;
        private int quantity;
        private String imageUrl;
        private ProductColor color;     //  Inner class of ShoppingCartResponseDto
        private boolean isExists;

        /*  private class CartProduct - Construtors  */
        public CartProduct(Long productId, String productName, double pricePerItem, int quantity, String imageUrl) {
            this.productId = productId;
            this.productName = productName;
            this.pricePerItem = pricePerItem;
            this.quantity = quantity;
            this.imageUrl = imageUrl;
        }

        public CartProduct(Long productId, String productName, double pricePerItem, int quantity, String imageUrl, ProductColor color) {
            this.productId = productId;
            this.productName = productName;
            this.pricePerItem = pricePerItem;
            this.quantity = quantity;
            this.imageUrl = imageUrl;
            this.color = color;
        }

        public CartProduct(Long productId, String productName, double pricePerItem, int quantity, String imageUrl, String colorCode, String colorName, int inStock) {
            this.productId = productId;
            this.productName = productName;
            this.pricePerItem = pricePerItem;
            this.quantity = quantity;
            this.imageUrl = imageUrl;
            this.color = new ProductColor(colorCode, colorName, inStock);
            this.isExists = true;
        }

        public CartProduct(Long productId, String productName, double pricePerItem, int quantity, String imageUrl, String colorCode, String colorName, int inStock, boolean isExists) {
            this.productId = productId;
            this.productName = productName;
            this.pricePerItem = pricePerItem;
            this.quantity = quantity;
            this.imageUrl = imageUrl;
            this.color = new ProductColor(colorCode, colorName, inStock);
            this.isExists = isExists;
        }

        /*  private class CartProduct - Getters and Setters  */
        public Long getProductId() {
            return this.productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return this.productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public double getPricePerItem() {
            return this.pricePerItem;
        }

        public void setPricePerItem(double pricePerItem) {
            this.pricePerItem = pricePerItem;
        }

        public int getQuantity() {
            return this.quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getImageUrl() {
            return this.imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public ProductColor getColor() {
            return this.color;
        }

        public void setColor(ProductColor color) {
            this.color = color;
        }

        public boolean isExists() { return isExists; }

        public void setExists(boolean exists) { isExists = exists; }
    }

    /*  public class ShoppingCartResponseDto - properties   */
    private long userId;
    List<CartProduct> productsInCart = new ArrayList<CartProduct>();

    /* public class ShoppingCartResponseDto - Constructors  */
    public ShoppingCartResponseDto() {

    }

    public ShoppingCartResponseDto(long userId) {
        this.userId = userId;
    }

    public ShoppingCartResponseDto(long userId, List<CartProduct> productsInCart) {
        this.userId = userId;
        this.productsInCart = productsInCart;
    }

    /*  public class ShoppingCartResponseDto - Getters and Setters  */
    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<CartProduct> getProductsInCart() {
        return this.productsInCart;
    }

    public void setProductsInCart(List<CartProduct> productsInCart) {
        this.productsInCart = productsInCart;
    }

    public boolean addCartProduct(Long productId, String productName, double pricePerItem, int quantity, String imageUrl, String colorHexCode, String colorName, int inStock) {
        CartProduct cartProduct = new CartProduct(productId, productName, pricePerItem, quantity, imageUrl, colorHexCode, colorName, inStock);
        return this.getProductsInCart().add(cartProduct);
    }

    public boolean addCartProduct(Long productId, String productName, double pricePerItem, int quantity, String imageUrl, String colorHexCode, String colorName, int inStock, boolean isExists) {
        CartProduct cartProduct = new CartProduct(productId, productName, pricePerItem, quantity, imageUrl, colorHexCode, colorName, inStock, isExists);
        return this.getProductsInCart().add(cartProduct);
    }
}
