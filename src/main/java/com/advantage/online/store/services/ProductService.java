package com.advantage.online.store.services;

import java.util.*;
import java.util.stream.Collectors;

import com.advantage.online.store.config.ImageManagementConfiguration;
import com.advantage.online.store.dto.*;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.advantage.online.store.dao.product.ProductRepository;
import com.advantage.util.ArgumentValidationHelper;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ProductService {

    public static final String PRODUCT_DEFAULT_QUANTITY = "product.inStock.default.value";
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

    public List<Product> filterByName(String pattern, int quantity) {
        return quantity > 0 ? productRepository.filterByName(pattern, quantity) : filterByName(pattern);
    }

    public List<Product> filterByName(String pattern) {
        return productRepository.filterByName(pattern);
    }

    public List<Product> getCategoryProducts(final Long categoryId) {

        ArgumentValidationHelper.validateArgumentIsNotNull(categoryId, "category id");
        return productRepository.getCategoryProducts(categoryId);
    }

    public Product getProductById(Long id) {
        return productRepository.get(id);
    }

    @Transactional
    public ProductResponseStatus createProduct(ProductDto dto) {
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

        if(dto.getImages().size() == 0) {
            dto.getImages().add(product.getManagedImageId());
        }

        product.setColors(getColorAttributes(dto.getColors(), product));
        product.setImages(getImageAttribute(dto.getImages(), product));

        return new ProductResponseStatus(true, product.getId(), "Product was created successful");
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductResponseStatus updateProduct(ProductDto dto, Long id) {
        Product product = productRepository.get(id);

        if(product == null) return new ProductResponseStatus(false, -1, "Product wasn't found");

        Category category = categoryService.getCategory(dto.getCategoryId());
        if (category == null) return new ProductResponseStatus(false, -1, "Could not find category");

        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setManagedImageId(dto.getImageUrl());
        product.setCategory(category);

        Set<ImageAttribute> imageAttributes = new HashSet<>(product.getImages());
        for (String s : dto.getImages()) {
            ImageAttribute imageAttribute  = new ImageAttribute(s);
            imageAttribute.setProduct(product);
            imageAttributes.add(imageAttribute);
        }

        product.setColors(getColorAttributes(dto.getColors(), product));
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

    public  Set<ImageAttribute> getImageAttribute(Collection<String> images, Product product) {
        Set<ImageAttribute> imageAttributes = new HashSet<>();
        for (String s : images) {
            ImageAttribute image = new ImageAttribute(s);
            image.setProduct(product);
            imageAttributes.add(image);
        }

        return imageAttributes;
    }

    public Set<ColorAttribute> getColorAttributes(Collection<ColorAttribute> colors, Product product) {
        Set<ColorAttribute> colorAttributes = new HashSet<>(product.getColors());
        for (ColorAttribute s : colors) {
            if(!(s.getInStock() > 0)) s.setInStock(Integer.parseInt(environment.getProperty(PRODUCT_DEFAULT_QUANTITY)));
            Optional<ColorAttribute> attribute =
                    colorAttributes.stream().filter(x -> x.getColor().equalsIgnoreCase(s.getColor())).findFirst();
            if(attribute.isPresent() && attribute.get().getInStock() != s.getInStock()) {
                attribute.get().setInStock(s.getInStock());
            }
            s.setColor(s.getColor().toUpperCase());
            s.setCode(s.getCode().toUpperCase());
            s.setProduct(product);
            colorAttributes.add(s);
        }

        return colorAttributes;
    }

    /**
     * Return colors unique set from Products collection
     * @param products Product collection
     * @return {@link HashSet} unique set of products colors
     */
    public Set<String> getColorsSet(Collection<Product> products) {
        Set<String> colors = new HashSet<>();
        for (Product product : products) {
            Set<ColorAttribute> set = product.getColors();
            colors.addAll(set.stream().map(ColorAttribute::getCode).collect(Collectors.toList()));
        }

        return colors;
    }

    /**
     * Return minimum price value from Product collection
     * @param products Product collection
     * @return {@link Double} price value
     */
    public String getMinPrice(List<Product> products){
        double price = products.stream().min(Comparator.comparing(Product::getPrice)).get().getPrice();

        return  Double.toString(price);
    }

    /**
     * Return maximum price value from Product collection
     * @param products Product collection
     * @return {@link Double} price value
     */
    public String geMaxPrice(List<Product> products){
        double price = products.stream().max(Comparator.comparing(Product::getPrice)).get().getPrice();

        return  Double.toString(price);
    }

    private Attribute getAttributeByDto(AttributeItem attribute) {
        return attributeService.getAttributeByName(attribute.getAttributeName());
    }
}
