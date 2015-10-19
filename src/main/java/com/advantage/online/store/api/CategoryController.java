package com.advantage.online.store.api;

import com.advantage.online.store.Constants;
import com.advantage.online.store.model.Category;
import com.advantage.online.store.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by kubany on 10/18/2015.
 */
@RestController
@RequestMapping(value = Constants.URI_API)
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "category", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllCategories(HttpServletRequest request, HttpServletResponse response) {
        List<Category> category = categoryService.getAllCategories();
        return new ResponseEntity<Object>(category, HttpStatus.OK);
    }
}
