package com.advantage.order.store.dto;

import java.util.List;

/**
 * @author Ostrovski Moti on 26/05/2016.
 */
public class OrdersHistoryDto {
    private List<OrderHistoryHeaderDto> ordersHistory;

    //region constructors
    public OrdersHistoryDto(){}

    public OrdersHistoryDto(List<OrderHistoryHeaderDto> ordersHistory) {
        this.ordersHistory = ordersHistory;
    }
    //endregion constructor

    public List<OrderHistoryHeaderDto> getOrdersHistory() {
        return ordersHistory;
    }

    public void setOrdersHistory(List<OrderHistoryHeaderDto> ordersHistory) {
        this.ordersHistory = ordersHistory;
    }
}
