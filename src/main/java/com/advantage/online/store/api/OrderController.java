package com.advantage.online.store.api;

import com.advantage.online.store.Constants;
import com.advantage.online.store.order.dto.ShoppingCartDto;
import com.advantage.online.store.order.dto.ShoppingCartProductDto;
import com.advantage.online.store.order.dto.ShoppingCartResponseStatus;
import com.advantage.online.store.order.model.ShoppingCart;
import com.advantage.online.store.order.services.ShoppingCartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Binyamin Regev on 09/12/2015.
 */
@RestController
@RequestMapping(value = "/order"+Constants.URI_API+"/v1")
public class OrderController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    private ShoppingCartResponseStatus shoppingCartResponseStatus;

    /*  =========================================================================================================   */
    @RequestMapping(value = "/carts/{userid}", method = RequestMethod.GET)
    @ApiOperation(value = "Get user shopping cart")
    public ResponseEntity<List<ShoppingCart>> getUserCart(@PathVariable("userid") Long userId, HttpServletRequest request, HttpServletResponse response) {

        System.out.println("getUserCart Parameters: ");
        System.out.println("   userId=" + userId);

        List<ShoppingCart> shoppingCarts = shoppingCartService.getShoppingCartsByUserId(Long.valueOf(userId));

        return new ResponseEntity<>(shoppingCarts, HttpStatus.OK);
    }

    /*  =========================================================================================================   */
    @RequestMapping(value="/carts/{userid}", method=RequestMethod.POST)
    @ApiOperation(value = "Add product to shopping cart")
    public ResponseEntity<ShoppingCartResponseStatus> addProductToCart(@PathVariable("userid") Long userId,
                                                                       @RequestParam("productid") Long productId,
                                                                       @RequestParam(value = "color", required = true) String stringColor,
                                                                       @RequestParam(value = "quantity", defaultValue = "1", required = false) int quantity) {

        System.out.println("addProductToCart Parameters: ");
        System.out.println("   userId=" + userId);
        System.out.println("   productId=" + productId);
        System.out.println("   color=" + stringColor);
        System.out.println("   quantity=" + quantity);

        shoppingCartResponseStatus = shoppingCartService.add(userId, productId, stringColor, quantity);

        return new ResponseEntity<>(shoppingCartResponseStatus, HttpStatus.OK);
    }

    /*  =========================================================================================================   */
    /*
    @RequestMapping(value="/carts/", method=RequestMethod.PUT)
    public ResponseEntity<ShoppingCartResponseStatus> updateProductQuantityInCart(@RequestBody ShoppingCartProductDto cartProduct) {
    */
    /*
    public ResponseEntity<ShoppingCartResponseStatus> updateProductQuantityInCart(@PathVariable int quantity,
                                                                                  @RequestParam long userId,
                                                                                  @RequestParam Long productId,
                                                                                  @RequestParam String hexColor) {
    */
    @RequestMapping(value="/carts/{userid}/product/{productid}/color/{color}&quantity={quantity}", method=RequestMethod.PUT)
    @ApiOperation(value = "Update quantity of product in shopping cart")
    public ResponseEntity<ShoppingCartResponseStatus> updateProductQuantityInCart(@PathVariable("userid") long userId,
                                                                                  @PathVariable("productid") Long productId,
                                                                                  @PathVariable("color") String hexColor,
                                                                                  @PathVariable("quantity") int quantity) {

        System.out.println("updateProductQuantityInCart Parameters: ");
        System.out.println("   userId=" + userId);
        System.out.println("   productId=" + productId);
        System.out.println("   color=" + hexColor);
        System.out.println("   quantity=" + quantity);

        shoppingCartService.updateProductQuantityInCart(userId,
                                                        productId,
                                                        hexColor,
                                                        quantity);

        return new ResponseEntity<>(shoppingCartResponseStatus, HttpStatus.OK);
    }

    /*  =========================================================================================================   */
    @RequestMapping(value="/carts/{userid}", method=RequestMethod.PUT)
    @ApiOperation(value = "Replace user shopping cart")
    public ResponseEntity<ShoppingCartResponseStatus> replaceUserCart(@RequestBody List<ShoppingCartDto> shoopingCartProducts,
                                                                      @PathVariable("userid") Long userId) {

        System.out.println("replaceUserCart Parameters: ");
        System.out.println("   userId=" + userId);
        System.out.println("   shoopingCarts=" + shoopingCartProducts);

        if (userId != null) {
            shoppingCartResponseStatus = shoppingCartService.replaceUserCart(Long.valueOf(userId), shoopingCartProducts);
        }
        else {
            shoppingCartResponseStatus.setSuccess(false);
            shoppingCartResponseStatus.setReason(ShoppingCart.MESSAGE_INVALID_USER_ID);
            shoppingCartResponseStatus.setId(-1);
        }

        return new ResponseEntity<>(shoppingCartResponseStatus, HttpStatus.OK);
    }

    /*  =========================================================================================================   */
    @RequestMapping(value="/carts/{userid}/product/{productid}/color/{color}", method=RequestMethod.DELETE)
    @ApiOperation(value = "Remove a product from user shopping cart")
    public ResponseEntity<ShoppingCartResponseStatus> removeProductFromUserCart(@PathVariable("userid") long userId,
                                                                                @PathVariable("productid") Long productId,
                                                                                @PathVariable("color") String hexColor) {

        System.out.println("removeProductFromUserCart Parameters: ");
        System.out.println("   userId=" + userId);
        System.out.println("   productId=" + productId);
        System.out.println("   color=" + hexColor);

        shoppingCartResponseStatus = shoppingCartService.removeProductFromUserCart(userId, productId, hexColor);

        return new ResponseEntity<>(shoppingCartResponseStatus, HttpStatus.OK);
    }

    /*  =========================================================================================================   */
    @RequestMapping(value="/carts/{userid}", method=RequestMethod.DELETE)
    @ApiOperation(value = "Clear user shopping cart")
    public ResponseEntity<ShoppingCartResponseStatus> clearUserCart(@PathVariable("userid") Long userId) {

        System.out.println("clearUserCart Parameters: ");
        System.out.println("   userId=" + userId);

        if (userId != null) {
            shoppingCartResponseStatus = shoppingCartService.clearUserCart(Long.valueOf(userId));
        }
        else {
            shoppingCartResponseStatus.setSuccess(false);
            shoppingCartResponseStatus.setReason(ShoppingCart.MESSAGE_INVALID_USER_ID);
            shoppingCartResponseStatus.setId(-1);
        }

        return new ResponseEntity<>(shoppingCartResponseStatus, HttpStatus.OK);
    }

}
