package ShippingExpress;

import ShippingExpress.WsModel.*;
import ShippingExpress.config.WebServiceConfig;
import ShippingExpress.model.ShippingExpressService;
import ShippingExpress.util.ArgumentValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ShipExEndpoint {
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
        if(!validation.equalsIgnoreCase(ArgumentValidationHelper.STATUS_OK)) {
            response.setStatus(validation);
            response.setSETransactionType(TRANSACTION_TYPE_SHIPPINGCOST);
            return response;
        }

        response.setAmount(shippingService.getShippingCost(request.getSEAddress().getCountry(), request.getSENumberOfProducts()));
        response.setCurrency(shippingService.getCurrency());
        response.setTransactionDate(shippingService.getFormatTimeNow());
        response.setStatus(ArgumentValidationHelper.shippingCostResponseValidation(response));

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
        if(!validation.equalsIgnoreCase(ArgumentValidationHelper.STATUS_OK)) {
            response.setStatus(validation);
            response.setSETransactionType(TRANSACTION_TYPE_PLACE_SHIPPING_ORDER);
            return response;
        }
        response.setTransactionDate(shippingService.getFormatTimeNow());
        response.setTransactionReference(shippingService.getTransactionReferenceNumber());
        response.setStatus("OK");

        return response;
    }
}
