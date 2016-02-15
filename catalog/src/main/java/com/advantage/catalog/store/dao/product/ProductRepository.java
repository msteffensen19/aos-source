package com.advantage.catalog.store.dao.product;

import java.util.Collection;
import java.util.List;

import com.advantage.catalog.store.model.category.Category;
import com.advantage.catalog.store.dao.DefaultCRUDOperations;
import com.advantage.catalog.store.model.product.Product;
import org.springframework.transaction.annotation.Transactional;

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
    Product create(String name, String description, double price, String imgUrl, Category category, String productStatus);

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
     * Delete a specific color from a product's {@link Set} of colors attributes.
     * @param product {@link Product} from which to delete the color.
     * @param hexColor RGB hexadecimal value of the color to delete.
     * @return 1 when successful, otherwise 0.
     */
    int deleteColorFromProduct(final Long productId, final String hexColor);

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