/**
* Created by kubany on 10/11/2015.
*/
package com.advantage.online.store.init;

import com.advantage.online.store.model.Category;
import com.advantage.online.store.model.Deal;
import com.advantage.online.store.model.DealType;
import com.advantage.online.store.model.Product;
import com.advantage.util.IOHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class DataSourceInit {
    @Autowired
    private EntityManagerFactory entityManagerFactory;


    public void init() throws Exception {

        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Category category = new Category("LAPTOPS", IOHelper.fileContentToByteArray("C:/Temp/advantage/Laptop.jpg"));
        session.persist(new Category("HEADPHONES", IOHelper.fileContentToByteArray("C:/Temp/advantage/Headphones.png")));
        session.persist(new Category("TABLETS", IOHelper.fileContentToByteArray("C:/Temp/advantage/Tablet.jpg")));
        session.persist(new Category("SPEAKERS", IOHelper.fileContentToByteArray("C:/Temp/advantage/Speakers.png")));
        session.persist(category);



        session.persist(new Category("MICE", IOHelper.fileContentToByteArray("C:/Temp/advantage/Mice.png")));
        session.persist(new Category("BAGS & CASES", IOHelper.fileContentToByteArray("C:/Temp/advantage/Bags.png")));

        Product product = new Product("HP EliteBook Folio", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", 550, category);
        session.persist(product);
        session.persist(new Product("LG G3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", 400, category));
        session.persist(new Product("Samsung Galaxy S5", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", 600, category));
        session.persist(new Product("Samsung Galaxy S4", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", 300, category));
        session.persist(new Product("iPhone 6S", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", 1000, category));

        session.persist(new Deal(DealType.DAILY,  "30% OFF", "Free shipping & free returns <br /> Up to $40", product));
        session.persist(new Deal(DealType.WEEKLY,  "weekly", "weekly", product));

        transaction.commit();
    }
}