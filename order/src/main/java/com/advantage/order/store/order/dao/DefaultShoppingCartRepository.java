package com.advantage.order.store.order.dao;

import com.advantage.order.store.config.ServiceConfiguration;
import com.advantage.order.store.dao.AbstractRepository;
import com.advantage.order.store.dto.ProductDto;
import com.advantage.order.store.order.dto.*;
import com.advantage.order.store.order.model.ShoppingCart;
import com.advantage.order.store.order.model.ShoppingCartPK;
import com.advantage.order.util.ArgumentValidationHelper;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
//import org.json.JSONObject;

/**
 * Order services - default repository for {@code ShoppingCart}.
 *
 * @author Binyamin Regev on 03/12/2015.
 */
@Component
@Qualifier("shoppingCartRepository")
@Repository
public class DefaultShoppingCartRepository extends AbstractRepository implements ShoppingCartRepository {

    //  FINALs for REST API calls - BEGIN
    //  Will be replaces with configuration variables (T.B.D.)
    private static final String CATALOG_GET_PRODUCT_BY_ID_URI = "/products/{product_id}";
    private static final String ACCOUNT_GET_APP_USER_BY_ID_URI = "/users/{user_id}";
    //  FINALs for REST API calls - END

    private static String NOT_FOUND = "NOT FOUND";

    private ShoppingCartResponseStatus shoppingCartResponse;
    private String failureMessage;

    /**
     * Gets current state and values of private property {@code shoppingCartResponse}
     * of class {@link ShoppingCartResponseStatus}.
     *
     * @return state and value of private property object {@code shoppingCartResponse}.
     */
    public ShoppingCartResponseStatus getShoppingCartResponse() {
        return this.shoppingCartResponse;
    }

    /**
     * Returns the current value of {@code failureMessage} private property.
     *
     * @return value of property {@code failureMessage}.
     */
    public String getFailureMessage() {
        return this.failureMessage;
    }

    /**
     * Set value (message) to property {@code failureMessage}.
     *
     * @param failureMessage Text to be set to private property.
     */
    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    //  =================================================
    //  Interface Methods
    //  =================================================

    /**
     * Add a single product to a specific user shopping cart. <br/>
     * If <b>an identical product</b> (same product-id and same color) does not exists in the
     * user shopping cart then the method will create a new product in the shopping cart. <br/>
     * otherwise, the method will update the existing identical product found in the user shopping cart. <br />
     *
     * @param userId    identifies specific {@code AppUser}.
     * @param productId identifies specific {@code Product}
     * @param color     identifies specific {@code color} of {@code ColorAttribute}.
     * @param quantity  number of product units added to the shopping cart.
     * @return {@link ShoppingCartResponseStatus} class of the product. If an error occured method will return {@code null} and
     */
    @Override
    public ShoppingCart addProductToShoppingCart(long userId, Long productId, int color, int quantity) {

        //  Validate Arguments
        ArgumentValidationHelper.validateLongArgumentIsPositive(userId, "user id");
        ArgumentValidationHelper.validateArgumentIsNotNull(productId, "product id");
        ArgumentValidationHelper.validateNumberArgumentIsPositive(color, "color decimal RGB value");
        ArgumentValidationHelper.validateNumberArgumentIsPositive(quantity, "quantity");

        //  Verify userId belongs to a registered user by calling "Account Service"
        //  REST API GET REQUEST using URI
        if (! isRegisteredUserExists(userId)) {
            shoppingCartResponse = new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
            return null;
        }

        ShoppingCartPK shoppingCartPk = new ShoppingCartPK(userId, productId, color);

        ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, shoppingCartPk);

        //  Check if there is this ShoppingCart already exists
        if (shoppingCart != null) {

            //  Existing product in user shopping cart (the same productId + color)
            shoppingCart.setQuantity(shoppingCart.getQuantity() + quantity);
            entityManager.persist(shoppingCart);

            this.failureMessage = "";
            shoppingCartResponse = new ShoppingCartResponseStatus(true,
                    ShoppingCart.MESSAGE_QUANTITY_OF_PRODUCT_IN_SHOPPING_CART_WAS_UPDATED_SUCCESSFULLY,
                    shoppingCart.getProductId());

        } else {
            //  New ShoppingCart
            shoppingCart = new ShoppingCart(userId, productId, color, quantity);
            entityManager.persist(shoppingCart);

            //  New product in shopping cart created successfully.
            this.failureMessage = "";

            shoppingCartResponse = new ShoppingCartResponseStatus(true,
                    ShoppingCart.MESSAGE_NEW_PRODUCT_UPDATED_SUCCESSFULLY,
                    shoppingCart.getProductId());
        }

        return shoppingCart;
    }

    /**
     * @param userId
     * @param productId
     * @param color
     * @param quantity
     * @return
     */
    @Override
    public ShoppingCart updateShoppingCart(long userId, Long productId, int color, int quantity) {

        //  Validate Arguments
        ArgumentValidationHelper.validateLongArgumentIsPositive(userId, "user id");
        ArgumentValidationHelper.validateArgumentIsNotNull(productId, "product id");
        ArgumentValidationHelper.validateLongArgumentIsPositive(Long.valueOf(productId), "product id");
        ArgumentValidationHelper.validateNumberArgumentIsPositive(color, "color decimal RGB value");
        ArgumentValidationHelper.validateNumberArgumentIsPositive(quantity, "quantity");

        //  Verify userId belongs to a registered user by calling "Account Service"
        //  REST API GET REQUEST using URI
        if (! isRegisteredUserExists(userId)) {
            shoppingCartResponse = new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
            return null;
        }

        ShoppingCartPK shoppingCartPk = new ShoppingCartPK(userId, productId, color);

        ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, shoppingCartPk);

        if (shoppingCart != null) {
            //  Product with color was found in user cart
            shoppingCart.setQuantity(quantity);     //  Set argument quantity as product quantity in user cart
            entityManager.persist(shoppingCart);    //  Update changes

            //  Set RESPONSE object
            shoppingCartResponse.setSuccess(true);
            shoppingCartResponse.setReason(ShoppingCart.MESSAGE_EXISTING_PRODUCT_UPDATED_SUCCESSFULLY);
            shoppingCartResponse.setId(productId);
        } else {
            //  Product with color not found in user cart - Set RESPONSE object to FAILURE
            shoppingCartResponse = new ShoppingCartResponseStatus(false,
                    ShoppingCart.MESSAGE_PRODUCT_WITH_COLOR_NOT_FOUND_IN_SHOPPING_CART,
                    -1);
        }

        return shoppingCart;
    }

    /**
     * Create {@link ShoppingCart} for {@code userId} with all the products in {@code cartProducts}. <br/>
     * If there is already a {@link ShoppingCart} exists for the {@code userId}
     * then the method will <b>MERGE</b> the {@code cartProducts} into the existing
     * {@link ShoppingCart}. If there is an identical product in {@code cartProducts}
     * and existing {@link ShoppingCart} then the quantity from {@code cartProducts}
     * will be set to the product in the existing {@link ShoppingCart}.
     *
     * @param userId       Id of {@code AppUser} to whom the {@link ShoppingCart} will be created.
     * @param cartProducts {@link Collection} of unique products in the {@link ShoppingCart}.
     * @return {@link ShoppingCartResponseStatus} class, properties values: <b>success</b> = {@code true} when successful or {@code false} if failed. <b>reason</b>=success or failure message text. <b>productId</b>=-1 because there are multiple products in the list.
     */
    public ShoppingCartResponseStatus createShoppingCart(long userId, Collection<ShoppingCart> cartProducts) {

        if ((cartProducts == null) || (cartProducts.size() == 0)) {
            return new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_NO_PRODUCTS_TO_UPDATE_IN_SHOPPING_CART, -1);
        }

        System.out.println("createShoppingCart.userId=" + userId);

        //  Verify userId belongs to a registered user by calling "Account Service"
        //  REST API GET REQUEST using URI
        if (! isRegisteredUserExists(userId)) {
            return new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
        }

        List<ShoppingCart> existingCart = this.getShoppingCartsByUserId(userId);
        if (existingCart == null) {
            //  Existing CartProduct IS EMPTY - Add all items.
            for (ShoppingCart cart : cartProducts) {
                entityManager.persist(cart);
            }
        } else {
            //  MERGE Existing CartProduct NOT EMPTY- Update existing products, add new items.
            for (ShoppingCart cart : cartProducts) {
                int i = existingCart.indexOf(cart);
                if (i != -1) {
                    //  Product found in existing cart
                    existingCart.get(i).setQuantity(cart.getQuantity());
                    entityManager.persist(existingCart.get(i));
                } else {
                    //  New product in cart
                    entityManager.persist(cart);
                }
            }
        }
        return new ShoppingCartResponseStatus(true, ShoppingCart.MESSAGE_SHOPPING_CART_UPDATED_SUCCESSFULLY, -1);
    }

    /**
     * Calls method {@link #addProductToShoppingCart(long, Long, int, int)} to add a single product to a specific user
     * shopping cart. <br/>
     *
     * @param userId    identifies specific {@code AppUser}.
     * @param productId identifies specific {@code Product}
     * @param color     identifies specific {@code color} of {@code ColorAttribute}.
     * @param quantity  number of product units added to the shopping cart.
     * @return {@link ShoppingCartResponseStatus} {@code shoppingCartResponse} property containing the {@code RESPONSE}
     * for the {@code REQUEST}.
     */
    public ShoppingCartResponseStatus add(long userId, Long productId, int color, int quantity) {
        addProductToShoppingCart(userId, productId, color, quantity);
        return shoppingCartResponse;
    }

    /**
     * Calls method {@link #updateShoppingCart(long, Long, int, int)} to update a single product in the user
     * shopping cart. <br/>
     *
     * @param userId    identifies specific {@code AppUser}.
     * @param productId identifies specific {@code Product}
     * @param color     identifies specific {@code color} of {@code ColorAttribute}.
     * @param quantity  number of product units added to the shopping cart.
     * @return {@link ShoppingCartResponseStatus} {@code shoppingCartResponse} property containing the {@code RESPONSE}
     * for the {@code REQUEST}.
     */
    public ShoppingCartResponseStatus update(long userId, Long productId, int color, int quantity) {
        updateShoppingCart(userId, productId, color, quantity);
        return shoppingCartResponse;
    }

    @Override
    public ShoppingCartResponseStatus removeProductFromUserCart(long userId, Long productId, int color) {

        //  Verify userId belongs to a registered user by calling "Account Service"
        //  REST API GET REQUEST using URI
        if (! isRegisteredUserExists(userId)) {
            return new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
        }

        ShoppingCartPK shoppingCartPk = new ShoppingCartPK(userId, productId, color);

        ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, shoppingCartPk);

        if (shoppingCart != null) {
            entityManager.remove(shoppingCart);

            shoppingCartResponse.setSuccess(true);
            shoppingCartResponse.setReason(ShoppingCart.MESSAGE_PRODUCT_WAS_DELETED_FROM_USER_CART_SUCCESSFULLY);
            shoppingCartResponse.setId(productId);
        } else {
            shoppingCartResponse.setSuccess(false);
            shoppingCartResponse.setReason(ShoppingCart.MESSAGE_PRODUCT_WITH_COLOR_NOT_FOUND_IN_SHOPPING_CART);
            shoppingCartResponse.setId(productId);
        }

        return shoppingCartResponse;
    }

    /**
     * Delete all {@link ShoppingCart} lines of specific <i>application user</i>
     * by {@code userId}. <br/>
     * Step #1: Use method {@link #getShoppingCartsByUserId} to get user's shopping carts. <br/>
     * Step #2: For each {@link ShoppingCart} get its ID and use method
     * {@link #removeProductFromUserCart} to delete it. <br/>
     *
     * @param userId
     * @return
     */
    @Override
    public ShoppingCartResponseStatus clearUserCart(long userId) {

        ArgumentValidationHelper.validateLongArgumentIsPositive(userId, "user id");

        System.out.println("deleteShoppingCartsByUserId.userId=" + userId);

        //  Verify userId belongs to a registered user by calling "Account Service"
        //  REST API GET REQUEST using URI
        if (! isRegisteredUserExists(userId)) {
            return new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
        }

        //  Get user's shopping carts
        List<ShoppingCart> shoppingCarts = getShoppingCartsByUserId(userId);

        //  For each {@link ShoppingCart} get its ID and use method
        if ((shoppingCarts == null) || (shoppingCarts.size() == 0)) {
            return new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_SHOPPING_CART_IS_EMPTY, -1);
        }

        for (ShoppingCart cart : shoppingCarts) {
            this.removeProductFromUserCart(userId, cart.getProductId(), cart.getColor());
            if (!shoppingCartResponse.isSuccess()) {
                return new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_USER_SHOPPING_CART_WAS_CLEARED, -1);
            }
        }

        return new ShoppingCartResponseStatus(true, ShoppingCart.MESSAGE_USER_SHOPPING_CART_WAS_CLEARED, -1);

    }

    /**
     * Get all {@link ShoppingCart} lines of specific <i>application user</i>
     * by {@code userId}.
     *
     * @param userId
     * @return list of products in the {@code ShoppingCart} of a specific user.
     */
    @Override
    public List<ShoppingCart> getShoppingCartsByUserId(long userId) {

        //  Verify userId belongs to a registered user by calling "Account Service"
        //  REST API GET REQUEST using URI
        if (! isRegisteredUserExists(userId)) {
            shoppingCartResponse = new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
            return null;
        }

        List<ShoppingCart> shoppingCarts = entityManager.createNamedQuery(ShoppingCart.QUERY_GET_CARTS_BY_USER_ID, ShoppingCart.class)
                .setParameter(ShoppingCart.PARAM_USER_ID, userId)
                .setMaxResults(ShoppingCart.MAX_NUM_OF_SHOPPING_CART_PRODUCTS)
                .getResultList();

        return ((shoppingCarts == null) || (shoppingCarts.isEmpty())) ? null : shoppingCarts;
    }

    /**
     * Get a single {@code Product} details using <b>REST API</b> {@code GET} request.
     *
     * @param productId Idetity of the product to get details.
     * @return {@link ProductDto} containing the JSON with requsted product details.
     */
    public ProductDto getProductDetails(Long productId) {
        /*  Build REQUEST URI */
        //String stringURL = Constants.URI_SERVER_CATALOG +
        //        CATALOG_GET_PRODUCT_BY_ID_URI.replace("{product_id}", String.valueOf(productId));
        String stringURL = ServiceConfiguration.getUriServerCatalog() +
                CATALOG_GET_PRODUCT_BY_ID_URI.replace("{product_id}", String.valueOf(productId));

        // stringURL = "http:/localhost:8080/catalog/api/v1/products/String.valueOf(productId)"
        System.out.println("stringURL=\"" + stringURL + "\"");

        ProductDto dto = null;

        try {
            String stringResponse = httpGet(stringURL);
            System.out.println("stringResponse = \"" + stringResponse + "\"");

            if (stringResponse.equalsIgnoreCase(NOT_FOUND)) {
                //  Product not found (409)
                dto = new ProductDto();
                dto.setProductId(productId);
                dto.setProductName(NOT_FOUND);
                dto.setImageUrl(NOT_FOUND);
                dto.setDescription(stringURL);  //  REST API URI for GET request
                dto.setPrice(-999999.99);       //  Price that makes no sense
            } else {
                dto = getProductDtofromJsonObjectString(stringResponse);

                System.out.println("Received Product information: ");
                System.out.println("   product id = " + dto.getProductId());
                System.out.println("   product name = " + dto.getProductName());
                System.out.println("   price per item = " + dto.getPrice());
                System.out.println("   managedImageId = \"" + dto.getImageUrl() + "\"");
                System.out.println("   ColorAttrubute.Code (hex) = \"" + dto.getColors().get(0).getCode() + "\"");
                System.out.println("   ColorAttrubute.Color (name) = \"" + dto.getColors().get(0).getColor() + "\"");
                System.out.println("   ColorAttrubute.inStock = " + dto.getColors().get(0).getInStock());
            }

        } catch (IOException e) {
            System.out.println("Calling httpGet(\"" + stringURL + "\") throws IOException: ");
            e.printStackTrace();
        }

        return dto;
    }

    /**
     * Get details for each {@link ShoppingCart} {@code Product}
     *
     * @param shoppingCarts
     * @return
     */
    public ShoppingCartResponseDto getCartProductsDetails(long userId, List<ShoppingCart> shoppingCarts) {

        //  Verify userId belongs to a registered user by calling "Account Service"
        //  REST API GET REQUEST using URI
        if (! isRegisteredUserExists(userId)) {
            shoppingCartResponse = new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
            return null; //  userId is not a registered user
        }

        ShoppingCartResponseDto userCart = new ShoppingCartResponseDto(userId);

        if (shoppingCarts != null) {
            if ((shoppingCarts.size() > 0) || (! shoppingCarts.isEmpty())) {

                /* Scan user shopping cart and add all product to userCart response object  */
                //for (ShoppingCart cart : shoppingCarts) {
                for (ShoppingCart cart : shoppingCarts) {

                    final ProductDto dto = getProductDetails(cart.getProductId());
                    if (dto.getProductName().equalsIgnoreCase(NOT_FOUND)) {
                        userCart.addCartProduct(dto.getProductId(),
                                dto.getProductName(),   //  "NOT FOUND"
                                dto.getPrice(),         //  -999999.99
                                cart.getQuantity(),
                                dto.getImageUrl(),      //  "NOT FOUND"
                                "000000",
                                "BLACK",
                                0,
                                false); //  isExists = false

                    } else {
                        /*  Add a product to user shopping cart response class  */
                        userCart.addCartProduct(dto.getProductId(),
                                dto.getProductName(),
                                dto.getPrice(),
                                cart.getQuantity(),
                                dto.getImageUrl(),
                                dto.getColors().get(0).getCode(),
                                dto.getColors().get(0).getColor(),
                                dto.getColors().get(0).getInStock());
                    }
                }
            }
            //else {
            //    //ShoppingCart.MESSAGE_SHOPPING_CART_IS_EMPTY
            //    userCart.setProductsInCart(new ArrayList<>());
            //}
        }
        //else {
        //    //ShoppingCart.MESSAGE_SHOPPING_CART_IS_EMPTY
        //    userCart.setProductsInCart(new ArrayList<>());
        //}

        //return ((userCart == null) || (userCart.isEmpty())) ? null : userCart;
        return userCart;
    }

    @Override
    public ShoppingCartResponseDto getUserShoppingCart(long userId) {
        List<ShoppingCart> shoppingCarts = getShoppingCartsByUserId(userId);

        ShoppingCartResponseDto shoppingCartResponse = new ShoppingCartResponseDto(userId);
        if ((shoppingCarts != null) && (shoppingCarts.size() > 0)) {
            shoppingCartResponse = getCartProductsDetails(userId, shoppingCarts);
        }

        return shoppingCartResponse;
    }

    public boolean isProductExistsInShoppingCart(long userId, Long productId, int color) {

        //  Verify userId belongs to a registered user by calling "Account Service"
        //  REST API GET REQUEST using URI
        if (! isRegisteredUserExists(userId)) {
            shoppingCartResponse = new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
            return false;
        }

        ShoppingCartPK shoppingCartPk = new ShoppingCartPK(userId, productId, color);

        ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, shoppingCartPk);

        return (shoppingCart != null);

    }

    @Override
    public ShoppingCartResponseStatus replace(long userId, Collection<ShoppingCartDto> cartProducts) {

        ArgumentValidationHelper.validateLongArgumentIsPositive(userId, "user id");

        //  Verify userId belongs to a registered user by calling "Account Service"
        //  REST API GET REQUEST using URI
        if (! isRegisteredUserExists(userId)) {
            return new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
        }

        shoppingCartResponse = clearUserCart(userId);

        if (shoppingCartResponse.isSuccess()) {
            //  Clear user cart was successful - add new cart to user
        }

        shoppingCartResponse = new ShoppingCartResponseStatus(true, ShoppingCart.MESSAGE_SHOPPING_CART_UPDATED_SUCCESSFULLY, -1);

        for (ShoppingCartDto cartProduct : cartProducts) {
            ShoppingCart shoppingCart = addProductToShoppingCart(userId,
                    cartProduct.getProductId(),
                    ShoppingCart.convertHexColorToInt(cartProduct.getHexColor()),
                    cartProduct.getQuantity());

            if (shoppingCart == null) {
                //  Override SUCCESS data and set failure information
                shoppingCartResponse = new ShoppingCartResponseStatus(false, "Failed to add product to user cart.", cartProduct.getProductId());

                //  Do we want to break out of the loop after 1 product failed to insert, or continue?
                //break;  //  Exit the loop
            }
        }

        return shoppingCartResponse;
    }


    /**
     * Get a specific {@link ShoppingCart} by values of primary-key columns. <br/>
     * <pre>
     *      <ul>Verify {@code userId}</ul> refer to a registered user via <b>REST API</b>. <br/>
     *      <ul>Verify {@code productId}</ul> refer to a registered user via <b>REST API</b>. <br/>
     *  </pre>
     *
     * @param userId
     * @param productId
     * @param color
     * @return Specific {@link ShoppingCart} uniquely identified by primary key fields values.
     */
    @Override
    public ShoppingCart getShoppingCartByPrimaryKey(long userId, Long productId, int color) {

        //  Verify userId belongs to a registered user by calling "Account Service"
        //  REST API GET REQUEST using URI
        if (! isRegisteredUserExists(userId)) {
            shoppingCartResponse = new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
            return null;
        }

        ShoppingCartPK shoppingCartPk = new ShoppingCartPK(userId, productId, color);
        ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, shoppingCartPk);

        if (shoppingCart != null) {
            System.out.println("userId=" + shoppingCart.getUserId() +
                    " productId=" + shoppingCart.getProductId() +
                    " color=" + shoppingCart.getColor() +
                    " quantity=" + shoppingCart.getQuantity());

            final ProductDto dto = getProductDetails(shoppingCart.getProductId());

            String name = dto.getProductName();
            double pricePerItem = dto.getPrice();
            String managedImageId = dto.getImageUrl();

            System.out.println("Received Product information: ");
            System.out.println("   product id = " + dto.getProductId());
            System.out.println("   product name = " + name);
            System.out.println("   price per item = " + pricePerItem);
            System.out.println("   managedImageId = \"" + managedImageId + "\"");
            System.out.println("   ColorAttrubute.HexColor = \"" + dto.getColors().get(0).getCode() + "\"");
            System.out.println("   ColorAttrubute.Color = \"" + dto.getColors().get(0).getColor() + "\"");
            System.out.println("   ColorAttrubute.inStock = " + dto.getColors().get(0).getInStock());

        } else {
            //  shoppingCart is null
            System.out.println("userId=" + shoppingCart.getUserId() +
                    " productId=" + shoppingCart.getProductId() +
                    " color=" + shoppingCart.getColor() +
                    " quantity=" + shoppingCart.getQuantity() + " - product not found");

            shoppingCartResponse = new ShoppingCartResponseStatus(false,
                    ShoppingCart.MESSAGE_PRODUCT_WITH_COLOR_NOT_FOUND_IN_SHOPPING_CART,
                    -1);
        }

        return shoppingCart;
    }

    /**
     * Verify the quantity of each product in user cart exists in stock. If quantity
     * in user cart is greater than the quantity in stock than add the product with
     * the quantity in stock to {@link ShoppingCartResponseDto} {@code Response} JSON. <br/>
     *
     * @param userId               Unique identity of the user.
     * @param shoppingCartProducts {@link List} of {@link ShoppingCartDto} products in
     *                             shopping cart to verify their quantities.
     * @return {@code null} when all quantities of the products in the user cart <b>are
     * equal or Less than</b> the quantities in stock. If the quantity of any cart product
     * <b>is greater than</b> the quantity in stock then the product will be added to the
     * list of products in the cart with the <ul>quantity in stock</ul>.
     * @see #getProductDetails(Long)
     */
    @Override
    public ShoppingCartResponseDto verifyProductsQuantitiesInUserCart(long userId, List<ShoppingCartDto> shoppingCartProducts) {

        System.out.println("DefaultShoppingCartRepository -> verifyProductsQuantitiesInUserCart(): userId=" + userId);

        //  Verify userId belongs to a registered user by calling "Account Service"
        //  REST API GET REQUEST using URI
        if (! isRegisteredUserExists(userId)) {
            shoppingCartResponse = new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
            return null;
        }

        ShoppingCartResponseDto responseDto = new ShoppingCartResponseDto(userId);

        for (ShoppingCartDto cartProduct : shoppingCartProducts) {

            ProductDto dto = getProductDetails(cartProduct.getProductId());

            if (cartProduct.getQuantity() > dto.getColors().get(0).getInStock()) {

                ShoppingCartPK shoppingCartPk = new ShoppingCartPK(userId,
                        cartProduct.getProductId(),
                        ShoppingCart.convertHexColorToInt(cartProduct.getHexColor()));
                ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, shoppingCartPk);

                if (shoppingCart != null) {
                    //  Update quantity of cart product to product's quantity in stock
                    shoppingCart.setQuantity(dto.getColors().get(0).getInStock());
                    entityManager.persist(shoppingCart);
                } else {
                    //  Unlikely, since we already got the product details and compare its quantity in stock to the same product in cart.
                    System.out.println("Product \"" + dto.getProductName() + "\" exists in table and in user " + userId + " cart, but cannot be found using primary-key.");
                }

                responseDto.addCartProduct(cartProduct.getProductId(),
                        dto.getProductName(),
                        dto.getPrice(),
                        dto.getColors().get(0).getInStock(),
                        dto.getImageUrl(),
                        dto.getColors().get(0).getCode(),
                        dto.getColors().get(0).getColor(),
                        dto.getColors().get(0).getInStock());

            }

        }

        //  If there are no protducts to update in user cart the return null
        if (responseDto.getProductsInCart().size() == 0) {
            responseDto = null;
        }

        return responseDto;
    }

    /**
     * Get shipping cost from {@code ShipEx} service using {@code SOAP} request (<i>WSDL</i>).
     * @return {@code SOAP} shipping cost response.
     */
    @Override
    public ShipExShippingCostResponse getShippingCostFromShipEx() {
//        try {
//            String stringUrl = ServiceConfiguration.getUriServerShipExWsdlRequest();
//
////            URL url = new URL("https://www.aaa.com/myws/MyService?WSDL");
//            URL url = new URL(stringUrl);
//            URLConnection uc = url.openConnection();
//            Map<String, List<String>> fields = uc.getHeaderFields();
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    /**
     * Do the purchase of products in user cart: <br/>
     * * Get shipping cost by calling {@code SOAP} request to <b>ShipEx</b>. <br/>
     * *
     * @param userId
     * @param orderPurchaseRequest
     * @return
     */
    @Override
    public ShoppingCartResponseStatus doPurchase(long userId, OrderPurchaseRequest orderPurchaseRequest) {

//        System.out.println("DefaultShoppingCartRepository -> doPurchase(): userId=" + userId);
//
//        //  Verify userId belongs to a registered user by calling "Account Service"
//        //  REST API GET REQUEST using URI
//        if (! isRegisteredUserExists(userId)) {
//            shoppingCartResponse = new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
//            return null;
//        }
//
//        ShipExShippingCostResponse response = this.getShippingCostFromShipEx();

        return null;
    }

    /**
     * Calling <b>Account Service</b> via REST API GET request - to
     * check isExists an {@code AppUser} with given {@code userId}.
     * @param userId {@code long} unique user identification to check.
     * @return {@code boolean}. <b>true</b> if userId belongs to a registered user,
     * <b>false</b> otherwise.
     */
    public boolean isRegisteredUserExists(long userId) {
        boolean isExists = false;

        /*  Build REQUEST URI */
        //String stringURL = Constants.URI_SERVER_ACCOUNT +
        //        ACCOUNT_GET_APP_USER_BY_ID_URI.replace("{user_id}", String.valueOf(userId));
        String stringURL = ServiceConfiguration.getUriServerAccount() +
                ACCOUNT_GET_APP_USER_BY_ID_URI.replace("{user_id}", String.valueOf(userId));

        // stringURL = "http:/localhost:8080/account/api/v1/accounts/String.valueOf(userId)"
        System.out.println("stringURL=\"" + stringURL + "\"");

        try {
            String stringResponse = httpGet(stringURL);
            System.out.println("Is exists a registered user with " + userId + " as unique id ?" + stringResponse);

            isExists = stringResponse.equalsIgnoreCase("true");

        } catch (IOException e) {
            System.out.println("Calling httpGet(\"" + stringURL + "\") throws IOException: ");
            e.printStackTrace();
        }

        return isExists;
    }

    /**
     * Call REST API POST request - T.B.D.
     * @param urlStr
     * @param paramName {@link String} array containing parameters names.
     * @param paramVal {@link String} array containing parameters values.
     * @return {@link String} containing {@code response} data.
     * @throws Exception
     */
    public static String httpPost(String urlStr,
                                  String[] paramName,
                                  String[] paramVal) throws Exception {

        URL url = new URL(urlStr);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setAllowUserInteraction(false);
        //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //conn.setRequestProperty("Content-Type", "application/json");    //  Send
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8"); //  Send
        conn.setRequestProperty("Accept", "application/json");          //  Receive
        conn.setRequestMethod("POST");

//        //  Prepare the JSON
//        JSONObject body = new JSONObject();
//        //body.put("keyName", "keyValue");
//        //OR
//        //body.put("keyName", JsonValue);
//        body.put("username","value");
//        body.put("password", "value");
//        body.put("tenantName", "value");
//        body.put("passwordCredentials", "value");


        // Create the form content
        OutputStream out = conn.getOutputStream();
        Writer writer = new OutputStreamWriter(out, "UTF-8");

//        out.write(body.toString().getBytes("UTF-8"));   //  Write JSON

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//        wr.write(body.toString());
        for (int i = 0; i < paramName.length; i++) {
            writer.write(paramName[i]);
            writer.write("=");
            writer.write(URLEncoder.encode(paramVal[i], "UTF-8"));
            writer.write("&");
        }
        writer.close();
        out.close();


        //  Get response code, and response data as string
        if (conn.getResponseCode() != 200) {
            throw new IOException(conn.getResponseMessage());
        }

        // Buffer the result into a string
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();

        conn.disconnect();
        return sb.toString();
    }

    /**
     * Call REST API GET request
     *
     * @param urlStr
     * @return
     * @throws IOException
     */
    public static String httpGet(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        int responseCode = conn.getResponseCode();

        String returnValue;

        if (responseCode == 200) {
            // Buffer the result into a string
            InputStreamReader inputStream = new InputStreamReader(conn.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStream);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            returnValue = sb.toString();

        } else {
            switch (responseCode) {
                case 409:
                    //  Product not found
                    returnValue = "Not found";
                    break;

                default:
                    System.out.println("httpGet -> responseCode=" + responseCode);
                    throw new IOException(conn.getResponseMessage());
            }
        }

        conn.disconnect();

        return returnValue;
    }

    private static ProductDto getProductDtofromJsonObjectString(String jsonObjectString) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ProductDto dto = objectMapper.readValue(jsonObjectString, ProductDto.class);

        return dto;
    }

}
