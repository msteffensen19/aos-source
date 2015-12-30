package com.advantage.catalog.store.init;

import com.advantage.catalog.store.dao.category.CategoryRepository;
import com.advantage.catalog.store.model.attribute.Attribute;
import com.advantage.catalog.store.model.category.Category;
import com.advantage.catalog.store.model.deal.Deal;
import com.advantage.catalog.store.model.product.Product;
import com.advantage.catalog.store.model.product.ProductAttributes;
import com.advantage.catalog.store.services.ProductService;
import com.advantage.root.store.dto.AttributeItem;
import com.advantage.root.store.dto.CategoryDto;
import com.advantage.root.store.dto.ProductDto;
import com.advantage.root.store.dto.PromotedProductDto;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.persistence.EntityManagerFactory;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceInit4Json {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

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

        String[] newAttributes = new String[]{"GRAPHICS", "Customization", "Operating System", "Processor", "Memory", "Display", "CONNECTOR", "COMPATIBILITY", "WEIGHT", "Wireless technology"};

        Map<String, Attribute> defAttributes = new HashMap<>();

        for (String attrib : newAttributes) {
            Attribute attribute = new Attribute();
            attribute.setName(attrib);
            session.persist(attribute);
            defAttributes.put(attrib.toUpperCase(), attribute);
        }
        transaction.commit();
        for (Map.Entry<String, Attribute> entry : defAttributes.entrySet()) {
            session.save(entry.getValue());
        }

        ClassPathResource filePath = new ClassPathResource("categoryProducts_4.json");
        File json = filePath.getFile();

        ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CategoryDto[] dtos = objectMapper.readValue(json, CategoryDto[].class);
        transaction = session.beginTransaction();
        Map<Long, Product> productMap = new HashMap<>();
        for (CategoryDto dto : dtos) {
            Category category = categoryRepository.get(dto.getCategoryId());

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

                if (p.getImages().size() == 0) {
                    p.getImages().add(product.getManagedImageId());
                }

                //TODO move to the productService
                product.setColors(productService.getColorAttributes(p.getColors(), product));
                product.setImages(productService.getImageAttribute(p.getImages(), product));

                productMap.put(product.getId(), product);
            }

            PromotedProductDto p = dto.getPromotedProduct();
            Long prodId = p.getId();
            Product parent = productMap.get(prodId);
            Assert.notNull(parent, "PromotedProduct null, promoted product id=" + prodId + ", category number=" + dto.getCategoryId());
            Deal deal = new Deal(10, parent.getDescription(), p.getPromotionHeader(), p.getPromotionSubHeader(), p.getStaringPrice(),
                    p.getPromotionImageId(), 0, "", "", parent);

            session.persist(deal);

        }
        transaction.commit();
    }

    private String getPath() throws IOException {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        String fullPath = URLDecoder.decode(path, "UTF-8");
        String pathArr[] = fullPath.split("/target/");
        fullPath = pathArr[0];

        return fullPath.substring(1);

        /*File catalinaBase = new File(System.getProperty("catalina.base")).getAbsoluteFile();
        File propertyFile = new File(catalinaBase, "webapps/root/app");

        return propertyFile.getAbsolutePath();*/

        // return new java.io.File( ".").getCanonicalPath().split("bin")[0];
    }

    /*private Set<ImageAttribute> getImageAttribute(Collection<String> images, Product product) {
        Set<ImageAttribute> imageAttributes = new HashSet<>();
        for (String s : images) {
            ImageAttribute image = new ImageAttribute(s);
            image.setProduct(product);
            imageAttributes.add(image);
        }

        return imageAttributes;
    }*/

   /* private  Set<ColorAttribute> getColorAttributes(Collection<ColorAttribute> colors, Product product) {
        Set<ColorAttribute> colorAttributes = new HashSet<>();
        for (ColorAttribute s : colors) {
            ColorAttribute color = new ColorAttribute(s.getName());
            color.setProduct(product);
            color.setInStock(s.getInStock());
            colorAttributes.add(color);
        }
        return colorAttributes;
    }*/
}
