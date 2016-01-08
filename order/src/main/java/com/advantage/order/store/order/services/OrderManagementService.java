package com.advantage.order.store.order.services;

import com.advantage.order.store.order.dao.OrderManagementRepository;
import com.advantage.order.store.order.dto.OrderPurchaseRequest;
import com.advantage.order.store.order.dto.OrderPurchaseResponse;
import com.advantage.order.store.order.model.UserOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Binyamin Regev on 07/01/2016.
 */
@Service
public class OrderManagementService {

    private static AtomicLong orderNumber;

    @Autowired
    @Qualifier("orderManagementRepository")
    public OrderManagementRepository orderManagementRepository;

    public OrderManagementService() {

        long result = new Date().getTime();

        //  If more than 10 digits than take the 10 right-most digits
        if (result > 9999999999L) {
            result %= 10000000000L;
        }

        //  10 - 10 = 0 => Math.pow(10, 0) = 1 => result *= 1 = result
        int power = 10 - String.valueOf(result).length();
        result *= Math.pow(10, power);

        orderNumber = new AtomicLong(result);
    }

    /**
     * Generate the next value of order number.
     */
    public static long generateOrderNumberNextValue() {
        return orderNumber.getAndIncrement();
    }

    /**
     * Do the order purchase process.
     * Evgeny: CHILL!!! This is just for me!!!!!!!
     *
     * Step #1: Get products info
     * Step #2: If product info differed from receive info - BREAK! Return to UI.
     * Step #3: Generate OrderNumber.
     * Step #4: Do payment (MasterCredit or SafePay) - receive Payment confirmation number.
     * Step #5: INSERT: Save order header and lines (NO TRACKING NUMBER YET!!!)
     * Step #6: Request Tracking Number from ShipEx.
     * Step #7: UPDATE: Save shipping express tracking number to order header.
     * Step #8: Return to UI with SUCCESSFUL code.
     *
     * @param userId
     * @param purchaseRequest
     * @return
     */
    @Transactional
    public OrderPurchaseResponse doPurchase(long userId, OrderPurchaseRequest purchaseRequest) {
        OrderPurchaseResponse purchaseResponse = new OrderPurchaseResponse();

        return purchaseResponse;
    }

    public long generateOrderNumber() {
        return 0L;
    }

    public UserOrder getUserOrder(long userId, long orderNumber) {
        return orderManagementRepository.getUserOrder(userId, orderNumber);
    }

    public void addUserOrder(OrderPurchaseRequest orderPurchaseRequest){

    }


}
