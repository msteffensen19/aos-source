package com.advantage.order.store.dto;

import java.util.List;

/**
 * @author Ostrovski Moti on 26/05/2016.
 */
public class OrdersHistoryDto {
    private List<OrderHistoryDto> ordersHistory;

    //region constructors
    public OrdersHistoryDto(){}

    public OrdersHistoryDto(List<OrderHistoryDto> ordersHistory) {
        this.ordersHistory = ordersHistory;
    }
    //endregion constructor

    public List<OrderHistoryDto> getOrdersHistory() {
        return ordersHistory;
    }

    public void setOrdersHistory(List<OrderHistoryDto> ordersHistory) {
        this.ordersHistory = ordersHistory;
    }
}
