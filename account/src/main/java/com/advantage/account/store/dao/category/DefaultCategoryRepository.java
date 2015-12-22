package com.advantage.account.store.dao.category;

import com.advantage.account.store.dao.AbstractRepository;
import com.advantage.account.store.model.category.Category;
import com.advantage.account.util.ArgumentValidationHelper;
import com.advantage.account.util.JPAQueryHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@Qualifier("categoryRepository")
@Repository
public class DefaultCategoryRepository extends AbstractRepository implements CategoryRepository {

    private static final int MAX_NUM_OF_CATEGORIES = 6;

    @Override
    public Category createCategory(final String name, final String managedImageId) {
        Category category = new Category(name, managedImageId);
        entityManager.persist(category);

        return category;
    }

    @Override
    public int delete(Category... entities) {
        ArgumentValidationHelper.validateArrayArgumentIsNotNullAndNotZeroLength(entities, "categories");
        int categoriesCount = entities.length;
        Collection<Long> categoryIds = new ArrayList<>(categoriesCount);

        for (Category category : entities) {
            Long categoryId = category.getCategoryId();
            categoryIds.add(categoryId);
        }

        String hql = JPAQueryHelper.getDeleteByPkFieldsQuery(Category.class, Category.FIELD_CATEGORY_ID,
                Category.PARAM_CATEGORY_ID);
        Query query = entityManager.createQuery(hql);
        query.setParameter(Category.PARAM_CATEGORY_ID, categoryIds);

        return query.executeUpdate();
    }

    @Override
    public List<Category> getAll() {
        List<Category> categories = entityManager.createNamedQuery(Category.QUERY_GET_ALL, Category.class)
                .setMaxResults(MAX_NUM_OF_CATEGORIES)
                .getResultList();

        return categories.isEmpty() ? null : categories;
    }

    @Override
    public Category get(Long entityId) {
        ArgumentValidationHelper.validateArgumentIsNotNull(entityId, "category id");
        String selectCategoryHql = JPAQueryHelper.getSelectByPkFieldQuery(Category.class, Category.FIELD_CATEGORY_ID,
                entityId);
        Query query = entityManager.createQuery(selectCategoryHql);

        return (Category) query.getSingleResult();
    }
}