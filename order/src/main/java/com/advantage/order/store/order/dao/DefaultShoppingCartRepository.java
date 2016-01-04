package com.advantage.order.store.order.dao;

import com.advantage.order.store.dao.AbstractRepository;
import com.advantage.order.store.order.dto.ShipExResponse;
import com.advantage.order.store.order.dto.ShoppingCartDto;
import com.advantage.order.store.order.dto.ShoppingCartResponseDto;
import com.advantage.order.store.order.dto.ShoppingCartResponseStatus;
import com.advantage.order.store.order.model.ShoppingCart;
import com.advantage.order.store.order.model.ShoppingCartPK;
import com.advantage.common.dto.ColorAttributeDto;
import com.advantage.common.dto.ProductDto;
import com.advantage.common.Constants;
import com.advantage.common.Url_resources;
import com.advantage.root.util.ArgumentValidationHelper;
import com.advantage.order.store.order.util.WSDLHelper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.net.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
    @Deprecated
    private static final String CATALOG_GET_PRODUCT_BY_ID_URI = "/products/{product_id}";
    @Deprecated
    private static final String ACCOUNT_GET_APP_USER_BY_ID_URI = "/users/{user_id}";

    private static final String CATALOG_PRODUCT = "products/";
    private static final String ACCOUNT_USERS = "users/";
    //  FINALs for REST API calls - END

    private static String NOT_FOUND = "NOT FOUND";

    private ShoppingCartResponseStatus shoppingCartResponse;
    private String failureMessage;

    public ShoppingCartResponseStatus getShoppingCartResponse() {
        return this.shoppingCartResponse;
    }

    public String getFailureMessage() {
        return this.failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public ShoppingCartResponseDto.CartProduct setNotFoundCartProduct(Long productId) {
        return new ShoppingCartResponseDto()
                .createCartProduct(productId, NOT_FOUND, -999999.99, 0, NOT_FOUND, false);
    }

    /**
     * Add a single product to a specific user shopping cart. <br/>
     * If <b>an identical product</b> (same product-id and same color) does not exists in the
     * user shopping cart then the method will create a new product in the shopping cart. <br/>
     * otherwise, the method will update the existing identical product found in the user shopping cart. <br />
     *
     * @param userId    identifies specific {@code AppUser}.
     * @param productId identifies specific {@code Product}
     * @param color     identifies specific {@code color} of {@code ColorAttributeDto}.
     * @param quantity  number of product units added to the shopping cart.
     * @return {@link ShoppingCartResponseStatus} class of the product. If an error occured method will return {@code null} and
     */
    @Override
    public ShoppingCart addProductToShoppingCart(long userId, Long productId, int color, int quantity, long lastUpdate) {

        //  Validate Arguments
        ArgumentValidationHelper.validateLongArgumentIsPositive(userId, "user id");
        ArgumentValidationHelper.validateLongArgumentIsPositiveOrZero(lastUpdate, "last update timestamp");
        ArgumentValidationHelper.validateArgumentIsNotNull(productId, "product id");
        ArgumentValidationHelper.validateNumberArgumentIsPositiveOrZero(color, "color decimal RGB value");
        ArgumentValidationHelper.validateNumberArgumentIsPositive(quantity, "quantity");

        //  Use API to verify userId belongs to a registered user by calling "Account Service"
        if (!isRegisteredUserExists(userId)) {
            shoppingCartResponse = new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
            return null;
        }

        ShoppingCart shoppingCart = null;

        if (isProductExists(productId, ShoppingCart.convertIntColorToHex(color))) {

            ShoppingCartPK shoppingCartPk = new ShoppingCartPK(userId, productId, color);
            shoppingCart = entityManager.find(ShoppingCart.class, shoppingCartPk);

            //  Check if there is this ShoppingCart already exists
            if (shoppingCart != null) {

                //  Existing product in user shopping cart (the same productId + color)
                shoppingCart.setQuantity(shoppingCart.getQuantity() + quantity);
                if (lastUpdate != 0) {
                    shoppingCart.setLastUpdate(lastUpdate);
                } else {
                    shoppingCart.setLastUpdate(new Date().getTime());
                }

                entityManager.persist(shoppingCart);

                this.failureMessage = "";
                shoppingCartResponse = new ShoppingCartResponseStatus(true,
                        ShoppingCart.MESSAGE_QUANTITY_OF_PRODUCT_IN_SHOPPING_CART_WAS_UPDATED_SUCCESSFULLY,
                        shoppingCart.getProductId());

            } else {
                //  New ShoppingCart
                shoppingCart = new ShoppingCart(userId, new Date().getTime(), productId, color, quantity);
                if (lastUpdate != 0) {
                    shoppingCart.setLastUpdate(lastUpdate);
                } else {
                    shoppingCart.setLastUpdate(new Date().getTime());
                }
                entityManager.persist(shoppingCart);

                //  New product in shopping cart created successfully.
                this.failureMessage = "";

                shoppingCartResponse = new ShoppingCartResponseStatus(true,
                        ShoppingCart.MESSAGE_NEW_PRODUCT_UPDATED_SUCCESSFULLY,
                        shoppingCart.getProductId());
            }
        } else {
            //  New product in shopping cart created successfully.
            this.failureMessage = ShoppingCart.MESSAGE_PRODUCT_NOT_FOUND_IN_CATALOG;

            shoppingCartResponse = new ShoppingCartResponseStatus(false,
                    ShoppingCart.MESSAGE_PRODUCT_NOT_FOUND_IN_CATALOG,
                    productId);
        }

        return shoppingCart;
    }

    /**
     * @param userId
     * @param productId
     * @param color
     * @param quantity
     * @return
     * @see java.util.Date
     */
    @Override
    public ShoppingCart updateShoppingCart(long userId, Long productId, int color, int quantity) {

        //  Validate Arguments
        ArgumentValidationHelper.validateLongArgumentIsPositive(userId, "user id");
        ArgumentValidationHelper.validateArgumentIsNotNull(productId, "product id");
        ArgumentValidationHelper.validateLongArgumentIsPositive(Long.valueOf(productId), "product id");
        ArgumentValidationHelper.validateNumberArgumentIsPositiveOrZero(color, "color decimal RGB value");
        ArgumentValidationHelper.validateNumberArgumentIsPositive(quantity, "quantity");

        //  Verify userId belongs to a registered user by calling "Account Service"
        //  REST API GET REQUEST using URI
        if (!isRegisteredUserExists(userId)) {
            shoppingCartResponse = new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
            return null;
        }

        ShoppingCart shoppingCart = null;

        if (isProductExists(productId, ShoppingCart.convertIntColorToHex(color))) {

            ShoppingCartPK shoppingCartPk = new ShoppingCartPK(userId, productId, color);
            shoppingCart = entityManager.find(ShoppingCart.class, shoppingCartPk);

            if (shoppingCart != null) {
                //  Product with color was found in user cart
                shoppingCart.setQuantity(quantity);     //  Set argument quantity as product quantity in user cart
                shoppingCart.setLastUpdate(new Date().getTime());

                entityManager.persist(shoppingCart);    //  Update changes

                //  Set RESPONSE object
                shoppingCartResponse.setSuccess(true);
                shoppingCartResponse.setReason(ShoppingCart.MESSAGE_EXISTING_PRODUCT_UPDATED_SUCCESSFULLY);
                shoppingCartResponse.setId(productId);
            } else {
                //  Product with color NOT FOUND in user cart - Set RESPONSE object to FAILURE
                this.failureMessage = ShoppingCart.MESSAGE_PRODUCT_WITH_COLOR_NOT_FOUND_IN_SHOPPING_CART;
                shoppingCartResponse = new ShoppingCartResponseStatus(
                        false,
                        ShoppingCart.MESSAGE_PRODUCT_WITH_COLOR_NOT_FOUND_IN_SHOPPING_CART,
                        -1);
            }

        } else {
            this.failureMessage = ShoppingCart.MESSAGE_PRODUCT_NOT_FOUND_IN_CATALOG;

            shoppingCartResponse = new ShoppingCartResponseStatus(
                    false,
                    ShoppingCart.MESSAGE_PRODUCT_NOT_FOUND_IN_CATALOG,
                    productId);
        }

        return shoppingCart;
    }

    /**
     * Calls method {@link #addProductToShoppingCart(long, Long, int, int, long)} to add a single product to a specific user
     * shopping cart. <br/>
     *
     * @param userId    identifies specific {@code AppUser}.
     * @param productId identifies specific {@code Product}
     * @param color     identifies specific {@code color} of {@code ColorAttributeDto}.
     * @param quantity  number of product units added to the shopping cart.
     * @return {@link ShoppingCartResponseStatus} {@code shoppingCartResponse} property containing the {@code RESPONSE}
     * for the {@code REQUEST}.
     */
    public ShoppingCartResponseStatus add(long userId, Long productId, int color, int quantity) {
        addProductToShoppingCart(userId, productId, color, quantity, 0);
        return shoppingCartResponse;
    }

    /**
     * Calls method {@link #updateShoppingCart(long, Long, int, int)} to update a single product in the user
     * shopping cart. <br/>
     *
     * @param userId    identifies specific {@code AppUser}.
     * @param productId identifies specific {@code Product}
     * @param color     identifies specific {@code color} of {@code ColorAttributeDto}.
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
        if (!isRegisteredUserExists(userId)) {
            return new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
        }

        ShoppingCart shoppingCart = null;

        if (isProductExists(productId, ShoppingCart.convertIntColorToHex(color))) {
            ShoppingCartPK shoppingCartPk = new ShoppingCartPK(userId, productId, color);

            shoppingCart = entityManager.find(ShoppingCart.class, shoppingCartPk);

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
        } else {
            //  New product in shopping cart created successfully.
            this.failureMessage = ShoppingCart.MESSAGE_PRODUCT_NOT_FOUND_IN_CATALOG;

            shoppingCartResponse = new ShoppingCartResponseStatus(false,
                    ShoppingCart.MESSAGE_PRODUCT_NOT_FOUND_IN_CATALOG,
                    productId);
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
        if (!isRegisteredUserExists(userId)) {
            return new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
        }

        //  Get user's shopping carts
        List<ShoppingCart> shoppingCarts = getShoppingCartsByUserId(userId);

        //  For each {@link ShoppingCart} get its ID and use method
        if ((shoppingCarts == null) || (shoppingCarts.size() == 0)) {
            return new ShoppingCartResponseStatus(true, ShoppingCart.MESSAGE_SHOPPING_CART_IS_EMPTY, -1);
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
        if (!isRegisteredUserExists(userId)) {
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
     * Get {@code Product} details from <b>CATALOG service</b> into {@link ProductDto}.
     *
     * @param productId to get details for.
     * @return {@code Product} details in {@link ProductDto}.
     */
    public ProductDto getProductDtoDetails(Long productId) {
        URL productsPrefixUrl;
        URL productByIdUrl = null;
        try {
            productsPrefixUrl = new URL(Url_resources.getUrlCatalog(), CATALOG_PRODUCT);
            productByIdUrl = new URL(productsPrefixUrl, String.valueOf(productId));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        System.out.println("stringURL=\"" + productByIdUrl.toString() + "\"");

        ProductDto dto = null;

        try {
            String stringResponse = httpGet(productByIdUrl);
            System.out.println("stringResponse = \"" + stringResponse + "\"");

            if (stringResponse.equalsIgnoreCase(NOT_FOUND)) {
                //  Product not found (409)
                dto = new ProductDto(productId, -1L, NOT_FOUND, -999999.99, NOT_FOUND, NOT_FOUND, null, null, null);
            } else {
                dto = getProductDtofromJsonObjectString(stringResponse);
            }
        } catch (IOException e) {
            System.out.println("Calling httpGet(\"" + productByIdUrl.toString() + "\") throws IOException: ");
            e.printStackTrace();
        }

        return dto;
    }

    /**
     * Get a single {@code Product} details using <b>REST API</b> {@code GET} request.
     *
     * @param productId Idetity of the product to get details.
     * @return {@link ProductDto} containing the JSON with requsted product details.
     */
    public ShoppingCartResponseDto.CartProduct getCartProductDetails(Long productId, String hexColor) {

        ProductDto dto = this.getProductDtoDetails(productId);

        ShoppingCartResponseDto.CartProduct cartProduct = null;

        if (!dto.getProductName().equalsIgnoreCase(NOT_FOUND)) {

            ColorAttributeDto colorAttrib = getProductColorAttribute(hexColor.toUpperCase(), dto.getColors());

            if (colorAttrib != null) {
                cartProduct = new ShoppingCartResponseDto()
                        .createCartProduct(dto.getProductId(),
                                dto.getProductName(),
                                dto.getPrice(),
                                0,
                                dto.getImageUrl(),
                                true);

                cartProduct.setColor(colorAttrib.getCode().toUpperCase(),
                        colorAttrib.getName().toUpperCase(),
                        colorAttrib.getInStock());

                System.out.println("Received Product information: ");
                System.out.println("   product id = " + dto.getProductId());
                System.out.println("   product name = " + dto.getProductName());
                System.out.println("   price per item = " + dto.getPrice());
                System.out.println("   managedImageId = \"" + dto.getImageUrl() + "\"");
                System.out.println("   ColorAttrubute.Code (hex) = \'" + colorAttrib.getCode().toUpperCase() + "\'");
                System.out.println("   ColorAttrubute.Color (name) = \"" + colorAttrib.getName().toUpperCase() + "\"");
                System.out.println("   ColorAttrubute.inStock = " + colorAttrib.getInStock());
            } else {
                //  Product with specific color NOT FOUND in Product table in CATALOG schema
                cartProduct = setNotFoundCartProduct(productId);
            }
        } else {
            //  Product with this productId not found in Product table in CATALOG schema (409)
            cartProduct = setNotFoundCartProduct(productId);
        }

        return cartProduct;
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
        if (!isRegisteredUserExists(userId)) {
            shoppingCartResponse = new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
            return null; //  userId is not a registered user
        }

        ShoppingCartResponseDto userCart = new ShoppingCartResponseDto(userId);

        if (shoppingCarts != null) {
            if ((shoppingCarts.size() > 0) || (!shoppingCarts.isEmpty())) {

                /* Scan user shopping cart and add all product to userCart response object  */
                //for (ShoppingCart cart : shoppingCarts) {
                for (ShoppingCart cart : shoppingCarts) {

                    //ProductDto dto = getCartProductDetails(cart.getProductId(),
                    //                                        ShoppingCart.convertIntColorToHex(cart.getColor()));
                    ShoppingCartResponseDto.CartProduct cartProduct = getCartProductDetails(cart.getProductId(),
                            ShoppingCart.convertIntColorToHex(cart.getColor()).toUpperCase());

                    if (cartProduct.getProductName().equalsIgnoreCase(NOT_FOUND)) {
                        userCart.addCartProduct(cartProduct.getProductId(),
                                cartProduct.getProductName(),   //  "NOT FOUND"
                                cartProduct.getPrice(),         //  -999999.99
                                cartProduct.getQuantity(),      //  0
                                cartProduct.getImageUrl(),      //  "NOT FOUND"
                                "000000",
                                "BLACK",
                                0,
                                false); //  isExists = false
                    } else {
                        /*  Add a product to user shopping cart response class  */
                        userCart.addCartProduct(cartProduct.getProductId(),
                                cartProduct.getProductName(),
                                cartProduct.getPrice(),
                                cart.getQuantity(),
                                cartProduct.getImageUrl(),
                                cartProduct.getColor().getCode(),
                                cartProduct.getColor().getName(),
                                cartProduct.getColor().getInStock());
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

    @Override
    public ShoppingCartResponseStatus replace(long userId, Collection<ShoppingCartDto> cartProducts) {

        ArgumentValidationHelper.validateLongArgumentIsPositive(userId, "user id");

        //  Verify userId belongs to a registered user by calling "Account Service"
        //  REST API GET REQUEST using URI
        if (!isRegisteredUserExists(userId)) {
            return new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
        }

        shoppingCartResponse = clearUserCart(userId);

        if (shoppingCartResponse.isSuccess()) {
            //  Clear user cart was successful - add new cart to user
            shoppingCartResponse = new ShoppingCartResponseStatus(true, ShoppingCart.MESSAGE_SHOPPING_CART_UPDATED_SUCCESSFULLY, -1);

            long lastUpdate = cartProducts.size();
            for (ShoppingCartDto cartProduct : cartProducts) {
                ShoppingCart shoppingCart = addProductToShoppingCart(userId,
                        cartProduct.getProductId(),
                        ShoppingCart.convertHexColorToInt(cartProduct.getHexColor()),
                        cartProduct.getQuantity(),
                        lastUpdate);

                if (shoppingCart != null) {
                    lastUpdate--;
                } else {
                    //  Override SUCCESS data and set failure information
                    shoppingCartResponse = new ShoppingCartResponseStatus(false, "Failed to add product to user cart.", cartProduct.getProductId());

                    //  Do we want to break out of the loop after 1 product failed to insert, or continue?
                    //break;  //  Exit the loop
                }
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
        if (!isRegisteredUserExists(userId)) {
            shoppingCartResponse = new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
            return null;
        }

        ShoppingCart shoppingCart = null;

        if (isProductExists(productId, ShoppingCart.convertIntColorToHex(color))) {
            ShoppingCartPK shoppingCartPk = new ShoppingCartPK(userId, productId, color);
            shoppingCart = entityManager.find(ShoppingCart.class, shoppingCartPk);

            if (shoppingCart != null) {
                System.out.println("userId=" + shoppingCart.getUserId() +
                        " productId=" + shoppingCart.getProductId() +
                        " color=" + shoppingCart.getColor() +
                        " quantity=" + shoppingCart.getQuantity());

                ShoppingCartResponseDto.CartProduct cartProduct = getCartProductDetails(shoppingCart.getProductId(),
                        ShoppingCart.convertIntColorToHex(color));
                if (cartProduct.getProductName().equalsIgnoreCase(NOT_FOUND)) {
                    String name = cartProduct.getProductName();
                    double pricePerItem = cartProduct.getPrice();
                    String managedImageId = cartProduct.getImageUrl();

                    System.out.println("Received Product information: product id=" + cartProduct.getProductId() +
                            ", product name=" + name +
                            ", price per item=" + pricePerItem +
                            ", managedImageId=\'" + managedImageId + "\'" +
                            ", Color.code=\'" + cartProduct.getColor().getCode() + "\'" +
                            ", Color.name=\'" + cartProduct.getColor().getName() + "\'" +
                            ", Color.inStock=" + cartProduct.getColor().getInStock());
                } else {
                    cartProduct = setNotFoundCartProduct(productId);
                }

            } else {
                //  shoppingCart is null
                System.out.println("userId=" + shoppingCart.getUserId() + " productId=" + shoppingCart.getProductId() + " color=" + shoppingCart.getColor() + " quantity=" + shoppingCart.getQuantity() + " - product not found");

                shoppingCartResponse = new ShoppingCartResponseStatus(false,
                        ShoppingCart.MESSAGE_PRODUCT_WITH_COLOR_NOT_FOUND_IN_SHOPPING_CART,
                        -1);
            }
        } else {
            //  New product in shopping cart created successfully.
            this.failureMessage = ShoppingCart.MESSAGE_PRODUCT_NOT_FOUND_IN_CATALOG;

            shoppingCartResponse = new ShoppingCartResponseStatus(false,
                    ShoppingCart.MESSAGE_PRODUCT_NOT_FOUND_IN_CATALOG,
                    productId);
        }

        return shoppingCart;
    }

    /**
     * Verify the quantity of each product in user cart exists in stock. If quantity
     * in user cart is greater than the quantity in stock than add the product with
     * the quantity in stock to {@link ShoppingCartResponseDto} {@code Response} JSON. <br/>
     *
     * @param userId               Unique identity of the user.
     * @param shoppingCartProducts {@link List} of {@link ShoppingCartDto} products in shopping cart to verify their quantities.
     * @return {@code null} when all quantities of the products in the user cart <b>are equal or Less than</b> the quantities in
     * stock. If the quantity of any cart product <b>is greater than</b> the quantity in stock then the product will be added to
     * the list of products in the cart with the <ul>quantity in stock</ul>.
     * @see #getCartProductDetails(Long, String)
     */
    @Override
    public ShoppingCartResponseDto verifyProductsQuantitiesInUserCart(long userId, List<ShoppingCartDto> shoppingCartProducts) {

        System.out.println("DefaultShoppingCartRepository -> verifyProductsQuantitiesInUserCart(): userId=" + userId);

        //  Verify userId belongs to a registered user by calling "Account Service"
        //  REST API GET REQUEST using URI
        if (!isRegisteredUserExists(userId)) {
            shoppingCartResponse = new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
            return null;
        }

        ShoppingCartResponseDto responseDto = new ShoppingCartResponseDto(userId);

        for (ShoppingCartDto cartProduct : shoppingCartProducts) {

            ShoppingCartResponseDto.CartProduct cartProductDto = getCartProductDetails(cartProduct.getProductId(), cartProduct.getHexColor());

            if (cartProduct.getQuantity() > cartProductDto.getColor().getInStock()) {

                ShoppingCartPK shoppingCartPk = new ShoppingCartPK(userId,
                        cartProduct.getProductId(),
                        ShoppingCart.convertHexColorToInt(cartProductDto.getColor().getCode()));

                ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, shoppingCartPk);

                if (shoppingCart != null) {
                    //  Update quantity of cart product to product's quantity in stock
                    shoppingCart.setColor(ShoppingCart.convertHexColorToInt(cartProductDto.getColor().getCode()));
                    shoppingCart.setQuantity(cartProductDto.getColor().getInStock());
                    entityManager.persist(shoppingCart);
                } else {
                    //  Unlikely to occur, since we already got the product details and compare its quantity in stock to the same product in cart.
                    System.out.println("Product \"" + cartProductDto.getProductName() + "\" exists in table and in user " + userId + " cart, but cannot be found using primary-key.");
                }

                responseDto.addCartProduct(cartProductDto.getProductId(),
                        cartProductDto.getProductName(),
                        cartProductDto.getPrice(),
                        cartProductDto.getColor().getInStock(),
                        cartProductDto.getImageUrl(),
                        cartProductDto.getColor().getCode(),
                        cartProductDto.getColor().getName(),
                        cartProductDto.getColor().getInStock());
            }
        }

        //  If there are no protducts to update in user cart the return null
        if (responseDto.getProductsInCart().size() == 0) {
            responseDto = null;
        }
        return responseDto;
    }

    @Override
    public ShipExResponse getShippingCostFromShipEx() {
        String wsdlFile = WSDLHelper.getWsdlFile(Url_resources.getUrlShipEx() + "/shipex.wsdl");
        System.out.println("WSDL File=\'" + wsdlFile + "\'");

        return new ShipExResponse();
    }

//    /**
//     * Do the purchase of products in user cart: <br/>
//     * * Get shipping cost by calling {@code SOAP} request to <b>ShipEx</b>. <br/>
//     * *
//     * @param userId
//     * @param orderPurchaseRequest
//     * @return
//     */
//    @Override
//    public ShoppingCartResponseStatus doPurchase(long userId, OrderPurchaseRequest orderPurchaseRequest) {
//
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
//
//        return null;
//    }

    /**
     * Calling <b>Account Service</b> via REST API GET request - to
     * check isExists an {@code AppUser} with given {@code userId}.
     *
     * @param userId {@code long} unique user identification to check.
     * @return {@code boolean}. <b>true</b> if userId belongs to a registered user, <b>false</b> otherwise.
     */
    public boolean isRegisteredUserExists(long userId) {
        boolean isExists = false;

        URL userPrefixUrl;
        URL userByIdUrl = null;
        try {
            userPrefixUrl = new URL(Url_resources.getUrlAccount(), ACCOUNT_USERS);
            userByIdUrl = new URL(userPrefixUrl, String.valueOf(userId));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // Constants.ACCOUNT_GET_APP_USER_BY_ID_URI.replace("{user_id}", String.valueOf(userId));

        System.out.println("stringURL=\"" + userByIdUrl.toString() + "\"");

        try {
            String stringResponse = httpGet(userByIdUrl);
            System.out.println("Is exists a registered user with " + userId + " as unique id ?" + stringResponse);

            isExists = stringResponse.equalsIgnoreCase("true");

        } catch (IOException e) {
            System.out.println("Calling httpGet(\"" + userByIdUrl.toString() + "\") throws IOException: ");
            e.printStackTrace();
        }

        return isExists;
    }

    public boolean isProductExists(Long productId, String hexColor) {
        boolean result = false;

        ProductDto productDetails = getProductDtoDetails(productId);
        if (!productDetails.getProductName().equalsIgnoreCase(NOT_FOUND)) {
            if (productDetails != null) {
                List<ColorAttributeDto> colors = productDetails.getColors();
                for (ColorAttributeDto color : colors) {
                    //  Better to compare integers than Strings - no problem with leading zeros
                    if (ShoppingCart.convertHexColorToInt(color.getCode()) == ShoppingCart.convertHexColorToInt(hexColor)) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    public ColorAttributeDto getProductColorAttribute(String hexColor, List<ColorAttributeDto> colors) {
        ColorAttributeDto returnColor = null;

        if ((colors != null) && (colors.size() > 0)) {
            for (ColorAttributeDto color : colors) {
                //  Better to compare integers than Strings - no problem with leading zeros
                /*if (color.getCode().equalsIgnoreCase(hexColor)) {*/
                if (ShoppingCart.convertHexColorToInt(color.getCode()) == ShoppingCart.convertHexColorToInt(hexColor)) {
                    returnColor = color;
                }
            }
        }

        return returnColor;
    }

    public static String httpGet(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        int responseCode = conn.getResponseCode();

        String returnValue;
        switch (responseCode) {
            case HttpStatus.SC_OK: {
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
                break;
            }
            case HttpStatus.SC_CONFLICT:
                //  Product not found
                returnValue = "Not found";
                break;

            default:
                System.out.println("httpGet -> responseCode=" + responseCode);
                throw new IOException(conn.getResponseMessage());
        }

        conn.disconnect();

        return returnValue;
    }

    private static ProductDto getProductDtofromJsonObjectString(String jsonObjectString) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        //TODO-BENY  Why false???
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ProductDto dto = objectMapper.readValue(jsonObjectString, ProductDto.class);

        return dto;
    }

}
