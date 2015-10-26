package com.advantage.online.store.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.advantage.online.store.model.Category;
import com.advantage.util.ArgumentValidationHelper;
import com.advantage.util.JPAQueryHelper;

/**
 * Created by kubany on 10/18/2015.
 */
@Repository
public class CategoryRepository extends AbstractRepository {

    private static final int MAX_NUM_OF_CATEGORIES = 6;

    public Category createCategory(final String name, final byte[] image) {

    	final Category category = new Category("LAPTOPS", image);
    	entityManager.persist(category);
    	return category;
    }

    public void deleteCategory(final Category category) {

		ArgumentValidationHelper.validateArgumentIsNotNull(category, "category");
		log.info("deleteCategory");
		final Long categoryId = category.getCategoryId();
		final String hql = JPAQueryHelper.getDeleteByPkFieldQuery(Category.class,
				                                                  "categoryId", categoryId);
		final Query query = entityManager.createQuery(hql);
		query.executeUpdate();
	}

    public List<Category> getAllCategories() {
        log.info("getAllCategories");
        List<Category> categories = entityManager.createNamedQuery(Category.GET_ALL, Category.class)
                .setMaxResults(MAX_NUM_OF_CATEGORIES)
                .getResultList();

        return categories.isEmpty() ? null : categories;
    }
}