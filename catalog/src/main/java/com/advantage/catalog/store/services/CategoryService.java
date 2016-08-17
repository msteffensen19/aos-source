package com.advantage.catalog.store.services;

import com.advantage.catalog.store.dao.category.CategoryRepository;
import com.advantage.catalog.store.model.attribute.Attribute;
import com.advantage.catalog.store.model.category.Category;
import com.advantage.catalog.store.model.category.CategoryAttributeFilter;
import com.advantage.catalog.store.model.product.Product;
import com.advantage.catalog.util.ArgumentValidationHelper;
import com.advantage.common.dto.CategoriesDto;
import com.advantage.common.dto.CategoryAttributeFilterResponse;
import com.advantage.common.dto.CategoryAttributeShowInFilter;
import com.advantage.common.dto.CategoryDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    private static final Logger logger = Logger.getLogger(CategoryService.class);

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

    @Transactional(readOnly = true)
    public CategoryAttributeFilterResponse getAllCategoryAttributesFilter() {
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

        if (logger.isInfoEnabled()) {
            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < categories.size(); i++) {
                sb.append(String.format("categories(%d): category.id=%d - name='%s'\n", i, categories.get(i).getCategoryId(), categories.get(i).getCategoryName()));
            }
            logger.info(sb);
        }
        //  endregion

        //  region Get and display attributes list
        List<Attribute> attributes = attributeService.getAllAttributes();
        ArgumentValidationHelper.validateCollectionArgumentIsNotNullAndNotEmpty(attributes, "attributes list");

        //  Sort attributes list (attribute-id 1 in List position 0)
        Collections.sort(attributes,
                new Comparator<Attribute>() {
                    public int compare(Attribute attribute1, Attribute attribute2) {
                        return (int) (attribute1.getId() - attribute2.getId());
                    }
                });

        if (logger.isInfoEnabled()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < attributes.size(); i++) {
                sb.append(String.format("attributes(%d): attribute.id=%d - name='%s'\n", i, attributes.get(i).getId(), attributes.get(i).getName()));
            }
            logger.info(sb);
        }
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

    public List<CategoriesDto> getCategoryDtoData() {
        List<CategoriesDto> categories = new ArrayList<>();

        for (long i = 1; i < 6; i++) {
            CategoriesDto dto = applyCategories(i);
            List<Product> categoryProducts = productService.getCategoryProducts(i);
            dto.setAttributes(attributeService.fillAttributeDto(categoryProducts));
            dto.setProducts(productService.getCategoryProductDtoByEntityCollection(categoryProducts));
            dto.setPromotedProduct(dealService.getPromotedProductDtoInCategory(i));
            dto.setColors(productService.getColorsSet(categoryProducts));
            categories.add(dto);
        }

        return categories;
    }

    private CategoryDto applyCategory(long categoryId) {
        Category category = getCategory(categoryId);

        return getCategoryDto(category);
    }

    private CategoriesDto applyCategories(long categoryId) {
        Category category = getCategory(categoryId);

        return getCategoriesDto(category);
    }

    public CategoryDto getCategoryDto(Category category) {
        ArgumentValidationHelper.validateArgumentIsNotNull(category, "category");
        CategoryDto dto = new CategoryDto();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        dto.setCategoryImageId(category.getManagedImageId());

        return dto;
    }

    public CategoriesDto getCategoriesDto(Category category) {
        ArgumentValidationHelper.validateArgumentIsNotNull(category, "category");
        CategoriesDto dto = new CategoriesDto();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        dto.setCategoryImageId(category.getManagedImageId());

        return dto;
    }

    public CategoryAttributeFilter findCategoryAttributeFilter(Long categoryId, Long attributeId) {
        CategoryAttributeFilter categoryAttributeFilter = categoryRepository.findCategoryAttributeFilter(categoryId, attributeId);
        return categoryAttributeFilter;
    }

    //  region Network Virtualization (NV)
    /**
     * Return HTTP status code of 4xx series according to Network Virtualization requirements.
     * @param httpStatusCode
     * @return
     */
    public HttpStatus returnHttpStatusCode4xxForNetworkVirtualization(int httpStatusCode) {
        HttpStatus httpStatus = null;

        switch (httpStatusCode) {
            case 400:
                httpStatus = HttpStatus.BAD_REQUEST;
            case 403:
                httpStatus = HttpStatus.FORBIDDEN;
            case 404:
                httpStatus = HttpStatus.NOT_FOUND;
            case 406:
                httpStatus = HttpStatus.NOT_ACCEPTABLE;
            case 408:
                httpStatus = HttpStatus.REQUEST_TIMEOUT;
            case 409:
                httpStatus = HttpStatus.CONFLICT;
            case 411:
                httpStatus = HttpStatus.LENGTH_REQUIRED;
            case 412:
                httpStatus = HttpStatus.PRECONDITION_FAILED;
            case 413:
                httpStatus = HttpStatus.PAYLOAD_TOO_LARGE;
            case 414:
                httpStatus = HttpStatus.URI_TOO_LONG;
            case 422:
                httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            case 423:
                httpStatus = HttpStatus.LOCKED;
            case 424:
                httpStatus = HttpStatus.FAILED_DEPENDENCY;
            case 426:
                httpStatus = HttpStatus.UPGRADE_REQUIRED;
            case 429:
                httpStatus = HttpStatus.TOO_MANY_REQUESTS;
            default:
                httpStatus = HttpStatus.BAD_REQUEST;
        }
        return httpStatus;
    }

    /**
     * Return HTTP status code of 5xx series according to Network Virtualization requirements.
     * @param httpStatusCode
     * @return
     */
    public HttpStatus returnHttpStatusCode5xxForNetworkVirtualization(int httpStatusCode) {
        HttpStatus httpStatus = null;

        switch (httpStatusCode) {
            case 500:
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            case 501:
                httpStatus = HttpStatus.NOT_IMPLEMENTED;
            case 503:
                httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
            case 505:
                httpStatus = HttpStatus.HTTP_VERSION_NOT_SUPPORTED;
            case 507:
                httpStatus = HttpStatus.INSUFFICIENT_STORAGE;
            case 509:
                httpStatus = HttpStatus.BANDWIDTH_LIMIT_EXCEEDED;
            case 510:
                httpStatus = HttpStatus.NOT_EXTENDED;
            case 511:
                httpStatus = HttpStatus.NETWORK_AUTHENTICATION_REQUIRED;
            default:
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return httpStatus;
    }
    //  endregion
}
