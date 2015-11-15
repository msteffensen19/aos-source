package com.advantage.online.store.dao;

import java.util.Collection;
import java.util.List;

import com.advantage.online.store.model.category.Category;
import com.advantage.online.store.model.product.Product;

/**
 * Created by kubany on 10/13/2015.
 */
public interface ProductRepository {

	Product createProduct(String name, String description, int price, Category category);
	int deleteProduct(Product product);
	int deleteProductsByIds(Collection<Long> productIds);
	int deleteProducts(Collection<Product> products);
	List<Product> getAllProducts();
	List<Product> getCategoryProducts(Long categoryId);
	List<Product> getCategoryProducts(Category category);
}