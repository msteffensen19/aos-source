package com.advantage.order.store.order.services;

//import com.advantage.order.store.order.dto.OrderPurchaseRequest;
import com.advantage.order.store.order.dao.ShoppingCartRepository;
import com.advantage.order.store.order.dto.ShipExResponse;
import com.advantage.order.store.order.dto.ShoppingCartDto;
import com.advantage.order.store.order.dto.ShoppingCartResponseDto;
import com.advantage.order.store.order.dto.ShoppingCartResponseStatus;
import com.advantage.order.store.order.model.ShoppingCart;
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
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public ShoppingCartResponseDto getShoppingCartsByUserId(long userId) {
        return shoppingCartRepository.getUserShoppingCart(userId);
    }

    /**
     * @param userId
     * @param productId
     * @param stringColor
     * @param quantity
     * @return
     */
    @Transactional
    public ShoppingCartResponseStatus add(long userId, Long productId, String stringColor, int quantity) {
        int color = ShoppingCart.convertHexColorToInt(stringColor);
        return shoppingCartRepository.add(userId, productId, color, quantity);
    }

    public ShoppingCartResponseStatus updateProductQuantityInCart(long userId, Long productId, String stringColor, int quantity) {
        int color = ShoppingCart.convertHexColorToInt(stringColor);
        System.out.println("ShoppingCartService.updateProductQuantityInCart -> color=" + color);
        return shoppingCartRepository.update(userId, productId, color, quantity);
    }

    public ShoppingCartResponseStatus replaceUserCart(long userId, List<ShoppingCartDto> shoppingCarts) {
        return shoppingCartRepository.replace(userId, shoppingCarts);
    }

    /**
     * @param userId
     * @param productId
     * @param stringColor
     * @return
     */
    @Transactional
    public ShoppingCartResponseStatus removeProductFromUserCart(long userId, Long productId, String stringColor) {
        int color = ShoppingCart.convertHexColorToInt(stringColor);
        return shoppingCartRepository.removeProductFromUserCart(userId, productId, color);
    }

    /**
     * @param userId
     * @return
     */
    @Transactional
    public ShoppingCartResponseStatus clearUserCart(long userId) {
        return shoppingCartRepository.clearUserCart(userId);
    }

    /**
     * Verify quantities of all products in user cart.
     *
     * @param userId               Unique user identity.
     * @param shoppingCartProducts {@link List} of {@link ShoppingCartDto} products in user cart to verify quantities.
     * @return {@link ShoppingCartResponseDto} products that had higher quantity in cart than in stock. {@code null}
     */
    @Transactional
    public ShoppingCartResponseDto verifyProductsQuantitiesInUserCart(long userId, List<ShoppingCartDto> shoppingCartProducts) {
        System.out.println("ShoppingCartService -> verifyProductsQuantitiesInUserCart(): userId=" + userId);

        return shoppingCartRepository.verifyProductsQuantitiesInUserCart(userId, shoppingCartProducts);
    }

    public ShipExResponse getShippingCostFromShipEx() {
        return shoppingCartRepository.getShippingCostFromShipEx();
    }


//    /**
//     * Call {@link ShoppingCartRepository} to do the order purchase process.
//     * @param userId
//     * @param orderPurchaseRequest
//     * @return
//     */
//    @Transactional
//    public ShoppingCartResponseStatus doPurchase(long userId, OrderPurchaseRequest orderPurchaseRequest) {
//        return shoppingCartRepository.doPurchase(userId, orderPurchaseRequest);
//    }

}

