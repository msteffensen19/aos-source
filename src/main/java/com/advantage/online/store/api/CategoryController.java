package com.advantage.online.store.api;

import com.advantage.online.store.Constants;
import com.advantage.online.store.dto.AttributeDto;
import com.advantage.online.store.dto.CategoryDto;
import com.advantage.online.store.dto.ProductDto;
import com.advantage.online.store.dto.PromotedProductDto;
import com.advantage.online.store.model.Deal;
import com.advantage.online.store.model.product.Product;
import com.advantage.online.store.model.category.Category;
import com.advantage.online.store.model.product.ProductAttributes;
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
    /* @Autowired
     private EntityAttributeService entityAttributeService;*/
    @Autowired
    private ProductService productService;
   /* @Autowired
    private AttributeTitleService attributeTitleService;*/

    @Autowired
    private DealService dealService;

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllCategories(HttpServletRequest request, HttpServletResponse response) {
        List<Category> category = categoryService.getAllCategories();
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @RequestMapping(value = "/categoryData/{category_id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCategoryData(@PathVariable("category_id") String id) {

        /*ArgumentValidationHelper.validateArgumentIsNotNull(request, "http servlet request");
        ArgumentValidationHelper.validateArgumentIsNotNull(response, "http servlet response");
        HttpServletHelper.validateParametersExistenceInRequest(request, true,
            RequestParameters.CATEGORY_ID);*/
        //final String categoryIdParameter = request.getParameter(RequestParameters.CATEGORY_ID);

        final Long categoryId = Long.valueOf(id);

        final Category category = categoryService.getCategory(categoryId);

        final CategoryDto categoryDto = new CategoryDto();

        categoryDto.applyCategory(category);

        final List<Product> categoryProducts = productService.getCategoryProducts(categoryId);

        Map<String, Set<String>> attrCollection = new HashMap<>();

        for (Product product : categoryProducts) {
            Set<ProductAttributes> productAttributes = product.getProductAttributes();
            for (ProductAttributes attribute : productAttributes) {
                String attrName = attribute.getAttribute().getName();
                String attrValue = attribute.getAttributeValue();

                Set<String> item = attrCollection.get(attrName);
                if(item == null){
                    item = new HashSet<>();
                    attrCollection.put(attrName, item);
                }
                item.add(attrValue);
            }
        }

        List<AttributeDto> attributeItems = new ArrayList<>();

        for (Map.Entry<String, Set<String>> item : attrCollection.entrySet()) {
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
        Deal promotion = dealService.getDealOfTheDay();
        categoryDto.setPromotedProduct(new PromotedProductDto(promotion.getStaringPrice(), promotion.getPromotionHeader(),
            promotion.getPromotionSubHeader(), promotion.getManagedImageId(),
            new ProductDto(dealService.getDealOfTheDay().getProduct())));

        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }
}