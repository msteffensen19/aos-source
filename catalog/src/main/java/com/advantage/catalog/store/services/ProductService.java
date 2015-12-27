package com.advantage.catalog.store.services;

import java.util.*;
import java.util.stream.Collectors;

import com.advantage.catalog.store.dao.attribute.AttributeRepository;
import com.advantage.catalog.store.dao.product.ProductRepository;
import com.advantage.root.store.dto.*;
import com.advantage.catalog.store.model.attribute.Attribute;
import com.advantage.catalog.store.model.category.Category;
import com.advantage.catalog.store.model.product.ImageAttribute;
import com.advantage.catalog.store.config.ImageManagementConfiguration;
import com.advantage.catalog.store.image.ImageManagement;
import com.advantage.catalog.store.image.ImageManagementAccess;
import com.advantage.catalog.store.image.ManagedImage;
import com.advantage.catalog.store.model.product.ColorAttribute;
import com.advantage.catalog.store.model.product.Product;
import com.advantage.catalog.store.model.product.ProductAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.advantage.catalog.util.ArgumentValidationHelper;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ProductService {

    public static final String PRODUCT_DEFAULT_QUANTITY = "product.inStock.default.value";
    @Autowired
    public ProductRepository productRepository;
    @Autowired
    public AttributeRepository attributeRepository;
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

        if (dto.getImages().size() == 0) {
            dto.getImages().add(product.getManagedImageId());
        }

        product.setColors(getColorAttributes(dto.getColors(), product));
        product.setImages(getImageAttribute(dto.getImages(), product));

        return new ProductResponseStatus(true, product.getId(), "Product was created successful");
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductResponseStatus updateProduct(ProductDto dto, Long id) {
        Product product = productRepository.get(id);

        if (product == null) return new ProductResponseStatus(false, -1, "Product wasn't found");

        Category category = categoryService.getCategory(dto.getCategoryId());
        if (category == null) return new ProductResponseStatus(false, -1, "Could not find category");

        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setManagedImageId(dto.getImageUrl());
        product.setCategory(category);

        Set<ImageAttribute> imageAttributes = new HashSet<>(product.getImages());
        for (String s : dto.getImages()) {
            ImageAttribute imageAttribute = new ImageAttribute(s);
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

            if (isAttributeExist) {
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

    @Transactional
    public ProductCollectionDto getProductCollectionDto() {
        List<Product> products = getAllProducts();
        ProductCollectionDto dto = new ProductCollectionDto();
        dto.setProducts(fillProducts(products));
        dto.setColors(getColorsSet(products));
        dto.setMinPrice(getMinPrice(products));
        dto.setMaxPrice(geMaxPrice(products));

        return dto;
    }
    /**
     * Determine entity object from DTO
     * @param images {@link Collection} collection of images ids
     * @param product {@link Product}
     * @return {@link Set} set of ImageAttribute
     */
    public Set<ImageAttribute> getImageAttribute(Collection<String> images, Product product) {
        Set<ImageAttribute> imageAttributes = new HashSet<>();
        for (String s : images) {
            ImageAttribute image = new ImageAttribute(s);
            image.setProduct(product);
            imageAttributes.add(image);
        }

        return imageAttributes;
    }

    /**
     * Determine entity object from DTO
     * @param colors {@link Collection} ColorAttributeDto collection
     * @param product {@link Product} Product entity
     * @return {@link Set} set of ColorAttributes
     */
    public Set<ColorAttribute> getColorAttributes(Collection<ColorAttributeDto> colors, Product product) {
        Set<ColorAttribute> colorAttributes = new HashSet<>(product.getColors());
        for (ColorAttributeDto s : colors) {
            if (!(s.getInStock() > 0))
                s.setInStock(Integer.parseInt(environment.getProperty(PRODUCT_DEFAULT_QUANTITY)));
            Optional<ColorAttribute> attribute =
                    colorAttributes.stream().filter(x -> x.getName().equalsIgnoreCase(s.getName())).findFirst();
            if (attribute.isPresent() && attribute.get().getInStock() != s.getInStock()) {
                attribute.get().setInStock(s.getInStock());
            }
            s.setName(s.getName().toUpperCase());
            s.setCode(s.getCode().toUpperCase());
            ColorAttribute colorAttribute = new ColorAttribute(s.getName(), s.getCode(), s.getInStock());
            colorAttribute.setProduct(product);
            colorAttributes.add(colorAttribute);
        }

        return colorAttributes;
    }

    /**
     * Return colors unique set from Products collection
     *
     * @param products {@link Collection} Product collection
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
     *
     * @param products {@link Collection} Product collection
     * @return {@link Double} price value
     */
    public String getMinPrice(Collection<Product> products) {
        double price = products.stream().min(Comparator.comparing(Product::getPrice)).get().getPrice();

        return Double.toString(price);
    }

    /**
     * Return maximum price value from Product collection
     *
     * @param products {@link Collection} Product collection
     * @return {@link Double} price value
     */
    public String geMaxPrice(Collection<Product> products) {
        double price = products.stream().max(Comparator.comparing(Product::getPrice)).get().getPrice();

        return Double.toString(price);
    }

    /**
     * Convert ProductAttributes collection to AttributeItem DTO
     *
     * @param attributes - ProductAttributes collection
     * @return AttributeItem DTO collection
     */
    public List<AttributeItem> productAttributesToAttributeValues(Collection<ProductAttributes> attributes) {
        List<AttributeItem> items = new ArrayList<>();
        for (ProductAttributes attribute : attributes) {
            String name = attribute.getAttribute().getName();
            String value = attribute.getAttributeValue();
            items.add(new AttributeItem(name, value));
        }

        return items;
    }

    /**
     * Fill all products DTO parameters
     *
     * @param products {@link Collection} Collection of products
     * @return {@link List} ProductDto collection
     */
    public List<ProductDto> fillProducts(Collection<Product> products) {
        if (products == null) return null;
        List<ProductDto> productDtos = new ArrayList<>(products.size());

        for (Product product : products) {
            ProductDto productDto = getDtoByEntity(product);
            productDtos.add(productDto);
        }

        return productDtos;
    }

    /**
     * Fill default products DTO parameters
     *
     * @param products {@link Collection} Collection products
     * @return {@link List} ProductDto collection
     */
    public List<ProductDto> fillPureProducts(Collection<Product> products) {
        if (products == null) return null;
        List<ProductDto> productDtos = new ArrayList<>(products.size());

        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setProductId(product.getId());
            productDto.setCategoryId(product.getCategory().getCategoryId());
            productDto.setProductName(product.getProductName());
            productDto.setPrice(product.getPrice());
            productDto.setImageUrl(product.getManagedImageId());

            productDtos.add(productDto);
        }

        return productDtos;
    }

    /**
     * Determine DTO object by entity object
     * @param product {@link List} Product object
     * @return {@link ProductDto} product DTO
     */
    public ProductDto getDtoByEntity(Product product) {
        return new ProductDto(product.getId(),
                product.getCategory().getCategoryId(),
                product.getProductName(),
                product.getPrice(),
                product.getDescription(),
                product.getManagedImageId(),
                fillAttributes(product),
                fillColorAttributes(product),
                fillImages(product.getImages()));
    }

    /**
     * Determine collection DTO from entities collection
     * @param products {@link List} collection of Products
     * @return {@link List} product DTO collection
     */
    public List<ProductDto> getDtoByEntityCollection(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>(products.size());
        for (Product categoryProduct : products) {
            ProductDto productDto = getDtoByEntity(categoryProduct);

            productDtos.add(productDto);
        }

        return productDtos;
    }

    /**
     * Build images IDs collection
     *
     * @param imageAttributes {@link Set} ImageAttribute collection
     * @return {@link List} images
     */
    private List<String> fillImages(Set<ImageAttribute> imageAttributes) {
        List<String> images = new ArrayList<>(imageAttributes.size());
        for (ImageAttribute image : imageAttributes) {
            images.add(image.getImageUrl());
        }

        return images;
    }

    /**
     * Build AttributeItem collection from Products attributes
     *
     * @param product {@link Product} Product object
     * @return {@link List} collection of attributes
     */
    private List<AttributeItem> fillAttributes(Product product) {
        Set<ProductAttributes> productAttributes = product.getProductAttributes();
        List<AttributeItem> items = new ArrayList<>(productAttributes.size());
        for (ProductAttributes attribute : productAttributes) {
            items.add(new AttributeItem(attribute.getAttribute().getName(), attribute.getAttributeValue()));
        }

        return items;
    }

    /**
     * Build ColorAttributeDto collection from Products attributes
     * @param product {@link Product} Product object
     * @return {@link List} collection of ColorAttributesDto
     */
    private List<ColorAttributeDto> fillColorAttributes(Product product) {
        Set<ColorAttribute> colorAttributes = product.getColors();
        List<ColorAttributeDto> dtos = new ArrayList<>(colorAttributes.size());
        for (ColorAttribute colorAttribute : colorAttributes) {
            dtos.add(new ColorAttributeDto(colorAttribute.getCode(), colorAttribute.getName(),
                    colorAttribute.getInStock()));
        }

        return dtos;
    }

    /**
     * Determine Attribute entity by DTO
     * @param attribute {@link AttributeItem} DTO
     * @return {@link Attribute} Attribute entity
     */
    private Attribute getAttributeByDto(AttributeItem attribute) {
        return attributeRepository.get(attribute.getAttributeName());
    }

}
