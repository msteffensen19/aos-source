package com.advantage.catalog.store.dao.attribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.advantage.catalog.store.model.attribute.Attribute;
import com.advantage.catalog.store.model.category.Category;
import com.advantage.catalog.util.ArgumentValidationHelper;
import com.advantage.catalog.util.JPAQueryHelper;
import com.advantage.catalog.store.dao.AbstractRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Component
@Qualifier("attributeRepository")
@Repository
public class DefaultAttributeRepository extends AbstractRepository implements AttributeRepository {
    @Override
    public Attribute create(String name) {
        Attribute attribute = new Attribute(name);
        entityManager.persist(attribute);

        return attribute;
    }

    @Override
    public int delete(Attribute... entities) {
        ArgumentValidationHelper.validateArrayArgumentIsNotNullAndNotZeroLength(entities, "attributes");
        int categoriesCount = entities.length;
        Collection<Long> ids = new ArrayList<>(categoriesCount);

        for (Attribute attribute : entities) {
            Long id = attribute.getId();
            ids.add(id);
        }

        String hql = JPAQueryHelper.getDeleteByPkFieldsQuery(Category.class, Attribute.FIELD_ID, Attribute.PARAM_ID);
        Query query = entityManager.createQuery(hql);
        query.setParameter(Attribute.PARAM_ID, ids);

        return query.executeUpdate();
    }

    @Override
    public List<Attribute> getAll() {
        return entityManager.createNamedQuery(Attribute.QUERY_GET_ALL, Attribute.class)
                .getResultList();
    }

    @Override
    public Attribute get(Long entityId) {
        ArgumentValidationHelper.validateArgumentIsNotNull(entityId, "attribute id");
        String hql = JPAQueryHelper.getSelectByPkFieldQuery(Attribute.class, Attribute.FIELD_ID, entityId);
        Query query = entityManager.createQuery(hql);

        return (Attribute) query.getSingleResult();
    }

    @Override
    public Attribute get(String name) {
        ArgumentValidationHelper.validateArgumentIsNotNull(name, "attribute name");
        Query query = entityManager.createNamedQuery(Attribute.QUERY_ATTRIBUTE_GET_BY_NAME);
        query.setParameter(Attribute.PARAM_ATTRIBUTE_NAME, name.toUpperCase());
        List<Attribute> attributes = query.getResultList();

        return attributes.isEmpty() ? null : attributes.get(0);
    }
}
