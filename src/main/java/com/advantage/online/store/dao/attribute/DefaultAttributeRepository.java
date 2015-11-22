package com.advantage.online.store.dao.attribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.advantage.online.store.model.attribute.Attribute;
import com.advantage.online.store.model.category.Category;
import com.advantage.util.ArgumentValidationHelper;
import com.advantage.util.JPAQueryHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.advantage.online.store.dao.AbstractRepository;

import javax.persistence.Query;

@Component
@Qualifier("attributeRepository")
@Repository
public class DefaultAttributeRepository extends AbstractRepository implements AttributeRepository {


    @Override
    public Attribute createAttribute(String name) {
        Attribute attribute = new Attribute(name);
        entityManager.persist(attribute);

        return attribute;
    }

    @Override
    public int deleteAttribute(Attribute attribute) {
        ArgumentValidationHelper.validateArgumentIsNotNull(attribute, "attribute");
        Long id = attribute.getId();
        String hql = JPAQueryHelper.getDeleteByPkFieldQuery(Attribute.class, Attribute.FIELD_ID, id);
        Query query = entityManager.createQuery(hql);

        return query.executeUpdate();
    }

    @Override
    public int deleteAttributes(Attribute... attributes) {
        ArgumentValidationHelper.validateArrayArgumentIsNotNullAndNotZeroLength(attributes, "attributes");
        int categoriesCount = attributes.length;
        Collection<Long> ids = new ArrayList<>(categoriesCount);

        for (Attribute attribute : attributes) {
            Long id = attribute.getId();
            ids.add(id);
        }

        String hql = JPAQueryHelper.getDeleteByPkFieldsQuery(Category.class, Attribute.FIELD_ID, Attribute.PARAM_ID);
        Query query = entityManager.createQuery(hql);
        query.setParameter(Attribute.PARAM_ID, ids);

        return query.executeUpdate();
    }

    @Override
    public List<Attribute> getAllAttributes() {
        return entityManager.createNamedQuery(Attribute.QUERY_GET_ALL, Attribute.class)
            .getResultList();
    }

    @Override
    public Attribute getAttribute(Long id) {
        ArgumentValidationHelper.validateArgumentIsNotNull(id, "attribute id");
        String hql = JPAQueryHelper.getSelectByPkFieldQuery(Attribute.class, Attribute.FIELD_ID, id);
        Query query = entityManager.createQuery(hql);

        return (Attribute) query.getSingleResult();
    }
}
