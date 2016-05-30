package com.advantage.order.store.dao;

import com.advantage.common.enums.PaymentMethodEnum;
import com.advantage.common.enums.TransactionTypeEnum;
import com.advantage.order.store.dto.OrderPaymentInformation;
import com.advantage.order.store.dto.OrderPurchaseResponse;
import com.advantage.order.store.dto.OrderPurchasedProductInformation;
import com.advantage.order.store.dto.OrderShippingInformation;
import com.advantage.order.store.model.OrderHeader;
import com.advantage.order.store.model.OrderHeaderPK;
import com.advantage.order.store.model.OrderLines;
import com.advantage.order.store.model.ShoppingCart;
import com.advantage.root.util.ArgumentValidationHelper;
import com.advantage.root.util.StringHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Moti Ostrovski on 30/05/2016.
 */
@Component
@Qualifier("orderHistoryHeaderManagementRepository")
@Repository
public class DefaultOrderHistoryHeaderManagementRepository extends AbstractRepository implements OrderHistoryHeaderManagementRepository {

    @Override
    public List<OrderHeader> getAll() {
        List<OrderHeader> orderHeaders = entityManager.createNamedQuery(OrderHeader.QUERY_GET_All_ORDERS_HISTORY, OrderHeader.class)
                .getResultList();

        return orderHeaders;
    }

    @Override
    public List<OrderHeader> getByUserId(long userId) {
        return null;
    }

    @Override
    public OrderHeader getOrderHeaderByOrderId(long orderId) {
        return null;
    }
}
