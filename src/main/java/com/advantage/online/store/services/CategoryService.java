package com.advantage.online.store.services;

import com.advantage.online.store.dao.CategoryRepository;
import com.advantage.online.store.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kubany on 10/18/2015.
 */
@Service
public class CategoryService {

    @Autowired
    public CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }
}