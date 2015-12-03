package com.advantage.online.store.order.doa;

import com.advantage.online.store.dao.AbstractRepository;
import com.advantage.online.store.order.model.ShoppingCart;
import com.advantage.util.ArgumentValidationHelper;

import java.util.Collection;
import java.util.List;

/**
 * @author Binyamin Regev on 03/12/2015.
 */
public class DefaultShoppingCartRepository extends AbstractRepository implements ShoppingCartRepository{
    @Override
    public ShoppingCart createShoppingCart(final String loginName, final Long productId, final String managedImageId, final String colorName, final String colorImageUrl, double price, final int quantity, double productTotal) {

        //  Validate Numeric Arguments
        ArgumentValidationHelper.validateArgumentIsNotNull(productId, "product id");

        ArgumentValidationHelper.validateDoubleArgumentIsPositive(price, "total price");
        ArgumentValidationHelper.validateDoubleArgumentIsPositiveOrZero(quantity, "quantity");
        ArgumentValidationHelper.validateDoubleArgumentIsPositiveOrZero(productTotal, "total for product");

        //  Validate String Arguments - Mandatory columns
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(managedImageId, "managed image id");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(colorName, "color name");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(colorImageUrl, "color image URL");

        return null;
    }

    @Override
    public int deleteShoppingCart(final Long id) {
        return 0;
    }

    /**
     * Find all shopping carts of a user and send the list of ids to method {@link #deleteByIds}
     * @param loginName
     * @return
     */
    @Override
    public int deleteShoppingCartsByLogin(final String loginName) {
        return 0;
    }

    @Override
    public List<ShoppingCart> getAllShoppingCards() {
        return null;
    }

    @Override
    public List<ShoppingCart> getShoppingCardsByLogin() {
        return null;
    }

    @Override
    public ShoppingCart create(String name) {
        return null;
    }

    @Override
    public Long create(ShoppingCart entity) {
        return null;
    }

    @Override
    public int delete(ShoppingCart... entities) {
        return 0;
    }

    @Override
    public ShoppingCart delete(Long id) {
        return null;
    }

    @Override
    public int deleteByIds(Collection<Long> ids) {
        return 0;
    }

    @Override
    public List<ShoppingCart> getAll() {
        return null;
    }

    @Override
    public ShoppingCart get(Long entityId) {
        return null;
    }
}
