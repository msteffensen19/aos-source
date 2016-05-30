package com.advantage.order.store.dao;

import com.advantage.order.store.model.OrderHeader;

import java.util.List;

/**
 *  @author Moti Ostrovski on 30/05/2016.
 */
public interface OrderHistoryHeaderManagementRepository {

    List<OrderHeader> getAll();

    List<OrderHeader> getByUserId(long userId);

    OrderHeader getOrderHeaderByOrderId(long orderId);

}
