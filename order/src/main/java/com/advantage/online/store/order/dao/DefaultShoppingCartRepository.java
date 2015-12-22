package com.advantage.online.store.order.dao;

import com.advantage.online.store.Constants;
import com.advantage.online.store.dao.AbstractRepository;
import com.advantage.online.store.dto.ProductDto;
import com.advantage.online.store.model.product.Product;
import com.advantage.online.store.order.dto.ShoppingCartDto;
import com.advantage.online.store.order.dto.ShoppingCartResponseDto;
import com.advantage.online.store.order.dto.ShoppingCartResponseStatus;
import com.advantage.online.store.order.model.ShoppingCart;
import com.advantage.online.store.order.model.ShoppingCartPK;
import com.advantage.util.ArgumentValidationHelper;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

/**
 * Order services - default repository for {@link ShoppingCart}.
 * @author Binyamin Regev on 03/12/2015.
 */
@Component
@Qualifier("shoppingCartRepository")
@Repository
public class DefaultShoppingCartRepository extends AbstractRepository implements ShoppingCartRepository{

    //  FINALs for REST API calls - BEGIN
    //  Will be replaces with configuration variables (T.B.D.)
    private static final String CATALOG_GET_PRODUCT_BY_ID_URI = "/products/{product_id}";
    //  FINALs for REST API calls - END

    private static String NOT_FOUND = "NOT FOUND";

    private ShoppingCartResponseStatus responseStatus;
    private String failureMessage;

    //  ***********************************************
    //  ******* USE REST API GET REQUEST URI    *******
    //  ***********************************************
    //@Autowired
    //private AppUserRepository appUserRepository;
    //  ***********************************************

    //  =================================================
    //  Local Class Methods
    //  =================================================
    /**
     * Gets current state and values of private property {@code responseStatus}
     * of class {@link ShoppingCartResponseStatus}.
     * @return state and value of private property object {@code responseStatus}.
     */
    public ShoppingCartResponseStatus getResponseStatus() {
        return this.responseStatus;
    }

    /**
     * Returns the current value of {@code failureMessage} private property.
     * @return value of property {@code failureMessage}.
     */
    public String getFailureMessage() {
        return this.failureMessage;
    }

    /**
     * Set value (message) to property {@code failureMessage}.
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
     * @param userId identifies specific {@code AppUser}.
     * @param productId identifies specific {@link Product}
     * @param color identifies specific {@code color} of {@code ColorAttribute}.
     * @param quantity number of product units added to the shopping cart.
     * @return {@link ShoppingCart} class of the product. If an error occured method will return {@code null} and
     * property {@link ShoppingCartResponseStatus} {@code responseStatus} will contain the details about the error.
     */
    @Override
    public ShoppingCart addProductToShoppingCart(long userId, Long productId, int color, int quantity) {

        //  Validate Arguments
        ArgumentValidationHelper.validateLongArgumentIsPositive(userId, "user id");
        ArgumentValidationHelper.validateArgumentIsNotNull(productId, "product id");
        ArgumentValidationHelper.validateNumberArgumentIsPositive(color, "color decimal RGB value");
        ArgumentValidationHelper.validateNumberArgumentIsPositive(quantity, "quantity");

        ShoppingCartPK shoppingCartPk = new ShoppingCartPK(userId, productId, color);

        ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, shoppingCartPk);

        //  Check if there is this ShoppingCart already exists
        if (shoppingCart != null) {

            //  Existing product in user shopping cart (the same productId + color)
            shoppingCart.setQuantity(shoppingCart.getQuantity() + quantity);
            entityManager.persist(shoppingCart);

            this.failureMessage = "";
            responseStatus = new ShoppingCartResponseStatus(true,
                                                            ShoppingCart.MESSAGE_QUANTITY_OF_PRODUCT_IN_SHOPPING_CART_WAS_UPDATED_SUCCESSFULLY,
                                                            shoppingCart.getProductId());

        }
        else {
            //  New ShoppingCart
            shoppingCart = new ShoppingCart(userId, productId, color, quantity);
            entityManager.persist(shoppingCart);

            //  New product in shopping cart created successfully.
            this.failureMessage = "";

            responseStatus = new ShoppingCartResponseStatus(true,
                                                            ShoppingCart.MESSAGE_NEW_PRODUCT_UPDATED_SUCCESSFULLY,
                                                            shoppingCart.getProductId());
        }

        return shoppingCart;
    }

    /**
     *
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

        ShoppingCartPK shoppingCartPk = new ShoppingCartPK(userId, productId, color);

        ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, shoppingCartPk);

        if (shoppingCart != null) {
            //  Product with color was found in user cart
            shoppingCart.setQuantity(quantity);     //  Set argument quantity as product quantity in user cart
            entityManager.persist(shoppingCart);    //  Update changes

            //  Set RESPONSE object
            responseStatus.setSuccess(true);
            responseStatus.setReason(ShoppingCart.MESSAGE_EXISTING_PRODUCT_UPDATED_SUCCESSFULLY);
            responseStatus.setId(productId);
        }
        else {
            //  Product with color not found in user cart - Set RESPONSE object to FAILURE
            responseStatus = new ShoppingCartResponseStatus(false,
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
     * @param userId Id of {@code AppUser} to whom the {@link ShoppingCart} will be created.
     * @param cartProducts {@link Collection} of unique products in the {@link ShoppingCart}.
     * @return {@link ShoppingCartResponseStatus} class, properties values: <b>success</b> = {@code true} when successful or {@code false} if failed. <b>reason</b>=success or failure message text. <b>productId</b>=-1 because there are multiple products in the list.
     */
    public ShoppingCartResponseStatus createShoppingCart(long userId, Collection<ShoppingCart> cartProducts) {

        if ((cartProducts == null) || (cartProducts.size() == 0)) {
            return new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_NO_PRODUCTS_TO_UPDATE_IN_SHOPPING_CART, -1);
        }

        System.out.println("createShoppingCart.userId=" + userId);

        //  *****************************************************************
        //  ******* Call "Account Service"REST API GET REQUEST URI    *******
        //  *****************************************************************
//        if (appUserRepository.get(userId) == null) {
//            return new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
//        }
        //  *****************************************************************


        List<ShoppingCart> existingCart = this.getShoppingCartsByUserId(userId);
        if (existingCart == null) {
            //  Existing CartProduct IS EMPTY - Add all items.
            for (ShoppingCart cart : cartProducts) {
                entityManager.persist(cart);
            }
        }
        else {
            //  MERGE Existing CartProduct NOT EMPTY- Update existing products, add new items.
            for (ShoppingCart cart : cartProducts) {
                int i = existingCart.indexOf(cart);
                if (i != -1) {
                    //  Product found in existing cart
                    existingCart.get(i).setQuantity(cart.getQuantity());
                    entityManager.persist(existingCart.get(i));
                }
                else {
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
     * @param userId identifies specific {@code AppUser}.
     * @param productId identifies specific {@link Product}
     * @param color identifies specific {@code color} of {@code ColorAttribute}.
     * @param quantity number of product units added to the shopping cart.
     * @return {@link ShoppingCartResponseStatus} {@code responseStatus} property containing the {@code RESPONSE}
     * for the {@code REQUEST}.
     */
    public ShoppingCartResponseStatus add(long userId, Long productId, int color, int quantity) {
        addProductToShoppingCart(userId, productId, color, quantity);
        return responseStatus;
    }

    /**
     * Calls method {@link #updateShoppingCart(long, Long, int, int)} to update a single product in the user
     * shopping cart. <br/>
     * @param userId identifies specific {@code AppUser}.
     * @param productId identifies specific {@link Product}
     * @param color identifies specific {@code color} of {@code ColorAttribute}.
     * @param quantity number of product units added to the shopping cart.
     * @return {@link ShoppingCartResponseStatus} {@code responseStatus} property containing the {@code RESPONSE}
     * for the {@code REQUEST}.
     */
    public ShoppingCartResponseStatus update(long userId, Long productId, int color, int quantity) {
        updateShoppingCart(userId, productId, color, quantity);
        return responseStatus;
    }

    @Override
    public ShoppingCartResponseStatus removeProductFromUserCart(long userId, Long productId, int color) {
        ShoppingCartPK shoppingCartPk = new ShoppingCartPK(userId, productId, color);

        ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, shoppingCartPk);

        if (shoppingCart != null) {
            entityManager.remove(shoppingCart);

            responseStatus.setSuccess(true);
            responseStatus.setReason(ShoppingCart.MESSAGE_PRODUCT_WAS_DELETED_FROM_USER_CART_SUCCESSFULLY);
            responseStatus.setId(productId);
        }
        else {
            responseStatus.setSuccess(false);
            responseStatus.setReason(ShoppingCart.MESSAGE_PRODUCT_WITH_COLOR_NOT_FOUND_IN_SHOPPING_CART);
            responseStatus.setId(productId);
        }

        return responseStatus;
    }

    /**
     * Delete all {@link ShoppingCart} lines of specific <i>application user</i>
     * by {@code userId}. <br/>
     * Step #1: Use method {@link #getShoppingCartsByUserId} to get user's shopping carts. <br/>
     * Step #2: For each {@link ShoppingCart} get its ID and use method
     * {@link #removeProductFromUserCart} to delete it. <br/>
     * @param userId
     * @return
     */
    @Override
    public ShoppingCartResponseStatus clearUserCart(long userId) {

        ArgumentValidationHelper.validateLongArgumentIsPositive(userId, "user id");

        System.out.println("deleteShoppingCartsByUserId.userId=" + userId);

        //  *****************************************************************
        //  ******* Call "Account Service"REST API GET REQUEST URI    *******
        //  *****************************************************************
        //  Verify that exists a User in the application with this userId
//        if (appUserRepository.get(userId) == null) {
//            return new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
//        }
        //  *****************************************************************

        //  Get user's shopping carts
        List<ShoppingCart> shoppingCarts = getShoppingCartsByUserId(userId);

        //  For each {@link ShoppingCart} get its ID and use method
        if ((shoppingCarts == null) || (shoppingCarts.size() == 0)) {
            return new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_SHOPPING_CART_IS_EMPTY, -1);
        }

        for (ShoppingCart cart : shoppingCarts) {
            this.removeProductFromUserCart(userId, cart.getProductId(), cart.getColor());
            if (! responseStatus.isSuccess()) {
                return new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_USER_SHOPPING_CART_WAS_CLEARED, -1);
            }
        }

        return new ShoppingCartResponseStatus(true, ShoppingCart.MESSAGE_USER_SHOPPING_CART_WAS_CLEARED, -1);

    }

    /**
     * <b>Irrelevant here</b>. Cannot create a new {@link ShoppingCart} only with 1 {@link String} value, there
     * are critical <b>Primary Key</b> fields values missing.
     * @param name value of a field name
     * @return {@code null}
     */
    @Override
    public ShoppingCart create(String name) { return null; }

    /**
     * <b>Irrelevant here</b>. <br />
     * Cannot create {@link ShoppingCart} and return {@link Long} identifier for an entiry
     * with a composite primary key of 3 fields.
     * @param entity single {@link Product} to update in the user {@link ShoppingCart}
     * @return 0.
     */
    @Override
    public Long create(ShoppingCart entity) {
        ////  Validate Numeric Arguments
        //ArgumentValidationHelper.validateArgumentIsNotNull(entity, "shopping cart");
        //
        ////  Check if there is this ShoppingCart already exists
        //ShoppingCart shoppingCart = getShoppingCartByPrimaryKey(entity.getUserId(),
        //                                                        entity.getProductId(),
        //                                                        entity.getColor());
        //if (shoppingCart == null) {
        //    //  New ShoppingCart
        //    entityManager.persist(entity);
        //
        //    //  New product in shopping cart created successfully.
        //    this.failureMessage = ShoppingCart.MESSAGE_NEW_PRODUCT_UPDATED_SUCCESSFULLY;
        //    responseStatus = new ShoppingCartResponseStatus(true, ShoppingCart.MESSAGE_NEW_PRODUCT_UPDATED_SUCCESSFULLY, entity.getProductId());
        //
        //    return entity.getProductId();
        //}
        //
        ////  Existing ShoppingCart
        //shoppingCart.setQuantity(shoppingCart.getQuantity() + entity.getQuantity());
        //entityManager.persist(shoppingCart);
        //
        ////  Existing product in shopping cart updated successfully.
        //this.failureMessage = ShoppingCart.MESSAGE_EXISTING_PRODUCT_UPDATED_SUCCESSFULLY;
        //responseStatus = new ShoppingCartResponseStatus(true, ShoppingCart.MESSAGE_EXISTING_PRODUCT_UPDATED_SUCCESSFULLY, shoppingCart.getProductId());
        //
        //return shoppingCart.getProductId();
        return Long.valueOf(0);
    }

    /**
     * <b>Irrelevant here</b>. <br />
     * No User-Story or BLI for this method.
     * @param entities One or more entities to delete.
     * @return 0.
     */
    @Override
    public int delete(ShoppingCart... entities) {
        return 0;
    }

    /**
     * <b>Irrelevant here</b>. <br />
     * Entity id (primary key) is composed of 3 fields.
     * @param id {@link Long} entity id
     * @return {@code null}.
     */
    @Override
    public ShoppingCart delete(Long id) { return null;}

    /**
     * <b>Irrelevant here</b>. <br />
     * Entity id (primary key) is composed of 3 fields.
     * @param ids
     * @return 0.
     */
    @Override
    public int deleteByIds(Collection<Long> ids) { return 0;}

    /**
     * <b>Irrelevant here</b>. <br />
     * For {@link ShoppingCart} must provide specific {@code userId}.
     * @return {@code null}
     */
    @Override
    public List<ShoppingCart> getAll() { return null; }

    /**
     * <b>Irrelevant here</b>. <br />
     * {@link ShoppingCart} entity id (primary key) is composed of 3 fields, not a single {@link Long} field.
     * @param entityId record id
     * @return {@code null}.
     */
    @Override
    public ShoppingCart get(Long entityId) { return null; }

    /**
     * Get all {@link ShoppingCart} lines of specific <i>application user</i>
     * by {@code userId}.
     * @param userId
     * @return list of products in the {@link ShoppingCart} of a specific user.
     */
    @Override
    public List<ShoppingCart> getShoppingCartsByUserId(long userId) {
        List<ShoppingCart> shoppingCarts = entityManager.createNamedQuery(ShoppingCart.QUERY_GET_CARTS_BY_USER_ID, ShoppingCart.class)
                .setParameter(ShoppingCart.PARAM_USER_ID, userId)
                .setMaxResults(ShoppingCart.MAX_NUM_OF_SHOPPING_CART_PRODUCTS)
                .getResultList();

        return ((shoppingCarts == null) || (shoppingCarts.isEmpty())) ? null : shoppingCarts;
    }

    /**
     * Get a single {@link Product} details using <b>REST API</b> {@code GET} request.
     * @param productId Idetity of the product to get details.
     * @return {@link ProductDto} containing the JSON with requsted product details.
     */
    public ProductDto getProductDetails(Long productId) {
        /*  Build REQUEST URI */
        String stringURL = Constants.URI_SERVER_CATALOG +
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
     * Get details for each {@link ShoppingCart} {@link Product}
     * @param shoppingCarts
     * @return
     */
    public ShoppingCartResponseDto getCartProductsDetails(long userId, List<ShoppingCart> shoppingCarts) {

        ShoppingCartResponseDto userCart = new ShoppingCartResponseDto(userId);

        /* Scan user shopping cart and add all product to userCart response object  */
        for (ShoppingCart cart: shoppingCarts) {

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

        //return ((userCart == null) || (userCart.isEmpty())) ? null : userCart;
        return userCart;
    }

    @Override
    public ShoppingCartResponseDto getUserShoppingCart(long userId) {
        List<ShoppingCart> shoppingCarts = getShoppingCartsByUserId(userId);

        return getCartProductsDetails(userId, shoppingCarts);
    }

    public boolean isProductExistsInShoppingCart(long userId, Long productId, int color) {
        ShoppingCartPK shoppingCartPk = new ShoppingCartPK(userId, productId, color);

        ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, shoppingCartPk);

        return (shoppingCart != null);

    }

    @Override
    public ShoppingCartResponseStatus replace(long userId, Collection<ShoppingCartDto> cartProducts) {

        ArgumentValidationHelper.validateLongArgumentIsPositive(userId, "user id");

        responseStatus = clearUserCart(userId);

        if (responseStatus.isSuccess()) {
            //  Clear user cart was successful - add new cart to user
        }

        responseStatus = new ShoppingCartResponseStatus(true, ShoppingCart.MESSAGE_SHOPPING_CART_UPDATED_SUCCESSFULLY, -1);

        for (ShoppingCartDto cartProduct : cartProducts) {
            ShoppingCart shoppingCart = addProductToShoppingCart(userId,
                                                                cartProduct.getProductId(),
                                                                ShoppingCart.convertHexColorToInt(cartProduct.getHexColor()),
                                                                cartProduct.getQuantity());

            if (shoppingCart == null) {
                //  Override SUCCESS data and set failure information
                responseStatus = new ShoppingCartResponseStatus(false, "Failed to add product to user cart.", cartProduct.getProductId());

                //  Do we want to break out of the loop after 1 product failed to insert, or continue?
                //break;  //  Exit the loop
            }
        }

        return responseStatus;
    }


    /**
     *  Get a specific {@link ShoppingCart} by values of primary-key columns. <br/>
     *  <pre>
     *      <ul>Verify {@code userId}</ul> refer to a registered user via <b>REST API</b>. <br/>
     *      <ul>Verify {@code productId}</ul> refer to a registered user via <b>REST API</b>. <br/>
     *  </pre>
     * @param userId
     * @param productId
     * @param color
     * @return Specific {@link ShoppingCart} uniquely identified by primary key fields values.
     */
    @Override
    public ShoppingCart getShoppingCartByPrimaryKey(long userId, Long productId, int color) {

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

            responseStatus = new ShoppingCartResponseStatus(false,
                    ShoppingCart.MESSAGE_PRODUCT_WITH_COLOR_NOT_FOUND_IN_SHOPPING_CART,
                    -1);
        }

        return shoppingCart;
    }

    /**
     * Verify the quantity of each product in user cart exists in stock. If quantity
     * in user cart is greater than the quantity in stock than add the product with
     * the quantity in stock to {@link ShoppingCartResponseDto} {@code Response} JSON. <br/>
     * @see #getProductDetails(Long)
     * @param userId Unique identity of the user.
     * @param shoppingCartProducts {@link List} of {@link ShoppingCartDto} products in
     *                             shopping cart to verify their quantities.
     * @return {@code null} when all quantities of the products in the user cart <b>are
     * equal or Less than</b> the quantities in stock. If the quantity of any cart product
     * <b>is greater than</b> the quantity in stock then the product will be added to the
     * list of products in the cart with the <ul>quantity in stock</ul>.
     */
    public ShoppingCartResponseDto verifyProductsQuantitiesInUserCart(long userId, List<ShoppingCartDto> shoppingCartProducts) {

        System.out.println("DefaultShoppingCartRepository -> verifyProductsQuantitiesInUserCart(): userId=" + userId);

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
     * Call REST API POST request
     * @param urlStr
     * @return
     */
    public static String httpPost(String urlStr) throws MalformedURLException {
        URL url = new URL(urlStr);

        return "";
    }

    /**
     * Call REST API GET request
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
