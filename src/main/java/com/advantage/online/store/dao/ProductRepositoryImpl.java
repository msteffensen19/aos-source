package com.advantage.online.store.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.advantage.online.store.model.Category;
import com.advantage.online.store.model.Product;
import com.advantage.util.ArgumentValidationHelper;
import com.advantage.util.JPAQueryHelper;

/**
 * Created by kubany on 10/13/2015.
 */
@Repository
public class ProductRepositoryImpl extends AbstractRepository {

    private static final int MAX_NUM_OF_PRODUCTS = 100;

    public Product createProduct(final String name, final String description, final int price,
     final Category category) {

    	final Product product = new Product(name, description, price, category);
    	entityManager.persist(product);
    	return product;
    }

	public void deleteProduct(final Product product) {

		ArgumentValidationHelper.validateArgumentIsNotNull(product, "product");
		log.info("deleteProduct");
		final Long productId = product.getId();
		final String hql = JPAQueryHelper.getDeleteByPkFieldQuery(Product.class, "id",
				                                                  productId);
		final Query query = entityManager.createQuery(hql);
		query.executeUpdate();
	}

    public void save(Product p) {
        entityManager.merge(p);
    }

    public List<Product> getAllProducts() {
        log.info("getAllProducts");
        List<Product> products = entityManager.createNamedQuery(Product.GET_ALL, Product.class)
                .setMaxResults(MAX_NUM_OF_PRODUCTS)
                .getResultList();

        return products.isEmpty() ? null : products;
    }

    public List<Product> getCategoryProducts(final Long categoryId) {

        ArgumentValidationHelper.validateArgumentIsNotNull(categoryId, "category id");
        final StringBuilder hql = new StringBuilder("FROM ");
        hql.append(Product.class.getName());
        hql.append(" P WHERE P.category.categoryId = :category");
        final Query query = entityManager.createQuery(hql.toString());
        query.setParameter("category", categoryId);
        return query.getResultList();
    }

    public List<Product> getCategoryProducts(final Category category) {

        ArgumentValidationHelper.validateArgumentIsNotNull(category, "category");
        final Long categoryId = category.getCategoryId();
        return getCategoryProducts(categoryId);
    }
}