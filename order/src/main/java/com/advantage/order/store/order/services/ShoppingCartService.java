package com.advantage.order.store.order.services;

//import com.advantage.order.store.order.dto.OrderPurchaseRequest;
import AccountServiceClient.AccountServicePort;
import AccountServiceClient.AccountServicePortService;
import AccountServiceClient.GetAccountByIdRequest;
import AccountServiceClient.GetAccountByIdResponse;
import ShipExServiceClient.*;
import com.advantage.common.Url_resources;
import com.advantage.common.enums.ResponseEnum;
import com.advantage.order.store.order.dao.ShoppingCartRepository;
import com.advantage.order.store.order.dto.*;
import com.advantage.order.store.order.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.List;

/**
 * @author Binyamin Regev on 03/12/2015.
 */
@Service
public class ShoppingCartService {
    public static final String ERROR_SHIPEX_GET_SHIPPING_COST_REQUEST_IS_EMPTY = "Get shipping cost request is empty";
    public static final String ERROR_SHIPEX_RESPONSE_FAILURE_CURRENCY_IS_EMPTY = "Get shipping cost response failure, currency is empty";
    public static final String ERROR_SHIPEX_RESPONSE_FAILURE_INVALID_EMPTY_AMOUNT = "Get shipping cost response failure, shipping cost amount invalid empty ";
    public static final String ERROR_SHIPEX_RESPONSE_FAILURE_TRANSACTION_TYPE_MISMATCH = "Get shipping cost response failure, transaction type mismatch";
    public static final String ERROR_SHIPEX_RESPONSE_FAILURE_TRANSACTION_DATE_IS_EMPTY = "Get shipping cost response failure, transaction date is empty";
    public static final String ERROR_SHIPEX_RESPONSE_FAILURE_TRANSACTION_REFERENCE_IS_EMPTY = "Get shipping cost response failure, transaction reference is empty";
    public static final String ERROR_SHIPEX_RESPONSE_FAILURE_INVALID_TRANSACTION_REFERENCE_LENGTH = "Get shipping cost response failure, invalid transaction reference length";

    @Autowired
    @Qualifier("shoppingCartRepository")
    public ShoppingCartRepository shoppingCartRepository;

    /**
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public ShoppingCartResponseDto getShoppingCartsByUserId(long userId) {
        return shoppingCartRepository.getUserShoppingCart(userId);
    }

    /**
     * @param userId
     * @param productId
     * @param stringColor
     * @param quantity
     * @return
     */
    @Transactional
    public ShoppingCartResponse add(long userId, Long productId, String stringColor, int quantity) {
        int color = ShoppingCart.convertHexColorToInt(stringColor);
        return shoppingCartRepository.add(userId, productId, color, quantity);
    }

    public ShoppingCartResponse updateProductQuantityInCart(long userId, Long productId, String stringColor, int quantity) {
        int color = ShoppingCart.convertHexColorToInt(stringColor);
        System.out.println("ShoppingCartService.updateProductQuantityInCart -> color=" + color);
        return shoppingCartRepository.update(userId, productId, color, quantity);
    }

    public ShoppingCartResponse replaceUserCart(long userId, List<ShoppingCartDto> shoppingCarts) {
        return shoppingCartRepository.replace(userId, shoppingCarts);
    }

    /**
     * @param userId
     * @param productId
     * @param stringColor
     * @return
     */
    @Transactional
    public ShoppingCartResponse removeProductFromUserCart(long userId, Long productId, String stringColor) {
        int color = ShoppingCart.convertHexColorToInt(stringColor);
        return shoppingCartRepository.removeProductFromUserCart(userId, productId, color);
    }

    /**
     * @param userId
     * @return
     */
    @Transactional
    public ShoppingCartResponse clearUserCart(long userId) {
        return shoppingCartRepository.clearUserCart(userId);
    }

    /**
     * Verify quantities of all products in user cart.
     *
     * @param userId               Unique user identity.
     * @param shoppingCartProducts {@link List} of {@link ShoppingCartDto} products in user cart to verify quantities.
     * @return {@link ShoppingCartResponseDto} products that had higher quantity in cart than in stock. {@code null}
     */
    @Transactional
    public ShoppingCartResponseDto verifyProductsQuantitiesInUserCart(long userId, List<ShoppingCartDto> shoppingCartProducts) {
        System.out.println("ShoppingCartService -> verifyProductsQuantitiesInUserCart(): userId=" + userId);

        return shoppingCartRepository.verifyProductsQuantitiesInUserCart(userId, shoppingCartProducts);
    }

    public GetAccountByIdResponse getAccountById(GetAccountByIdRequest accountRequest) {
        GetAccountByIdResponse accountResponse = new GetAccountByIdResponse();

        accountResponse.setResult(accountRequest != null);
        if (accountResponse.isResult()) {
            URL urlWsdlLocation = Url_resources.getUrlSoapAccount();

            AccountServicePortService accountPortService = new AccountServicePortService(urlWsdlLocation);

            AccountServicePort accountServicePort = accountPortService.getAccountServicePortSoap11();

            accountResponse = accountServicePort.getAccountById(accountRequest);
        }
        else {
            System.out.println("Get account by id request is empty");
        }

        return accountResponse;
    }

    public ShippingCostResponse getShippingCostFromShipEx(ShippingCostRequest costRequest) {

        ShippingCostResponse costResponse = null;

        if (costRequest == null) {
            return generateShippingCostResponseError(costRequest.getSETransactionType(), ERROR_SHIPEX_GET_SHIPPING_COST_REQUEST_IS_EMPTY);
        }

        if (costResponse == null) {
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

        }

        return costResponse;
    }

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

    public ShippingCostResponse generateShippingCostResponseError(String transactionType, String errorText) {

        ShippingCostResponse costResponse = new ShippingCostResponse();

        costResponse.setSETransactionType(transactionType);
        costResponse.setReason(errorText);
        costResponse.setAmount("0");
        costResponse.setCode(ResponseEnum.ERROR.getStringCode());

        return costResponse;
    }

    public PlaceShippingOrderResponse generatePlaceShippingOrderResponseError(String transactionType, String errorText) {

        PlaceShippingOrderResponse orderResponse = new PlaceShippingOrderResponse();

        orderResponse.setSETransactionType(transactionType);
        orderResponse.setCode(ResponseEnum.ERROR.getStringCode());
        orderResponse.setReason(errorText);
        orderResponse.setTransactionDate("");
        orderResponse.setTransactionReference("");

        return orderResponse;
    }

}

