package com.advantage.online.store.init;

import com.advantage.online.store.dao.category.CategoryRepository;
import com.advantage.online.store.dto.AttributeItem;
import com.advantage.online.store.dto.CategoryDto;
import com.advantage.online.store.dto.ProductDto;
import com.advantage.online.store.dto.PromotedProductDto;
import com.advantage.online.store.model.Deal;
import com.advantage.online.store.model.attribute.Attribute;
import com.advantage.online.store.model.category.Category;
import com.advantage.online.store.model.product.Product;
import com.advantage.online.store.model.product.ProductAttributes;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@Component
public class DataSourceInit4Json {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    CategoryRepository categoryRepository;

    public void init() throws Exception {

        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        final Category category1 = new Category("LAPTOPS", "1235");
        session.persist(category1);

        final Category category2 = new Category("HEADPHONES", "1234");
        session.persist(category2);

        final Category category3 = new Category("TABLETS", "1236");
        session.persist(category3);

        final Category category4 = new Category("SPEAKERS", "1237");
        session.persist(category4);

        final Category category5 = new Category("MICE", "1238");
        session.persist(category5);

        final Category category6 = new Category("BAGS & CASES", "1239");
        session.persist(category6);

        /*Attributes INIT*/
        Map<String, Attribute> defAttributes = new HashMap<>();
        Attribute attribute1 = new Attribute();
        attribute1.setName("Operating System");

        Attribute attribute2 = new Attribute();
        attribute2.setName("Price");

        Attribute attribute3 = new Attribute();
        attribute3.setName("Color");

        Attribute attribute4 = new Attribute();
        attribute4.setName("Customization");

        Attribute attribute5 = new Attribute();
        attribute5.setName("Processor");

        Attribute attribute6 = new Attribute();
        attribute6.setName("Display");

        Attribute attribute7 = new Attribute();
        attribute7.setName("Memory");
        transaction.commit();

        defAttributes.put(attribute1.getName().toUpperCase(), attribute1);
        defAttributes.put(attribute2.getName().toUpperCase(), attribute2);
        defAttributes.put(attribute3.getName().toUpperCase(), attribute3);
        defAttributes.put(attribute4.getName().toUpperCase(), attribute4);
        defAttributes.put(attribute5.getName().toUpperCase(), attribute5);
        defAttributes.put(attribute6.getName().toUpperCase(), attribute6);
        defAttributes.put(attribute7.getName().toUpperCase(), attribute7);

        for (Map.Entry<String, Attribute> entry : defAttributes.entrySet()) {
            session.save(entry.getValue());
        }
        /**/

        /*Load JSON File*/
        String filePath = getPath() + "\\src\\main\\webapp\\app\\categoryProducts_4.json";
        File json = new File(filePath);
        ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CategoryDto dto = objectMapper.readValue(json, CategoryDto.class);
        Category category = categoryRepository.getCategory(dto.getId());

        transaction = session.beginTransaction();

        Map<Long, Product> productMap = new HashMap<>();

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
    }

    private String getPath() throws UnsupportedEncodingException {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        String fullPath = URLDecoder.decode(path, "UTF-8");
        String pathArr[] = fullPath.split("/target/");
        fullPath = pathArr[0];

        return fullPath.substring(1);
    }
}
