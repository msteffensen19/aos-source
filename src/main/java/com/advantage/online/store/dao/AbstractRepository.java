package com.advantage.online.store.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

abstract class AbstractRepository {

    @PersistenceContext
    protected EntityManager entityManager;
    protected final Logger log;

    public AbstractRepository() {

        final Class<?> cls = getClass();
        log = LoggerFactory.getLogger(cls);
    }
}