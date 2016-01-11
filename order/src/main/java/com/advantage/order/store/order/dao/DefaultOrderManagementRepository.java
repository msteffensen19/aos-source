package com.advantage.order.store.order.dao;

import com.advantage.common.enums.PaymentMethodEnum;
import com.advantage.common.enums.TransactionTypeEnum;
import com.advantage.order.store.dao.AbstractRepository;
import com.advantage.order.store.order.dto.*;
import com.advantage.order.store.order.model.*;
import com.advantage.root.util.ArgumentValidationHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Binyamin Regev on 07/01/2016.
 */
@Component
@Qualifier("orderManagementRepository")
@Repository
public class DefaultOrderManagementRepository extends AbstractRepository implements OrderManagementRepository {

    private static final String MESSAGE_SHIPPING_TRACKING_NUMBER_UPDATED_SUCCESSFULLY = "Purchase order, shipping tracking number was updated successfully";
    private static final String MESSAGE_ORDER_NOT_FOUND_TRACKING_NUMBER_NOT_UPDATED = "Purchase order not found, tracking number not updated";

    private OrderPurchaseResponse orderPurchaseResponse = null;

    /**
     * Add an order to {@code Entities} {@link OrderHeader} and {@link OrderLines} in <b><i>ORDER</i></b> Schema.
     * @param userId
     * @param orderNumber
     * @param orderTimestamp
     * @param orderShippingInformation
     * @param orderPaymentInformation
     * @param purchasedProducts
     */
    @Override
    public void addUserOrder(long userId, long orderNumber, long orderTimestamp,
                             OrderShippingInformation orderShippingInformation,
                             OrderPaymentInformation orderPaymentInformation,
                             List<OrderPurchasedProductInformation> purchasedProducts) {

        OrderHeader orderHeader = new OrderHeader();

        ArgumentValidationHelper.validateLongArgumentIsPositive(userId, "user id");
        ArgumentValidationHelper.validateLongArgumentIsPositive(orderNumber, "order number");
        ArgumentValidationHelper.validateLongArgumentIsPositive(orderTimestamp, "order timestamp");

        ArgumentValidationHelper.validateNumberArgumentIsPositive(orderShippingInformation.getNumberOfProducts(), "number of products");
        ArgumentValidationHelper.validateDoubleArgumentIsPositive(orderShippingInformation.getShippingCost(), "shipping cost");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(orderShippingInformation.getAddress(), "shipping address");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(orderShippingInformation.getCity(), "shipping city");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(orderShippingInformation.getPostalCode(), "shipping postal code");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(orderShippingInformation.getState(), "shipping state");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(orderShippingInformation.getCountryCode(), "shipping address");

        this.validatePaymentMethod(orderPaymentInformation.getPaymentMethod(), "payment method");
        this.validateTransactionType(orderPaymentInformation.getTransactionType(), "transaction type");
        ArgumentValidationHelper.validateLongArgumentIsPositive(orderPaymentInformation.getReferenceNumber(), "payment confirmation number");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(orderPaymentInformation.getCustomerPhone(), "customer phone");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(orderPaymentInformation.getTransactionDate(), "transaction date");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(orderPaymentInformation.getAccountNumber(), "account number");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(orderPaymentInformation.getCurrency(), "currency");
        ArgumentValidationHelper.validateDoubleArgumentIsPositive(orderPaymentInformation.getAmount(), "payment amount");

        if (orderPaymentInformation.getPaymentMethod().equalsIgnoreCase(PaymentMethodEnum.MASTER_CREDIT.getStringCode())) {
            ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(orderPaymentInformation.getCardNumber(), "MasterCredit Card Number");
            ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(orderPaymentInformation.getExpirationDate(), "MasterCredit card expiration date");
            ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(orderPaymentInformation.getCustomerName(), "MasterCredit customer name on card");
            ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(orderPaymentInformation.getCvvNumber(), "MasterCredit card CVV number");
        }
        else if (orderPaymentInformation.getPaymentMethod().equalsIgnoreCase(PaymentMethodEnum.SAFE_PAY.getStringCode())) {
            ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(orderPaymentInformation.getUsername(), "SafePay Card Number");
        }

        orderHeader.setUserId(userId);
        orderHeader.setOrderNumber(orderNumber);
        orderHeader.setOrderTimestamp(orderTimestamp);

        orderHeader.setNumberOfProducts(orderShippingInformation.getNumberOfProducts());
        orderHeader.setShippingCost(orderShippingInformation.getShippingCost());
        orderHeader.setShippingAddress(orderShippingInformation.getAddress());
        orderHeader.setShippingCity(orderShippingInformation.getCity());
        orderHeader.setShippingPostalCode(orderShippingInformation.getPostalCode());
        orderHeader.setShippingState(orderShippingInformation.getState());
        orderHeader.setShippingCountry(orderShippingInformation.getCountryCode());

        orderHeader.setPaymentMethod(orderPaymentInformation.getPaymentMethod());
        orderHeader.setTransactionType(orderPaymentInformation.getTransactionType());
        orderHeader.setPaymentConfirmationNumber(orderPaymentInformation.getReferenceNumber());
        orderHeader.setCustomerPhone(orderPaymentInformation.getCustomerPhone());
        orderHeader.setTransactionDate(orderPaymentInformation.getTransactionDate());
        orderHeader.setAccountNumber(orderPaymentInformation.getAccountNumber());
        orderHeader.setCurrency(orderPaymentInformation.getCurrency());
        orderHeader.setAmount(orderPaymentInformation.getAmount());

        orderHeader.setCardNumber(orderPaymentInformation.getCardNumber());
        orderHeader.setExpirationDate(orderPaymentInformation.getExpirationDate());
        orderHeader.setCustomerName(orderPaymentInformation.getCustomerName());
        orderHeader.setCvvNumber(orderPaymentInformation.getCvvNumber());
        orderHeader.setUsername(orderPaymentInformation.getUsername());

        entityManager.getTransaction().begin();
        entityManager.persist(orderHeader);

        boolean successful = true;

        //  Validate OrderLines information
        //List<OrderPurchasedProductInformation> purchasedProducts
        for (OrderPurchasedProductInformation purchasedProduct: purchasedProducts) {
            ArgumentValidationHelper.validateArgumentIsNotNull(purchasedProduct.getProductId(), "product id");
            ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(purchasedProduct.getProductName(), "product name");
            ArgumentValidationHelper.validateNumberArgumentIsPositiveOrZero(ShoppingCart.convertHexColorToInt(purchasedProduct.getHexColor()), "color decimal RGB value");
            ArgumentValidationHelper.validateDoubleArgumentIsPositiveOrZero(purchasedProduct.getPricePerItem(), "price per item");
            ArgumentValidationHelper.validateNumberArgumentIsPositive(purchasedProduct.getQuantity(), "quantity");

            OrderLines orderLines = new OrderLines(userId, orderNumber,
                    purchasedProduct.getProductId(),
                    purchasedProduct.getProductName(),
                    ShoppingCart.convertHexColorToInt(purchasedProduct.getHexColor()),
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
                    "",
                    MESSAGE_SHIPPING_TRACKING_NUMBER_UPDATED_SUCCESSFULLY,
                    orderNumber);

        } else {
            orderPurchaseResponse = new OrderPurchaseResponse(false,
                    "",
                    MESSAGE_ORDER_NOT_FOUND_TRACKING_NUMBER_NOT_UPDATED,
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
