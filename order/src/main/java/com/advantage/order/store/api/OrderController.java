package com.advantage.order.store.api;

//import com.advantage.order.store.order.dto.OrderPurchaseRequest;
import com.advantage.order.store.order.services.ShoppingCartService;
import com.advantage.order.store.Constants;
import com.advantage.order.store.order.dto.ShoppingCartDto;
import com.advantage.order.store.order.dto.ShoppingCartResponseDto;
import com.advantage.order.store.order.dto.ShoppingCartResponseStatus;
import com.advantage.order.store.order.model.ShoppingCart;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Binyamin Regev on 09/12/2015.
 */
@RestController
@RequestMapping(value = Constants.URI_API + "/v1")
public class OrderController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    private ShoppingCartResponseStatus shoppingCartResponseStatus;

    /*  =========================================================================================================   */
    @RequestMapping(value = "/carts/{userid}", method = RequestMethod.GET)
    @ApiOperation(value = "Get user shopping cart")
    public ResponseEntity<ShoppingCartResponseDto> getUserCar4t(@PathVariable("userid") Long userId,
                                                               HttpServletRequest request, HttpServletResponse response) {

        ShoppingCartResponseDto userShoppingCart = shoppingCartService.getShoppingCartsByUserId(Long.valueOf(userId));

        if (userShoppingCart == null) {
            return new ResponseEntity<>(userShoppingCart, HttpStatus.NOT_FOUND);    //  404 = Resource not found
        }
//        else if (userShoppingCart.getProductsInCart().size() == 0) {
//            List<ShoppingCartResponseDto.CartProduct> productsInCart = new ArrayList<>();
//            userShoppingCart.setProductsInCart(productsInCart);
//
//            return new ResponseEntity<>(userShoppingCart, HttpStatus.OK);
//        }
        else {
            return new ResponseEntity<>(userShoppingCart, HttpStatus.OK);
        }
    }

    /*  =========================================================================================================   */
    @RequestMapping(value = "/carts/{userid}/product/{productid}/color/{color}", method = RequestMethod.POST)
    @ApiOperation(value = "Add product to shopping cart")
    public ResponseEntity<ShoppingCartResponseStatus> addProductToCart(@PathVariable("userid") Long userId,
                                                                       @PathVariable("productid") Long productId,
                                                                       @PathVariable("color") String hexColor,
                                                                       @RequestParam(value = "quantity", defaultValue = "1", required = false) int quantity,
                                                                       HttpServletRequest request) {

        shoppingCartResponseStatus = shoppingCartService.add(userId, productId, hexColor, quantity);

        return new ResponseEntity<>(shoppingCartResponseStatus, HttpStatus.OK);
    }

    /*  =========================================================================================================   */
    @RequestMapping(value = "/carts/{userid}/product/{productid}/color/{color}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update quantity of product in shopping cart")
    public ResponseEntity<ShoppingCartResponseStatus> updateProductQuantityInCart(@PathVariable("userid") long userId,
                                                                                  @PathVariable("productid") Long productId,
                                                                                  @PathVariable("color") String hexColor,
                                                                                  @RequestParam("quantity") int quantity,
                                                                                  HttpServletRequest request) {

        shoppingCartService.updateProductQuantityInCart(userId,
                productId,
                hexColor,
                quantity);

        return new ResponseEntity<>(shoppingCartResponseStatus, HttpStatus.OK);
    }

    /*  =========================================================================================================   */
    @RequestMapping(value = "/carts/{userid}", method = RequestMethod.PUT)
    @ApiOperation(value = "Replace user shopping cart")
    public ResponseEntity<ShoppingCartResponseStatus> replaceUserCart(@RequestBody List<ShoppingCartDto> shoopingCartProducts,
                                                                      @PathVariable("userid") Long userId,
                                                                      HttpServletRequest request) {

        if (userId != null) {
            shoppingCartResponseStatus = shoppingCartService.replaceUserCart(Long.valueOf(userId), shoopingCartProducts);
        } else {
            shoppingCartResponseStatus.setSuccess(false);
            shoppingCartResponseStatus.setReason(ShoppingCart.MESSAGE_INVALID_USER_ID);
            shoppingCartResponseStatus.setId(-1);
        }

        return new ResponseEntity<>(shoppingCartResponseStatus, HttpStatus.OK);
    }

    /*  =========================================================================================================   */
    @RequestMapping(value = "/carts/{userid}/product/{productid}/color/{color}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Remove a product from user shopping cart")
    public ResponseEntity<ShoppingCartResponseStatus> removeProductFromUserCart(@PathVariable("userid") long userId,
                                                                                @PathVariable("productid") Long productId,
                                                                                @PathVariable("color") String hexColor,
                                                                                HttpServletRequest request) {

        shoppingCartResponseStatus = shoppingCartService.removeProductFromUserCart(userId, productId, hexColor);

        return new ResponseEntity<>(shoppingCartResponseStatus, HttpStatus.OK);
    }

    /*  =========================================================================================================   */
    @RequestMapping(value = "/carts/{userid}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Clear user shopping cart")
    public ResponseEntity<ShoppingCartResponseStatus> clearUserCart(@PathVariable("userid") Long userId,
                                                                    HttpServletRequest request) {

        if (userId != null) {
            shoppingCartResponseStatus = shoppingCartService.clearUserCart(Long.valueOf(userId));
        } else {
            shoppingCartResponseStatus.setSuccess(false);
            shoppingCartResponseStatus.setReason(ShoppingCart.MESSAGE_INVALID_USER_ID);
            shoppingCartResponseStatus.setId(-1);
        }

        if (shoppingCartResponseStatus.isSuccess()) {
            return new ResponseEntity<>(shoppingCartResponseStatus, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(shoppingCartResponseStatus, HttpStatus.CONFLICT);
        }
    }

    /*  =========================================================================================================   */

    /**
     * <b>REST API</b> {@code PUT} request to verify quantities of all products in user cart.
     *
     * @param shoopingCartProducts {@link List} of {@link ShoppingCartDto} products in user cart to verify quantities.
     * @param userId               Unique user identity.
     * @return {@link ShoppingCartResponseDto} products that had higher quantity in cart than in stock. {@code null}
     * when all products quantities less or equal to their quantities in stock.
     */
    @RequestMapping(value = "/carts/{userid}/quantity", method = RequestMethod.PUT)
    @ApiOperation(value = "Verify products quantities in user cart")
    public ResponseEntity<ShoppingCartResponseDto> verifyProductsQuantitiesInUserCart(@RequestBody List<ShoppingCartDto> shoopingCartProducts,
                                                                                      @PathVariable("userid") long userId) {

        System.out.println("OrderController -> verifyProductsQuantitiesInUserCart(): userId=" + userId);
        ShoppingCartResponseDto responseDto = shoppingCartService.verifyProductsQuantitiesInUserCart(userId, shoopingCartProducts);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /*  =========================================================================================================   */

//    @RequestMapping(value = "/carts/{user_id}/purchase", method = RequestMethod.POST)
//    @ApiOperation(value = "Do purchase of products in cart")
//    public ResponseEntity<ShoppingCartResponseStatus> doPurchase(@RequestBody OrderPurchaseRequest orderPurchaseRequest,
//                                                                @PathVariable("user_id") long userId) {
//
//        System.out.println("OrderController -> doPurchase(): userId=" + userId);
//
//        ShoppingCartResponseStatus purchaseResponse = shoppingCartService.doPurchase(userId, orderPurchaseRequest);
//
//        return new ResponseEntity<>(purchaseResponse, HttpStatus.OK);
//    }

}
