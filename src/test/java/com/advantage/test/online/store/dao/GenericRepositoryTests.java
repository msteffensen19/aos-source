package com.advantage.test.online.store.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

abstract class GenericRepositoryTests {

	@Autowired
    PlatformTransactionManager transactionManager;
	final TransactionDefinition transactionDefinition;

	public GenericRepositoryTests() {

		transactionDefinition = new DefaultTransactionDefinition();
	}
}