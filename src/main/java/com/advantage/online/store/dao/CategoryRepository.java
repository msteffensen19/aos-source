package com.advantage.online.store.dao;

import java.util.List;

import com.advantage.online.store.model.Category;

public interface CategoryRepository {

	Category createCategory(String name, String managedImageId);
	int deleteCategory(Category category);
	int deleteCategories(Category... category);
	List<Category> getAllCategories();
}