package com.advantage.catalog.store.services;

import com.advantage.catalog.store.dto.AttributeDto;
import com.advantage.catalog.store.model.attribute.Attribute;
import com.advantage.catalog.store.dao.attribute.AttributeRepository;
import com.advantage.catalog.store.model.product.Product;
import com.advantage.catalog.store.model.product.ProductAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AttributeService {
    @Autowired
    AttributeRepository attributeRepository;

    @Transactional(readOnly = true)
    private List<Attribute> getAllAttributes() {
        return attributeRepository.getAll();
    }

    @Transactional
    public Attribute createAttribute(String name) {
        return attributeRepository.create(name.toUpperCase());
    }

    /**
     * Fill AttributeDto from ProductAttributes
     *
     * @param products            Products collection
     * @return AttributeDto collection
     */
    public List<AttributeDto> fillAttributeDto(Collection<Product> products) {

        Map<String, Set<String>> attrCollection = new LinkedHashMap<>();
        for (Attribute attribute : getAllAttributes()) {
            attrCollection.put(attribute.getName(), null);
        }

        for (Product product : products) {
            Set<ProductAttributes> productAttributes = product.getProductAttributes();
            for (ProductAttributes attribute : productAttributes) {
                String attrName = attribute.getAttribute().getName();
                String attrValue = attribute.getAttributeValue();

                Set<String> item = attrCollection.get(attrName);

                if (item == null) {
                    item = new HashSet<>();
                    attrCollection.put(attrName, item);
                }
                item.add(attrValue);
            }
        }

        List<AttributeDto> attributeItems = new ArrayList<>();

        for (Map.Entry<String, Set<String>> item : attrCollection.entrySet()) {
            if (item.getValue() == null) continue;
            attributeItems.add(new AttributeDto(item.getKey(), item.getValue()));
        }

        return attributeItems;
    }
}
