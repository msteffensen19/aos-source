package com.advantage.online.store.dao.product;

import java.util.Collection;
import java.util.List;

import com.advantage.online.store.dao.DefaultCRUDOperations;
import com.advantage.online.store.model.category.Category;
import com.advantage.online.store.model.product.Product;

public interface ProductRepository extends DefaultCRUDOperations<Product> {
	/**
	 * Create Product entity
	 * @param name
	 * @param description
	 * @param price
	 * @param category
	 * @return entity reference
	 */
	Product createProduct(String name, String description, int price, Category category);

	/**
	 * Delete entities by ids
	 * @param productIds Id's collection
	 * @return he number of entities deleted
	 */
	int deleteProductsByIds(Collection<Long> productIds);

	/**
	 * Get product categories by categoryId
	 * @param categoryId Category id
	 * @return
	 */
	List<Product> getCategoryProducts(Long categoryId);

	/**
	 * Get product categories by Category
	 * @param category Category of a product
	 * @return Product collection
	 */
	List<Product> getCategoryProducts(Category category);

	int deleteProducts(final Collection<Product> products);
}