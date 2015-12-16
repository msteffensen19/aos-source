package com.advantage.online.store.dao.attribute;

import com.advantage.online.store.dao.DefaultCRUDOperations;
import com.advantage.online.store.model.attribute.Attribute;

public interface AttributeRepository extends DefaultCRUDOperations<Attribute> {
    /**
     * Get entity by record nane
     * @param name Name of the category
     * @return entity reference
     */
    Attribute get(String name);

}