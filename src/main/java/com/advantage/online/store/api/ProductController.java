package com.advantage.online.store.api;

import com.advantage.online.store.Constants;
import com.advantage.online.store.dto.*;
import com.advantage.online.store.model.attribute.Attribute;
import com.advantage.online.store.model.category.Category;
import com.advantage.online.store.model.deal.Deal;
import com.advantage.online.store.model.product.Product;
import com.advantage.online.store.model.product.ProductAttributes;
import com.advantage.online.store.services.AttributeService;
import com.advantage.online.store.services.CategoryService;
import com.advantage.online.store.services.DealService;
import com.advantage.online.store.services.ProductService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping(value = Constants.URI_API + "/catalog")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttributeService attributeService;
    @Autowired
    private DealService dealService;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<ProductCollectionDto> getAllProducts(HttpServletRequest request, HttpServletResponse response) {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> productDtos = new ArrayList<>(products.size());

        for (Product product : products) {
            ProductDto productDto = new ProductDto(product);
            productDtos.add(productDto);
        }

        ProductCollectionDto dto = new ProductCollectionDto();
        dto.setProducts(productDtos);
        dto.setColors(productService.getColorsSet(products));
        dto.setMinPrice(productService.getMinPrice(products));
        dto.setMaxPrice(productService.geMaxPrice(products));


        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{product_id}", method = RequestMethod.GET)
    public ResponseEntity<ProductDto> getProductById(@PathVariable("product_id") Long id) {
        Product product = productService.getProductById(id);
        ProductDto dto = new ProductDto(product);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<ProductResponseStatus> createProduct(@RequestBody ProductDto product) {
        if (product == null) {
            return new ResponseEntity<>(new ProductResponseStatus(false, -1, "Data not valid"), HttpStatus.NO_CONTENT);
        }

        ProductResponseStatus responseStatus = productService.createProduct(product);

        return responseStatus.isSuccess() ? new ResponseEntity<>(responseStatus, HttpStatus.OK) :
            new ResponseEntity<>(responseStatus, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/products/images", method = RequestMethod.POST)
    public ResponseEntity<ProductResponseStatus> createProductWithImage(@RequestParam("product") String product,
                                                                        @RequestParam("file") MultipartFile file) {
        ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD,
            JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        ProductDto dto = null;
        try {
            dto = objectMapper.readValue(product, ProductDto.class);
        } catch (IOException e) {
            e.printStackTrace();
            new ResponseEntity<>(new ProductResponseStatus(false, -1, "json not valid"), HttpStatus.BAD_REQUEST);
        }

        if (dto == null) {
            return new ResponseEntity<>(new ProductResponseStatus(false, -1, "Data not valid"), HttpStatus.NO_CONTENT);
        }
        if (file.isEmpty()) {
            return new ResponseEntity<>(new ProductResponseStatus(false, -1, "File was empty"), HttpStatus.NO_CONTENT);
        }

        ImageUrlResponseStatus imageResponseStatus = productService.fileUpload(file);

        if (!imageResponseStatus.isSuccess()) {
            return new ResponseEntity<>(new ProductResponseStatus(false, -1, imageResponseStatus.getReason()),
                HttpStatus.BAD_REQUEST);
        }
        dto.setImageUrl(imageResponseStatus.getId());
        ProductResponseStatus responseStatus = productService.createProduct(dto);
        responseStatus.setImageId(imageResponseStatus.getId());

        return responseStatus.isSuccess() ? new ResponseEntity<>(responseStatus, HttpStatus.OK) :
            new ResponseEntity<>(responseStatus, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/products/{product_id}", method = RequestMethod.PUT)
    public ResponseEntity<ProductResponseStatus> updateProduct(@RequestBody ProductDto product,
                                                               @PathVariable("product_id") Long id) {
        ProductResponseStatus responseStatus = productService.updateProduct(product, id);
        return new ResponseEntity<>(responseStatus, HttpStatus.OK);
    }

    @RequestMapping(value = "/products/categories/{category_id}/products", method = RequestMethod.GET)
    public ResponseEntity<CategoryDto> getCategoryData(@PathVariable("category_id") String id) {
        final Long categoryId = Long.valueOf(id);
        final Category category = categoryService.getCategory(categoryId);
        final CategoryDto categoryDto = new CategoryDto();
        categoryDto.applyCategory(category);
        final List<Product> categoryProducts = productService.getCategoryProducts(categoryId);

        Map<String, Set<String>> attrCollection = new LinkedHashMap<>();
        List<Attribute> allAttributed = attributeService.getAllAttributes();
        for (Attribute attribute : allAttributed) {
            attrCollection.put(attribute.getName(), null);
        }

        for (Product product : categoryProducts) {
            Set<ProductAttributes> productAttributes = product.getProductAttributes();
            for (ProductAttributes attribute : productAttributes) {
                String attrName = attribute.getAttribute().getName();
                String attrValue = attribute.getAttributeValue();

                //if (!attrCollection.containsKey(attrName)) attrCollection.put(attrName, null);
                Set<String> item = attrCollection.get(attrName);

                if (item == null) {
                    item = new HashSet<>();
                    attrCollection.put(attrName, item);
                }
                item.add(attrValue);
            }
        }

        List<AttributeDto> attributeItems = new ArrayList<>();

        for (Map.Entry<String, Set<String>> item : attrCollection.entrySet()) {
            if (item.getValue() == null) continue;
            attributeItems.add(new AttributeDto(item.getKey(), item.getValue()));
        }

        categoryDto.setAttributes(attributeItems);

        final int categoryProductsCount = categoryProducts.size();
        final List<ProductDto> productDtos = new ArrayList<>(categoryProductsCount);

        for (final Product categoryProduct : categoryProducts) {
            final ProductDto productDto = new ProductDto(categoryProduct);

            productDtos.add(productDto);
        }

        categoryDto.setProducts(productDtos);
        Deal promotion = dealService.getDealOfTheDay(categoryId);
        PromotedProductDto promotedProductDto = promotion == null ? null :
            new PromotedProductDto(promotion.getStaringPrice(), promotion.getPromotionHeader(),
                promotion.getPromotionSubHeader(), promotion.getManagedImageId(), new ProductDto(promotion.getProduct()));

        categoryDto.setPromotedProduct(promotedProductDto);
        categoryDto.setMaxPrice(productService.geMaxPrice(categoryProducts));
        categoryDto.setMinPrice(productService.getMinPrice(categoryProducts));
        categoryDto.setColors(productService.getColorsSet(categoryProducts));

        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/images", method = RequestMethod.POST)
    public ResponseEntity<ImageUrlResponseStatus> imageUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            ImageUrlResponseStatus responseStatus = productService.fileUpload(file);
            return responseStatus.isSuccess() ? new ResponseEntity<>(responseStatus, HttpStatus.OK) :
                new ResponseEntity<>(responseStatus, HttpStatus.EXPECTATION_FAILED);
        } else {
            return new ResponseEntity<>(new ImageUrlResponseStatus("", false,
                "Failed to upload, file was empty."), HttpStatus.EXPECTATION_FAILED);
        }
    }
}