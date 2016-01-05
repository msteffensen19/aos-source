package com.advantage.order.store.order.dao;

import com.advantage.order.store.order.dto.*;
import com.advantage.order.store.order.model.ShoppingCart;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * @author Binyamin Regev on 03/12/2015.
 */
public interface ShoppingCartRepository {

    /*  Retrieve all products of user's ShoppingCart    */
    List<ShoppingCart> getShoppingCartsByUserId(long userId);

    @Transactional
    ShoppingCartResponseDto getUserShoppingCart(long userId);

    /*  Add     */
    @Transactional
    ShoppingCartResponse add(long userId, Long productId, int color, int quantity);

    /*  Add a single product to user's ShoppingCart */
    ShoppingCart addProductToShoppingCart(long userId, Long productId, int color, int quantity, long lastUpdate);

    /* Update   */
    /*  Update a single product in user's ShoppingCart  */
    ShoppingCart updateShoppingCart(long userId, Long productId, int color, int quantity);

    @Transactional
    ShoppingCartResponse update(long userId, Long productId, int color, int quantity);

    /* Replace user entire shopping cart */
    @Transactional
    ShoppingCartResponse replace(long userId, Collection<ShoppingCartDto> cartProducts);

    /*  Delete a specific product with specific color from user's ShoppingCart  */
    ShoppingCartResponse removeProductFromUserCart(long userId, Long productId, int color);

    /*  Delete all products of user's ShoppingCart  */
    ShoppingCartResponse clearUserCart(long userId);

    /*  Get specific product from user shopping cart    */
    @Transactional
    ShoppingCart getShoppingCartByPrimaryKey(long userId, Long productId, int color);

    ShoppingCartResponseDto verifyProductsQuantitiesInUserCart(long userId, List<ShoppingCartDto> shoppingCartProducts);

    ShipExResponse getShippingCostFromShipEx();

    //ShoppingCartResponse doPurchase(long userId, OrderPurchaseRequest orderPurchaseRequest);
}
