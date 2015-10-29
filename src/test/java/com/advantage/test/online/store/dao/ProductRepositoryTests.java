package com.advantage.test.online.store.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;

import com.advantage.online.store.dao.CategoryRepository;
import com.advantage.online.store.dao.ProductRepository;
import com.advantage.online.store.model.Category;
import com.advantage.online.store.model.Product;
import com.advantage.test.cfg.AdvantageTestContextConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AdvantageTestContextConfiguration.class})
public class ProductRepositoryTests extends GenericRepositoryTests {

	@Autowired
    private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;

	@Test
	public void testGetCategoryProductsFetch() {

    	final TransactionStatus transactionStatusForCreation = transactionManager.getTransaction(transactionDefinition);
    	final Category category1 = categoryRepository.createCategory("LAPTOPS",
   	                                                                new byte[]{1, 2, 3, 4});
    	final Category category2 = categoryRepository.createCategory("CELL PHONES",
                                                                     new byte[]{1, 2, 3, 4});
    	final Category category3 = categoryRepository.createCategory("KEYBOARDS",
                                                                     new byte[]{1, 2, 3, 4});
    	final int CATEGORY1_PRODUCTS_COUNT = 10;
    	final int CATEGORY2_PRODUCTS_COUNT = 5;

    	for (int i = 0; i < CATEGORY1_PRODUCTS_COUNT; i++) {

    		final String productName = "product" + i;
			productRepository.createProduct("LG G3", productName, 400, category1);
    	}

    	for (int i = 0; i < CATEGORY2_PRODUCTS_COUNT; i++) {

    		final String nameAndDescriptionForTest = "product" + i;
			productRepository.createProduct(nameAndDescriptionForTest,
					                        nameAndDescriptionForTest, 400, category2);
    	}

    	transactionManager.commit(transactionStatusForCreation);
    	final List<Product> category1Products = productRepository.getCategoryProducts(category1);
    	Assert.assertEquals(CATEGORY1_PRODUCTS_COUNT, category1Products.size());
    	final List<Product> category2Products = productRepository.getCategoryProducts(category2);
    	Assert.assertEquals(CATEGORY2_PRODUCTS_COUNT, category2Products.size());
    	final List<Product> category3Products = productRepository.getCategoryProducts(category3);
    	Assert.assertTrue(category3Products.isEmpty());
    	final TransactionStatus transactionStatusForDeletion = transactionManager.getTransaction(transactionDefinition);
    	productRepository.deleteProducts(category1Products);
    	productRepository.deleteProducts(category2Products);
    	categoryRepository.deleteCategories(category1, category2, category3);
    	transactionManager.commit(transactionStatusForDeletion);
	}
}