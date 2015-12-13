package com.advantage.online.store.api;

import com.advantage.online.store.Constants;
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

    class cartProduct {
        private Long productId;
        private String stringColor;
        private int quantity;

        public cartProduct() {
        }

        public cartProduct(Long productId, String stringColor) {
            this.productId = productId;
            this.stringColor = stringColor;
        }

        public cartProduct(Long productId, String stringColor, int quantity) {
            this.productId = productId;
            this.stringColor = stringColor;
            this.quantity = quantity;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public String getStringColor() {
            return stringColor;
        }

        public void setStringColor(String stringColor) {
            this.stringColor = stringColor;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    @Autowired
    private ShoppingCartService shoppingCartService;

    private ShoppingCartResponseStatus shoppingCartResponseStatus;

    /*  =========================================================================================================   */
    @RequestMapping(value = "/carts/{userid}", method = RequestMethod.GET)
    @ApiOperation(value = "Get user cart")
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
    @ApiOperation(value = "Replace user shopping cart")
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
    @RequestMapping(value="/carts/{userid}", method=RequestMethod.POST)
    @ApiOperation(value = "Add product to cart")
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

    /*  =========================================================================================================   */
    @RequestMapping(value="/carts/{userid}?product={productid}&color={color}", method=RequestMethod.DELETE)
    public ResponseEntity<ShoppingCartResponseStatus> removeProductFromUserCart(@PathVariable("userid") Long userId,
                                                                                @PathVariable("productid") Long productId,
                                                                                @PathVariable("color") String color) {

        System.out.println("removeProductFromUserCart Parameters: ");
        System.out.println("   userId=" + userId);
        System.out.println("   productId=" + productId);
        System.out.println("   color=" + color);

        shoppingCartResponseStatus = shoppingCartService.removeProductFromUserCart(userId, productId, color);

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

}
