/**
 * Created by kubany on 10/11/2015.
 */
package com.advantage.online.store.init;

import com.advantage.online.store.model.attribute.Attribute;
import com.advantage.online.store.model.category.Category;
import com.advantage.online.store.model.deal.Deal;
import com.advantage.online.store.model.deal.DealType;
import com.advantage.online.store.model.product.Product;
import com.advantage.online.store.model.product.ProductAttributes;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;


@Component
public class DataSourceInit {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public void init() throws Exception {

        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        final Category category1 = new Category("HEADPHONES", "1234");
        session.persist(category1);

        //final ManagedImage managedImage3 = imageManagement.addManagedImage("C:/Temp/advantage/Tablet.jpg", false);
        final Category category3 = new Category("TABLETS", "1236");
        session.persist(category3);

        //final ManagedImage managedImage4 = imageManagement.addManagedImage("C:/Temp/advantage/Speakers.png", false);
        final Category category4 = new Category("SPEAKERS", "1237");
        session.persist(category4);

        //final ManagedImage managedImage1 = imageManagement.addManagedImage("C:/Temp/advantage/Laptop.jpg", false);
        final Category category2 = new Category("LAPTOPS", "1235");
        session.persist(category2);

        //final ManagedImage managedImage5 = imageManagement.addManagedImage("C:/Temp/advantage/Mice.png", false);
        final Category category5 = new Category("MICE", "1238");
        session.persist(category5);

        //final ManagedImage managedImage6 = imageManagement.addManagedImage("C:/Temp/advantage/Bags.png", false);
        final Category category6 = new Category("BAGS & CASES", "1239");
        session.persist(category6);

        /*Attributes INIT*/
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
        /**/

        /*Products INIT*/
        Product product1 = new Product("HP EliteBook Folio", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", 550, category1);
        Product product2 = new Product("LG G3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", 400, category1);
        Product product3 = new Product("Samsung Galaxy S5", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", 600, category1);
        Product product4 = new Product("Samsung Galaxy S4", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", 300, category1);
        Product product5 = new Product("iPhone 6S", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", 1000, category1);
        product1.setManagedImageId("1234");
        product2.setManagedImageId("1234");
        product3.setManagedImageId("1234");
        product4.setManagedImageId("1234");
        product5.setManagedImageId("1234");

        session.persist(product1);
        session.persist(product2);
        session.persist(product3);
        session.persist(product4);
        session.persist(product5);

        /*Operating system*/
        ProductAttributes productAttribute1 = new ProductAttributes();
        productAttribute1.setProduct(product1);
        productAttribute1.setAttribute(attribute1);
        productAttribute1.setAttributeValue("Linux");

        /*price*/
        ProductAttributes productAttribute2 = new ProductAttributes();
        productAttribute2.setProduct(product1);
        productAttribute2.setAttribute(attribute2);
        productAttribute2.setAttributeValue("200");

        /*color*/
        ProductAttributes productAttribute3 = new ProductAttributes();
        productAttribute3.setProduct(product1);
        productAttribute3.setAttribute(attribute3);
        productAttribute3.setAttributeValue("FFFFFF");

        /*customization*/
        ProductAttributes productAttribute4 = new ProductAttributes();
        productAttribute4.setProduct(product1);
        productAttribute4.setAttribute(attribute4);
        productAttribute4.setAttributeValue("Gaming");

        /*processor*/
        ProductAttributes productAttribute5 = new ProductAttributes();
        productAttribute5.setProduct(product1);
        productAttribute5.setAttribute(attribute5);
        productAttribute5.setAttributeValue("intel(R) Core(TM) i5-6300HQ");

        /*display*/
        ProductAttributes productAttribute6 = new ProductAttributes();
        productAttribute6.setProduct(product1);
        productAttribute6.setAttribute(attribute6);
        productAttribute6.setAttributeValue("15.6-inch diagonal HD BrightView WLED-backlit Display (1366x768)");

        /*memory*/
        ProductAttributes productAttribute7 = new ProductAttributes();
        productAttribute7.setProduct(product1);
        productAttribute7.setAttribute(attribute7);
        productAttribute7.setAttributeValue("16GB DDR3 - 2 DIMM");

        List<ProductAttributes> productAttributes = new ArrayList<>();
        productAttributes.add(productAttribute1);
        productAttributes.add(productAttribute2);
        productAttributes.add(productAttribute3);
        productAttributes.add(productAttribute4);
        productAttributes.add(productAttribute5);
        productAttributes.add(productAttribute6);
        productAttributes.add(productAttribute7);

        session.persist(new Deal(DealType.DAILY, "Free shipping & free returns <br /> Up to $40",
                "EXPLORE THE NEW DESIGN", "Supremely thin, yet incredibly durable", "490", "1240",
                30, "2015-11-15 00:00:00", "2015-11-30 23:59:59", product1));
        session.persist(new Deal(DealType.WEEKLY, "Free shipping & free returns <br /> Up to $40",
                "EXPLORE THE NEW DESIGN", "Supremely thin, yet incredibly durable", "490", "1240",
                30, "2015-11-15 00:00:00", "2015-11-30 23:59:59", product1));

        session.persist(attribute1);
        session.persist(attribute2);
        session.persist(attribute3);
        session.persist(attribute4);
        session.persist(attribute5);
        session.persist(attribute6);
        session.persist(attribute7);

        productAttributes.forEach(session::save);



        /*EntityAttribute entityAttribute = new EntityAttribute();
        entityAttribute.setAttribute(attributeTitle);
        entityAttribute.setValue("Windows 7");
        entityAttribute.setEntityKey(category1.getCategoryId());
        entityAttribute.setEntityType(EntityType.CATEGORY.getEntityTypeCode());*/

        /*Product attributes INIT*/
        /*EntityAttribute entityAttribute2 = new EntityAttribute();
        entityAttribute2.setAttribute(attribute1);
        entityAttribute2.setValue("Windows 7");
        entityAttribute2.setEntityKey(product.getCategoryId());
        entityAttribute2.setEntityType(EntityType.PRODUCT.getEntityTypeCode());

        EntityAttribute entityAttribute4 = new EntityAttribute();
        entityAttribute4.setAttribute(attribute2);
        entityAttribute4.setValue("400");
        entityAttribute4.setEntityKey(product.getCategoryId());
        entityAttribute4.setEntityType(EntityType.PRODUCT.getEntityTypeCode());

        EntityAttribute entityAttribute5 = new EntityAttribute();
        entityAttribute5.setAttribute(attribute3);
        entityAttribute5.setValue("FFFFFF");
        entityAttribute5.setEntityKey(product.getCategoryId());
        entityAttribute5.setEntityType(EntityType.PRODUCT.getEntityTypeCode());*/



    /*    session.persist(entityAttribute2);
        session.persist(entityAttribute4);*/
        //session.persist(entityAttribute5);

       /* CategoryAttributeTitleAssociation association = new CategoryAttributeTitleAssociation();
        association.setAttribute(attribute1);
        association.setCategory(category1);
        session.persist(association);

        EntityAttribute entityAttribute3 = new EntityAttribute();
        entityAttribute3.setAttribute(attribute1);
        entityAttribute3.setValue("Windows 7");
        entityAttribute3.setEntityKey(attribute1.getId());
        entityAttribute3.setEntityType(EntityType.ATTRIBUTE_TITLE.getEntityTypeCode());*/

        transaction.commit();
    }
}