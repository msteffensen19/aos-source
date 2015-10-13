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
public interface ProductRepository {

    public void save(Product p);
    public void delete(Product p);
    List<Product> getAllProducts();
    boolean insertProduct(Product p);
    boolean updateProduct(Product p);
    boolean deleteProduct(Product p);
}
