package com.advantage.root.store.dao.category;

import com.advantage.root.store.dao.DefaultCRUDOperations;
import com.advantage.root.store.model.category.Category;

public interface CategoryRepository extends DefaultCRUDOperations<Category> {
    /**
     * Create Category entity
     *
     * @param name           Name of category
     * @param managedImageId Image identificator
     * @return
     */
    Category createCategory(String name, String managedImageId);
}