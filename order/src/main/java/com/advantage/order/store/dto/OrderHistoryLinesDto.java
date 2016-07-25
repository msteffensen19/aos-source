package com.advantage.order.store.dto;

import com.advantage.order.store.model.OrderLines;

import java.util.Date;
import java.util.List;

/**
 * @author Binyamin Regev on on 24/07/2016.
 */
public class OrderHistoryLinesDto {

    private long userId;
    private long orderNumber;
    private long orderTimestamp;
    private Date orderDate;
    private double totalPrice;
    private List<OrderLineDto> orderLines;


    public OrderHistoryLinesDto() {
    }

    public OrderHistoryLinesDto(long userId, long orderNumber, long orderTimestamp) {
        this.userId = userId;
        this.orderNumber = orderNumber;
        this.orderTimestamp = orderTimestamp;
    }

    public OrderHistoryLinesDto(long userId, long orderNumber, Date orderDate) {
        this.userId = userId;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
    }

    public OrderHistoryLinesDto(long userId, long orderNumber, long orderTimestamp, double totalPrice, List<OrderLineDto> orderLines) {
        this.userId = userId;
        this.orderNumber = orderNumber;
        this.orderTimestamp = orderTimestamp;
        this.totalPrice = totalPrice;
        this.orderLines = orderLines;
    }

    public OrderHistoryLinesDto(long userId, long orderNumber, Date orderDate, double totalPrice, List<OrderLineDto> orderLines) {
        this.userId = userId;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.orderLines = orderLines;
    }

    public OrderHistoryLinesDto(long userId, long orderNumber, long orderTimestamp, Date orderDate, double totalPrice, List<OrderLineDto> orderLines) {
        this.userId = userId;
        this.orderNumber = orderNumber;
        this.orderTimestamp = orderTimestamp;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.orderLines = orderLines;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public long getOrderTimestamp() {
        return orderTimestamp;
    }

    public void setOrderTimestamp(long orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderLineDto> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineDto> orderLines) {
        this.orderLines = orderLines;
    }

    public void addOrderLine(OrderLineDto orderLine) {
        totalPrice += (orderLine.getPricePerItem() * orderLine.getQuantity());
        orderLines.add(orderLine);
    }

    public void addOrderLine(long userId, long orderNumber, Long productId, String productName, int productColor, double pricePerItem, int quantity) {
        addOrderLine(new OrderLineDto(userId,
                orderNumber,
                productId,
                productName,
                productColor,
                pricePerItem,
                quantity));
    }

    public void removeOrderLine(OrderLineDto orderLine) {
        totalPrice -= (orderLine.getPricePerItem() * orderLine.getQuantity());
        orderLines.remove(orderLine);
    }

    public void clearOrderLines() {
        totalPrice = 0;
        orderLines.clear();
    }

}
