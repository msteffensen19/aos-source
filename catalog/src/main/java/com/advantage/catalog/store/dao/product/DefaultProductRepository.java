package com.advantage.catalog.store.dao.product;

import com.advantage.catalog.store.model.category.Category;
import com.advantage.catalog.util.ArgumentValidationHelper;
import com.advantage.catalog.store.dao.AbstractRepository;
import com.advantage.catalog.store.model.product.Product;
import com.advantage.catalog.util.JPAQueryHelper;
import com.advantage.common.enums.ProductStatusEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@Qualifier("productRepository")
@Repository
public class DefaultProductRepository extends AbstractRepository implements ProductRepository {
    private static final int MAX_NUM_OF_PRODUCTS = 100;

    /**
     * Create Product entity
     *
     * @param name            {@link String} product name
     * @param description     {@link String} product description
     * @param price           {@link Integer} product price
     * @param imgUrl
     * @param category        {@link Category} category which be related with product
     * @param productStatus @return entity reference
     */
    @Override
    public Product create(String name, String description, double price, String imgUrl, Category category, String productStatus) {
        //validate productStatus
        if(!ProductStatusEnum.contains(productStatus)) return null;
        Product product = new Product(name, description, price, category,productStatus);
        product.setManagedImageId(imgUrl);
        entityManager.persist(product);

        return product;
    }

    @Override
    public Product create(String name, String description, double price, String imgUrl, Category category) {
        Product product = new Product(name, description, price, category);
        product.setManagedImageId(imgUrl);
        entityManager.persist(product);

        return product;
    }

    @Override
    public Long create(Product product) {
        entityManager.persist(product);

        return product.getId();
    }

    @Override
    public int deleteByIds(Collection<Long> ids) {
        ArgumentValidationHelper.validateCollectionArgumentIsNotNullAndNotEmpty(ids, "product ids");
        String hql = JPAQueryHelper.getDeleteByPkFieldsQuery(Product.class, Product.FIELD_ID, Product.PARAM_ID);
        Query query = entityManager.createQuery(hql);
        query.setParameter(Product.PARAM_ID, ids);

        return query.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Product> getCategoryProducts(Long categoryId) {
        ArgumentValidationHelper.validateArgumentIsNotNull(categoryId, "category id");
        String hql = getSelectProductsByCategoryIdHql();
        Query query = entityManager.createQuery(hql);
        query.setParameter(Product.PARAM_CATEGORY_ID, categoryId);

        return query.getResultList();
    }

    @Override
    public List<Product> getCategoryProducts(Category category) {
        ArgumentValidationHelper.validateArgumentIsNotNull(category, "category");
        Long categoryId = category.getCategoryId();

        return getCategoryProducts(categoryId);
    }

    private String getSelectProductsByCategoryIdHql() {
        StringBuilder hql = new StringBuilder("FROM ");
        hql.append(Product.class.getName());
        hql.append(" P WHERE P.");
        hql.append(Product.FIELD_CATEGORY_ID);
        hql.append(" = :");
        hql.append(Product.PARAM_CATEGORY_ID);

        return hql.toString();
    }

    @Override
    public int delete(Product... entities) {
        ArgumentValidationHelper.validateArrayArgumentIsNotNullAndNotZeroLength(entities, "products");
        int productsCount = entities.length;
        Collection<Long> productIds = new ArrayList<>(productsCount);

        for (final Product product : entities) {
            productIds.add(product.getId());
        }

        return deleteByIds(productIds);
    }

    /**
     * Delete a specific product with all its colors, or just a specific color.
     * @param productId
     * @param hexColor RGB hexadecimal value of the color to delete. If {@code null} or empty then delete the product with all its colors.
     * @return 1 when successful, otherwise 0.
     */
    public int deleteColorFromProduct(final Long productId, final String hexColor) {
        ArgumentValidationHelper.validateLongArgumentIsPositive(productId, "product id");

        int result = 0;

        if (hexColor.isEmpty()) {
            Product product = this.get(productId);
            result = this.delete(product);
        } else {
            result = this.deleteColorFromProduct(productId, hexColor);
        }

        return result;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = entityManager.createNamedQuery(Product.QUERY_GET_ALL, Product.class)
                .getResultList();

        return products.isEmpty() ? null : products;
    }

    @Override
    public List<Product> filterByName(String pattern, int quantity) {
        List<Product> products = entityManager.createNamedQuery(Product.PRODUCT_FILTER_BY_NAME, Product.class)
                .setParameter("pname", "%" + pattern.toUpperCase() + "%")
                .setMaxResults(MAX_NUM_OF_PRODUCTS)
                .getResultList();

        return products.isEmpty() ? null : products;
    }

    @Override
    public List<Product> filterByName(String pattern) {
        List<Product> products = entityManager.createNamedQuery(Product.PRODUCT_FILTER_BY_NAME, Product.class)
                .setParameter("pname", "%" + pattern.toUpperCase() + "%")
                .getResultList();

        return products.isEmpty() ? null : products;
    }

    @Override
    public Product get(Long entityId) {
        ArgumentValidationHelper.validateArgumentIsNotNull(entityId, "product id");
        String hql = JPAQueryHelper.getSelectByPkFieldQuery(Product.class, Product.FIELD_ID, entityId);
        Query query = entityManager.createQuery(hql);

        List<Product> productList = query.getResultList();
        return productList.size() != 0 ? productList.get(0) : null;
    }

    @Override
    public int delete(final Collection<Product> products) {
        ArgumentValidationHelper.validateCollectionArgumentIsNotNullAndNotEmpty(products,
                "products");
        final int productsCount = products.size();
        final Collection<Long> productIds = new ArrayList<>(productsCount);

        for (final Product product : products) {

            final long productId = product.getId();
            productIds.add(productId);
        }

        return deleteByIds(productIds);
    }
}