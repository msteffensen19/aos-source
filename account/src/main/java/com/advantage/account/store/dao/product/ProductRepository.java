package com.advantage.account.store.dao.product;

import com.advantage.account.store.dao.DefaultCRUDOperations;
import com.advantage.account.store.model.category.Category;
import com.advantage.account.store.model.product.Product;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface ProductRepository extends DefaultCRUDOperations<Product> {
    /**
     * Create Product entity
     *
     * @param name        {@link String} product name
     * @param description {@link String} product description
     * @param price       {@link Integer} product price
     * @param category    {@link Category} category which be related with product
     * @return entity reference
     */
    @Transactional
    Product create(String name, String description, double price, String imgUrl, Category category);

    /**
     * Get product categories by categoryId
     *
     * @param categoryId {@link Long} Category id
     * @return Product collection
     */
    List<Product> getCategoryProducts(Long categoryId);

    /**
     * Get product categories by Category
     *
     * @param category {@link Category} Category of a product
     * @return Product collection
     */
    List<Product> getCategoryProducts(Category category);

    /**
     * Delete collection of a product
     *
     * @param products {@link List<Product>} product collection
     * @return the number of entities deleted
     */
    int delete(final Collection<Product> products);

    /**
     * Search products by name pattern
     *
     * @param pattern  name pattern
     * @param quantity quantity of products
     * @return {@link List} collection of Products
     */
    List<Product> filterByName(String pattern, int quantity);

    /**
     * Search products by name pattern
     *
     * @param pattern name pattern
     * @return {@link List} collection of Products
     */
    List<Product> filterByName(String pattern);
}