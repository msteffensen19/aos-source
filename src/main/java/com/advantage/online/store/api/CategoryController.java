package com.advantage.online.store.api;

import com.advantage.online.store.Constants;
import com.advantage.online.store.dto.AttributeDto;
import com.advantage.online.store.dto.CategoryDto;
import com.advantage.online.store.dto.ProductDto;
import com.advantage.online.store.dto.PromotedProductDto;
import com.advantage.online.store.model.attribute.Attribute;
import com.advantage.online.store.model.deal.Deal;
import com.advantage.online.store.model.product.Product;
import com.advantage.online.store.model.category.Category;
import com.advantage.online.store.model.product.ProductAttributes;
import com.advantage.online.store.services.AttributeService;
import com.advantage.online.store.services.CategoryService;
import com.advantage.online.store.services.DealService;
import com.advantage.online.store.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

@RestController
@RequestMapping(value = Constants.URI_API)
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity<List<Category>> getAllCategories(HttpServletRequest request, HttpServletResponse response) {
        List<Category> category = categoryService.getAllCategories();

        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}