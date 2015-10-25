package com.advantage.online.store.dao;

import com.advantage.online.store.model.Deal;
import com.advantage.online.store.model.DealType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Component
@Qualifier("dealRepository")
@Repository
public class DefaultDealRepository extends AbstractRepository implements DealRepository {

    public List<Deal> getAllDeals() {

        log.info("getAllDeals");
        final Query query = entityManager.createNamedQuery(Deal.QUERY_GET_ALL);
        return query.getResultList();
    }

    public Deal getDealOfTheDay() {

        log.info("getDealOfTheDay");
        final Query query = entityManager.createNamedQuery(Deal.QUERY_GET_BY_TYPE);
        query.setParameter("dealType", DealType.DAILY.getDealTypeCode());
        final List<Deal> deals = query.getResultList();
        final Deal dealOfTheDay;

        if (deals.isEmpty()) {

            System.out.println("kuku");
            dealOfTheDay = null;
        } else {

            dealOfTheDay = deals.get(0);
            System.out.println(dealOfTheDay.getName());
        }

        return dealOfTheDay;
    }
}