package com.advantage.online.store.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.advantage.online.store.image.ImageManagement;
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

    @Autowired
    private ImageManagement imageManagement;

    @Override
    public Category createCategory(final String name, final byte[] image) {

    	final Category category = new Category(name, image);
    	entityManager.persist(category);
    	return category;
    }

    @Override
    public int deleteCategory(final Category category) {

		ArgumentValidationHelper.validateArgumentIsNotNull(category, "category");
		final Long categoryId = category.getCategoryId();
		final String hql = JPAQueryHelper.getDeleteByPkFieldQuery(Category.class,
				                                                  Category.FIELD_CATEGORY_ID,
				                                                  categoryId);
		final Query query = entityManager.createQuery(hql);
		return query.executeUpdate();
	}

    @Override
    public int deleteCategories(final Category... categories) {

    	ArgumentValidationHelper.validateArrayArgumentIsNotNullAndNotZeroLength(categories,
    			                                                                "categories");
    	final int categoriesCount = categories.length;
    	final Collection<Long> categoryIds = new ArrayList<Long>(categoriesCount);
    	
    	for (final Category category : categories) {

    		final Long categoryId = category.getCategoryId();
    		categoryIds.add(categoryId);
    	}

    	final String hql = JPAQueryHelper.getDeleteByPkFieldsQuery(Category.class,
    			                                                   Category.FIELD_CATEGORY_ID,
    			                                                   Category.PARAM_CATEGORY_ID);
		final Query query = entityManager.createQuery(hql);
		query.setParameter(Category.PARAM_CATEGORY_ID, categoryIds);
		return query.executeUpdate();
    }

    @Override
    public List<Category> getAllCategories() {

        List<Category> categories = entityManager.createNamedQuery(Category.QUERY_GET_ALL, Category.class)
                .setMaxResults(MAX_NUM_OF_CATEGORIES)
                .getResultList();

        return categories.isEmpty() ? null : categories;
    }
}