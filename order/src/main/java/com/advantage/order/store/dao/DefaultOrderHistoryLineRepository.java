package com.advantage.order.store.dao;

import com.advantage.order.store.model.OrderLines;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Moti Ostrovski on 30/05/2016.
 */
@Component
@Qualifier("orderHistoryLineManagementRepository")
@Repository
public class DefaultOrderHistoryLineRepository extends AbstractRepository implements OrderHistoryLineRepository {
    @Override
    public List<OrderLines> getAllOrderLinesByOrderId (long orderId) {
        try {
            List<OrderLines> orderLines = entityManager.createNamedQuery(OrderLines.QUERY_GET_ORDER_LINES_BY_ORDER, OrderLines.class).setParameter("onum",orderId)
                    .getResultList();
            return orderLines;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
