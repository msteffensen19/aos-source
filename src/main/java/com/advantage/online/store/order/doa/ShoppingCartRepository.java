package com.advantage.online.store.order.doa;

import com.advantage.online.store.dao.DefaultCRUDOperations;
import com.advantage.online.store.order.dto.ShoppingCartResponseStatus;
import com.advantage.online.store.order.model.ShoppingCart;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * @author Binyamin Regev on 03/12/2015.
 */
public interface ShoppingCartRepository extends DefaultCRUDOperations<ShoppingCart> {

    /*  Add a single product to user's ShoppingCart */
    ShoppingCart addToShoppingCart(long userId, Long productId, int color, int quantity);

    /*  Update a single product in user's ShoppingCart  */
    ShoppingCart updateShoppingCart(long userId, Long productId, int color, int quantity);

    /*  Add list of product to user's ShoppingCart  */
    ShoppingCartResponseStatus createShoppingCart(long userId, Collection<ShoppingCart> cartProducts);

    /*  Delete all products of user's ShoppingCart  */
    ShoppingCartResponseStatus deleteShoppingCartsByUserId(long userId);

    /*  Delete a specific product with specific color from user's ShoppingCart  */
    ShoppingCartResponseStatus removeProductFromUserCart(long userId, Long productId, int color);

    /*  Retrieve all products of user's ShoppingCart    */
    List<ShoppingCart> getShoppingCartsByUserId(long userId);

    /*  Add     */
    @Transactional
    ShoppingCartResponseStatus add(long userId, Long productId, int color, int quantity);

    /* Update   */
    @Transactional
    ShoppingCartResponseStatus Update(long userId, Long productId, int color, int quantity);

    /* Replace  */
    @Transactional
    ShoppingCartResponseStatus replace(long userId, Collection<ShoppingCart> cartProducts);
}
