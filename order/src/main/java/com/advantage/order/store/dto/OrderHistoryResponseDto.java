package com.advantage.order.store.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Author ostrovsm on 30/05/2016.
 */
public class OrderHistoryResponseDto {

    private String message;
    List<OrderHistoryDto> ordersHistory;

    public OrderHistoryResponseDto(){
        ordersHistory = new ArrayList<OrderHistoryDto>(){};
    }

    public List<OrderHistoryDto> getOrdersHistory() {
        ordersHistory = ordersHistory ==null ? new ArrayList<OrderHistoryDto>(){}: ordersHistory;
        return ordersHistory;
    }

    public void setOrdersHistory(List<OrderHistoryDto> ordersHistory) {
        this.ordersHistory = ordersHistory;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addOrderHistoryDto(OrderHistoryDto orderHistoryDto){
        ordersHistory = ordersHistory ==null ? new ArrayList<OrderHistoryDto>(){}: ordersHistory;
        ordersHistory.add(orderHistoryDto);
    }
}
