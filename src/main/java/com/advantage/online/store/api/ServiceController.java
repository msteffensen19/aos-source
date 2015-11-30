package com.advantage.online.store.api;

import com.advantage.online.store.Constants;
import com.advantage.online.store.config.ImageManagementConfiguration;
import com.advantage.online.store.dao.product.ProductRepository;
import com.advantage.online.store.dao.category.CategoryRepository;
import com.advantage.online.store.dto.*;
import com.advantage.online.store.image.ImageManagement;
import com.advantage.online.store.image.ImageManagementAccess;
import com.advantage.online.store.image.ManagedImage;
import com.advantage.online.store.model.deal.Deal;
import com.advantage.online.store.model.attribute.Attribute;
import com.advantage.online.store.model.category.Category;
import com.advantage.online.store.model.product.Product;
import com.advantage.online.store.model.product.ProductAttributes;
import com.advantage.online.store.services.AttributeService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = Constants.URI_API + "/service")
public class ServiceController {
    @Autowired
    AttributeService attributeService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private ProductRepository productRepository;


    @RequestMapping(value = "/initdb", method = RequestMethod.POST)
    public Boolean initDb(@RequestBody CategoryDto dto) {
        try {
            Category category = categoryRepository.get(dto.getId());
            SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Map<Long, Product> productMap = new HashMap<>();
            List<Product> productList = productRepository.getAll();
            for (Product product : productList) {
                productMap.put(product.getId(), product);
            }

            Map<String, Attribute> defAttributes = new HashMap<>();

            List<Attribute> attributeList = attributeService.getAllAttributes();
            for (Attribute attribute : attributeList) {
                defAttributes.put(attribute.getName().toUpperCase(), attribute);
            }

            /*PRODUCT*/
            for (ProductDto p : dto.getProducts()) {
                Product product = new Product(p.getProductName(), p.getDescription(), p.getPrice(), category);
                product.setManagedImageId(p.getImageUrl());
                session.persist(product);
                //load attributes
                for (AttributeItem a : p.getAttributes()) {
                    ProductAttributes attributes = new ProductAttributes();
                    attributes.setProduct(product);
                    attributes.setAttribute(defAttributes.get(a.getAttributeName().toUpperCase()));
                    attributes.setAttributeValue(a.getAttributeValue());

                    session.save(attributes);
                }

                productMap.put(product.getId(), product);
            }

            PromotedProductDto p = dto.getPromotedProduct();
            Product parent = productMap.get(p.getId());

            Deal deal = new Deal(10, parent.getDescription(), p.getPromotionHeader(), p.getPromotionSubHeader(), p.getStaringPrice(),
                p.getPromotionImageId(), 0, "", "",  parent);

            session.persist(deal);
            transaction.commit();

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Autowired
    private Environment environment;

    @RequestMapping(value="/uploadImage", method=RequestMethod.POST)
    public ResponseEntity<ImageUrlResponseStatus> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String imageManagementRepository = environment.getProperty(ImageManagementConfiguration.PROPERTY_IMAGE_MANAGEMENT_REPOSITORY);
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                ImageManagement imageManagement = ImageManagementAccess.getImageManagement(
                    ImageManagementConfiguration.getPath(imageManagementRepository));

                ManagedImage managedImage = imageManagement.addManagedImage(bytes, file.getOriginalFilename(), true);
                imageManagement.persist();

                return new ResponseEntity<>(new ImageUrlResponseStatus(managedImage.getId(), true,
                    "Image successfully uploaded"), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ImageUrlResponseStatus("", false,
                    "Failed to upload " + e.getMessage()), HttpStatus.EXPECTATION_FAILED);
            }
        } else {
            return new ResponseEntity<>(new ImageUrlResponseStatus("", false,
                "Failed to upload, file was empty."), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
