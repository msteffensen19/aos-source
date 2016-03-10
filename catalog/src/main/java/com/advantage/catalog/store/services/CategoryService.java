package com.advantage.catalog.store.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.advantage.catalog.store.dao.category.CategoryRepository;
import com.advantage.catalog.store.model.attribute.Attribute;
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

        System.out.println("CategoryService.getAllCategoryAttributesFilter");

        //  region Get and display categories list
        List<Category> categories = this.getAllCategories();
        ArgumentValidationHelper.validateCollectionArgumentIsNotNullAndNotEmpty(categories, "categories list");
        //  Sort attributes list (attribute-id 1 in List position 0)
        Collections.sort(categories,
                new Comparator<Category>() {
                    public int compare(Category category1, Category category2) {
                        return (int)(category1.getCategoryId() - category2.getCategoryId());
                    }
                });
        for (int i = 0; i < categories.size(); i++) {
            System.out.println("categories(" + i + "): category .id=" + categories.get(i).getCategoryId() + " - .name=\'" + categories.get(i).getCategoryName() + "\'");
        }
        System.out.println("");
        //  endregion

        //  region Get and display attributes list
        List<Attribute> attributes = attributeService.getAllAttributes();
        ArgumentValidationHelper.validateCollectionArgumentIsNotNullAndNotEmpty(attributes, "attributes list");

        //  Sort attributes list (attribute-id 1 in List position 0)
        Collections.sort(attributes,
                new Comparator<Attribute>() {
                    public int compare(Attribute attribute1, Attribute attribute2) {
                        return (int)(attribute1.getId() - attribute2.getId());
                    }
                });

        for (int i = 0; i < attributes.size(); i++) {
            System.out.println("attributes(" + i + "): attribute .id=" + attributes.get(i).getId() + " - .name=\'" + attributes.get(i).getName() + "\'");
        }
        System.out.println("");
        //  endregion

        List<CategoryAttributeFilter> categoriesAttributes = categoryRepository.getAllCategoryAttributeFilter();

        CategoryAttributeFilterResponse categoryAttributeFilterResponse = new CategoryAttributeFilterResponse();

        if ((categoriesAttributes != null) && (categoriesAttributes.size() > 0)) {
            for (CategoryAttributeFilter categoryAttributeFilter: categoriesAttributes) {
                String categoryName =  categories.get((int) (categoryAttributeFilter.getCategoryId() - 1)).getCategoryName();
                String attributeName = attributes.get((int) (categoryAttributeFilter.getAttributeId() - 1)).getName();

                categoryAttributeFilterResponse.createCategoryAttributeShowInFilter(new CategoryAttributeShowInFilter(
                        categoryAttributeFilter.getCategoryId(),
                        categoryName,
                        categoryAttributeFilter.getAttributeId(),
                        attributeName,
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

    public String getCategoryName(int categoryId) {
        String categoryName = categoryRepository.getCategoryName(categoryId);
        return categoryName;
    }

    public String getAllCategories02(int p_int) {
        String categoryName = categoryRepository.getAllCategories02(p_int);
        return categoryName;
    }
}
