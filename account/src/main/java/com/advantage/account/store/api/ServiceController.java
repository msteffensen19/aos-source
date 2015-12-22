package com.advantage.account.store.api;

import com.advantage.account.store.Constants;
import com.advantage.account.store.dao.category.CategoryRepository;
import com.advantage.account.store.dao.product.ProductRepository;
import com.advantage.account.store.log.AppUserAuthorize;
import com.advantage.account.store.model.deal.Deal;
import com.advantage.account.store.user.dto.AppUserConfigurationResponseStatus;
import com.advantage.account.store.user.services.AppUserConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/service" + Constants.URI_API + "/v1")
public class ServiceController {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AppUserConfigurationService appUserConfigurationService;

//    @RequestMapping(value = "/initdb", method = RequestMethod.POST)
//    public Boolean initDb(@RequestBody CategoryDto dto) {
//        try {
//            Category category = categoryRepository.get(dto.getCategoryId());
//            SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
//
//            Session session = sessionFactory.openSession();
//            Transaction transaction = session.beginTransaction();
//
//            Map<Long, Product> productMap = new HashMap<>();
//            List<Product> productList = productRepository.getAll();
//            for (Product product : productList) {
//                productMap.put(product.getId(), product);
//            }
//
//            Map<String, Attribute> defAttributes = new HashMap<>();
//
//            List<Attribute> attributeList = attributeService.getAllAttributes();
//            for (Attribute attribute : attributeList) {
//                defAttributes.put(attribute.getName().toUpperCase(), attribute);
//            }
//
//            PRODUCT
//            for (ProductDto p : dto.getProducts()) {
//                Product product = new Product(p.getProductName(), p.getDescription(), p.getPrice(), category);
//                product.setManagedImageId(p.getImageUrl());
//                session.persist(product);
//                //load attributes
//                for (AttributeItem a : p.getAttributes()) {
//                    ProductAttributes attributes = new ProductAttributes();
//                    attributes.setProduct(product);
//                    attributes.setAttribute(defAttributes.get(a.getAttributeName().toUpperCase()));
//                    attributes.setAttributeValue(a.getAttributeValue());
//
//                    session.save(attributes);
//                }
//
//                productMap.put(product.getId(), product);
//            }
//
//            PromotedProductDto p = dto.getPromotedProduct();
//            Product parent = productMap.get(p.getId());
//
//            Deal deal = new Deal(10, parent.getDescription(), p.getPromotionHeader(), p.getPromotionSubHeader(), p.getStaringPrice(),
//                p.getPromotionImageId(), 0, "", "",  parent);
//
//            session.persist(deal);
//            transaction.commit();
//
//            return true;
//        }
//        catch (Exception e) {
//            return false;
//        }
//    }

    @RequestMapping(value = "/clientConfiguration", method = RequestMethod.GET)
    public ResponseEntity<AppUserConfigurationResponseStatus> getAllConfigurationParameters(HttpServletRequest request) {

        AppUserConfigurationResponseStatus appUserConfigurationResponseStatus = appUserConfigurationService.getAllConfigurationParameters();

        return new ResponseEntity<>(appUserConfigurationResponseStatus, (appUserConfigurationResponseStatus != null ? HttpStatus.OK : HttpStatus.NOT_FOUND));

    }

    @RequestMapping(value = "/tests/auth_method", method = RequestMethod.GET)
    @AppUserAuthorize
    public ResponseEntity<Deal> testAuthMethod(@RequestHeader(value = "User-Token") String token,
                                               final HttpServletRequest request,
                                               final HttpServletResponse response) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
