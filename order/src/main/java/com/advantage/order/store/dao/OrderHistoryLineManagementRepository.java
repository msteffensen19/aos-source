package com.advantage.order.store.dao;

import com.advantage.order.store.model.OrderLines;

import java.util.List;

/**
 * @author Moti Ostrovski on 30/05/2016.
 */
public interface OrderHistoryLineManagementRepository {

    List<OrderLines> getAllOrderLinesByOrderId(long orderId);

}
