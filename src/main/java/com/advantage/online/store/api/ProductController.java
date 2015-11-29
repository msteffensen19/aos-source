package com.advantage.online.store.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.advantage.online.store.dto.AttributeItem;
import com.advantage.online.store.dto.ProductApiDto;
import com.advantage.online.store.dto.ProductInfoDto;
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

    @RequestMapping(value = "/product/products", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getAllProducts(HttpServletRequest request, HttpServletResponse response) {
        List<Product> products = productService.getAllProducts();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/product/{product_id}", method = RequestMethod.GET)
    public ResponseEntity<ProductInfoDto> getProductById(@PathVariable ("product_id") Long id){
        Product product = productService.getProductById(id);

        ProductInfoDto dto = new ProductInfoDto(product);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(value = "/product/create", method = RequestMethod.POST)
    public ResponseEntity<ProductResponseStatus> createProduct(@RequestBody ProductApiDto product) {
        if(product == null)  {
            return new ResponseEntity<>(new ProductResponseStatus(false, -1, "Data not valid"), HttpStatus.NO_CONTENT);
        }

        ProductResponseStatus responseStatus = productService.createProduct(product);

        return responseStatus.isSuccess() ? new ResponseEntity<>(responseStatus, HttpStatus.OK) :
            new ResponseEntity<>(responseStatus, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/product/update/{product_id}", method = RequestMethod.POST)
    public ResponseEntity<ProductResponseStatus> updateProduct(@RequestBody ProductApiDto product, @PathVariable ("product_id") Long id) {
        ProductResponseStatus responseStatus = productService.updateProduct(product, id);
        return new ResponseEntity<>(responseStatus, HttpStatus.OK);
    }
}