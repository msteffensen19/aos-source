package com.advantage.order.store.dao;

import com.advantage.order.store.model.OrderHeader;
import com.advantage.order.store.model.OrderLines;

import java.util.List;

/**
 *  @author Moti Ostrovski on 30/05/2016.
 */
public interface HistoryOrderHeaderRepository {

    List<OrderHeader> getAll();

    List<OrderHeader> getOrdersHeaderByUserId(long userId);

    List<OrderHeader> getOrdersHeaderByOrderId(long orderId);

    List<OrderHeader> getOrdersHeaderByOrderIdAndUserId(long orderId,long userId);

    List<OrderLines> getAllOrdersLines();

    List<OrderLines> getOrdersLinesByUserId(long userId);

    List<OrderLines> getOrdersLinesByOrderId(long orderId);

    List<OrderLines> getOrdersLinesByOrderIdAndUserId(long orderId,long userId);

}
