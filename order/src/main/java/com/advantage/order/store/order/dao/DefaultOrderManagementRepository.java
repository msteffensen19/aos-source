package com.advantage.order.store.order.dao;

import com.advantage.common.enums.PaymentMethodEnum;
import com.advantage.common.enums.TransactionTypeEnum;
import com.advantage.order.store.dao.AbstractRepository;
import com.advantage.order.store.order.dto.OrderPurchaseResponse;
import com.advantage.order.store.order.model.OrderHeader;
import com.advantage.order.store.order.model.OrderHeaderPK;
import com.advantage.order.store.order.model.OrderLines;
import com.advantage.order.store.order.model.UserOrder;
import com.advantage.root.util.ArgumentValidationHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author Binyamin Regev on 07/01/2016.
 */
public class DefaultOrderManagementRepository extends AbstractRepository implements OrderManagementRepository {

    private static final String MESSAGE_SHIPPING_TRACKING_NUMBER_UPDATED_SUCCESSFULLY = "Purchase order, shipping tracking number was updated successfully";
    private static final String MESSAGE_SHIPPING_COST_INVALID_AMOUNT = "Purchase order, shipping cost contains invalid value";
    private static final String MESSAGE_SHIPPING_INFORMATION_IS_INCOMPLETE_EMPTY = "Purchase order, shipping information is incomplete, field %s is empty";
    private static final String MESSAGE_SHIPPING_INFORMATION_IS_INCOMPLETE_INVALID = "Purchase order, shipping information is incomplete, field %s contains an invalid value";
    private static final String MESSAGE_PAYMENT_INFORMATION_MC_IS_INCOMPLETE_EMPTY = "Purchase order, MasterCredit payment information is incomplete, field %s is empty";
    private static final String MESSAGE_PAYMENT_INFORMATION_MC_IS_INCOMPLETE_INVALID = "Purchase order, MasterCredit payment information is incomplete, field %s contains an invalid value";
    private static final String MESSAGE_PAYMENT_INFORMATION_SP_IS_INCOMPLETE_EMPTY = "Purchase order, SafePay payment information is incomplete, field %s is empty";
    private static final String MESSAGE_PAYMENT_INFORMATION_SP_IS_INCOMPLETE_INVALID = "Purchase order, SafePay payment information is incomplete, field %s contains an invalid value";
    private static final String MESSAGE_PRODUCT_INFORMATION_IS_INCOMPLETE_EMPTY = "Purchase order, product information is incomplete, field %s is empty";
    private static final String MESSAGE_PRODUCT_INFORMATION_IS_INCOMPLETE_INVALID = "Purchase order, product information is incomplete, field %s contains an invalid value";

    private OrderPurchaseResponse orderPurchaseResponse = null;

    /**
     * Get {@link UserOrder} by {@code userId} and {@code orderNumber}.
     * @param userId
     * @param orderNumber
     * @return
     */
    @Override
    public UserOrder getUserOrder(long userId, long orderNumber) {
        return null;
    }

    /**
     * Add an order to {@code Entities} {@link OrderHeader} and {@link OrderLines} in <b><i>ORDER</i></b> Schema.
     * @param userOrder
     */
    @Override
    public void addUserOrder(UserOrder userOrder) {
        OrderHeader orderHeader = new OrderHeader();

        ArgumentValidationHelper.validateLongArgumentIsPositive(userOrder.getUserId(), "user id");
        ArgumentValidationHelper.validateLongArgumentIsPositive(userOrder.getOrderNumber(), "order number");
        ArgumentValidationHelper.validateNumberArgumentIsPositive(userOrder.getNumberOfProducts(), "number of products");
        ArgumentValidationHelper.validateDoubleArgumentIsPositive(userOrder.getShippingCost(), "shipping cost");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(userOrder.getShippingAddress(), "shipping address");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(userOrder.getShippingCity(), "shipping city");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(userOrder.getShippingPostalCode(), "shipping postal code");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(userOrder.getShippingState(), "shipping state");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(userOrder.getShippingCountry(), "shipping address");
        this.validatePaymentMethod(userOrder.getPaymentMethod(), "payment method");
        this.validateTransactionType(userOrder.getTransactionType(), "transaction type");
        ArgumentValidationHelper.validateLongArgumentIsPositive(userOrder.getPaymentConfirmationNumber(), "order number");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(userOrder.getCustomerPhone(), "customer phone");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(userOrder.getTransactionDate(), "transaction date");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(userOrder.getAccountNumber(), "account number");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(userOrder.getCurrency(), "currency");
        ArgumentValidationHelper.validateDoubleArgumentIsPositive(userOrder.getAmount(), "payment amount");

        if (userOrder.getPaymentMethod().equalsIgnoreCase(PaymentMethodEnum.MASTER_CREDIT.getStringCode())) {
            ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(userOrder.getCardNumber(), "MasterCredit Card Number");
            ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(userOrder.getExpirationDate(), "MasterCredit card expiration date");
            ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(userOrder.getCustomerName(), "MasterCredit customer name on card");
            ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(userOrder.getCvvNumber(), "MasterCredit card CVV number");
        }
        else if (userOrder.getPaymentMethod().equalsIgnoreCase(PaymentMethodEnum.SAFE_PAY.getStringCode())) {
            ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(userOrder.getUsername(), "SafePay Card Number");
        }

        orderHeader.setUserId(userOrder.getUserId());

        orderHeader.setOrderNumber(userOrder.getOrderNumber());
        orderHeader.setNumberOfProducts(userOrder.getNumberOfProducts());
        orderHeader.setShippingCost(userOrder.getShippingCost());
        orderHeader.setShippingAddress(userOrder.getShippingAddress());
        orderHeader.setShippingCity(userOrder.getShippingCity());
        orderHeader.setShippingPostalCode(userOrder.getShippingPostalCode());
        orderHeader.setShippingState(userOrder.getShippingState());
        orderHeader.setShippingCountry(userOrder.getShippingCountry());
        orderHeader.setPaymentMethod(userOrder.getPaymentMethod());
        orderHeader.setTransactionType(userOrder.getTransactionType());
        orderHeader.setPaymentConfirmationNumber(userOrder.getPaymentConfirmationNumber());
        orderHeader.setCustomerPhone(userOrder.getCustomerPhone());
        orderHeader.setTransactionDate(userOrder.getTransactionDate());
        orderHeader.setAccountNumber(userOrder.getAccountNumber());
        orderHeader.setCurrency(userOrder.getCurrency());
        orderHeader.setAmount(userOrder.getAmount());

        orderHeader.setCardNumber(userOrder.getCardNumber());
        orderHeader.setExpirationDate(userOrder.getExpirationDate());
        orderHeader.setCustomerName(userOrder.getCustomerName());
        orderHeader.setCvvNumber(userOrder.getCvvNumber());
        orderHeader.setUsername(userOrder.getUsername());


        entityManager.getTransaction().begin();
        entityManager.persist(orderHeader);

        boolean successful = true;

        //  Validate OrderLines information
        List<UserOrder.UserOrderLines> purchasedProducts = userOrder.getOrderLines();
        for (UserOrder.UserOrderLines purchasedProduct: purchasedProducts) {
            ArgumentValidationHelper.validateArgumentIsNotNull(purchasedProduct.getProductId(), "product id");
            ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(purchasedProduct.getProductName(), "product name");
            ArgumentValidationHelper.validateNumberArgumentIsPositiveOrZero(purchasedProduct.getProductColor(), "color decimal RGB value");
            ArgumentValidationHelper.validateDoubleArgumentIsPositiveOrZero(purchasedProduct.getPricePerItem(), "price per item");
            ArgumentValidationHelper.validateNumberArgumentIsPositive(purchasedProduct.getQuantity(), "quantity");


            OrderLines orderLines = new OrderLines(userOrder.getUserId(),
                    userOrder.getOrderNumber(),
                    purchasedProduct.getProductId(),
                    purchasedProduct.getProductName(),
                    purchasedProduct.getProductColor(),
                    purchasedProduct.getPricePerItem(),
                    purchasedProduct.getQuantity());

            entityManager.persist(orderLines);
        }

        if (successful) {
            entityManager.getTransaction().commit();
        }
        else {
            entityManager.getTransaction().rollback();
        }

    }

    @Override
    public void updateUserOrderTrackingNumber(long userId, long orderNumber, long shippingTrackingNumber) {
        OrderHeaderPK orderHeaderPk = new OrderHeaderPK(userId, orderNumber);
        OrderHeader orderHeader = entityManager.find(OrderHeader.class, orderHeaderPk);

        //  Check if there is this ShoppingCart already exists
        if (orderHeader != null) {

            //  Existing product in user shopping cart (the same productId + color)
            orderHeader.setShippingTrackingNumber(shippingTrackingNumber);

            entityManager.persist(orderHeader);

            orderPurchaseResponse = new OrderPurchaseResponse(true,
                    MESSAGE_SHIPPING_TRACKING_NUMBER_UPDATED_SUCCESSFULLY,
                    userId,
                    orderNumber);

        } else {
            orderPurchaseResponse = new OrderPurchaseResponse(false,
                    MESSAGE_SHIPPING_TRACKING_NUMBER_UPDATED_SUCCESSFULLY,
                    userId,
                    orderNumber);
        }
    }

    /*

     */
    private void validatePaymentMethod(final String paymentMethod, final String argumentInformativeName) {

        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(paymentMethod, "payment method");

        if (paymentMethod.trim().length() == 0) {
            final String messageString = getBlankStringArgumentMessage(argumentInformativeName);
            throw new IllegalArgumentException(messageString);
        }
        else if (PaymentMethodEnum.contains(paymentMethod)) {
            final String stringMessage = getNotInListArgumentMessage(paymentMethod, argumentInformativeName);
            throw new IllegalArgumentException(stringMessage);
        }
    }

    private void validateTransactionType(final String transactionType, final String argumentInformativeName) {

        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(transactionType, argumentInformativeName);

        if (transactionType.trim().length() == 0) {
            final String messageString = getBlankStringArgumentMessage(argumentInformativeName);
            throw new IllegalArgumentException(messageString);
        }
        else if (TransactionTypeEnum.contains(transactionType)) {
            final String stringMessage = getNotInListArgumentMessage(transactionType, argumentInformativeName);
            throw new IllegalArgumentException(stringMessage);
        }
    }

    private String getBlankStringArgumentMessage(final String argumentInformativeName) {

        assert StringUtils.isNoneBlank(argumentInformativeName);

        final StringBuilder message = new StringBuilder("Could not accept a blank or empty string as argument [");
        message.append(argumentInformativeName);
        message.append("]");
        return message.toString();
    }

    private String getNotInListArgumentMessage(final String argumentValue, final String argumentInformativeName) {

        assert StringUtils.isNoneBlank(argumentInformativeName);

        final StringBuilder message = new StringBuilder("Could not process ")
                .append(argumentInformativeName)
                .append(" \'")
                .append(argumentValue)
                .append("\' not in list as an argument");
        return message.toString();
    }
}
