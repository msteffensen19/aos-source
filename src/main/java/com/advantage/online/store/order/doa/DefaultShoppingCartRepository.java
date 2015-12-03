package com.advantage.online.store.order.doa;

import com.advantage.online.store.dao.AbstractRepository;
import com.advantage.online.store.order.model.ShoppingCart;

import java.util.Collection;
import java.util.List;

/**
 * @author Binyamin Regev on 03/12/2015.
 */
public class DefaultShoppingCartRepository extends AbstractRepository implements ShoppingCartRepository{
    @Override
    public ShoppingCart createShoppingCart() {
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
