package com.advantage.order.store.order.services;

//import com.advantage.order.store.order.dto.OrderPurchaseRequest;
import AccountServiceClient.AccountServicePort;
import AccountServiceClient.AccountServicePortService;
import AccountServiceClient.GetAccountByIdRequest;
import AccountServiceClient.GetAccountByIdResponse;
import ShipExServiceClient.*;
import com.advantage.common.Url_resources;
import com.advantage.common.enums.ResponseEnum;
import com.advantage.order.store.order.dao.ShoppingCartRepository;
import com.advantage.order.store.order.dto.*;
import com.advantage.order.store.order.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
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
    public ShoppingCartResponse add(long userId, Long productId, String stringColor, int quantity) {
        int color = ShoppingCart.convertHexColorToInt(stringColor);
        return shoppingCartRepository.add(userId, productId, color, quantity);
    }

    public ShoppingCartResponse updateProductQuantityInCart(long userId, Long productId, String stringColor, int quantity) {
        int color = ShoppingCart.convertHexColorToInt(stringColor);
        System.out.println("ShoppingCartService.updateProductQuantityInCart -> color=" + color);
        return shoppingCartRepository.update(userId, productId, color, quantity);
    }

    public ShoppingCartResponse replaceUserCart(long userId, List<ShoppingCartDto> shoppingCarts) {
        return shoppingCartRepository.replace(userId, shoppingCarts);
    }

    /**
     * @param userId
     * @param productId
     * @param stringColor
     * @return
     */
    @Transactional
    public ShoppingCartResponse removeProductFromUserCart(long userId, Long productId, String stringColor) {
        int color = ShoppingCart.convertHexColorToInt(stringColor);
        return shoppingCartRepository.removeProductFromUserCart(userId, productId, color);
    }

    /**
     * @param userId
     * @return
     */
    @Transactional
    public ShoppingCartResponse clearUserCart(long userId) {
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

}

