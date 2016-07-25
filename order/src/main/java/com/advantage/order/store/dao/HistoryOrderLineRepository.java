package com.advantage.order.store.dao;

import com.advantage.order.store.model.OrderLines;

import java.util.List;

/**
 * @author Moti Ostrovski on 30/05/2016.
 */
//public interface HistoryOrderLineRepository extends DefaultCRUDOperations<OrderLines>{
public interface HistoryOrderLineRepository {

    List<OrderLines> getAll();

    List<OrderLines> getHistoryOrderLinesByOrderId(long orderId);

    List<OrderLines> getHistoryOrderLinesByUserId(long userId);

}
