package com.advantage.online.store.order.doa;

import com.advantage.online.store.Constants;
import com.advantage.online.store.dao.AbstractRepository;
import com.advantage.online.store.dao.product.ProductRepository;
import com.advantage.online.store.dto.CategoryDto;
import com.advantage.online.store.dto.ProductDto;
import com.advantage.online.store.model.category.Category;
import com.advantage.online.store.model.product.ColorAttribute;
import com.advantage.online.store.model.product.Product;
import com.advantage.online.store.order.dto.ShoppingCartDto;
import com.advantage.online.store.order.dto.ShoppingCartResponseDto;
import com.advantage.online.store.order.dto.ShoppingCartResponseStatus;
import com.advantage.online.store.order.model.ShoppingCart;
import com.advantage.online.store.order.model.ShoppingCartPK;
import com.advantage.online.store.user.dao.AppUserRepository;
import com.advantage.online.store.user.model.AppUser;
import com.advantage.util.ArgumentValidationHelper;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
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
    private static final String SERVER_ACCOUNT_URI = "http://localhost:8080/account";
    private static final String SERVER_CATALOG_URI = "http://localhost:8080/catalog";
    private static final String SERVER_ORDER_URI = "http://localhost:8080/order";
    private static final String SERVER_SERVICE_URI = "http://localhost:8080/service";

    private static final String CATALOG_SERVICE_URI = Constants.URI_API + "/v1";
    private static final String CATALOG_GET_PRODUCT_BY_ID_URI = "/products/{product_id}";
    //  FINALs for REST API calls - END

    @Autowired
    private ProductRepository productRepository;

    private ShoppingCartResponseStatus responseStatus;
    private String failureMessage;

    @Autowired
    private AppUserRepository appUserRepository;

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
     * @param userId identifies specific {@link AppUser}.
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
     * @param userId Id of {@link AppUser} to whom the {@link ShoppingCart} will be created.
     * @param cartProducts {@link Collection} of unique products in the {@link ShoppingCart}.
     * @return {@link ShoppingCartResponseStatus} class, properties values: <b>success</b> = {@code true} when successful or {@code false} if failed. <b>reason</b>=success or failure message text. <b>productId</b>=-1 because there are multiple products in the list.
     */
    public ShoppingCartResponseStatus createShoppingCart(long userId, Collection<ShoppingCart> cartProducts) {

        if ((cartProducts == null) || (cartProducts.size() == 0)) {
            return new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_NO_PRODUCTS_TO_UPDATE_IN_SHOPPING_CART, -1);
        }

        System.out.println("createShoppingCart.userId=" + userId);

        if (appUserRepository.get(userId) == null) {
            return new ShoppingCartResponseStatus(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
        }

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
     * @param userId identifies specific {@link AppUser}.
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
     * @param userId identifies specific {@link AppUser}.
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

        //  Verify that exists a User in the application with this userId
        if (appUserRepository.get(userId) == null) {
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

    @Override
    public ShoppingCartResponseDto getUserShoppingCart(long userId) {
        List<ShoppingCart> shoppingCarts = getShoppingCartsByUserId(userId);


        ShoppingCartResponseDto userCart = new ShoppingCartResponseDto(userId);

        /* Scan user shopping cart and add all product to userCart response object  */
        for (ShoppingCart cart: shoppingCarts) {

            /*  Build REQUEST URI */
            String stringURL = SERVER_CATALOG_URI + CATALOG_SERVICE_URI +
                    CATALOG_GET_PRODUCT_BY_ID_URI.replace("{product_id}", String.valueOf(cart.getProductId()));

            // stringURL = "http:/localhost:8080/catalog/api/v1/products/String.valueOf(productId)"
            System.out.println("stringURL=\"" + stringURL + "\"");

            try {
                String stringResponse = httpGet(stringURL);
                System.out.println("stringResponse = \"" + stringResponse + "\"");

                final ProductDto dto = getProductDtofromJsonObjectString(stringResponse);

                /*  Add a product to user shopping cart response class  */
                userCart.addCartProduct(dto.getProductId(),
                        dto.getProductName(),
                        dto.getPrice(),
                        cart.getQuantity(),
                        dto.getImageUrl(),
                        dto.getColors().get(0).getCode(),
                        dto.getColors().get(0).getColor(),
                        dto.getColors().get(0).getInStock());

                System.out.println("Received Product information: ");
                System.out.println("   product id = " + dto.getProductId());
                System.out.println("   product name = " + dto.getProductName());
                System.out.println("   price per item = " + dto.getPrice());
                System.out.println("   managedImageId = \"" + dto.getImageUrl() + "\"");
                System.out.println("   ColorAttrubute.Code (hex) = \"" + dto.getColors().get(0).getCode() + "\"");
                System.out.println("   ColorAttrubute.Color (name) = \"" + dto.getColors().get(0).getColor() + "\"");
                System.out.println("   ColorAttrubute.inStock = " + dto.getColors().get(0).getInStock());

            } catch (IOException e) {
                System.out.println("Calling httpGet(\"" + stringURL + "\") throws IOException: ");
                e.printStackTrace();
            }
        }

        //return ((shoppingCarts == null) || (shoppingCarts.isEmpty())) ? null : shoppingCarts;
        return userCart;
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
     *  <p>
     *      <ul>Verify {@code userId}</ul> refer to a registered user via <b>REST API</b>. <br/>
     *      <ul>Verify {@code productId}</ul> refer to a registered user via <b>REST API</b>. <br/>
     *  </p>
     *  <p>
     *     Remember that if the request URL of <b>REST API {@code GET}</b> {@link RequestMethod} includes
     *     parameters, they must be properly encoded (e.g., a space is &quat;%20&quat;, etc.). The class
     *     {@link URLEncoder} can be used to perform this encoding.
     *  </p>
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

            String stringURL = SERVER_CATALOG_URI +
                    CATALOG_SERVICE_URI +
                    CATALOG_GET_PRODUCT_BY_ID_URI.replace("{product_id}", String.valueOf(productId));

            // stringURL = "http:/localhost:8080/catalog/api/v1/products/String.valueOf(productId)"
            System.out.println("stringURL=\"" + stringURL + "\"");

            try {
                String stringResponse = httpGet(stringURL);
                System.out.println("stringResponse = \"" + stringResponse + "\"");

                ProductDto dto = getProductDtofromJsonObjectString(stringResponse);

                List<ColorAttribute> colors = dto.getColors();
                double pricePerItem = dto.getPrice();
                String name = dto.getProductName();
                String description = dto.getDescription();
                String managedImageId = dto.getImageUrl();

                System.out.println("Received Product information: ");
                System.out.println("   product id = " + dto.getProductId());
                System.out.println("   product name = " + name);
                System.out.println("   product Description = " + description);
                System.out.println("   price per item = " + pricePerItem);
                System.out.println("   managedImageId = \"" + managedImageId + "\"");
                System.out.println("   ColorAttrubute.HexColor = \"" + colors.get(0).getCode() + "\"");
                System.out.println("   ColorAttrubute.Color = \"" + colors.get(0).getColor() + "\"");
                System.out.println("   ColorAttrubute.inStock = " + colors.get(0).getInStock());

                /*
                {
                    "productId":1,
                    "categoryId":1,
                    "productName":"HP Pavilion 15t Touch Laptop",
                    "price":519.99,
                    "description":"Redesigned with you in mind, the HP Pavilion keeps getting better. Our best-selling notebook is now more powerful so you can watch more, play more, and store more, all in style.",
                    "imageUrl":"1241",
                    "attributes":[
                                    {
                                    "attributeName":"PROCESSOR",
                                    "attributeValue":"Intel(R) Core(TM) i5-6200U Dual CoreProcessor"
                                    },
                                    {
                                    "attributeName":"CUSTOMIZATION",
                                    "attributeValue":"Gaming"},
                                    {
                                    "attributeName":"OPERATING SYSTEM",
                                    "attributeValue":"Windows 10"
                                    },
                                    {
                                    "attributeName":"DISPLAY",
                                    "attributeValue":"15.6-inch diagonal HD WLED-backlit Display (1366x768) Touchscreen"
                                    },
                                    {
                                    "attributeName":"MEMORY","attributeValue":"16GB DDR3 - 2 DIMM"
                                    }
                                 ],
                    "colors":   [
                                    {
                                    "inStock":10,
                                    "color":"SILVER",
                                    "code":"C0C0C0"
                                    }
                                ],
                    "images":   [
                                    "1241"
                                ]
                }
                 */
            } catch (IOException e) {
                System.out.println("Calling httpGet(\"" + stringURL + "\") throws IOException: ");
                System.out.println("Exception.getMessage() = " + e.getMessage());
                System.out.println("Exception.getLocalizedMessage() = " + e.getLocalizedMessage());
                System.out.println("Exception.toString() = " + e.toString());

                e.printStackTrace();

            }

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

    public static String httpGet(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        if (conn.getResponseCode() != 200) {
            System.out.println("httpGet -> conn.getResponseMessage()=" + conn.getResponseMessage());
            System.out.println("httpGet -> conn.toString()=" + conn.toString());
            System.out.println("httpGet -> conn.getErrorStream().toString()=" + conn.getErrorStream().toString());

            throw new IOException(conn.getResponseMessage());
        }

        // Buffer the result into a string
        InputStreamReader inputStream = new InputStreamReader(conn.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStream);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        bufferedReader.close();

        conn.disconnect();
        return sb.toString();
    }

    private static ProductDto getProductDtofromJsonObjectString(String jsonObjectString) throws IOException {

        //JsonReader jsonReader = Json.createReader(new StringReader(jsonObjectString));
        //JsonObject object = jsonReader.readObject();
        //jsonReader.close();

        //ClassPathResource filePath = new ClassPathResource("categoryProducts_4.json");
        //File json = filePath.getFile();

        ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ProductDto dto = objectMapper.readValue(jsonObjectString, ProductDto.class);

        return dto;
    }
}
