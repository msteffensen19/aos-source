package com.advantage.common.dto;

/**
 * Author Moti Ostrovski on 24/05/2016.
 */
public class OrderHistoryDto {

    private long orderNumber;
    private long orderTimestamp;
    private double shippingTrackingNumber;
    private String paymentMethod;
    private double orderTotalSum;
    private double orderShipingCost;
    private String shippingAddress;
    private OrderHistoryAccountDto customer;
    private OrderHistoryProductDto product;


    public OrderHistoryDto(){}


}
