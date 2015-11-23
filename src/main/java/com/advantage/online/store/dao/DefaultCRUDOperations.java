package com.advantage.online.store.dao;

import java.util.List;

public interface DefaultCRUDOperations<T> {
    /**
     * Create new entity
     *
     * @param name value of a field name
     * @return reference of a new object
     */
    default T create(String name) {
        return null;
    }

    /**
     * Delete records
     * @param entities One or more entities
     * @return the number of entities updated or deleted
     */
    int delete(T... entities);

    /**
     * Delete recprd by id
     * @param id record id
     * @return entity reference
     */
    default T delete(Long id) {
        T entity = get(id);
        if(entity != null) delete(entity);

        return entity;
    }

    /**
     * Get entities collection
     * @return entities collection
     */
    List<T> getAll();

    /**
     * Get entity by record id
     * @param entityId record id
     * @return entity reference
     */
    T get(Long entityId);
}
