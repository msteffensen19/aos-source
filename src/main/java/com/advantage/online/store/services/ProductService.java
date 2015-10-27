package com.advantage.online.store.services;

import com.advantage.online.store.dao.ProductRepositoryImpl;
import com.advantage.online.store.model.Product;
import com.advantage.util.ArgumentValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kubany on 10/13/2015.
 */
@Service
public class ProductService {

    @Autowired
    public ProductRepositoryImpl productRepository;

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {

        return productRepository.getAllProducts();
    }

    @Transactional(readOnly = true)
    public List<Product> getCategoryProducts(final Long categoryId) {

        ArgumentValidationHelper.validateArgumentIsNotNull(categoryId, "category id");
        return productRepository.getCategoryProducts(categoryId);
    }
}
