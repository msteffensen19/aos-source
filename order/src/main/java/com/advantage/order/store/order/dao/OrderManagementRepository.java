package com.advantage.order.store.order.dao;

import com.advantage.order.store.order.model.UserOrder;

/**
 * @author Binyamin Regev on 06/01/2016.
 */
public interface OrderManagementRepository {

    /**
     * Get user order header and lines
     * @param userId
     * @param orderNumber
     * @return {@link UserOrder}
     */
    UserOrder getUserOrder(long userId, long orderNumber);

    void addUserOrder(UserOrder userOrder);

    void updateUserOrderTrackingNumber(long userId, long orderNumber, long shippingTrackingNumber);

}
