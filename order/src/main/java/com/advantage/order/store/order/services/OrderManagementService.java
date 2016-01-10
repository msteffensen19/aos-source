package com.advantage.order.store.order.services;

import ShipExServiceClient.*;
import com.advantage.common.Constants;
import com.advantage.common.Url_resources;
import com.advantage.common.enums.PaymentMethodEnum;
import com.advantage.common.enums.ResponseEnum;
import com.advantage.common.enums.TransactionTypeEnum;
import com.advantage.order.store.order.dao.ShoppingCartRepository;
import com.advantage.order.store.order.dto.*;
import com.advantage.order.store.order.dao.OrderManagementRepository;
import com.advantage.order.store.order.model.ShoppingCart;
import com.advantage.order.store.order.model.UserOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Binyamin Regev on 07/01/2016.
 */
@Service
public class OrderManagementService {

    public static final String ERROR_SHIPEX_GET_SHIPPING_COST_REQUEST_IS_EMPTY = "Get shipping cost request is empty";
    public static final String ERROR_SHIPEX_RESPONSE_FAILURE_CURRENCY_IS_EMPTY = "Get shipping cost response failure, currency is empty";
    public static final String ERROR_SHIPEX_RESPONSE_FAILURE_INVALID_EMPTY_AMOUNT = "Get shipping cost response failure, shipping cost amount invalid empty ";
    public static final String ERROR_SHIPEX_RESPONSE_FAILURE_TRANSACTION_TYPE_MISMATCH = "Get shipping cost response failure, transaction type mismatch";
    public static final String ERROR_SHIPEX_RESPONSE_FAILURE_TRANSACTION_DATE_IS_EMPTY = "Get shipping cost response failure, transaction date is empty";
    public static final String ERROR_SHIPEX_RESPONSE_FAILURE_TRANSACTION_REFERENCE_IS_EMPTY = "Get shipping cost response failure, transaction reference is empty";
    public static final String ERROR_SHIPEX_RESPONSE_FAILURE_INVALID_TRANSACTION_REFERENCE_LENGTH = "Get shipping cost response failure, invalid transaction reference length";

    private static AtomicLong orderNumber;

    @Autowired
    @Qualifier("orderManagementRepository")
    public OrderManagementRepository orderManagementRepository;

    @Autowired
    @Qualifier("shoppingCartRepository")
    public ShoppingCartRepository shoppingCartRepository;

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
     * Do the order purchase process.
     * Evgeny: CHILL!!! This is just for me!!!!!!!
     *
     * (V)  Step #1: Get products info
     * (V)  Step #2: Generate OrderNumber.
     * ( )  Step #3: Do payment (MasterCredit or SafePay) - send REST POST request, receive Payment confirmation number.
     * (V)  Step #4: INSERT: Save order header and lines (NO TRACKING NUMBER YET!!!)
     * ( )  Step #5: Send request to get Tracking Number from ShipEx.
     * (V)  Step #6: UPDATE: Save shipping express tracking number to order header.
     * ( )  Step #7: Return to UI with SUCCESSFUL code.
     *
     * @param userId
     * @param purchaseRequest
     * @return
     */
    @Transactional
    public OrderPurchaseResponse doPurchase(long userId, OrderPurchaseRequest purchaseRequest) {

        OrderPurchaseResponse purchaseResponse = new OrderPurchaseResponse();

        //  Step #1: Get products info
        List<OrderPurchasedProductInformation> purchasedProducts = getPurchasedProductsInformation(userId, purchaseRequest.getPurchasedProducts());

        //  Step #2: Generate order number
        long orderNumber = generateOrderNumberNextValue();

        //  Step #3: Do payment MasterCredit / SafePay
        if (purchaseRequest.getOrderPaymentInformation().getPaymentMethod().equals(PaymentMethodEnum.MASTER_CREDIT.getStringCode())) {
            MasterCreditRequest masterCreditRequest = new MasterCreditRequest(
                    purchaseRequest.getOrderPaymentInformation().getTransactionType(),
                    Long.valueOf(purchaseRequest.getOrderPaymentInformation().getCardNumber()),
                    purchaseRequest.getOrderPaymentInformation().getExpirationDate(),
                    purchaseRequest.getOrderPaymentInformation().getCustomerName(),
                    purchaseRequest.getOrderPaymentInformation().getCustomerPhone(),
                    Integer.valueOf(purchaseRequest.getOrderPaymentInformation().getCvvNumber()),
                    purchaseRequest.getOrderPaymentInformation().getTransactionDate(),
                    Long.valueOf(purchaseRequest.getOrderPaymentInformation().getAccountNumber()),
                    purchaseRequest.getOrderPaymentInformation().getAmount(),
                    purchaseRequest.getOrderPaymentInformation().getCurrency());

            MasterCreditResponse masterCreditResponse = payWithMasterCredit(masterCreditRequest);

            // Check "masterCreditResponse"

        }
        else if (purchaseRequest.getOrderPaymentInformation().getPaymentMethod().equals(PaymentMethodEnum.SAFE_PAY.getStringCode())) {
            SafePayRequest safePayRequest = new SafePayRequest(
                    purchaseRequest.getOrderPaymentInformation().getTransactionType(),
                    purchaseRequest.getOrderPaymentInformation().getUsername(),
                    purchaseRequest.getOrderPaymentInformation().getPassword(),
                    purchaseRequest.getOrderPaymentInformation().getCustomerPhone(),
                    purchaseRequest.getOrderPaymentInformation().getTransactionDate(),
                    Long.valueOf(purchaseRequest.getOrderPaymentInformation().getAccountNumber()),
                    purchaseRequest.getOrderPaymentInformation().getAmount(),
                    purchaseRequest.getOrderPaymentInformation().getCurrency());

            SafePayResponse safePayResponse = payWithSafePay(safePayRequest);

            // Check "safePayResponse"
        }

        return purchaseResponse;
    }

    /**
     *  For each purchased product:
     *      Retrieve product-name, product-color-name and price-per-item.
     */
    private List<OrderPurchasedProductInformation> getPurchasedProductsInformation(long userId,
                                                                                   List<ShoppingCartDto> cartProducts) {
        List<OrderPurchasedProductInformation> purchasedProducts = new ArrayList<>();

        for (ShoppingCartDto cartProduct : cartProducts) {
            ShoppingCartResponseDto.CartProduct product =
                    shoppingCartRepository.getCartProductDetails(cartProduct.getProductId(),
                                                                cartProduct.getHexColor().toUpperCase());

            if (! product.getProductName().equalsIgnoreCase(Constants.NOT_FOUND)) {
                /*  Add a product to user shopping cart response class  */
                purchasedProducts.add(new OrderPurchasedProductInformation(cartProduct.getProductId(),
                        product.getProductName(),
                        product.getColor().getCode(),
                        product.getColor().getName(),
                        product.getPrice(),
                        cartProduct.getQuantity()));
            } else {
                //  Product from cart was not found in CATALOG
                purchasedProducts.add(new OrderPurchasedProductInformation(cartProduct.getProductId(),
                        Constants.NOT_FOUND,
                        "000000",
                        "BLACK",
                        -999999.99,                     //  Price-per-item
                        cartProduct.getQuantity()));
            }

        }

        return purchasedProducts;
    }

    /**
     * Generate the next value of order number.
     */
    public static long generateOrderNumberNextValue() {
        return orderNumber.getAndIncrement();
    }

    /**
     * Send REST POST request to pay for order using <b>MasterCredit</b> service.
     * @param masterCreditRequest
     * @return
     */
    public MasterCreditResponse payWithMasterCredit(MasterCreditRequest masterCreditRequest) {

        // RequestMethod.POST)
        URL urlPayment = null;
        try {
            urlPayment = new URL(Url_resources.getUrlMasterCredit(),"payments/payment");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        return new MasterCreditResponse();
    }

    /**
     * Send REST POST request to pay for order using <b>SafePay</b> service.
     * @param safePayRequest
     * @return
     */
    public SafePayResponse payWithSafePay(SafePayRequest safePayRequest) {
        return new SafePayResponse();
    }

    /**
     * Add user order header and line in ORDER schema.
     * @param orderPurchaseRequest
     */
    public void addUserOrder(OrderPurchaseRequest orderPurchaseRequest){

    }

    /**
     * Send SOAP request for ShipEx tracking number and receive it in response.
     * @param orderRequest Shipping Express request for tracking number.
     * @return Shipping Express Tracking Number in {@link PlaceShippingOrderResponse}.
     */
    public PlaceShippingOrderResponse placeShippingOrder(PlaceShippingOrderRequest orderRequest) {

        PlaceShippingOrderResponse orderResponse = null;

        if (orderRequest == null) {
            orderResponse = generatePlaceShippingOrderResponseError(orderRequest.getSETransactionType(), "Shipping order request is empty");
        }

        if (orderResponse == null) {
            URL urlWsdlLocation = Url_resources.getUrlSoapShipEx();

            //QName serviceName = new QName("https://www.AdvantageOnlineBanking.com/ShipEx/", "ShipExPortService");
            //ShipExPortService shipExPortService = new ShipExPortService(urlWsdlLocation, serviceName);
            ShipExPortService shipExPortService = new ShipExPortService(urlWsdlLocation);

            ShipExPort shipExPort = shipExPortService.getShipExPortSoap11();

            orderResponse = shipExPort.placeShippingOrder(orderRequest);

            if (! orderResponse.getCode().equalsIgnoreCase(ResponseEnum.OK.getStringCode())) {
                System.out.println("Shipping Express: placeShippingOrder() --> Response returned \'" + orderResponse.getCode() + "\', Reason: \'" + orderResponse.getReason() + "\'");
                orderResponse = generatePlaceShippingOrderResponseError(orderRequest.getSETransactionType(), "Shipping Express: placeShippingOrder() --> Response returned '" + orderResponse.getCode() + "\', Reason: \'" + orderResponse.getReason() + "\'");
            }
            else if (orderResponse.getTransactionDate().isEmpty()) {
                System.out.println("Shipping Express: placeShippingOrder() --> " + ERROR_SHIPEX_RESPONSE_FAILURE_TRANSACTION_DATE_IS_EMPTY);
                orderResponse = generatePlaceShippingOrderResponseError(orderRequest.getSETransactionType(), "Shipping Express: placeShippingOrder() --> " + ERROR_SHIPEX_RESPONSE_FAILURE_TRANSACTION_DATE_IS_EMPTY);
            }
            else if (orderResponse.getTransactionReference().isEmpty()) {
                System.out.println("Shipping Express: placeShippingOrder() --> " + ERROR_SHIPEX_RESPONSE_FAILURE_TRANSACTION_REFERENCE_IS_EMPTY);
                orderResponse = generatePlaceShippingOrderResponseError(orderRequest.getSETransactionType(), "Shipping Express: placeShippingOrder() --> " + ERROR_SHIPEX_RESPONSE_FAILURE_TRANSACTION_REFERENCE_IS_EMPTY);
            }
            else if (orderResponse.getTransactionReference().length() == 10) {
                System.out.println("Shipping Express: placeShippingOrder() --> " + ERROR_SHIPEX_RESPONSE_FAILURE_INVALID_TRANSACTION_REFERENCE_LENGTH);
                orderResponse = generatePlaceShippingOrderResponseError(orderRequest.getSETransactionType(), "Shipping Express: placeShippingOrder() --> " + ERROR_SHIPEX_RESPONSE_FAILURE_INVALID_TRANSACTION_REFERENCE_LENGTH);
            }
            else if (orderResponse.getSETransactionType().equalsIgnoreCase(orderRequest.getSETransactionType())) {
                System.out.println("Shipping Express: placeShippingOrder() --> " + ERROR_SHIPEX_RESPONSE_FAILURE_TRANSACTION_TYPE_MISMATCH);
                orderResponse = generatePlaceShippingOrderResponseError(orderRequest.getSETransactionType(), "Shipping Express: placeShippingOrder() --> " + ERROR_SHIPEX_RESPONSE_FAILURE_TRANSACTION_TYPE_MISMATCH);
            }
        }
        return orderResponse;
    }

    /**
     * Update tracking number in {@code OrderHeder} entity.
     * @param userId
     * @param orderNumber
     * @param shippingTrackingNumber
     */
    public void updateUserOrderTrackingNumber(long userId, long orderNumber, long shippingTrackingNumber) {
        orderManagementRepository.updateUserOrderTrackingNumber(userId, orderNumber, shippingTrackingNumber);
    }

    public ShippingCostResponse getShippingCostFromShipEx(ShippingCostRequest costRequest) {

        ShippingCostResponse costResponse = null;

        if (costRequest == null) {
            return generateShippingCostResponseError(costRequest.getSETransactionType(), ERROR_SHIPEX_GET_SHIPPING_COST_REQUEST_IS_EMPTY);
        }


        URL urlWsdlLocation = Url_resources.getUrlSoapShipEx();

        //QName serviceName = new QName("https://www.AdvantageOnlineBanking.com/ShipEx/", "ShipExPortService");
        //ShipExPortService shipExPortService = new ShipExPortService(urlWsdlLocation, serviceName);
        ShipExPortService shipExPortService = new ShipExPortService(urlWsdlLocation);

        ShipExPort shipExPort = shipExPortService.getShipExPortSoap11();

        costResponse = shipExPort.shippingCost(costRequest);

        if (! costResponse.getCode().equalsIgnoreCase(ResponseEnum.OK.getStringCode())) {
            System.out.println("Shipping Express: getShippingCost() --> Response returned \'" + costResponse.getCode() + "\', Reason: \'" + costResponse.getReason() + "\'");
            costResponse = generateShippingCostResponseError(costRequest.getSETransactionType(), "Shipping Express: getShippingCost() --> Response returned \'" + costResponse.getCode() + "\', Reason: \'" + costResponse.getReason() + "\'");
        }
        else if (costResponse.getAmount().isEmpty()) {
            System.out.println("Shipping Express: getShippingCost() --> " + ERROR_SHIPEX_RESPONSE_FAILURE_INVALID_EMPTY_AMOUNT);
            costResponse = generateShippingCostResponseError(costRequest.getSETransactionType(), "Shipping Express: getShippingCost() --> " + ERROR_SHIPEX_RESPONSE_FAILURE_INVALID_EMPTY_AMOUNT);
        }
        else if (costResponse.getCurrency().isEmpty()) {
            System.out.println("Shipping Express: getShippingCost() --> " + ERROR_SHIPEX_RESPONSE_FAILURE_CURRENCY_IS_EMPTY);
            costResponse = generateShippingCostResponseError(costRequest.getSETransactionType(), "Shipping Express: getShippingCost() --> " + ERROR_SHIPEX_RESPONSE_FAILURE_CURRENCY_IS_EMPTY);
        }
        else if (costResponse.getSETransactionType().equalsIgnoreCase(costRequest.getSETransactionType())) {
            System.out.println("Shipping Express: getShippingCost() --> " + ERROR_SHIPEX_RESPONSE_FAILURE_TRANSACTION_TYPE_MISMATCH);
            costResponse = generateShippingCostResponseError(costRequest.getSETransactionType(), "Shipping Express: getShippingCost() --> " + ERROR_SHIPEX_RESPONSE_FAILURE_TRANSACTION_TYPE_MISMATCH);
        }

        return costResponse;
    }

    public UserOrder getUserOrder(long userId, long orderNumber) {
        return orderManagementRepository.getUserOrder(userId, orderNumber);
    }

    private ShippingCostResponse generateShippingCostResponseError(String transactionType, String errorText) {

        ShippingCostResponse costResponse = new ShippingCostResponse();

        costResponse.setSETransactionType(transactionType);
        costResponse.setReason(errorText);
        costResponse.setAmount("0");
        costResponse.setCode(ResponseEnum.ERROR.getStringCode());

        return costResponse;
    }

    private PlaceShippingOrderResponse generatePlaceShippingOrderResponseError(String transactionType, String errorText) {

        PlaceShippingOrderResponse orderResponse = new PlaceShippingOrderResponse();

        orderResponse.setSETransactionType(transactionType);
        orderResponse.setCode(ResponseEnum.ERROR.getStringCode());
        orderResponse.setReason(errorText);
        orderResponse.setTransactionDate("");
        orderResponse.setTransactionReference("");

        return orderResponse;
    }
}
