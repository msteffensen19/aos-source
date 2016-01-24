package com.advantage.catalog.store.api;

import com.advantage.catalog.store.model.category.Category;
import com.advantage.catalog.store.model.deal.Deal;
import com.advantage.catalog.store.model.product.Product;
import com.advantage.catalog.store.services.AttributeService;
import com.advantage.catalog.store.services.CategoryService;
import com.advantage.catalog.store.services.DealService;
import com.advantage.catalog.store.services.ProductService;
import com.advantage.catalog.util.ArgumentValidationHelper;
import com.advantage.common.Constants;
import com.advantage.common.dto.*;
import com.advantage.common.security.AuthorizeAsAdmin;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = Constants.URI_API + "/v1")
public class CatalogController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttributeService attributeService;
    @Autowired
    private DealService dealService;

    //region /products
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<ProductCollectionDto> getAllProducts(HttpServletRequest request) {
        return new ResponseEntity<>(productService.getProductCollectionDto(), HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{product_id}", method = RequestMethod.GET)
    public ResponseEntity<ProductDto> getProductById(@PathVariable("product_id") Long id,
                                                     HttpServletRequest request) {
        Product product = productService.getProductById(id);
        if (product == null) return new ResponseEntity<>(HttpStatus.CONFLICT);
        ProductDto dto = productService.getDtoByEntity(product);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @AuthorizeAsAdmin
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = false, dataType = "string", paramType = "header", value = "JSON Web Token", defaultValue = "Bearer ")})
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductDto product,
                                                               HttpServletRequest request) {
        if (product == null) {
            return new ResponseEntity<>(new ProductResponseDto(false, -1, "Data not valid"), HttpStatus.NO_CONTENT);
        }

        ProductResponseDto responseStatus = productService.createProduct(product);

        return responseStatus.isSuccess() ? new ResponseEntity<>(responseStatus, HttpStatus.OK) :
                new ResponseEntity<>(responseStatus, HttpStatus.BAD_REQUEST);
    }

    @AuthorizeAsAdmin
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = false, dataType = "string", paramType = "header", value = "JSON Web Token", defaultValue = "Bearer ")})
    @RequestMapping(value = "/products/images", method = RequestMethod.POST)
    public ResponseEntity<ProductResponseDto> createProductWithImage(@RequestParam("product") String product,
                                                                        @RequestParam("file") MultipartFile file,
                                                                        HttpServletRequest request) {
        ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD,
                JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        ProductDto dto = null;
        try {
            dto = objectMapper.readValue(product, ProductDto.class);
        } catch (IOException e) {
            e.printStackTrace();
            new ResponseEntity<>(new ProductResponseDto(false, -1, "json not valid"), HttpStatus.BAD_REQUEST);
        }

        if (dto == null) {
            return new ResponseEntity<>(new ProductResponseDto(false, -1, "Data not valid"), HttpStatus.NO_CONTENT);
        }
        if (file.isEmpty()) {
            return new ResponseEntity<>(new ProductResponseDto(false, -1, "File was empty"), HttpStatus.NO_CONTENT);
        }

        ImageUrlResponseDto imageResponseStatus = productService.fileUpload(file);

        if (!imageResponseStatus.isSuccess()) {
            return new ResponseEntity<>(new ProductResponseDto(false, -1, imageResponseStatus.getReason()),
                    HttpStatus.BAD_REQUEST);
        }
        dto.setImageUrl(imageResponseStatus.getId());
        ProductResponseDto responseStatus = productService.createProduct(dto);
        responseStatus.setImageId(imageResponseStatus.getId());

        return responseStatus.isSuccess() ? new ResponseEntity<>(responseStatus, HttpStatus.OK) :
                new ResponseEntity<>(responseStatus, HttpStatus.BAD_REQUEST);
    }

    @AuthorizeAsAdmin
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = false, dataType = "string", paramType = "header", value = "JSON Web Token", defaultValue = "Bearer ")})
    @RequestMapping(value = "/products/{product_id}", method = RequestMethod.PUT)
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody ProductDto product,
                                                               @PathVariable("product_id") Long id,
                                                               HttpServletRequest request) {
        ProductResponseDto responseStatus = productService.updateProduct(product, id);
        return new ResponseEntity<>(responseStatus, HttpStatus.OK);
    }

    @RequestMapping(value = "/products/search", method = RequestMethod.GET)
    @ApiOperation(value = "Search product by Name")
    public ResponseEntity<List<CategoryDto>> searchProductByName(@RequestParam("name") String name,
                                                                 @RequestParam(value = "quantityPerEachCategory",
                                                                         defaultValue = "-1", required = false) Integer quantity,
                                                                 HttpServletRequest request) {
        if (quantity == 0) return new ResponseEntity<>(HttpStatus.OK);
        List<Product> products = productService.filterByName(name, quantity);
        if (products == null) return new ResponseEntity<>(HttpStatus.OK);
        List<ProductDto> productDtos = productService.fillPureProducts(products);
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Product product : products) {
            if (categoryDtos.stream()
                    .anyMatch(x -> Long.compare(x.getCategoryId(), product.getCategory().getCategoryId()) == 0)) {
                continue;
            }
            Category category = product.getCategory();
            CategoryDto categoryDto = categoryService.getCategoryDto(category);
            List<ProductDto> productsDtoList = productDtos.stream()
                    .filter(x -> Long.compare(x.getCategoryId(), categoryDto.getCategoryId()) == 0)
                    .collect(Collectors.toList());
            if (categoryDto.getProducts() == null) {
                if (productsDtoList.size() > quantity) {
                    productsDtoList = quantity == -1 ? productsDtoList : productsDtoList.subList(0, quantity);
                }
                categoryDto.setProducts(productsDtoList);
            }
            categoryDtos.add(categoryDto);
        }
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    //endregion
    //region /categories
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity<List<Category>> getAllCategories(HttpServletRequest request) {
        List<Category> category = categoryService.getAllCategories();

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @RequestMapping(value = "/categories/{category_id}/products", method = RequestMethod.GET)
    public ResponseEntity<CategoryDto> getCategoryData(@PathVariable("category_id") String id,
                                                       HttpServletRequest request) {
        Long categoryId = Long.valueOf(id);
        CategoryDto categoryDto = categoryService.getCategoryDto(categoryId);

        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    //endregion
    @AuthorizeAsAdmin
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = false, dataType = "string", paramType = "header", value = "JSON Web Token", defaultValue = "Bearer ")})
    @RequestMapping(value = "/images", method = RequestMethod.POST)
    public ResponseEntity<ImageUrlResponseDto> imageUpload(@RequestParam("file") MultipartFile file,
                                                              HttpServletRequest request) {
        if (!file.isEmpty()) {
            ImageUrlResponseDto responseStatus = productService.fileUpload(file);
            return responseStatus.isSuccess() ? new ResponseEntity<>(responseStatus, HttpStatus.OK) :
                    new ResponseEntity<>(responseStatus, HttpStatus.EXPECTATION_FAILED);
        } else {
            return new ResponseEntity<>(new ImageUrlResponseDto("", false,
                    "Failed to upload, file was empty."), HttpStatus.EXPECTATION_FAILED);
        }
    }

    //region /deals
    @RequestMapping(value = "/deals", method = RequestMethod.GET)
    public ResponseEntity<List<Deal>> getAllDeals(final HttpServletRequest request,
                                                  final HttpServletResponse response) {

        ArgumentValidationHelper.validateArgumentIsNotNull(request, "http servlet request");
        ArgumentValidationHelper.validateArgumentIsNotNull(response, "http servlet response");
        final List<Deal> deals = dealService.getAllDeals();

        return new ResponseEntity<>(deals, HttpStatus.OK);
    }

    //@RequestMapping(value = "/catalog/deals/0", method = RequestMethod.GET)
    @RequestMapping(value = "/deals/search", method = RequestMethod.GET)
    public ResponseEntity<List<Deal>> getDealOfTheDay(@RequestParam(value = "dealOfTheDay", defaultValue = "false") boolean search,
                                                      final HttpServletRequest request,
                                                      final HttpServletResponse response) {

        if (!search) return new ResponseEntity<>(HttpStatus.OK);

        ArgumentValidationHelper.validateArgumentIsNotNull(request, "http servlet request");
        ArgumentValidationHelper.validateArgumentIsNotNull(response, "http servlet response");
        List<Deal> deals = new ArrayList<>();
        Deal dealOfTheDay = dealService.getDealOfTheDay();
        deals.add(dealOfTheDay);

        return new ResponseEntity<>(deals, HttpStatus.OK);
    }
    //endregion

}