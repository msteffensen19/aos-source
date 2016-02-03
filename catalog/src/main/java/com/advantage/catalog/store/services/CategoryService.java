package com.advantage.catalog.store.services;

import java.util.List;

import com.advantage.catalog.store.dao.category.CategoryRepository;
import com.advantage.catalog.store.model.category.CategoryAttributeFilter;
import com.advantage.common.dto.CategoryAttributeFilterResponse;
import com.advantage.common.dto.CategoryAttributeShowInFilter;
import com.advantage.common.dto.CategoryDto;
import com.advantage.catalog.store.model.category.Category;
import com.advantage.catalog.store.model.product.Product;
import com.advantage.catalog.util.ArgumentValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    @Autowired
    public CategoryRepository categoryRepository;
    @Autowired
    private AttributeService attributeService;
    @Autowired
    private ProductService productService;
    @Autowired
    DealService dealService;
    @Transactional
    public Category createCategory(final String name, final String managedImageId) {

        return categoryRepository.createCategory(name, managedImageId);
    }

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.getAll();
    }

    @Transactional(readOnly = true)
    public Category getCategory(final Long categoryId) {
        ArgumentValidationHelper.validateArgumentIsNotNull(categoryId, "category id");
        return categoryRepository.get(categoryId);
    }

    //by moti
    @Transactional(readOnly = true)
    public CategoryAttributeFilterResponse getAllCategoryAttributesFilter() {

        List<CategoryAttributeFilter> categoriesAttributes = categoryRepository.getAllCategoryAttributeFilter();

        CategoryAttributeFilterResponse categoryAttributeFilterResponse = new CategoryAttributeFilterResponse();

        if ((categoriesAttributes != null) && (categoriesAttributes.size() > 0)) {
            for (CategoryAttributeFilter categoryAttributeFilter: categoriesAttributes) {
                categoryAttributeFilterResponse.createCategoryAttributeShowInFilter(new CategoryAttributeShowInFilter(
                        categoryAttributeFilter.getCategoryId(),
                        categoryAttributeFilter.getAttributeId(),
                        categoryAttributeFilter.isShowInFilter()));
            }
        } else {
            categoryAttributeFilterResponse = null;
        }

        return categoryAttributeFilterResponse;
    }

    public CategoryDto getCategoryDto(long categoryId) {
        CategoryDto dto = applyCategory(categoryId);
        List<Product> categoryProducts = productService.getCategoryProducts(categoryId);
        dto.setAttributes(attributeService.fillAttributeDto(categoryProducts));
        dto.setProducts(productService.getDtoByEntityCollection(categoryProducts));
        dto.setPromotedProduct(dealService.getPromotedProductDtoInCategory(categoryId));
        dto.setColors(productService.getColorsSet(categoryProducts));

        return dto;
    }

    private CategoryDto applyCategory(long categoryId) {
        Category category = getCategory(categoryId);

        return getCategoryDto(category);
    }

    public CategoryDto getCategoryDto(Category category) {
        ArgumentValidationHelper.validateArgumentIsNotNull(category, "category");
        CategoryDto dto = new CategoryDto();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        dto.setCategoryImageId(category.getManagedImageId());

        return dto;
    }
}