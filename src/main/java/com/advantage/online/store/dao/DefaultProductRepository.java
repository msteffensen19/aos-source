package com.advantage.online.store.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

import com.advantage.online.store.model.product.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.advantage.online.store.model.category.Category;
import com.advantage.util.ArgumentValidationHelper;
import com.advantage.util.JPAQueryHelper;

/**
 * Created by kubany on 10/13/2015.
 */
@Component
@Qualifier("productRepository")
@Repository
public class DefaultProductRepository extends AbstractRepository implements ProductRepository {

    private static final int MAX_NUM_OF_PRODUCTS = 100;

    @Override
    public Product createProduct(final String name, final String description, final int price,
     final Category category) {

    	final Product product = new Product(name, description, price, category);
    	entityManager.persist(product);
    	return product;
    }

    @Override
	public int deleteProduct(final Product product) {

		ArgumentValidationHelper.validateArgumentIsNotNull(product, "product");
		final Long productId = product.getId();
		final String hql = JPAQueryHelper.getDeleteByPkFieldQuery(Product.class,
				                                                  Product.FIELD_ID,
				                                                  productId);
		final Query query = entityManager.createQuery(hql);
		return query.executeUpdate();
	}

    @Override
    public int deleteProductsByIds(final Collection<Long> productIds) {

    	ArgumentValidationHelper.validateCollectionArgumentIsNotNullAndNotEmpty(productIds,
    			                                                                "product ids");
    	final String hql = JPAQueryHelper.getDeleteByPkFieldsQuery(Product.class,
    			                                                   Product.FIELD_ID,
    			                                                   Product.PARAM_ID);
    	final Query query = entityManager.createQuery(hql);
    	query.setParameter(Product.PARAM_ID, productIds);
    	return query.executeUpdate();
    }

    @Override
    public int deleteProducts(final Collection<Product> products) {

    	ArgumentValidationHelper.validateCollectionArgumentIsNotNullAndNotEmpty(products,
                                                                                "products");
    	final int productsCount = products.size();
    	final Collection<Long> productIds = new ArrayList<Long>(productsCount);
    	
    	for (final Product product : products) {

    		final long productId = product.getId();
    		productIds.add(productId);
    	}

    	return deleteProductsByIds(productIds);
    }

    @Override
    public List<Product> getAllProducts() {

        List<Product> products = entityManager.createNamedQuery(Product.QUERY_GET_ALL, Product.class)
                .setMaxResults(MAX_NUM_OF_PRODUCTS)
                .getResultList();

        return products.isEmpty() ? null : products;
    }

	@Override
	@SuppressWarnings("unchecked")
    public List<Product> getCategoryProducts(final Long categoryId) {

        ArgumentValidationHelper.validateArgumentIsNotNull(categoryId, "category id");
        final String hql = getSelectProductsByCategoryIdHql();
        final Query query = entityManager.createQuery(hql);
        query.setParameter(Product.PARAM_CATEGORY_ID, categoryId);
        return query.getResultList();
    }

    @Override
    public List<Product> getCategoryProducts(final Category category) {

        ArgumentValidationHelper.validateArgumentIsNotNull(category, "category");
        final Long categoryId = category.getCategoryId();
        return getCategoryProducts(categoryId);
    }

    private String getSelectProductsByCategoryIdHql() {

		final StringBuilder hql = new StringBuilder("FROM ");
        hql.append(Product.class.getName());
        hql.append(" P WHERE P.");
        hql.append(Product.FIELD_CATEGORY_ID);
        hql.append(" = :");
        hql.append(Product.PARAM_CATEGORY_ID);
		return hql.toString();
	}
}