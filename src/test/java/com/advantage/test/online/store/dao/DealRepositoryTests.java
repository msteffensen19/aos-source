package com.advantage.test.online.store.dao;

import java.io.IOException;
//import java.io.IOException;
import java.util.List;

import com.advantage.online.store.dao.category.CategoryRepository;
import com.advantage.online.store.model.product.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;


import com.advantage.online.store.dao.DealRepository;
import com.advantage.online.store.dao.ProductRepository;
import com.advantage.online.store.model.category.Category;
import com.advantage.online.store.model.Deal;
import com.advantage.online.store.model.DealType;
import com.advantage.test.cfg.AdvantageTestContextConfiguration;

/**
 * @author Binyamin Regev
 * JUnit tests on {@link DealRepository}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AdvantageTestContextConfiguration.class})
public class DealRepositoryTests extends GenericRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DealRepository dealRepository;

    @Test
    public void testCreatDeal_VerifyDateFromStringFormat() {
    	final TransactionStatus transactionStatusForCreation = transactionManager.getTransaction(transactionDefinition);
    	final Category category = categoryRepository.createCategory("LAPTOPS",
   	                                                                "1234");
    	final Product product = productRepository.createProduct("LG G3",
    			                                                "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
    			                                                400, category);

    	final Deal deal = dealRepository.createDeal(DealType.WEEKLY, "Test Create Deal", "Test Create Deal", 25.00, "2015-11-15 00:00:00", "2015-11-21 23:59:59", product, "default-deal-image");
    	
    	transactionManager.commit(transactionStatusForCreation);
    	final List<Deal> deals = dealRepository.getAllDeals();
    	Assert.assertNotNull(deals);
    	Assert.assertEquals(1, deals.size());
    	final TransactionStatus transactionStatusForDeletion = transactionManager.getTransaction(transactionDefinition);
    	
    	dealRepository.deleteDeal(deal);
    	productRepository.deleteProduct(product);
    	categoryRepository.deleteCategory(category);
    	transactionManager.commit(transactionStatusForDeletion);
    }

    @Test
    public void testCreatDeal_VerifyDateToStringFormat() {
    	final TransactionStatus transactionStatusForCreation = transactionManager.getTransaction(transactionDefinition);
    	final Category category = categoryRepository.createCategory("LAPTOPS",
   	                                                                "1234");
    	final Product product = productRepository.createProduct("LG G3",
    			                                                "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
    			                                                400, category);

    	final Deal deal = dealRepository.createDeal(DealType.WEEKLY, "Test Create Deal", "Test Create Deal", 25.00, "2015-11-15 00:00:00", "2015-11-21 23:59:59", product, "default-deal-image");
    	
    	transactionManager.commit(transactionStatusForCreation);
    	final List<Deal> deals = dealRepository.getAllDeals();
    	Assert.assertNotNull(deals);
    	Assert.assertEquals(1, deals.size());
    	final TransactionStatus transactionStatusForDeletion = transactionManager.getTransaction(transactionDefinition);
    	
    	dealRepository.deleteDeal(deal);
    	productRepository.deleteProduct(product);
    	categoryRepository.deleteCategory(category);
    	transactionManager.commit(transactionStatusForDeletion);
    }

    @Test
    public void testGetAllDeals() {

        final TransactionStatus transactionStatusForCreation = transactionManager.getTransaction(transactionDefinition);
        final Category category = categoryRepository.createCategory("LAPTOPS",
            "1234");
        final Product product = productRepository.createProduct("LG G3",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
            400, category);

        for (int i = 0; i < 10; i++) {

<<<<<<< HEAD
    	for (int i = 0; i < 10; i++) {
    		final String dealName = "test deal" + i;
	    	dealRepository.createDeal(DealType.WEEKLY, dealName, "test deal", 25.00, "2015-11-15 00:00:00", "2015-11-21 23:59:59", product, "default-deal-image");
    	}
=======
            final String description = "test deal" + i;
            dealRepository.createDeal(DealType.WEEKLY, description, "header", "header", "200", "1234", product);

>>>>>>> e91b911e98c2286ef0a2c3026cb95336d9b8ce0f

            transactionManager.commit(transactionStatusForCreation);
            final List<Deal> deals = dealRepository.getAllDeals();
            Assert.assertNotNull(deals);
            Assert.assertEquals(10, deals.size());
            final TransactionStatus transactionStatusForDeletion = transactionManager.getTransaction(transactionDefinition);

<<<<<<< HEAD
    	for (final Deal deal : deals) {
    		dealRepository.deleteDeal(deal);
    	}
=======
            for (final Deal deal : deals) {

                dealRepository.deleteDeal(deal);
            }
>>>>>>> e91b911e98c2286ef0a2c3026cb95336d9b8ce0f

            productRepository.deleteProduct(product);
            categoryRepository.deleteCategory(category);
            transactionManager.commit(transactionStatusForDeletion);
        }
    }
    
    /*
     * Test creating a deal. It requires creating a category and product prior to 
     * calling <b>dealRepository.createDeal(DealType, String, String, Product)</b>.
     * <br/>
     * <i>DealType</i> can only be one of the ENUM values. 
     */
    @Test
    public void testDealCreation() throws IOException {

<<<<<<< HEAD
    	final TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
    	final TransactionStatus transactionStatusForCreation = transactionManager.getTransaction(transactionDefinition);
    	final Category category = categoryRepository.createCategory("LAPTOPS",
    			                                                    "1234");
    	final Product product = productRepository.createProduct("LG G3",
    			                                                "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
    			                                                400, category);
    	final Deal deal = dealRepository.createDeal(DealType.DAILY, 
													"test deal", 
													"11.11.2015 - Singles Day SALE",
													50.00,
													"2015-11-11 00:00:00",
													"2015-11-11 23:59:59",
													product,
													"singles-days-deal-image");
		//final Deal deal = dealRepository.createDeal(DealType.WEEKLY, 
		//											"test deal", 
		//											"11.11.2015 - Singles Day SALE",
		//											25.00,
		//											"2015-11-11 00:00:00",
		//											"2015-11-11 23:59:59",
		//											product,
		//											"default-deal-image");
    	
    	transactionManager.commit(transactionStatusForCreation);
    	Assert.assertNotNull(deal);
    	final TransactionStatus transactionStatusForDeletion = transactionManager.getTransaction(transactionDefinition);
    	dealRepository.deleteDeal(deal);
    	productRepository.deleteProduct(product);
    	categoryRepository.deleteCategory(category);
    	transactionManager.commit(transactionStatusForDeletion);
=======
        final TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        final TransactionStatus transactionStatusForCreation = transactionManager.getTransaction(transactionDefinition);
        final Category category = categoryRepository.createCategory("LAPTOPS",
            "1234");
        final Product product = productRepository.createProduct("LG G3",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
            400, category);
        final Deal deal = dealRepository.createDeal(DealType.WEEKLY, "description", "header", "header", "200", "1234", product);
        transactionManager.commit(transactionStatusForCreation);
        Assert.assertNotNull(deal);
        final TransactionStatus transactionStatusForDeletion = transactionManager.getTransaction(transactionDefinition);
        dealRepository.deleteDeal(deal);
        productRepository.deleteProduct(product);
        categoryRepository.deleteCategory(category);
        transactionManager.commit(transactionStatusForDeletion);
>>>>>>> e91b911e98c2286ef0a2c3026cb95336d9b8ce0f
    }
}