package com.advantage.online.store.dao;

import java.util.List;

import javax.persistence.Query;

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
public class DefaultDealRepository extends AbstractRepository implements DealRepository {

	@Override
	public Deal createDeal(DealType dealType,  String description, String promotionHeader,
                           String  promotionSubHeader, String staringPrice, String managedImageId,
                           double discount, String dateFrom, String dateTo,
                           Product product) {

    	final Deal deal = new Deal(dealType, description, promotionHeader, promotionSubHeader, staringPrice,
            managedImageId, discount, dateFrom, dateTo, product);
    	entityManager.persist(deal);
    	return deal;
    }

	@Override
	public int deleteDeal(final Deal deal) {

		ArgumentValidationHelper.validateArgumentIsNotNull(deal, "deal");
		final Long dealId = deal.getId();
		final String hql = JPAQueryHelper.getDeleteByPkFieldQuery(Deal.class, Deal.FIELD_ID,
				                                                  dealId);
		final Query query = entityManager.createQuery(hql);
		return query.executeUpdate();
	}

	@Override
    @SuppressWarnings("unchecked")
	public List<Deal> getAllDeals() {

        final Query query = entityManager.createNamedQuery(Deal.QUERY_GET_ALL);
        return query.getResultList();
    }

	@Override
    public Deal getDealOfTheDay() {

        final Query query = entityManager.createNamedQuery(Deal.QUERY_GET_BY_TYPE);
        final Integer dealtypeCode = DealType.DAILY.getDealTypeCode();
        query.setParameter(Deal.PARAM_DEAL_TYPE, dealtypeCode);
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