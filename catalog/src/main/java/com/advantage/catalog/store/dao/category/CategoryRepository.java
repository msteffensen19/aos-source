package com.advantage.catalog.store.dao.category;

import com.advantage.catalog.store.dao.DefaultCRUDOperations;
import com.advantage.catalog.store.model.category.Category;
import com.advantage.catalog.store.model.category.CategoryAttributeFilter;
import java.util.List;

public interface CategoryRepository extends DefaultCRUDOperations<Category> {
    /**
     * Create Category entity
     *
     * @param name           Name of category
     * @param managedImageId Image identificator
     * @return
     *
     *
     */
    Category createCategory(String name, String managedImageId);

    /**
     *
     * @param categoryAttributeFilterObj
     */
    void addCategoryAttributeFilter(CategoryAttributeFilter categoryAttributeFilterObj);

    /**
     *
     * @return
     */
    List<CategoryAttributeFilter> get();
}