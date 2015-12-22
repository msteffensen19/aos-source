package com.advantage.catalog.store.services;

import com.advantage.catalog.store.model.attribute.Attribute;
import com.advantage.catalog.store.dao.attribute.AttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AttributeService {
    @Autowired
    AttributeRepository attributeRepository;

    @Transactional(readOnly = true)
    public List<Attribute> getAllAttributes() {
        return attributeRepository.getAll();
    }

    @Transactional(readOnly = true)
    public Attribute getAttributeByName(String name) {
        return attributeRepository.get(name);
    }

    @Transactional
    public Attribute createAttribute(String name) {
        return attributeRepository.create(name.toUpperCase());
    }
}
