package com.advantage.online.store.services;

import java.util.List;

import com.advantage.online.store.model.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.advantage.online.store.dao.product.ProductRepository;
import com.advantage.util.ArgumentValidationHelper;

/**
 * Created by kubany on 10/13/2015.
 */
@Service
public class ProductService {

    @Autowired
    public ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {

        return productRepository.getAll();
    }

    @Transactional(readOnly = true)
    public List<Product> getCategoryProducts(final Long categoryId) {

        ArgumentValidationHelper.validateArgumentIsNotNull(categoryId, "category id");
        return productRepository.getCategoryProducts(categoryId);
    }
}
