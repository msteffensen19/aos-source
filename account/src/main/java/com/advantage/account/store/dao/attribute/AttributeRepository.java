package com.advantage.account.store.dao.attribute;

import com.advantage.account.store.dao.DefaultCRUDOperations;
import com.advantage.account.store.model.attribute.Attribute;

public interface AttributeRepository extends DefaultCRUDOperations<Attribute> {
    /**
     * Get entity by record nane
     *
     * @param name Name of the category
     * @return entity reference
     */
    Attribute get(String name);

}