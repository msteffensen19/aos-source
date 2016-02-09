package com.advantage.order.store.order.services;

//import com.advantage.order.store.order.dto.OrderPurchaseRequest;
import com.advantage.common.Constants;
import com.advantage.common.Url_resources;
import com.advantage.common.dto.ColorAttributeDto;
import com.advantage.common.dto.ProductDto;
import com.advantage.order.store.order.dao.ShoppingCartRepository;
import com.advantage.order.store.order.dto.*;
import com.advantage.order.store.order.model.ShoppingCart;
import com.advantage.root.util.ArgumentValidationHelper;
import com.advantage.root.util.ValidationHelper;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * @author Binyamin Regev on 03/12/2015.
 */
@Service
public class ShoppingCartService {

    private static final String CATALOG_PRODUCT = "products/";

    @Autowired
    @Qualifier("shoppingCartRepository")
    public ShoppingCartRepository shoppingCartRepository;

    /**
     * @param userId
     * @return
     */
    public ShoppingCartResponseDto getShoppingCartsByUserId(long userId) {
        return getUserShoppingCart(userId);
    }

    /**
     * @param userId
     * @param productId
     * @param stringColor
     * @param quantity
     * @return
     */
    @Transactional
    public ShoppingCartResponse addProductToCart(long userId, Long productId, String stringColor, int quantity) {

        int color = ShoppingCart.convertHexColorToInt(stringColor);

        //  Validate Arguments
        ArgumentValidationHelper.validateLongArgumentIsPositive(userId, "user id");
        ArgumentValidationHelper.validateArgumentIsNotNull(productId, "product id");
        ArgumentValidationHelper.validateNumberArgumentIsPositiveOrZero(color, "color decimal RGB value");
        ArgumentValidationHelper.validateNumberArgumentIsPositive(quantity, "quantity");

        /*
        //  Use API to verify userId belongs to a registered user by calling "Account Service"
        if (!isRegisteredUserExists(userId)) {
            shoppingCartResponse = new ShoppingCartResponse(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
            return null;
        }
         */
        ShoppingCartResponse shoppingCartResponse = null;

        if (isProductExists(productId, ShoppingCart.convertIntColorToHex(color))) {

            ShoppingCart shoppingCart = shoppingCartRepository.find(userId, productId, color);

            //  Check if there is this ShoppingCart already exists
            if (shoppingCart != null) {

                int totalQuantity = shoppingCart.getQuantity() + quantity;

                shoppingCartRepository.update(userId, productId, color, totalQuantity);

                shoppingCartResponse = new ShoppingCartResponse(true,
                        ShoppingCart.MESSAGE_QUANTITY_OF_PRODUCT_IN_SHOPPING_CART_WAS_UPDATED_SUCCESSFULLY,
                        shoppingCart.getProductId());

            } else {
                //  New ShoppingCart
                shoppingCart = new ShoppingCart(userId, new Date().getTime(), productId, color, quantity);
                shoppingCartRepository.add(shoppingCart);
                shoppingCartResponse = new ShoppingCartResponse(true,
                        ShoppingCart.MESSAGE_NEW_PRODUCT_UPDATED_SUCCESSFULLY,
                        shoppingCart.getProductId());
            }
        } else {
            shoppingCartResponse = new ShoppingCartResponse(false,
                    ShoppingCart.MESSAGE_PRODUCT_NOT_FOUND_IN_CATALOG,
                    productId);
        }

        return shoppingCartResponse;
    }

    /*
        productId   color       Quantity
        1           YELLOW      5
        1           BLUE        4

        Action: { productId=1, color=BLUE, newColor=YELLOW, Quantity=1 }

     */
    public ShoppingCartResponse updateProductInCart(long userId, Long productId, String hexColor, String hexColorNew, int quantity) {

        if (((! ValidationHelper.isValidColorHexNumber(hexColor)) ||
                (! ValidationHelper.isValidColorHexNumber(hexColorNew)) ||
                (hexColor.equalsIgnoreCase(hexColorNew))) && (quantity < 0)) {
            return new ShoppingCartResponse(false,
                                            "Error: Bad request, Nothing to do",
                                            productId);
        }

        ShoppingCartResponse shoppingCartResponse = null;

        int color = ShoppingCart.convertHexColorToInt(hexColor);

        /*  Update QUANTITY of product in user cart */
        if (quantity > 0) {
            /* Update product quantity in cart  */
            System.out.println("ShoppingCartService.update(" + userId + ", " + productId + ", " + color + ", " + quantity + ")");
            shoppingCartResponse = shoppingCartRepository.update(userId, productId, color, quantity);
        }

        /*  Update COLOR CHANGE of product in user cart */
        if ((ValidationHelper.isValidColorHexNumber(hexColor)) &&
                (ValidationHelper.isValidColorHexNumber(hexColorNew)) &&
                (! hexColor.equalsIgnoreCase(hexColorNew)))
        {

            /*  Try to find a product with the same productId and new color in the user's cart  */
            int newColor = ShoppingCart.convertHexColorToInt(hexColorNew);
            ShoppingCart shoppingCart = shoppingCartRepository.find(userId, productId, newColor);

            if (shoppingCart != null) {
                /*
                    There's already a product with the new color:
                    1. Delete product with previous color, but save its quantity.
                    2. Add quantity of product with previous color to product with new color.
                 */
                int result = shoppingCartRepository.removeProductFromUserCart(userId, productId, color);
                shoppingCartResponse = shoppingCartRepository.getShoppingCartResponse();
                if (shoppingCartResponse.isSuccess()) {
                    int totalQuantity = shoppingCart.getQuantity() + quantity;
                    shoppingCartRepository.update(userId, productId, newColor, totalQuantity);
                } else {
                    shoppingCartResponse.setReason("Error: failed to change product's color");
                }
            }
            else {
                /*
                    Unlikely to occur, but need to cover it:
                    Add a new product with the new color to user cart
                 */
                shoppingCartRepository.removeProductFromUserCart(userId, productId, color);
                shoppingCartRepository.add(new ShoppingCart(userId, productId, newColor, quantity));
            }
        }
        else {
            //  Nothing to update - Bad Request
            shoppingCartResponse.setSuccess(false);
            shoppingCartResponse.setReason("Error: Bad request. Product's color was not changed");
            shoppingCartResponse.setId(productId);
        }

        return shoppingCartResponse;
    }

    public ShoppingCartResponse replaceUserCart(long userId, List<ShoppingCartDto> shoppingCarts) {
        return shoppingCartRepository.replace(userId, shoppingCarts);
    }

    /**
     * @param userId
     * @param productId
     * @param stringColor
     * @return
     */
    @Transactional
    public ShoppingCartResponse removeProductFromUserCart(long userId, Long productId, String stringColor) {
        int color = ShoppingCart.convertHexColorToInt(stringColor);
        int result = shoppingCartRepository.removeProductFromUserCart(userId, productId, color);
        return shoppingCartRepository.getShoppingCartResponse();
    }

    /**
     * @param userId
     * @return
     */
    @Transactional
    public ShoppingCartResponse clearUserCart(long userId) {
        return shoppingCartRepository.clearUserCart(userId);
    }

    /**
     * Verify the quantity of each product in user cart exists in stock. If quantity
     * in user cart is greater than the quantity in stock than add the product with
     * the quantity in stock to {@link ShoppingCartResponseDto} {@code Response} JSON. <br/>
     * @param userId               Unique user identity.
     * @param shoppingCartProducts {@link List} of {@link ShoppingCartDto} products in user cart to verify quantities.
     * @return {@code null} when all quantities of the products in the user cart <b>are equal or Less than</b> the quantities in
     * stock. If the quantity of any cart product <b>is greater than</b> the quantity in stock then the product will be added to
     * the list of products in the cart with the <ul>quantity in stock</ul>.
     */
    @Transactional
    public ShoppingCartResponseDto verifyProductsQuantitiesInUserCart(long userId, List<ShoppingCartDto> shoppingCartProducts) {
        System.out.println("ShoppingCartService -> verifyProductsQuantitiesInUserCart(): userId=" + userId);

        System.out.println("DefaultShoppingCartRepository -> verifyProductsQuantitiesInUserCart(): userId=" + userId);

        /*
        //  Verify userId belongs to a registered user by calling "Account Service"
        //  REST API GET REQUEST using URI
        if (!isRegisteredUserExists(userId)) {
            shoppingCartResponse = new ShoppingCartResponse(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
            return null;
        }
         */
        ShoppingCartResponseDto responseDto = new ShoppingCartResponseDto(userId);

        for (ShoppingCartDto cartProduct : shoppingCartProducts) {

            ShoppingCartResponseDto.CartProduct cartProductDto = getCartProductDetails(cartProduct.getProductId(), cartProduct.getHexColor());

            if (cartProduct.getQuantity() > cartProductDto.getColor().getInStock()) {

                ShoppingCart shoppingCart = shoppingCartRepository.find(userId,
                        cartProduct.getProductId(),
                        ShoppingCart.convertHexColorToInt(cartProductDto.getColor().getCode()));

                if (shoppingCart != null) {
                    //  Update quantity of cart product to product's quantity in stock
                    shoppingCartRepository.update(userId,
                            cartProduct.getProductId(),
                            ShoppingCart.convertHexColorToInt(cartProductDto.getColor().getCode()),
                            cartProductDto.getColor().getInStock());
                } else {
                    //  Unlikely to occur, since we already got the product details and compare its
                    //  quantity in stock to the same product in cart. But, still need to be covered.
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

        //return shoppingCartRepository.verifyProductsQuantitiesInUserCart(userId, shoppingCartProducts);
        return responseDto;

    }

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

            if (stringResponse.equalsIgnoreCase(Constants.NOT_FOUND)) {
                //  Product not found (409)
                dto = new ProductDto(productId, -1L, Constants.NOT_FOUND, -999999.99, Constants.NOT_FOUND, Constants.NOT_FOUND, null, null, null);
            } else {
                dto = getProductDtofromJsonObjectString(stringResponse);
            }
        } catch (IOException e) {
            System.out.println("Calling httpGet(\"" + productByIdUrl.toString() + "\") throws IOException: ");
            e.printStackTrace();
        }

        return dto;
    }

    public ShoppingCartResponseDto getUserShoppingCart(long userId) {
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.getShoppingCartProductsByUserId(userId);

        ShoppingCartResponseDto shoppingCartResponse = new ShoppingCartResponseDto(userId);
        if ((shoppingCarts != null) && (shoppingCarts.size() > 0)) {
            shoppingCartResponse = getCartProductsDetails(userId, shoppingCarts);
        }

        return shoppingCartResponse;
    }

    public ShoppingCartResponseDto getCartProductsDetails(long userId, List<ShoppingCart> shoppingCarts) {

        /*
        //  Verify userId belongs to a registered user by calling "Account Service"
        //  REST API GET REQUEST using URI
        if (!isRegisteredUserExists(userId)) {
            shoppingCartResponse = new ShoppingCartResponse(false, ShoppingCart.MESSAGE_INVALID_USER_ID, -1);
            return null; //  userId is not a registered user
        }
         */
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

                    if (cartProduct.getProductName().equalsIgnoreCase(Constants.NOT_FOUND)) {
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


    /**
     * Get a single {@code Product} details using <b>REST API</b> {@code GET} request.
     * @param productId Idetity of the product to get details.
     * @return {@link ProductDto} containing the JSON with requsted product details.
     */
    public ShoppingCartResponseDto.CartProduct getCartProductDetails(Long productId, String hexColor) {

        ProductDto dto = this.getProductDtoDetails(productId);

        ShoppingCartResponseDto.CartProduct cartProduct = null;

        if (!dto.getProductName().equalsIgnoreCase(Constants.NOT_FOUND)) {

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

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ProductDto dto = objectMapper.readValue(jsonObjectString, ProductDto.class);

        return dto;
    }

    public ShoppingCartResponseDto.CartProduct setNotFoundCartProduct(Long productId) {
        return new ShoppingCartResponseDto()
                .createCartProduct(productId, Constants.NOT_FOUND, -999999.99, 0, Constants.NOT_FOUND, false);
    }

    public boolean isProductExists(Long productId, String hexColor) {
        boolean result = false;

        ProductDto productDetails = getProductDtoDetails(productId);
        if (!productDetails.getProductName().equalsIgnoreCase(Constants.NOT_FOUND)) {
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

}

