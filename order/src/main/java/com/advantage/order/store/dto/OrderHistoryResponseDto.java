package com.advantage.order.store.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ostrovsm on 30/05/2016.
 */
public class OrderHistoryResponseDto {

    private String message;
    List<OrderHistoryHeaderDto> ordersHistory;

    public OrderHistoryResponseDto(){
        ordersHistory = new ArrayList<OrderHistoryHeaderDto>(){};
    }

    public List<OrderHistoryHeaderDto> getOrdersHistory() {
        ordersHistory = ordersHistory ==null ? new ArrayList<OrderHistoryHeaderDto>(){}: ordersHistory;
        return ordersHistory;
    }

    public void setOrdersHistory(List<OrderHistoryHeaderDto> ordersHistory) {
        this.ordersHistory = ordersHistory;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addOrderHistoryDto(OrderHistoryHeaderDto orderHistoryHeaderDto){
        ordersHistory = ordersHistory ==null ? new ArrayList<OrderHistoryHeaderDto>(){}: ordersHistory;
        ordersHistory.add(orderHistoryHeaderDto);
    }
}
