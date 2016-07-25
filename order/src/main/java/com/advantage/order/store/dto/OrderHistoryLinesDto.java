package com.advantage.order.store.dto;

import com.advantage.common.enums.ColorPalletEnum;
import com.advantage.order.store.model.OrderLines;
import com.advantage.order.store.model.ShoppingCart;

import java.util.Date;
import java.util.List;

/**
 * @author Binyamin Regev on on 24/07/2016.
 */
public class OrderHistoryLinesDto {

    private long orderNumber;
    private long orderTimestamp;
    private Date orderDate;
    private double totalPrice;

    private List<OrderLineDto> orderLines;

    public OrderHistoryLinesDto() {
    }

    public OrderHistoryLinesDto(long orderNumber, long orderTimestamp) {
        this.orderNumber = orderNumber;
        this.orderTimestamp = orderTimestamp;
    }

    public OrderHistoryLinesDto(long orderNumber, Date orderDate) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
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

    public void addOrderLine(OrderLines line) {

    }

    public void addOrderLine(long userId, long orderNumber, Long productId, String productName, int productColor, String productColorName, double pricePerItem, int quantity) {
        addOrderLine(new OrderLines(userId,
                orderNumber,
                productId,
                productName,
                productColor,
                pricePerItem,
                quantity));

    }

}
