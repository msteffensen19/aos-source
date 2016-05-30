package com.advantage.order.store.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * Author ostrovsm on 30/05/2016.
 */
public class OrderHistoryCollectionDto {
    List<OrderHistoryDto> orderHistoryCollection;
    public OrderHistoryCollectionDto(){
        orderHistoryCollection = new ArrayList<OrderHistoryDto>(){};
    }

    public List<OrderHistoryDto> getOrderHistoryCollection() {
        orderHistoryCollection = orderHistoryCollection==null ? new ArrayList<OrderHistoryDto>(){}: orderHistoryCollection;
        return orderHistoryCollection;
    }

    public void setOrderHistoryCollection(List<OrderHistoryDto> orderHistoryCollection) {
        this.orderHistoryCollection = orderHistoryCollection;
    }

    public void addOrderHistoryDto(OrderHistoryDto orderHistoryDto){
        orderHistoryCollection = orderHistoryCollection==null ? new ArrayList<OrderHistoryDto>(){}: orderHistoryCollection;
        orderHistoryCollection.add(orderHistoryDto);
    }
}
