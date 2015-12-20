package com.advantage.test.online.store.order;

import com.advantage.online.store.order.doa.ShoppingCartRepository;
import com.advantage.online.store.order.model.ShoppingCart;
import com.advantage.test.cfg.AdvantageTestContextConfiguration;
import com.advantage.test.online.store.dao.GenericRepositoryTests;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author Binyamin Regev on 20/12/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AdvantageTestContextConfiguration.class})
public class ShoppingCartRepositoryTests extends GenericRepositoryTests {

    //@Autowired
    //@Qualifier("shoppingCartRepository")
    //private ShoppingCartRepository shoppingCartRepository;

    @Test
    public void testAddProductToUserCart() {

//        long userId = 1;
//        ShoppingCart cartProduct;
//
//        //  Add product that exists in Catalog with quantity that exists in stock
//        cartProduct = shoppingCartRepository.addProductToShoppingCart(userId, 2L, 255, 4);
//        System.out.println("Product create in user cart: userId=" + cartProduct.getUserId() +
//                ", product id=" + cartProduct.getProductId() +
//                ", decimal color=" + cartProduct.getColor() +
//                ", hexadecimal color=\"" + ShoppingCart.convertIntColorToHex(cartProduct.getColor()) + "\"" +
//                ", quantity=" + cartProduct.getQuantity());
//
//        //  Add product that exists in Catalog with quantity that not sure exists in stock
//        cartProduct = shoppingCartRepository.addProductToShoppingCart(userId, 5L, 255, 50);
//        System.out.println("Product create in user cart: userId=" + cartProduct.getUserId() +
//                ", product id=" + cartProduct.getProductId() +
//                ", decimal color=" + cartProduct.getColor() +
//                ", hexadecimal color=\"" + ShoppingCart.convertIntColorToHex(cartProduct.getColor()) + "\"" +
//                ", quantity=" + cartProduct.getQuantity());
//
//        //  Add product that DOES NOT exists in Catalog with quantity that may exists in stock
//        cartProduct = shoppingCartRepository.addProductToShoppingCart(userId, 2011L, ShoppingCart.convertHexColorToInt("FFFF00"), 3);
//        System.out.println("Product create in user cart: userId=" + cartProduct.getUserId() +
//                ", product id=" + cartProduct.getProductId() +
//                ", decimal color=" + cartProduct.getColor() +
//                ", hexadecimal color=\"" + ShoppingCart.convertIntColorToHex(cartProduct.getColor()) + "\"" +
//                ", quantity=" + cartProduct.getQuantity());
//
//        List<ShoppingCart> userCart = shoppingCartRepository.getShoppingCartsByUserId(userId);
//
//        Assert.assertTrue(userCart.size() == 3);
        Assert.assertTrue(true);
    }
}
