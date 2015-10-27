package com.advantage.test.online.store.dao;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.advantage.online.store.dao.CategoryRepository;
import com.advantage.online.store.dao.DealRepository;
import com.advantage.online.store.dao.ProductRepositoryImpl;
import com.advantage.online.store.model.Category;
import com.advantage.online.store.model.Deal;
import com.advantage.online.store.model.DealType;
import com.advantage.online.store.model.Product;
import com.advantage.test.cfg.AdvantageTestContextConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AdvantageTestContextConfiguration.class})
public class DealRepositoryTests {

	@Autowired
    private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepositoryImpl productRepository;
    @Autowired
    private DealRepository dealRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    public void testDeals() {

    	final TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
    	final TransactionStatus transactionStatusForCreation = transactionManager.getTransaction(transactionDefinition);
    	final Category category = categoryRepository.createCategory("LAPTOPS",
   	                                                                new byte[]{1, 2, 3, 4});
    	final Product product = productRepository.createProduct("LG G3",
    			                                                "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
    			                                                400, category);

    	for (int i = 0; i < 10; i++) {

    		final String dealName = "test deal" + i;
	    	dealRepository.createDeal(DealType.WEEKLY, dealName, "test deal", product);
    	}

    	transactionManager.commit(transactionStatusForCreation);
    	final List<Deal> deals = dealRepository.getAllDeals();
    	Assert.assertNotNull(deals);
    	Assert.assertEquals(10, deals.size());
    	final TransactionStatus transactionStatusForDeletion = transactionManager.getTransaction(transactionDefinition);
    	
    	for (final Deal deal : deals) {

    		dealRepository.deleteDeal(deal);
    	}

    	productRepository.deleteProduct(product);
    	categoryRepository.deleteCategory(category);
    	transactionManager.commit(transactionStatusForDeletion);
    }

    @Test
    public void testDealCreation() throws IOException {

    	final TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
    	final TransactionStatus transactionStatusForCreation = transactionManager.getTransaction(transactionDefinition);
    	final Category category = categoryRepository.createCategory("LAPTOPS",
   	                                                                new byte[]{1, 2, 3, 4});
    	final Product product = productRepository.createProduct("LG G3",
    			                                                "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
    			                                                400, category);
    	final Deal deal = dealRepository.createDeal(DealType.WEEKLY, "test deal", "test deal",
    			                                    product);
    	transactionManager.commit(transactionStatusForCreation);
    	Assert.assertNotNull(deal);
    	final TransactionStatus transactionStatusForDeletion = transactionManager.getTransaction(transactionDefinition);
    	dealRepository.deleteDeal(deal);
    	productRepository.deleteProduct(product);
    	categoryRepository.deleteCategory(category);
    	transactionManager.commit(transactionStatusForDeletion);
    }
}