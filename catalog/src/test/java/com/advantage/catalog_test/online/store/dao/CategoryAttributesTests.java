package com.advantage.catalog_test.online.store.dao;

import com.advantage.catalog.store.dao.attribute.AttributeRepository;
import com.advantage.catalog.store.dao.category.CategoryRepository;
import com.advantage.catalog.store.model.attribute.Attribute;
import com.advantage.catalog.store.model.category.Category;
import com.advantage.catalog_test.cfg.AdvantageTestContextConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Moti Ostrovski on 02/02/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AdvantageTestContextConfiguration.class})

public class CategoryAttributesTests extends GenericRepositoryTests{
    public static final int CATEGORY_NUMBER = 5;
    @Qualifier("categoryRepository")
    @Autowired
    private CategoryRepository categoryRepository;

    @Qualifier("attributeRepository")
    @Autowired
    private AttributeRepository attributeRepository;

    @Test
    public void testCategoriesFilled() throws IOException {
        final TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        final TransactionStatus transactionForCreation = transactionManager.getTransaction(transactionDefinition);

        //create categories
        //System.out.println("creating 5 categories...");
        Category category = null;
        category = categoryRepository.createCategory("LAPTOPS", "1235");
        category = categoryRepository.createCategory("HEADPHONES", "1234");
        category = categoryRepository.createCategory("TABLETS", "1236");
        category = categoryRepository.createCategory("SPEAKERS", "1237");
        category = categoryRepository.createCategory("MICE", "1238");

        //System.out.println("COMMITing 5 categories.");
        transactionManager.commit(transactionForCreation);

        //System.out.println("Going to retrieve categories from table...");
        final List<Category> categories = categoryRepository.getAll();
       // System.out.println("Retrieved " + categories.size() + " categories from table");

        Assert.assertEquals("Error! Expecting " + CATEGORY_NUMBER + " categories, but got " + categories.size(), CATEGORY_NUMBER, categories.size());

        final TransactionStatus transactionStatusForDeletion = transactionManager.getTransaction(transactionDefinition);

        //delete categories
        for(Category selected : categories) {
            categoryRepository.delete(selected);
        }

        transactionManager.commit(transactionStatusForDeletion);
    }

    @Test
    public void testAttributesFilled() throws IOException {
        final TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        final TransactionStatus transactionForCreation = transactionManager.getTransaction(transactionDefinition);

        String[] newAttributes = new String[]{"GRAPHICS", "Customization", "Operating System", "Processor", "Memory", "Display", "CONNECTOR", "COMPATIBILITY", "WEIGHT", "Wireless technology", "Sensor resolution", "Type", "Manufacturer"};

        Map<String, Attribute> defAttributes = new HashMap<>();

        System.out.println("adding 13 attributes to AttributeRepository");
        for (String attrib : newAttributes) {
            Attribute attribute = null;
            attribute =attributeRepository.create(attrib);
            defAttributes.put(attrib.toUpperCase(), attribute);

        }
        System.out.println("commiting 13 attributes");
        transactionManager.commit(transactionForCreation);
    }

}
