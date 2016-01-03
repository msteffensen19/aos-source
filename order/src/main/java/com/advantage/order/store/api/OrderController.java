package com.advantage.order.store.api;

//import com.advantage.order.store.order.dto.OrderPurchaseRequest;
import com.advantage.order.store.order.dto.ShipExResponse;
import com.advantage.order.store.order.dto.ShoppingCartDto;
import com.advantage.order.store.order.dto.ShoppingCartResponseDto;
import com.advantage.order.store.order.dto.ShoppingCartResponseStatus;
import com.advantage.order.store.order.model.ShoppingCart;
import com.advantage.order.store.order.services.ShoppingCartService;
import com.advantage.root.string_resources.Constants;
import com.advantage.root.string_resources.Url_resources;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<ShoppingCartResponseDto> getUserCart(@PathVariable("userid") Long userId,
                                                               HttpServletRequest request, HttpServletResponse response) {

        ShoppingCartResponseDto userCartResponseDto = shoppingCartService.getShoppingCartsByUserId(Long.valueOf(userId));

        if (userCartResponseDto == null) {
            return new ResponseEntity<>(userCartResponseDto, HttpStatus.NOT_FOUND);    //  404 = Resource not found
        }
        else {
            return new ResponseEntity<>(userCartResponseDto, HttpStatus.OK);
        }
    }

    /*  =========================================================================================================   */
    @RequestMapping(value = "/carts/{userid}/product/{productid}/color/{color}", method = RequestMethod.POST)
    @ApiOperation(value = "Add product to shopping cart")
    /*public ResponseEntity<ShoppingCartResponseStatus> addProductToCart(@PathVariable("userid") Long userId,*/
    public ResponseEntity<ShoppingCartResponseDto> addProductToCart(@PathVariable("userid") Long userId,
                                                                       @PathVariable("productid") Long productId,
                                                                       @PathVariable("color") String hexColor,
                                                                       @RequestParam(value = "quantity", defaultValue = "1", required = false) int quantity,
                                                                       HttpServletRequest request) {

        shoppingCartResponseStatus = shoppingCartService.add(userId, productId, hexColor, quantity);

        /*return new ResponseEntity<>(shoppingCartResponseStatus, HttpStatus.OK);*/
        ShoppingCartResponseDto userCartResponseDto = shoppingCartService.getShoppingCartsByUserId(Long.valueOf(userId));
        if (userCartResponseDto == null) {
            return new ResponseEntity<>(userCartResponseDto, HttpStatus.NOT_FOUND);    //  404 = Resource not found
        }
        else {
            //return new ResponseEntity<>(userCartResponseDto, HttpStatus.OK);
            return new ResponseEntity<>(userCartResponseDto, HttpStatus.CREATED);
        }
    }

    /*  =========================================================================================================   */
    @RequestMapping(value = "/carts/{userid}/product/{productid}/color/{color}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update quantity of product in shopping cart")
    /*public ResponseEntity<ShoppingCartResponseStatus> updateProductQuantityInCart(@PathVariable("userid") long userId,*/
    public ResponseEntity<ShoppingCartResponseDto> updateProductQuantityInCart(@PathVariable("userid") Long userId,
                                                                                  @PathVariable("productid") Long productId,
                                                                                  @PathVariable("color") String hexColor,
                                                                                  @RequestParam("quantity") int quantity,
                                                                                  HttpServletRequest request) {

        shoppingCartResponseStatus = shoppingCartService.updateProductQuantityInCart(Long.valueOf(userId), productId, hexColor, quantity);

        /*return new ResponseEntity<>(shoppingCartResponseStatus, HttpStatus.OK);*/
        ShoppingCartResponseDto userCartResponseDto = shoppingCartService.getShoppingCartsByUserId(Long.valueOf(userId));
        if (userCartResponseDto == null) {
            return new ResponseEntity<>(userCartResponseDto, HttpStatus.NOT_FOUND);    //  404 = Resource not found
        }
        else {
            return new ResponseEntity<>(userCartResponseDto, HttpStatus.OK);
        }
    }

    /*  =========================================================================================================   */
    @RequestMapping(value = "/carts/{userid}", method = RequestMethod.PUT)
    @ApiOperation(value = "Replace user shopping cart")
    public ResponseEntity<ShoppingCartResponseStatus> replaceUserCart(@RequestBody List<ShoppingCartDto> shoopingCartProducts,
                                                                      @PathVariable("userid") Long userId,
                                                                      HttpServletRequest request) {

        HttpStatus httpStatus = HttpStatus.OK;

        if (userId != null) {
            shoppingCartResponseStatus = shoppingCartService.replaceUserCart(Long.valueOf(userId), shoopingCartProducts);

            if (shoppingCartResponseStatus.isSuccess()) {
                ShoppingCartResponseDto userCartResponseDto = shoppingCartService.getShoppingCartsByUserId(Long.valueOf(userId));

                if (userCartResponseDto == null) {
                    //  Unlikely scenario - update of user cart successful and get user cart failed
                    httpStatus = HttpStatus.NOT_FOUND;
                    shoppingCartResponseStatus = new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_SHOPPING_CART_IS_EMPTY, -1);
                }
                else {
                    httpStatus = HttpStatus.OK;
                }
            }
            else {
                //  Replace user cart failed
                //  NOT_IMPLEMENTED (501) = The server either does not recognise the
                //                          request method, or it lacks the ability
                //                          to fulfill the request.
                httpStatus = HttpStatus.NOT_IMPLEMENTED;

                shoppingCartResponseStatus.setSuccess(false);
                shoppingCartResponseStatus.setReason(ShoppingCart.MESSAGE_REPLACE_USER_CART_FAILED);
                shoppingCartResponseStatus.setId(-1);
            }
        }
        else {
            httpStatus = HttpStatus.NOT_FOUND;  //  Resource (registered user_id) not found

            shoppingCartResponseStatus.setSuccess(false);
            shoppingCartResponseStatus.setReason(ShoppingCart.MESSAGE_INVALID_USER_ID);
            shoppingCartResponseStatus.setId(-1);
        }

        return new ResponseEntity<>(shoppingCartResponseStatus, httpStatus);
    }

    /*  =========================================================================================================   */
    @RequestMapping(value = "/carts/{userid}/product/{productid}/color/{color}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Remove a product from user shopping cart")
    /*public ResponseEntity<ShoppingCartResponseStatus> removeProductFromUserCart(@PathVariable("userid") long userId,*/
    public ResponseEntity<ShoppingCartResponseDto> removeProductFromUserCart(@PathVariable("userid") long userId,
                                                                             @PathVariable("productid") Long productId,
                                                                             @PathVariable("color") String hexColor,
                                                                             HttpServletRequest request) {

        shoppingCartResponseStatus = shoppingCartService.removeProductFromUserCart(userId, productId, hexColor);

        /*return new ResponseEntity<>(shoppingCartResponseStatus, HttpStatus.OK);*/
        ShoppingCartResponseDto userCartResponseDto = shoppingCartService.getShoppingCartsByUserId(Long.valueOf(userId));
        if (userCartResponseDto == null) {
            return new ResponseEntity<>(userCartResponseDto, HttpStatus.NOT_FOUND);    //  404 = Resource not found
        }
        else {
            return new ResponseEntity<>(userCartResponseDto, HttpStatus.OK);
        }
    }

    /*  =========================================================================================================   */
    @RequestMapping(value = "/carts/{userid}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Clear user shopping cart")
    /*public ResponseEntity<ShoppingCartResponseStatus> clearUserCart(@PathVariable("userid") Long userId,*/
    public ResponseEntity<ShoppingCartResponseDto> clearUserCart(@PathVariable("userid") Long userId,
                                                                 HttpServletRequest request) {

        if (userId != null) {
            shoppingCartResponseStatus = shoppingCartService.clearUserCart(Long.valueOf(userId));
        } else {
            shoppingCartResponseStatus.setSuccess(false);
            shoppingCartResponseStatus.setReason(ShoppingCart.MESSAGE_INVALID_USER_ID);
            shoppingCartResponseStatus.setId(-1);
        }

        /*
        if (shoppingCartResponseStatus.isSuccess()) {
            return new ResponseEntity<>(shoppingCartResponseStatus, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(shoppingCartResponseStatus, HttpStatus.CONFLICT);
        }
        */
        ShoppingCartResponseDto userCartResponseDto = shoppingCartService.getShoppingCartsByUserId(Long.valueOf(userId));
        if (userCartResponseDto == null) {
            return new ResponseEntity<>(userCartResponseDto, HttpStatus.NOT_FOUND);    //  404 = Resource not found
        }
        else {
            return new ResponseEntity<>(userCartResponseDto, HttpStatus.OK);
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
        /*return new ResponseEntity<>(responseDto, HttpStatus.OK);*/

        ShoppingCartResponseDto userCartResponseDto = shoppingCartService.getShoppingCartsByUserId(Long.valueOf(userId));
        if (userCartResponseDto == null) {
            return new ResponseEntity<>(userCartResponseDto, HttpStatus.NOT_FOUND);    //  404 = Resource not found
        }
        else {
            return new ResponseEntity<>(userCartResponseDto, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/carts/ShipEx/dev_only", method = RequestMethod.GET)
    @ApiOperation(value = "Get ShipEx Shipping Cost WSDL")
    public ResponseEntity<ShipExResponse> getShippingCostFromShipEx(HttpServletRequest request, HttpServletResponse response) {
        ShipExResponse shipExResponse = shoppingCartService.getShippingCostFromShipEx();
        return new ResponseEntity<>(shipExResponse, HttpStatus.OK);
    }

    /*  =========================================================================================================   */

    public ResponseEntity<String> getWsdlStringTest1() {

        String stringURI = Url_resources.getUrlPrefixShipEx() + "/shipex.wsdl";

        System.out.println("Starting SOAPRequest...");
        try {
            URL url = new URL(stringURI);
            URLConnection uc = url.openConnection();
            System.out.println("url connection=\'" + uc.toString());
            Map<String, List<String>> fields = uc.getHeaderFields();

            System.out.println("SOAPConnectionFactory sfc = SOAPConnectionFactory.newInstance();");
            SOAPConnectionFactory sfc = SOAPConnectionFactory.newInstance();
            System.out.println("SOAPConnectionFactory sfc = " + sfc + "\n");

            System.out.println("SOAPConnection connection = sfc.createConnection();");
            SOAPConnection connection = sfc.createConnection();
            System.out.println("SOAPConnection connection = " + connection + "\n");

            System.out.println("MessageFactory mf = MessageFactory.newInstance();");
            MessageFactory mf = MessageFactory.newInstance();

            System.out.println("SOAPMessage sm = mf.createMessage();");
            SOAPMessage sm = mf.createMessage();
            System.out.println("SOAPMessage sm = " + sm + "\n");

            System.out.println("SOAPHeader sh = sm.getSOAPHeader();");
            SOAPHeader sh = sm.getSOAPHeader();
            System.out.println("SOAPHeader sh = " + sh + "\n");

            System.out.println("SOAPBody sb = sm.getSOAPBody();");
            SOAPBody sb = sm.getSOAPBody();
            System.out.println("SOAPBody sb = " + sb + "\n");

            System.out.println("sh.detachNode();");
            sh.detachNode();

            System.out.println("QName bodyName = new QName(" + stringURI + ", \"ShippingCostRequest\", \"d\");");
            QName bodyName = new QName(stringURI, "ShippingCostRequest", "d");
            System.out.println("QName bodyName = " + bodyName + "\n");

            System.out.println("SOAPBodyElement bodyElement = sb.addBodyElement(bodyName);");
            SOAPBodyElement bodyElement = sb.addBodyElement(bodyName);
            System.out.println("SOAPBodyElement bodyElement = " + bodyElement + "\n");

            System.out.println("QName qn = new QName(\"aName\");");
            QName qn = new QName("aName");
            System.out.println("QName qn = " + qn + "\n");

            System.out.println("SOAPElement quotation = bodyElement.addChildElement(qn);");
            SOAPElement quotation = bodyElement.addChildElement(qn);

            System.out.println("quotation.addTextNode(\"TextMode\");");
            quotation.addTextNode("TextMode");

            System.out.println("\n Soap Request:\n");

            sm.writeTo(System.out);

            System.out.println("URL endpoint = new URL(\"http://yourServer.com\");");
            URL endpoint = new URL("http://yourServer.com");

            System.out.println("SOAPMessage response = connection.call(sm, endpoint);");
            SOAPMessage response = connection.call(sm, endpoint);

            System.out.println("System.out.println(response.getContentDescription());");
            System.out.println(response.getContentDescription());

        } catch (Exception ex) {
            System.out.println("ex.printStackTrace();");
            ex.printStackTrace();
        }

        return new ResponseEntity<>("", HttpStatus.OK);
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
