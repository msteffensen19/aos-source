package com.advantage.online.store.order.services;

import com.advantage.online.store.order.doa.ShoppingCartRepository;
import com.advantage.online.store.order.dto.ShoppingCartResponseStatus;
import com.advantage.online.store.order.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Binyamin Regev on 03/12/2015.
 */
@Service
public class ShoppingCartService {

    @Autowired
    @Qualifier("shoppingCartRepository")
    public ShoppingCartRepository shoppingCartRepository;

    /**
     *
     * @param userId
     * @param productId
     * @param color
     * @param quantity
     * @return
     */
    @Transactional
    public ShoppingCartResponseStatus add(long userId, Long productId, int color, int quantity) {
        return shoppingCartRepository.add(userId, productId, color, quantity);
    }

    public ShoppingCartResponseStatus replaceUserCart(long userId, List<ShoppingCart> shoppingCarts) {
        return shoppingCartRepository.replace(userId, shoppingCarts);
    }

    /**
     *
     * @param userId
     * @param productId
     * @param color
     * @return
     */
    @Transactional
    public ShoppingCartResponseStatus removeProductFromUserCart(long userId, Long productId, int color) {
        return shoppingCartRepository.removeProductFromUserCart(userId, productId, color);
    }

    /**
     *
     * @param userId
     * @return
     */
    @Transactional
    public ShoppingCartResponseStatus clearUserCart(long userId) {
        return shoppingCartRepository.deleteShoppingCartsByUserId(userId);
    }

    /**
     *
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public List<ShoppingCart> getShoppingCartsByUserId(long userId) {
        return shoppingCartRepository.getShoppingCartsByUserId(userId);
    }

/*
    public ResponseEntity<List<ShoppingCart>> getUserCart(@PathVariable("userid") Long userId, HttpServletRequest request, HttpServletResponse response);

    public ResponseEntity<ShoppingCartResponseStatus> replaceUserCart(@PathVariable("userid") Long userId,
                                                                      @RequestBody List<ShoppingCart> shoopingCarts);

    public ResponseEntity<ShoppingCartResponseStatus> addProductToCart(@PathVariable("userid") Long userId,
                                                                       @PathVariable("product") Long productId,
                                                                       @PathVariable("color") Integer color,
                                                                       @PathVariable("quantity") Integer quantity);

    public ResponseEntity<ShoppingCartResponseStatus> removeProductFromUserCart(@PathVariable("userid") Long userId,
                                                                                @PathVariable("product") Long productId,
                                                                                @PathVariable("color") Integer color);

    public ResponseEntity<ShoppingCartResponseStatus> updateProductQuantityInCart(@PathVariable("userid") Long userId,
                                                                                  @PathVariable("product") Long productId,
                                                                                  @PathVariable("color") String color,
                                                                                  @PathVariable("quantity") Integer quantity);
 */

}

