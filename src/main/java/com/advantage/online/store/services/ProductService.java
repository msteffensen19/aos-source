package com.advantage.online.store.services;

import java.util.List;

import com.advantage.online.store.dto.AttributeItem;
import com.advantage.online.store.dto.ProductApiDto;
import com.advantage.online.store.dto.ProductResponseStatus;
import com.advantage.online.store.model.attribute.Attribute;
import com.advantage.online.store.model.category.Category;
import com.advantage.online.store.model.product.Product;
import com.advantage.online.store.model.product.ProductAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.advantage.online.store.dao.product.ProductRepository;
import com.advantage.util.ArgumentValidationHelper;

@Service
@Transactional
public class ProductService {

    @Autowired
    public ProductRepository productRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    private AttributeService attributeService;

    public List<Product> getAllProducts() {

        return productRepository.getAll();
    }

    public List<Product> getCategoryProducts(final Long categoryId) {

        ArgumentValidationHelper.validateArgumentIsNotNull(categoryId, "category id");
        return productRepository.getCategoryProducts(categoryId);
    }

    public Product getProductById(Long id) {
        return productRepository.get(id);
    }

    @Transactional
    public ProductResponseStatus createProduct(ProductApiDto dto) {
        Category category = categoryService.getCategory(dto.getCategoryId());

        if (category == null) return new ProductResponseStatus(false, -1, "Could not find category");

        Product product = productRepository.create(dto.getProductName(), dto.getDescription(), dto.getPrice(),
            dto.getImageUrl(), category);

        if (product == null) return new ProductResponseStatus(false, -1, "Product wasn't created");

        for (AttributeItem item : dto.getAttributes()) {
            ProductAttributes productAttributes = new ProductAttributes();
            productAttributes.setAttributeValue(item.getAttributeValue());
            productAttributes.setProduct(product);

            Attribute attribute = getAttributeByDto(item);
            if (attribute == null) {
                attribute = attributeService.createAttribute(item.getAttributeName());
            }

            productAttributes.setAttribute(attribute);
            product.getProductAttributes().add(productAttributes);
        }

        return new ProductResponseStatus(true, product.getId(), "Product was created successful");
    }

    private Attribute getAttributeByDto(AttributeItem attribute) {
        return attributeService.getAttributeByName(attribute.getAttributeName());
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductResponseStatus updateProduct(ProductApiDto dto, Long id) {
        Product product = productRepository.get(id);

        if(product == null) return new ProductResponseStatus(false, -1, "Product wasn't found");

        Category category = categoryService.getCategory(dto.getCategoryId());
        if (category == null) return new ProductResponseStatus(false, -1, "Could not find category");

        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setManagedImageId(dto.getImageUrl());
        product.setCategory(category);

        for (AttributeItem item : dto.getAttributes()) {
            String attrName = item.getAttributeName();
            String attrValue = item.getAttributeValue();

            ProductAttributes productAttributes = new ProductAttributes();
            boolean isAttributeExist = product.getProductAttributes().stream().
                anyMatch(i -> i.getAttribute().getName().equalsIgnoreCase(attrName));

            if(isAttributeExist) {
                productAttributes = product.getProductAttributes().stream().
                    filter(x -> x.getAttribute().getName().equalsIgnoreCase(attrName)).
                    findFirst().get();

                productAttributes.setAttributeValue(attrValue);
            }

            Attribute attribute = getAttributeByDto(item);
            if (attribute == null) {
                attribute = attributeService.createAttribute(attrName);
            }

            productAttributes.setAttributeValue(attrValue);
            productAttributes.setProduct(product);

            productAttributes.setAttribute(attribute);
            product.getProductAttributes().add(productAttributes);
        }

        return new ProductResponseStatus(true, product.getId(), "Product was updated successful");
    }
}
