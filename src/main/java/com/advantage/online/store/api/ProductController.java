package com.advantage.online.store.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.advantage.online.store.model.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.advantage.online.store.Constants;
import com.advantage.online.store.services.ProductService;
import com.advantage.util.ArgumentValidationHelper;
import com.advantage.util.HttpServletHelper;

/**
 * Created by kubany on 10/13/2015.
 */
@RestController
@RequestMapping(value = Constants.URI_API)
public class ProductController {

    private static final String REQUEST_PARAM_CATEGORY_ID = "category_id";

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "products", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllProducts(HttpServletRequest request, HttpServletResponse response) {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<Object>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "categoryProducts", method = RequestMethod.GET)
    public ResponseEntity<Object> getCategoryProducts(final HttpServletRequest request,
                                                      final HttpServletResponse response) {

        ArgumentValidationHelper.validateArgumentIsNotNull(request, "http servlet request");
        ArgumentValidationHelper.validateArgumentIsNotNull(response, "http servlet response");
        HttpServletHelper.validateParametersExistenceInRequest(request, true,
            ProductController.REQUEST_PARAM_CATEGORY_ID);
        final String categoryIdParameter = request.getParameter(ProductController.REQUEST_PARAM_CATEGORY_ID);
        final Long categoryId = Long.valueOf(categoryIdParameter);
        final List<Product> products = productService.getCategoryProducts(categoryId);
        return new ResponseEntity<Object>(products, HttpStatus.OK);
    }
}