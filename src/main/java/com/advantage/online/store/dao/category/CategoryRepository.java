package com.advantage.online.store.dao.category;

import java.util.List;

import com.advantage.online.store.model.category.Category;

public interface CategoryRepository {

	Category createCategory(String name, String managedImageId);
	int deleteCategory(Category category);
	int deleteCategories(Category... category);
	List<Category> getAllCategories();
	Category getCategory(Long categoryId);
}