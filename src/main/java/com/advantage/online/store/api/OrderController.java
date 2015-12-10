package com.advantage.online.store.api;

import com.advantage.online.store.Constants;
import com.advantage.online.store.order.dto.ShoppingCartResponseStatus;
import com.advantage.online.store.order.model.ShoppingCart;
import com.advantage.online.store.order.services.ShoppingCartService;
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
    public ResponseEntity<List<ShoppingCart>> getUserCart(@PathVariable("userid") Long userId, HttpServletRequest request, HttpServletResponse response) {

        System.out.println("getUserCart Parameters: ");
        System.out.println("   userId=" + userId);

        List<ShoppingCart> shoppingCarts = shoppingCartService.getShoppingCartsByUserId(Long.valueOf(userId));

        return new ResponseEntity<>(shoppingCarts, HttpStatus.OK);
    }

    /*
    EXAMPLE:
    ========
    @RequestMapping(value = "/products/{product_id}", method = RequestMethod.PUT)
    public ResponseEntity<ProductResponseStatus> updateProduct(@RequestBody ProductDto product,
                                                               @PathVariable("product_id") Long id) {
    */
    /*  =========================================================================================================   */
    @RequestMapping(value="/carts/{userid}", method=RequestMethod.PUT)
    public ResponseEntity<ShoppingCartResponseStatus> replaceUserCart(@RequestBody List<ShoppingCart> shoopingCarts,
                                                                      @PathVariable("userid") Long userId) {

        System.out.println("replaceUserCart Parameters: ");
        System.out.println("   userId=" + userId);
        System.out.println("   shoopingCarts=" + shoopingCarts);

        if (userId != null) {
            shoppingCartResponseStatus = shoppingCartService.replaceUserCart(Long.valueOf(userId),
                                                                            shoopingCarts);
        }
        else {
            shoppingCartResponseStatus.setSuccess(false);
            shoppingCartResponseStatus.setReason(ShoppingCart.MESSAGE_INVALID_USER_ID);
            shoppingCartResponseStatus.setId(-1);
        }

        return new ResponseEntity<>(shoppingCartResponseStatus, HttpStatus.OK);
    }

    /*  =========================================================================================================   */
    @RequestMapping(value="/carts/{userid}", method=RequestMethod.DELETE)
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

    /*  =========================================================================================================   */
    /*    @RequestMapping(value="/order/carts/{userid}?product={productid}&color={color}&quantity={x}", method=RequestMethod.POST)  */
    @RequestMapping(value="/carts/{userid}?product={productid}&color={color}", method=RequestMethod.POST)
    public ResponseEntity<ShoppingCartResponseStatus> addProductToCart(@PathVariable("userid") Long userId,
                                                                       @PathVariable("productid") Long productId,
                                                                       @PathVariable("color") Integer color) {


        System.out.println("addProductToCart Parameters: ");
        System.out.println("   userId=" + userId);
        System.out.println("   productId=" + productId);
        System.out.println("   color=" + color);

        return new ResponseEntity<>(shoppingCartResponseStatus, HttpStatus.OK);
    }

    /*  =========================================================================================================   */
    @RequestMapping(value="/carts/{userid}?product={productid}&color={color}&quantity={x}", method=RequestMethod.POST)
    public ResponseEntity<ShoppingCartResponseStatus> addProductToCart(@PathVariable("userid") Long userId,
                                                                       @PathVariable("productid") Long productId,
                                                                       @PathVariable("color") Integer color,
                                                                       @PathVariable("quantity") Integer quantity) {
        System.out.println("addProductToCart Parameters: ");
        System.out.println("   userId=" + userId);
        System.out.println("   productId=" + productId);
        System.out.println("   color=" + color);
        System.out.println("   quantity=" + quantity);

        if (quantity == null) { quantity.valueOf(1); }

        return new ResponseEntity<>(shoppingCartResponseStatus, HttpStatus.OK);
    }

    /*  =========================================================================================================   */
    @RequestMapping(value="/carts/{userid}?product={productid}&color={color}", method=RequestMethod.DELETE)
    public ResponseEntity<ShoppingCartResponseStatus> removeProductFromUserCart(@PathVariable("userid") Long userId,
                                                                                @PathVariable("productid") Long productId,
                                                                                @PathVariable("color") Integer color) {

        System.out.println("removeProductFromUserCart Parameters: ");
        System.out.println("   userId=" + userId);
        System.out.println("   productId=" + productId);
        System.out.println("   color=" + color);

        shoppingCartResponseStatus = shoppingCartService.removeProductFromUserCart(userId, productId, color);

        return new ResponseEntity<>(shoppingCartResponseStatus, HttpStatus.OK);
    }

    /*  =========================================================================================================   */
    @RequestMapping(value="/carts/{userid}?product={productid}&color={color}&quantity={x}", method=RequestMethod.PUT)
    public ResponseEntity<ShoppingCartResponseStatus> updateProductQuantityInCart(@PathVariable("userid") Long userId,
                                                                                  @PathVariable("product") Long productId,
                                                                                  @PathVariable("color") String color,
                                                                                  @PathVariable("quantity") Integer quantity) {
        System.out.println("updateProductQuantityInCart Parameters: ");
        System.out.println("   userId=" + userId);
        System.out.println("   productId=" + productId);
        System.out.println("   color=" + color);
        System.out.println("   quantity=" + quantity);

        return new ResponseEntity<>(shoppingCartResponseStatus, HttpStatus.OK);
    }

}
