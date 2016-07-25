package com.advantage.order.store.dao;

import com.advantage.order.store.model.OrderHeader;

import java.util.List;

/**
 *  @author Moti Ostrovski on 30/05/2016.
 */
public interface OrderHistoryHeaderRepository {

    List<OrderHeader> getAll();

    List<OrderHeader> getOrdersHeaderByUserId(long userId);

    List<OrderHeader> getOrdersHeaderByOrderId(long orderId);

    List<OrderHeader> getOrdersHeaderByOrderIdAndUserId(long orderId,long userId);

}
