/**
* Created by kubany on 10/11/2015.
*/
package com.advantage.online.store.init;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.advantage.online.store.image.ImageManagement;
import com.advantage.online.store.image.ManagedImage;
import com.advantage.online.store.model.Category;
import com.advantage.online.store.model.Deal;
import com.advantage.online.store.model.DealType;
import com.advantage.online.store.model.Product;

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
        
        Product product = new Product("HP EliteBook Folio", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", 550, category1);
        product.setColorName("yellow");
        session.persist(product);
        session.persist(new Product("LG G3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", 400, category1));
        session.persist(new Product("Samsung Galaxy S5", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", 600, category1));
        session.persist(new Product("Samsung Galaxy S4", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", 300, category1));
        session.persist(new Product("iPhone 6S", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", 1000, category1));

        session.persist(new Deal(DealType.DAILY,  "30% OFF", "Up to $40 off select bose speakers with purchase of this pc", product));
        session.persist(new Deal(DealType.WEEKLY,  "weekly", "weekly", product));

        transaction.commit();
    }
}