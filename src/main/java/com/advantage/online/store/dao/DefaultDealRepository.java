package com.advantage.online.store.dao;

import com.advantage.online.store.model.Deal;
import com.advantage.online.store.model.DealType;
import com.advantage.online.store.model.Product;
import com.advantage.util.ArgumentValidationHelper;
import com.advantage.util.JPAQueryHelper;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Component
@Qualifier("dealRepository")
@Repository
public class DefaultDealRepository extends AbstractRepository implements DealRepository {

	public Deal createDeal(final DealType dealType, final String name,
     final String description, final Product product) {

    	final Deal deal = new Deal(dealType, name, description, product);
    	entityManager.persist(deal);
    	return deal;
    }

	@Override
	public void deleteDeal(final Deal deal) {

		ArgumentValidationHelper.validateArgumentIsNotNull(deal, "deal");
		log.info("deleteDeal");
		final Long dealId = deal.getId();
		final String hql = JPAQueryHelper.getDeleteByPkFieldQuery(Deal.class, "id", dealId);
		final Query query = entityManager.createQuery(hql);
		query.executeUpdate();
	}

    @SuppressWarnings("unchecked")
	public List<Deal> getAllDeals() {

        log.info("getAllDeals");
        final Query query = entityManager.createNamedQuery(Deal.QUERY_GET_ALL);
        return query.getResultList();
    }

    public Deal getDealOfTheDay() {

        log.info("getDealOfTheDay");
        final Query query = entityManager.createNamedQuery(Deal.QUERY_GET_BY_TYPE);
        final Integer dealtypeCode = DealType.DAILY.getDealTypeCode();
        query.setParameter("dealType", dealtypeCode);
        @SuppressWarnings("unchecked")
		final List<Deal> deals = query.getResultList();
        final Deal dealOfTheDay;

        if (deals.isEmpty()) {

            dealOfTheDay = null;
        } else {

            dealOfTheDay = deals.get(0);
        }

        return dealOfTheDay;
    }
}