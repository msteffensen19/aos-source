package com.advantage.order.store.dao;

import com.advantage.order.store.model.OrderHeader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Moti Ostrovski on 30/05/2016.
 */
@Component
@Qualifier("orderHistoryHeaderRepository")
@Repository
public class DefaultHistoryOrderHeaderRepository extends AbstractRepository implements HistoryOrderHeaderRepository {

    @Override
    public List<OrderHeader> getAll() {
        List<OrderHeader> orderHeaders = entityManager.createNamedQuery(OrderHeader.QUERY_GET_All_ORDERS_HISTORY, OrderHeader.class)
                .getResultList();

        return orderHeaders;
    }

    @Override
    public List<OrderHeader> getOrdersHeaderByUserId(long userId) {
        List<OrderHeader> orderHeaders = entityManager.createNamedQuery(OrderHeader.QUERY_GET_ORDERS_BY_USER_ID, OrderHeader.class)
                .setParameter(OrderHeader.PARAM_USER_ID,userId)
                .getResultList();

        return orderHeaders;
    }

    @Override
    public List<OrderHeader> getOrdersHeaderByOrderId(long orderId) {
        List<OrderHeader> orderHeaders = entityManager.createNamedQuery(OrderHeader.QUERY_GET_ORDER_BY_ORDER, OrderHeader.class)
                .setParameter(OrderHeader.PARAM_ORDER_NUMBER,orderId)
                .getResultList();
        return orderHeaders;
    }

    @Override
    public List<OrderHeader> getOrdersHeaderByOrderIdAndUserId(long orderId, long userId) {
        List<OrderHeader> orderHeaders = entityManager.createNamedQuery(OrderHeader.QUERY_GET_ORDER_BY_PK_COLUMNS, OrderHeader.class)
                .setParameter(OrderHeader.PARAM_ORDER_NUMBER,orderId)
                .setParameter(OrderHeader.PARAM_USER_ID, userId)
                .getResultList();
        return orderHeaders;
    }
}
