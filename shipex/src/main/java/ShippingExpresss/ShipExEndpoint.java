package ShippingExpresss;

import ShippingExpresss.WsModel.TrackNumberRequest;
import ShippingExpresss.WsModel.TrackNumberResponse;
import ShippingExpresss.model.ShippingExpressService;
import ShippingExpresss.WsModel.ShippingCostRequest;
import ShippingExpresss.WsModel.ShippingCostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ShipExEndpoint {
    private static final String NAMESPACE_URI = "http://advantage.store/shipex/service";

    private ShippingExpressService shippingService;

    @Autowired
    public ShipExEndpoint(ShippingExpressService shippingService) {
        this.shippingService = shippingService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ShippingCostRequest")
    @ResponsePayload
    public ShippingCostResponse getShippingCost(@RequestPayload ShippingCostRequest request) {
        ShippingCostResponse response = new ShippingCostResponse();
        response.setCost(shippingService.getShippingCost(request.getCountryName(), request.getQuantity()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "TrackNumberRequest")
    @ResponsePayload
    public TrackNumberResponse getShippingTrackNumber(@RequestPayload TrackNumberRequest request) {
        TrackNumberResponse response = new TrackNumberResponse();
        response.setTrack(shippingService.getTrackNumber());

        return response;
    }
}
