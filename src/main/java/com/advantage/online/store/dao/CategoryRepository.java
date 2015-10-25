package com.advantage.online.store.dao;

import com.advantage.online.store.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by kubany on 10/18/2015.
 */
@Repository
public class CategoryRepository {

    private static final Logger log = LoggerFactory.getLogger(CategoryRepository.class);
    private static final int MAX_NUM_OF_CATEGORIES = 6;

    @PersistenceContext
    private EntityManager em;

    public List<Category> getAllCategories() {
        log.info("getAllCategories");
        List<Category> categories = em.createNamedQuery(Category.GET_ALL, Category.class)
                .setMaxResults(MAX_NUM_OF_CATEGORIES)
                .getResultList();

        return categories.isEmpty() ? null : categories;
    }
}
