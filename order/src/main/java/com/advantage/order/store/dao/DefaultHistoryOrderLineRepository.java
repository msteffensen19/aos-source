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
@Qualifier("orderHistoryLineRepository")
@Repository
public class DefaultHistoryOrderLineRepository extends AbstractRepository implements HistoryOrderLineRepository {
    @Override
    public List<OrderLines> getAll() {
        return null;
    }

    @Override
    public List<OrderLines> getHistoryOrderLinesByOrderId(long orderId) {
        try {
            List<OrderLines> orderLines = entityManager.createNamedQuery(OrderLines.QUERY_GET_ORDER_LINES_BY_ORDER, OrderLines.class)
                    .setParameter(OrderLines.PARAM_ORDER_NUMBER, orderId)
                    .getResultList();
            return orderLines;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<OrderLines> getHistoryOrderLinesByUserId(long userId) {
        try {
            List<OrderLines> orderLines = entityManager.createNamedQuery(OrderLines.QUERY_GET_ORDERS_LINES_BY_USER_ID, OrderLines.class)
                    .setParameter(OrderLines.PARAM_USER_ID, userId)
                    .getResultList();
            return orderLines;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
