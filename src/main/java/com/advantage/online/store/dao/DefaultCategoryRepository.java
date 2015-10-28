package com.advantage.online.store.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.advantage.online.store.model.Category;
import com.advantage.util.ArgumentValidationHelper;
import com.advantage.util.JPAQueryHelper;

/**
 * Created by kubany on 10/18/2015.
 */
@Component
@Qualifier("categoryRepository")
@Repository
public class DefaultCategoryRepository extends AbstractRepository implements CategoryRepository {

    private static final int MAX_NUM_OF_CATEGORIES = 6;

    @Override
    public Category createCategory(final String name, final byte[] image) {

    	final Category category = new Category("LAPTOPS", image);
    	entityManager.persist(category);
    	return category;
    }

    @Override
    public int deleteCategory(final Category category) {

		ArgumentValidationHelper.validateArgumentIsNotNull(category, "category");
		log.info("deleteCategory");
		final Long categoryId = category.getCategoryId();
		final String hql = JPAQueryHelper.getDeleteByPkFieldQuery(Category.class,
				                                                  Category.FIELD_CATEGORY_ID,
				                                                  categoryId);
		final Query query = entityManager.createQuery(hql);
		return query.executeUpdate();
	}

    @Override
    public List<Category> getAllCategories() {
        log.info("getAllCategories");
        List<Category> categories = entityManager.createNamedQuery(Category.QUERY_GET_ALL, Category.class)
                .setMaxResults(MAX_NUM_OF_CATEGORIES)
                .getResultList();

        return categories.isEmpty() ? null : categories;
    }
}