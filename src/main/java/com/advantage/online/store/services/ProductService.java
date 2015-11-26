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

               /* return new ProductResponseStatus(false, -1, "Could not find attribute " +
                    item.getAttributeName());*/
            }

            productAttributes.setAttribute(attribute);
            product.getProductAttributes().add(productAttributes);
        }

        return new ProductResponseStatus(true, product.getId(), "Product was created successful");
    }

    private Attribute getAttributeByDto(AttributeItem attribute) {
        return attributeService.getAttributeByName(attribute.getAttributeName());
    }
}
