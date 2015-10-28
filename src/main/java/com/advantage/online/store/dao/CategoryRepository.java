package com.advantage.online.store.dao;

import java.util.List;

import com.advantage.online.store.model.Category;

public interface CategoryRepository {

	Category createCategory(String name, byte[] image);
	int deleteCategory(Category category);
	List<Category> getAllCategories();
}