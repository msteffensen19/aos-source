package com.advantage.online.store.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.advantage.online.store.config.ImageManagementConfiguration;
import com.advantage.online.store.dto.AttributeItem;
import com.advantage.online.store.dto.ImageUrlResponseStatus;
import com.advantage.online.store.dto.ProductApiDto;
import com.advantage.online.store.dto.ProductResponseStatus;
import com.advantage.online.store.image.ImageManagement;
import com.advantage.online.store.image.ImageManagementAccess;
import com.advantage.online.store.image.ManagedImage;
import com.advantage.online.store.model.attribute.Attribute;
import com.advantage.online.store.model.category.Category;
import com.advantage.online.store.model.product.ColorAttribute;
import com.advantage.online.store.model.product.ImageAttribute;
import com.advantage.online.store.model.product.Product;
import com.advantage.online.store.model.product.ProductAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.advantage.online.store.dao.product.ProductRepository;
import com.advantage.util.ArgumentValidationHelper;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ProductService {

    @Autowired
    public ProductRepository productRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    private AttributeService attributeService;
    @Autowired
    private Environment environment;

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

        if(dto.getColors().size() == 0) {
            dto.getColors().add(dto.getAttributes()
                .stream()
                .filter(x -> x.getAttributeName().equalsIgnoreCase("color")).findFirst().get().getAttributeValue());
        }

        if(dto.getImages().size() == 0) {
            dto.getImages().add(product.getManagedImageId());
        }

        product.setColors(getColorAttributes(dto.getColors(), product));
        product.setImages(getImageAttribute(dto.getImages(), product));

        return new ProductResponseStatus(true, product.getId(), "Product was created successful");
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

        Set<ColorAttribute> colorAttributes = new HashSet<>(product.getColors());
        Set<ImageAttribute> imageAttributes = new HashSet<>(product.getImages());
        for (String s : dto.getColors()) {
            ColorAttribute colorAttribute  =new ColorAttribute(s);
            colorAttribute.setProduct(product);
            colorAttributes.add(colorAttribute);
        }
        for (String s : dto.getImages()) {
            ImageAttribute imageAttribute  = new ImageAttribute(s);
            imageAttribute.setProduct(product);
            imageAttributes.add(imageAttribute);
        }

        product.setColors(colorAttributes);
        product.setImages(imageAttributes);

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

    @Transactional(rollbackFor = Exception.class)
    public ImageUrlResponseStatus fileUpload(MultipartFile file) {
        String imageManagementRepository =
            environment.getProperty(ImageManagementConfiguration.PROPERTY_IMAGE_MANAGEMENT_REPOSITORY);
        try {
            byte[] bytes = file.getBytes();
            String originalFileName = file.getOriginalFilename();
            ImageManagement imageManagement = ImageManagementAccess.getImageManagement(
                ImageManagementConfiguration.getPath(imageManagementRepository));

            ManagedImage managedImage = imageManagement.addManagedImage(bytes, originalFileName, true);
            imageManagement.persist();

            return new ImageUrlResponseStatus(managedImage.getId(), true, "Image successfully uploaded");
        } catch (Exception e) {
            return new ImageUrlResponseStatus("", false, "Failed to upload " + e.getMessage());
        }
    }

    private Set<ImageAttribute> getImageAttribute(Collection<String> images, Product product) {
        Set<ImageAttribute> imageAttributes = new HashSet<>();
        for (String s : images) {
            ImageAttribute image = new ImageAttribute(s);
            image.setProduct(product);
            imageAttributes.add(image);
        }

        return imageAttributes;
    }

    private  Set<ColorAttribute> getColorAttributes(Collection<String> colors, Product product) {
        Set<ColorAttribute> colorAttributes = new HashSet<>();
        for (String s : colors) {
            ColorAttribute color = new ColorAttribute(s);
            color.setProduct(product);
            colorAttributes.add(color);
        }

        return colorAttributes;
    }

    private Attribute getAttributeByDto(AttributeItem attribute) {
        return attributeService.getAttributeByName(attribute.getAttributeName());
    }
}
