package com.advantage.online.store.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.advantage.online.store.dto.AttributeItem;
import com.advantage.online.store.dto.ProductApiDto;
import com.advantage.online.store.dto.ProductResponseStatus;
import com.advantage.online.store.model.attribute.Attribute;
import com.advantage.online.store.model.category.Category;
import com.advantage.online.store.model.product.Product;
import com.advantage.online.store.model.product.ProductAttributes;
import com.advantage.online.store.services.AttributeService;
import com.advantage.online.store.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.advantage.online.store.Constants;
import com.advantage.online.store.services.ProductService;
import com.advantage.util.ArgumentValidationHelper;
import com.advantage.util.HttpServletHelper;

@RestController
@RequestMapping(value = Constants.URI_API)
public class ProductController {

    private static final String REQUEST_PARAM_CATEGORY_ID = "category_id";

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttributeService attributeService;

    @RequestMapping(value = "products", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getAllProducts(HttpServletRequest request, HttpServletResponse response) {
        List<Product> products = productService.getAllProducts();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "categoryProducts", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getCategoryProducts(final HttpServletRequest request,
                        final HttpServletResponse response,
                        @RequestParam(ProductController.REQUEST_PARAM_CATEGORY_ID) Long categoryId) {
        ArgumentValidationHelper.validateArgumentIsNotNull(request, "http servlet request");
        ArgumentValidationHelper.validateArgumentIsNotNull(response, "http servlet response");
        HttpServletHelper.validateParametersExistenceInRequest(request, true, ProductController.REQUEST_PARAM_CATEGORY_ID);
        final List<Product> products = productService.getCategoryProducts(categoryId);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseEntity<ProductResponseStatus> createProduct(@RequestBody ProductApiDto product) {
        if(product == null)  {
            return new ResponseEntity<>(new ProductResponseStatus(false, -1, "Data not valid"), HttpStatus.NO_CONTENT);
        }

        ProductResponseStatus responseStatus = productService.createProduct(product);

        return responseStatus.isSuccess() ? new ResponseEntity<>(responseStatus, HttpStatus.OK) :
            new ResponseEntity<>(responseStatus, HttpStatus.BAD_REQUEST);
    }



}