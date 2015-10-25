package com.advantage.online.store.dao;

import com.advantage.online.store.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by kubany on 10/13/2015.
 */
@Repository
public class ProductRepositoryImpl extends AbstractRepository {

    private static final int MAX_NUM_OF_PRODUCTS = 100;

    public void save(Product p) {
        entityManager.merge(p);
    }


    public void delete(Product p) {
    }


    public List<Product> getAllProducts() {
        log.info("getAllProducts");
        List<Product> products = entityManager.createNamedQuery(Product.GET_ALL, Product.class)
                .setMaxResults(MAX_NUM_OF_PRODUCTS)
                .getResultList();

        return products.isEmpty() ? null : products;
    }
}
