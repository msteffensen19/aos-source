package com.advantage.online.store.dao.deal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.advantage.online.store.dao.AbstractRepository;
import com.advantage.online.store.dao.DefaultCRUDOperations;
import com.advantage.online.store.model.product.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.advantage.online.store.model.Deal;
import com.advantage.online.store.model.DealType;
import com.advantage.util.ArgumentValidationHelper;
import com.advantage.util.JPAQueryHelper;

@Component
@Qualifier("dealRepository")
@Repository
public class DefaultDealRepository extends AbstractRepository implements  DealRepository {

	@Override
	public Deal create(DealType dealType, String description, String promotionHeader,
                       String promotionSubHeader, String staringPrice, String managedImageId,
                       double discount, String dateFrom, String dateTo,
                       Product product) {

    	final Deal deal = new Deal(dealType, description, promotionHeader, promotionSubHeader, staringPrice,
            managedImageId, discount, dateFrom, dateTo, product);
    	entityManager.persist(deal);
    	return deal;
    }

	@Override
    public Deal getDealOfTheDay() {
        Query query = entityManager.createNamedQuery(Deal.QUERY_GET_BY_TYPE);
        Integer dealTypeCode = DealType.DAILY.getDealTypeCode();
        query.setParameter(Deal.PARAM_DEAL_TYPE, dealTypeCode);
        @SuppressWarnings("unchecked")
		List<Deal> deals = query.getResultList();
        Deal dealOfTheDay;

        if (deals.isEmpty()) {
            dealOfTheDay = null;
        } else {
            dealOfTheDay = deals.get(0);
        }

        return dealOfTheDay;
    }

    @Override
    public int delete(Deal... entities) {
        ArgumentValidationHelper.validateArrayArgumentIsNotNullAndNotZeroLength(entities, "deal");
        List<Long> dealIds = new ArrayList<>(entities.length);

        for (Deal entity : entities) {
            dealIds.add(entity.getId());
        }

        String hql = JPAQueryHelper.getDeleteByPkFieldQuery(Deal.class, Deal.FIELD_ID, dealIds);
        final Query query = entityManager.createQuery(hql);
        return query.executeUpdate();
    }

    @Override
    public List<Deal> getAll() {
        List<Deal> deals = entityManager.createNamedQuery(Deal.QUERY_GET_ALL, Deal.class).getResultList();

        return deals.isEmpty() ? null : deals;
    }

    @Override
    public Deal get(Long entityId) {
        ArgumentValidationHelper.validateArgumentIsNotNull(entityId, "deal id");
        String hql = JPAQueryHelper.getSelectByPkFieldQuery(Deal.class, Deal.FIELD_ID,
            entityId);
        Query query = entityManager.createQuery(hql);

        return (Deal) query.getSingleResult();
    }
}