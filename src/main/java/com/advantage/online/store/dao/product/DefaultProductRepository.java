package com.advantage.online.store.dao.product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.advantage.online.store.dao.AbstractRepository;
import com.advantage.online.store.model.product.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.advantage.online.store.model.category.Category;
import com.advantage.util.ArgumentValidationHelper;
import com.advantage.util.JPAQueryHelper;
import org.springframework.transaction.annotation.Transactional;

@Component
@Qualifier("productRepository")
@Repository
public class DefaultProductRepository extends AbstractRepository implements ProductRepository {

    private static final int MAX_NUM_OF_PRODUCTS = 100;

    @Override
    @Transactional
    public Product create(String name, String description, int price, Category category) {

        Product product = new Product(name, description, price, category);
        product.setManagedImageId("1234");
    	entityManager.persist(product);
        entityManager.flush();

    	return product;
    }

    @Override
    @Transactional
    public void create(Product product) {
        product.setManagedImageId("1234");
        entityManager.persist(product);
        entityManager.flush();

    }

    @Override
    public int deleteByIds(Collection<Long> productIds) {

    	ArgumentValidationHelper.validateCollectionArgumentIsNotNullAndNotEmpty(productIds, "product ids");
    	String hql = JPAQueryHelper.getDeleteByPkFieldsQuery(Product.class, Product.FIELD_ID, Product.PARAM_ID);
    	Query query = entityManager.createQuery(hql);
    	query.setParameter(Product.PARAM_ID, productIds);

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

    @Override
    public List<Product> getAll() {
        List<Product> products = entityManager.createNamedQuery(Product.QUERY_GET_ALL, Product.class)
            .setMaxResults(MAX_NUM_OF_PRODUCTS)
            .getResultList();

        return products.isEmpty() ? null : products;
    }

    @Override
    public Product get(Long entityId) {
        ArgumentValidationHelper.validateArgumentIsNotNull(entityId, "product id");
        String hql = JPAQueryHelper.getSelectByPkFieldQuery(Product.class, Product.FIELD_ID, entityId);
        Query query = entityManager.createQuery(hql);

        return (Product) query.getSingleResult();
    }

    @Override
    public int delete(final Collection<Product> products) {

        ArgumentValidationHelper.validateCollectionArgumentIsNotNullAndNotEmpty(products,
            "products");
        final int productsCount = products.size();
        final Collection<Long> productIds = new ArrayList<Long>(productsCount);

        for (final Product product : products) {

            final long productId = product.getId();
            productIds.add(productId);
        }

        return deleteByIds(productIds);
    }
}