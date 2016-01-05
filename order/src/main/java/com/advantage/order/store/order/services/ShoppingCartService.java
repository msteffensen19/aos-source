package com.advantage.order.store.order.services;

//import com.advantage.order.store.order.dev_only.OrderPurchaseRequest;
import AccountServiceClient.GetAccountByIdResponse;
import ShipExServiceClient.*;
import com.advantage.common.Constants;
import com.advantage.common.enums.ResponseEnum;
import com.advantage.common.enums.TransactionTypeEnum;
import com.advantage.order.store.order.dao.ShoppingCartRepository;
import com.advantage.order.store.order.dto.ShipExResponse;
import com.advantage.order.store.order.dto.ShoppingCartDto;
import com.advantage.order.store.order.dto.ShoppingCartResponse;
import com.advantage.order.store.order.dto.ShoppingCartResponseDto;
import com.advantage.order.store.order.model.ShoppingCart;
import com.advantage.root.util.ArgumentValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author Binyamin Regev on 03/12/2015.
 */
@Service
public class ShoppingCartService {

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

//    public GetAccountByIdResponse getAccountById() {
//        return new GetAccountByIdResponse();
//    }

    public ShippingCostResponse getShippingCostFromShipEx() throws MalformedURLException {
        //  "GetAccountById"
        //  Shipping Express --> "ShippingCost"
        /*
        shoppingCartRepository.getShippingCostFromShipEx();
         */
        SEAddress address = new SEAddress();
        ShippingCostRequest costRequest = new ShippingCostRequest();

        address.setAddressLine1("address");
        address.setCity("Jerusalem");
        address.setCountry("IL");
        address.setPostalCode("123123");
        address.setState("Israel");

        costRequest.setSEAddress(address);
        costRequest.setSETransactionType(Constants.TRANSACTION_TYPE_SHIPPING_COST);
        costRequest.setSECustomerName("Customer Full Name");
        costRequest.setSECustomerPhone("+972 77 7654321");
        costRequest.setSENumberOfProducts(1);

        URL urlWsdlLocation = new URL("http://localhost:8080/ShipEx/shipex.wsdl");
        //ShipExPortService shipExPortService = new ShipExPortService(urlWsdlLocation, serviceName);
        ShipExPortService shipExPortService = new ShipExPortService(urlWsdlLocation);

        ShipExPort shipExPort = shipExPortService.getShipExPortSoap11();
        ShippingCostResponse costResponse = shipExPort.shippingCost(costRequest);

        if (costResponse.getCode().equalsIgnoreCase(ResponseEnum.OK.getStringCode())) {
            //  Failure - Response code IS NOT "OK"
            System.out.println("Failure - Response code IS NOT \'OK\'");
        }

        if (costResponse.getAmount().isEmpty()) {
            //  Failure - invalid amount (empty)
            System.out.println("Failure - invalid amount (empty)");
        }

        if (costResponse.getCurrency().isEmpty()) {
            //  Failure - invalid currency (empty)
            System.out.println("Failure - invalid currency (empty)");
        }

        if (costResponse.getSETransactionType().equalsIgnoreCase(costRequest.getSETransactionType())) {
            //  Failure - Transaction type mismatch
            System.out.println("Failure - Transaction type mismatch");
        }

        return costResponse;
    }


//    /**
//     * Call {@link ShoppingCartRepository} to do the order purchase process.
//     * @param userId
//     * @param orderPurchaseRequest
//     * @return
//     */
//    @Transactional
//    public ShoppingCartResponse doPurchase(long userId, OrderPurchaseRequest orderPurchaseRequest) {
//        return shoppingCartRepository.doPurchase(userId, orderPurchaseRequest);
//    }

}

