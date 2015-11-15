package com.advantage.online.store.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.advantage.online.store.dao.category.CategoryRepository;
import com.advantage.online.store.model.category.Category;
import com.advantage.util.ArgumentValidationHelper;

/**
 * Created by kubany on 10/18/2015.
 */
@Service
public class CategoryService {

    @Autowired
    public CategoryRepository categoryRepository;

    @Transactional
    public Category createCategory(final String name, final String managedImageId) {

        return categoryRepository.createCategory(name, managedImageId);
    }

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    @Transactional(readOnly = true)
    public Category getCategory(final Long categoryId) {
        ArgumentValidationHelper.validateArgumentIsNotNull(categoryId, "category id");
        return categoryRepository.getCategory(categoryId);
    }
}