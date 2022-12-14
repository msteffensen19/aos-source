package com.advantage.order.store.dao;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface DefaultCRUDOperations<T> {
    /**
     * Create new entity
     *
     * @param name value of a field name
     * @return reference of a new object
     */
    @Transactional
    default T create(String name) {
        throw new NotImplementedException((String) null);
    }

    /**
     * Create new entity
     *
     * @param entity
     * @return entity Id
     */
    @Transactional
    default Long create(T entity) {
        throw new NotImplementedException((String) null);
    }

    /**
     * Delete records
     *
     * @param entities One or more entities
     * @return the number of entities updated or deleted
     */
    @Transactional
    int delete(T... entities);

    /**
     * Delete recprd by id
     *
     * @param id record id
     * @return entity reference
     */
    @Transactional
    default T delete(Long id) {
        T entity = get(id);
        if (entity != null) delete(entity);

        return entity;
    }

    /**
     * Delete entities by ids collection
     *
     * @param ids
     * @return the number of entities deleted
     */
    @Transactional
    default int deleteByIds(Collection<Long> ids) {
        throw new NotImplementedException((String) null);
    }

    /**
     * Get entities collection
     *
     * @return entities collection
     */
    List<T> getAll();

    /**
     * Get entity by record id
     *
     * @param entityId record id
     * @return entity reference
     */
    T get(Long entityId);
}
