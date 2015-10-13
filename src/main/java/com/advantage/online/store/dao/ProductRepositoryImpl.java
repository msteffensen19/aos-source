package com.advantage.online.store.dao;

import com.advantage.online.store.model.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by kubany on 10/13/2015.
 */
@Repository
public class ProductRepositoryImpl{

    private static final int MAX_NUM_OF_PRODUCTS = 100;
    @PersistenceContext
    private EntityManager em;


    public void save(Product p) {
        em.merge(p);
    }


    public void delete(Product p) {
    }


    public List<Product> getAllProducts() {
        List<Product> products = em.createNamedQuery(Product.GET_ALL, Product.class)
                .setMaxResults(MAX_NUM_OF_PRODUCTS)
                .getResultList();

        return products.isEmpty() ? null : products;
    }
}
