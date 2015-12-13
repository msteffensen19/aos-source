package com.advantage.online.store.order.doa;

import com.advantage.online.store.dao.AbstractRepository;
import com.advantage.online.store.dao.product.DefaultProductRepository;
import com.advantage.online.store.dao.product.ProductRepository;
import com.advantage.online.store.model.product.Product;
import com.advantage.online.store.order.dto.ShoppingCartResponseStatus;
import com.advantage.online.store.order.model.ShoppingCart;
import com.advantage.online.store.order.model.ShoppingCartPK;
import com.advantage.online.store.user.dao.AppUserRepository;
import com.advantage.online.store.user.dao.DefaultAppUserRepository;
import com.advantage.online.store.user.model.AppUser;
import com.advantage.util.ArgumentValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

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
        ArgumentValidationHelper.validateArgumentIsNotNull(color, "color decimal RGB value");
        ArgumentValidationHelper.validateNumberArgumentIsPositive(quantity, "quantity");

        ShoppingCart shoppingCart = null;

        //  Check if there is this ShoppingCart already exists
        if (isProductExistsInShoppingCart(userId, productId, color)) {
            //  Existing ShoppingCart
            this.failureMessage = ShoppingCart.MESSAGE_IDENTICAL_PRODUCT_AND_COLOR_ALREADY_EXISTS_IN_SHOPPING_CART;

            responseStatus = new ShoppingCartResponseStatus(false,
                                                            ShoppingCart.MESSAGE_IDENTICAL_PRODUCT_AND_COLOR_ALREADY_EXISTS_IN_SHOPPING_CART,
                                                            -1);

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
        ArgumentValidationHelper.validateArgumentIsNotNull(color, "color decimal RGB value");
        ArgumentValidationHelper.validateNumberArgumentIsPositive(quantity, "quantity");

        ShoppingCart shoppingCart = getShoppingCartByPrimaryKey(userId, productId, color);
        
        if (shoppingCart != null) {
            shoppingCart.setQuantity(shoppingCart.getQuantity() + quantity);
            entityManager.persist(shoppingCart);

            //  Existing product in shopping cart updated successfully.
            this.failureMessage = "";

            responseStatus.setSuccess(true);
            responseStatus.setReason(ShoppingCart.MESSAGE_EXISTING_PRODUCT_UPDATED_SUCCESSFULLY);
            responseStatus.setId(shoppingCart.getProductId());
        }
        else {
            //  Product not found in shopping cart
            this.failureMessage = ShoppingCart.MESSAGE_PRODUCT_WITH_COLOR_NOT_FOUND_IN_SHOPPING_CART;

            responseStatus.setSuccess(false);
            responseStatus.setReason(ShoppingCart.MESSAGE_PRODUCT_WITH_COLOR_NOT_FOUND_IN_SHOPPING_CART);
            responseStatus.setId(-1);
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
    @Override
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
    public ShoppingCartResponseStatus Update(long userId, Long productId, int color, int quantity) {
        updateShoppingCart(userId, productId, color, quantity);
        return responseStatus;
    }

    @Override
    public ShoppingCartResponseStatus removeProductFromUserCart(long userId, Long productId, int color) {
        ShoppingCartPK shoppingCartPk = new ShoppingCartPK(userId, productId, color);

        ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, shoppingCartPk);

        if (shoppingCart != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(shoppingCart);
            entityManager.getTransaction().commit();

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
    public ShoppingCartResponseStatus deleteShoppingCartsByUserId(long userId) {

        ArgumentValidationHelper.validateLongArgumentIsPositive(userId, "user id");

        if (appUserRepository == null) {
            System.out.println("deleteShoppingCartsByUserId -> AppUserRepository is null");
            appUserRepository = new DefaultAppUserRepository();
            System.out.println("deleteShoppingCartsByUserId -> AppUserRepository = " + appUserRepository);
        }

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

    public boolean isProductExistsInShoppingCart(long userId, Long productId, int color) {
        ShoppingCartPK shoppingCartPk = new ShoppingCartPK(userId, productId, color);

        ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, shoppingCartPk);

        return (shoppingCart != null);

    }

    /**
     * Get a specific {@link ShoppingCart} by values of primary-key columns.
     * @param userId
     * @param productId
     * @param color
     * @return Specific {@link ShoppingCart} uniquely identified by primary key fields values.
     */
    public ShoppingCart getShoppingCartByPrimaryKey(long userId, Long productId, int color) {
        ShoppingCartPK shoppingCartPk = new ShoppingCartPK(userId, productId, color);

        ShoppingCart shoppingCart = entityManager.find(ShoppingCart.class, shoppingCartPk);

        if (shoppingCart == null) {
            responseStatus = new ShoppingCartResponseStatus(false,
                                                            ShoppingCart.MESSAGE_PRODUCT_WITH_COLOR_NOT_FOUND_IN_SHOPPING_CART,
                                                            -1);
            return null;
        }

        System.out.println("userId=" + shoppingCart.getUserId() +
                " productId=" + shoppingCart.getProductId() +
                " color=" + shoppingCart.getColor() +
                " quantity=" + shoppingCart.getQuantity());


        Product product = productRepository.get(productId);

        /*
        String productManagedImageId = product.getManagedImageId();
        double productPricePerItem = product.getPrice();
        String productName = product.getName();
        String productDescription = product.getDescription();
        */

        /*
        List<ShoppingCart> shoppingCarts = entityManager.createNamedQuery(ShoppingCart.QUERY_GET_CART_BY_PK_COLUMNS, ShoppingCart.class)
                .setParameter(ShoppingCart.PARAM_USER_ID, userId)
                .setParameter(ShoppingCart.PARAM_PRODUCT_ID, productId)
                .setParameter(ShoppingCart.PARAM_COLOR_ID, colorId)
                .setMaxResults(ShoppingCart.MAX_NUM_OF_SHOPPING_CART_PRODUCTS)
                .getResultList();

        return ((shoppingCarts == null) || (shoppingCarts.isEmpty())) ? null : shoppingCarts.get(0);
        */

        return shoppingCart;
    }

    @Override
    public ShoppingCartResponseStatus replace(long userId, Collection<ShoppingCart> cartProducts) {
        return null;
    }
}
