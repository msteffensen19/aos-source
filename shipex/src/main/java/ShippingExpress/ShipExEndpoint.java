package ShippingExpress;

import ShippingExpress.WsModel.*;
import ShippingExpress.config.WebServiceConfig;
import ShippingExpress.model.ShippingExpressService;
import ShippingExpress.util.ArgumentValidationHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ShipExEndpoint {
    final static Logger logger = Logger.getLogger("ShipEx");
    public static final String TRANSACTION_TYPE_SHIPPINGCOST = "SHIPPINGCOST";
    public static final String TRANSACTION_TYPE_PLACE_SHIPPING_ORDER = "PlaceShippingOrder";
    private ShippingExpressService shippingService;

    @Autowired
    public ShipExEndpoint(ShippingExpressService shippingService) {
        this.shippingService = shippingService;
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "ShippingCostRequest")
    @ResponsePayload
    public ShippingCostResponse getShippingCost(@RequestPayload ShippingCostRequest request) {
        ShippingCostResponse response = new ShippingCostResponse();
        String validation = ArgumentValidationHelper.shippingCostRequestValidation(request);
        logShippingCostRequest(request);
        response.setSETransactionType(TRANSACTION_TYPE_SHIPPINGCOST);
        if (!validation.equalsIgnoreCase(ArgumentValidationHelper.STATUS_OK)) {
            response.setStatus(validation);
            logShippingCostResponse(response);
            return response;
        }

        response.setAmount(shippingService.getShippingCost(request.getSEAddress().getCountry(),
                request.getSENumberOfProducts()));
        response.setCurrency(shippingService.getCurrency());
        response.setTransactionDate(shippingService.getFormatTimeNow());
        response.setStatus(ArgumentValidationHelper.shippingCostResponseValidation(response));
        logShippingCostResponse(response);

        return response;
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "TrackNumberRequest")
    @ResponsePayload
    public TrackNumberResponse getShippingTrackNumber(@RequestPayload TrackNumberRequest request) {
        TrackNumberResponse response = new TrackNumberResponse();
        response.setTrack(shippingService.getTrackNumber());

        return response;
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "PlaceShippingOrderRequest")
    @ResponsePayload
    public PlaceShippingOrderResponse placeShippingOrder(@RequestPayload PlaceShippingOrderRequest request) {
        PlaceShippingOrderResponse response = new PlaceShippingOrderResponse();
        String validation = ArgumentValidationHelper.placeShippingOrderRequestValidation(request);
        logPlaceOrderRequest(request);
        response.setSETransactionType(TRANSACTION_TYPE_PLACE_SHIPPING_ORDER);
        if (!validation.equalsIgnoreCase(ArgumentValidationHelper.STATUS_OK)) {
            response.setStatus(validation);
            logPlaceOrderResponse(response);
            return response;
        }
        response.setTransactionDate(shippingService.getFormatTimeNow());
        response.setTransactionReference(shippingService.getTransactionReferenceNumber());
        response.setStatus("OK");
        logPlaceOrderResponse(response);

        return response;
    }

    private void logShippingCostRequest(ShippingCostRequest request) {
        String builder = "TransactionType: " + request.getSETransactionType() + " Request" +
                " SENumberOfProducts: " + request.getSENumberOfProducts();
        logger.info(builder);
    }

    private void logShippingCostResponse(ShippingCostResponse response) {
        String builder = "TransactionType: " + response.getSETransactionType() + " Response" +
                "; SEResponse.Status: " + response.getStatus() +
                "; SEResponse.Amount: " + response.getAmount() +
                "; TransactionDate: " + response.getTransactionDate();
        logger.info(builder);
    }

    private void logPlaceOrderRequest(PlaceShippingOrderRequest request) {
        String builder = "TransactionType: " + request.getSETransactionType() + " Request" +
                "; OrderNumber: " + request.getOrderNumber() +
                "; SEOrderAmount: " + request.getAmount();

        logger.info(builder);
    }

    private void logPlaceOrderResponse(PlaceShippingOrderResponse response) {
        String builder = "TransactionType: " + response.getSETransactionType() + " Response" +
                "; SEResponse.Status: " + response.getStatus() +
                "; SETransactionReference: " + response.getTransactionReference() +
                "; TransactionDate: " + response.getTransactionDate();

        logger.info(builder);
    }
}
