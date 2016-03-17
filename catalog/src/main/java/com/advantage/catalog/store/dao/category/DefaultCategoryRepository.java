package com.advantage.catalog.store.dao.category;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.persistence.Query;

import com.advantage.catalog.store.model.category.Category;
import com.advantage.catalog.store.model.category.CategoryAttributeFilter;
import com.advantage.catalog.store.model.category.CategoryAttributeFilterPK;
import com.advantage.catalog.util.ArgumentValidationHelper;
import com.advantage.catalog.util.JPAQueryHelper;
import com.advantage.catalog.store.dao.AbstractRepository;
import com.advantage.common.Constants;
import com.advantage.common.dto.CatalogResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

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
    public List<CategoryAttributeFilter> getAllCategoryAttributeFilter() {
        List<CategoryAttributeFilter> caf = entityManager.createNamedQuery(CategoryAttributeFilter.QUERY_GET_ALL, CategoryAttributeFilter.class)
                .getResultList();

        return caf.isEmpty() ? null : caf;
    }

    @Override
    public void addCategoryAttributeFilter(CategoryAttributeFilter categoryAttributeFilterObj) {
        ArgumentValidationHelper.validateArgumentIsNotNull(categoryAttributeFilterObj,"CategoryAttributeFilter object");
        ArgumentValidationHelper.validateLongArgumentIsPositive(categoryAttributeFilterObj.getCategoryId(),"category id");
        ArgumentValidationHelper.validateLongArgumentIsPositive(categoryAttributeFilterObj.getAttributeId(),"attribute id");
        entityManager.persist(categoryAttributeFilterObj);
    }

    @Override
    public void updateCategoryAttributeFilter(Long categoryId, Long attributeId, boolean showInFilter) {
        CategoryAttributeFilter categoryAttributeFilter = findCategoryAttributeFilter(categoryId, attributeId);
        if (categoryAttributeFilter != null) {
            categoryAttributeFilter.setShowInFilter(showInFilter);
            entityManager.persist(categoryAttributeFilter);
        }
    }

    @Override
    public CategoryAttributeFilter findCategoryAttributeFilter(Long categoryId, Long attributeId) {
        CategoryAttributeFilterPK categoryAttributeFilterPk = new CategoryAttributeFilterPK(categoryId, attributeId);
        CategoryAttributeFilter categoryAttributeFilter = entityManager.find(CategoryAttributeFilter.class, categoryAttributeFilterPk);

        return categoryAttributeFilter;
    }

    //@Override
    //public int delete(Category... entities) {
    //    ArgumentValidationHelper.validateArrayArgumentIsNotNullAndNotZeroLength(entities, "categories");
    //    int categoriesCount = entities.length;
    //    Collection<Long> categoryIds = new ArrayList<>(categoriesCount);
    //
    //    for (Category category : entities) {
    //        Long categoryId = category.getCategoryId();
    //        categoryIds.add(categoryId);
    //    }
    //
    //    String hql = JPAQueryHelper.getDeleteByPkFieldsQuery(Category.class, Category.FIELD_CATEGORY_ID,
    //            Category.PARAM_CATEGORY_ID);
    //    Query query = entityManager.createQuery(hql);
    //    query.setParameter(Category.PARAM_CATEGORY_ID, categoryIds);
    //
    //    return query.executeUpdate();
    //}
    @Override
    public int delete(Category... entities) {
        int count = 0;
        for (Category entity : entities) {
            if (entityManager.contains(entity)) {
                entityManager.remove(entity);
                count++;
            }
        }

        return count;
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

        Category category = (Category) query.getSingleResult();
        return (category != null ? category : null);
    }

    @Override
    public CatalogResponse dbRestoreFactorySettings() {

        SessionFactory sessionFactory = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);

        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        //entityManager.createNativeQuery(statement).executeUpdate();
        //int result = session.createSQLQuery(statement).executeUpdate();
        String resultTruncate = (String) entityManager.createNativeQuery("SELECT public.truncate_catalog_tables()")
                .getSingleResult();
        transaction.commit();
        session.flush();
        session.close();

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        String resultInserts = (String) entityManager.createNativeQuery("SELECT public.restore_db_factory_settings()")
                .getSingleResult();
        transaction.commit();
        boolean commited = transaction.wasCommitted();
        session.flush();
        session.close();
        StringBuilder sb = new StringBuilder("Database Restore factory settings successful").append(Constants.NEW_LINE)
                .append(Constants.TRIPLE_SPACES).append("table \"category\"").append(Constants.NEW_LINE)
                .append(Constants.TRIPLE_SPACES).append("table \"attribute\"").append(Constants.NEW_LINE);

        //  region Restore Factory Settings table CATEGORY_ATTRIBUTES_FILTER
        try {
            ClassPathResource filePath = new ClassPathResource("categoryAttributes_4.json");

            File json = filePath.getFile();

            ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
            CategoryAttributeFilter[] categoryAttributeFilters = objectMapper.readValue(json, CategoryAttributeFilter[].class);

            for (CategoryAttributeFilter categoryAttributeFilter : categoryAttributeFilters) {
                entityManager.persist(categoryAttributeFilter);
            }

            if (this.getAllCategoryAttributeFilter().size() > 0) {
                sb.append(Constants.TRIPLE_SPACES).append("table \"category_attribute_filter\"").append(Constants.NEW_LINE);
                System.out.println("Database Restore Factory Settings successful - table \"category_attributes_filter\"");
            }

            //transaction.commit();
            //session.flush();
            //session.close();

        } catch (IOException e) {
            e.printStackTrace();
            return new CatalogResponse(false, "Restore factory settings FAILED - table Category_Attribute_Filter", -1);
        }

        //  endregion

        return new CatalogResponse(true, sb.toString(), 1);
    }

    @Override
    public CatalogResponse dbRestoreFactorySettingsCategoryAttributesFilter() {

        //try {
        //    ClassPathResource filePath = new ClassPathResource("categoryAttributes_4.json");
        //
        //    File json = filePath.getFile();
        //
        //    ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        //    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        //    CategoryAttributeFilter[] categoryAttributeFilters = objectMapper.readValue(json, CategoryAttributeFilter[].class);
        //
        //    ////  Initialize "category_attributes_filter"
        //    //SessionFactory sessionFactory = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
        //    //Session session = sessionFactory.openSession();
        //    //
        //    //Transaction transaction = session.beginTransaction();
        //    //entityManager.getTransaction().begin();
        //
        //    for (CategoryAttributeFilter categoryAttributeFilter : categoryAttributeFilters) {
        //        entityManager.persist(categoryAttributeFilter);
        //    }
        //
        //    if (this.getAllCategoryAttributeFilter().size() > 0) {
        //        System.out.println("Database Restore Factory Settings successful - table \"category_attributes_filter\"");
        //    }
        //
        //    //transaction.commit();
        //    //session.flush();
        //    //session.close();
        //
        //} catch (IOException e) {
        //    e.printStackTrace();
        //    return new CatalogResponse(false, "Restore factory settings FAILED - table Category_Attribute_Filter", -1);
        //}

        return new CatalogResponse(true, "Restore factory settings successful - table Category_Attribute_Filter", 1);
    }
}