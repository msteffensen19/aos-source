package ShippingExpress;

import ShippingExpress.WsModel.*;
import ShippingExpress.model.ShippingExpressService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ShipexApplication.class)
public class ShipexApplicationTests {

    @Autowired
    private ShippingExpressService service;

    private ShippingCostRequest costRequest;
    private PlaceShippingOrderRequest orderRequest;
    private SEAddress address;
    private ShipExEndpoint endpoint;

    @Before
    public void init() {
        costRequest = new ShippingCostRequest();
        orderRequest = new PlaceShippingOrderRequest();
        address = new SEAddress();
        endpoint = new ShipExEndpoint(service);

        address.setAddressLine1("address");
        address.setCity("city");
        address.setCountry("ua");
        address.setPostalCode("123123");
        address.setState("state");
        address.setPhone("+1231234567");

        costRequest.setSETransactionType(ShipExEndpoint.TRANSACTION_TYPE_SHIPPING_COST);
        costRequest.setSEAddress(address);
        costRequest.setSENumberOfProducts(5);

        orderRequest.setSEAddress(address);
        orderRequest.setSETransactionType(ShipExEndpoint.TRANSACTION_TYPE_PLACE_SHIPPING_ORDER);
        orderRequest.setOrderNumber("1234567890");
    }

    @Test
    public void getShippingCostTest() throws IOException {
        ShippingCostResponse response = endpoint.getShippingCost(costRequest);

        Assert.assertEquals("OK", response.getStatus());
        Assert.assertEquals(true, !response.getAmount().isEmpty());
        Assert.assertEquals(true, !response.getCurrency().isEmpty());
        Assert.assertEquals(costRequest.getSETransactionType(), response.getSETransactionType());
    }

    @Test
    public void shippingCostRequestValidatorTest() {
        //validate transaction type
        ShippingCostRequest request = getDefaultCostRequest();
        request.setSETransactionType("invalid type");
        ShippingCostResponse response = endpoint.getShippingCost(request);
        Assert.assertEquals(true, response.getStatus().contains("ERROR"));

        //validate addressLine1
        request = getDefaultCostRequest();
        request.getSEAddress().setAddressLine1(createStringWithLength(51));
        response = endpoint.getShippingCost(request);
        Assert.assertEquals(true, response.getStatus().contains("ERROR"));

        //validate city
        request = getDefaultCostRequest();
        request.getSEAddress().setCity(createStringWithLength(26));
        response = endpoint.getShippingCost(request);
        Assert.assertEquals(true, response.getStatus().contains("ERROR"));

        //validate state
        request = getDefaultCostRequest();
        request.getSEAddress().setState(createStringWithLength(11));
        response = endpoint.getShippingCost(request);
        Assert.assertEquals(true, response.getStatus().contains("ERROR"));

        //validate number of products
        request = getDefaultCostRequest();
        request.setSENumberOfProducts(123456);
        Assert.assertEquals(true, response.getStatus().contains("ERROR"));

        //validate phone number
        //phone !contains "+"
        request = getDefaultCostRequest();
        request.getSEAddress().setPhone("1234567");
        response = endpoint.getShippingCost(request);
        Assert.assertEquals(true, response.getStatus().contains("ERROR"));

        //pnone=5
        request.getSEAddress().setPhone("+1234");
        response = endpoint.getShippingCost(request);
        Assert.assertEquals(true, response.getStatus().contains("OK"));

        //phone>20
        request.getSEAddress().setPhone("+23567890123456789201");
        response = endpoint.getShippingCost(request);
        Assert.assertEquals(true, response.getStatus().contains("ERROR"));

        //phone<=20
        request.getSEAddress().setPhone("+2356789012345678920");
        response = endpoint.getShippingCost(request);
        Assert.assertEquals(true, response.getStatus().contains("OK"));

        //validate country alias
        request = getDefaultCostRequest();
        request.getSEAddress().setCountry(createStringWithLength(3));
        response = endpoint.getShippingCost(request);
        Assert.assertEquals(true, response.getStatus().contains("ERROR"));
        request.getSEAddress().setCountry(createStringWithLength(0));
        response = endpoint.getShippingCost(request);
        Assert.assertEquals(true, response.getStatus().contains("ERROR"));
    }

    @Test
    public void PlaceShippingOrderTest() {
        PlaceShippingOrderResponse response = endpoint.placeShippingOrder(orderRequest);

        Assert.assertEquals("OK", response.getStatus());
        Assert.assertEquals(true, !response.getTransactionDate().isEmpty());
        Assert.assertEquals(true, !response.getTransactionReference().isEmpty());
        Assert.assertEquals(10, response.getTransactionReference().length());
        Assert.assertEquals(orderRequest.getSETransactionType(), response.getSETransactionType());
    }

    private ShippingCostRequest getDefaultCostRequest() {
        ShippingCostRequest request = new ShippingCostRequest();
        SEAddress seAddress = new SEAddress();
        seAddress.setAddressLine1(costRequest.getSEAddress().getAddressLine1());
        seAddress.setCountry(costRequest.getSEAddress().getCountry());
        seAddress.setState(costRequest.getSEAddress().getState());
        seAddress.setPostalCode(costRequest.getSEAddress().getPostalCode());
        seAddress.setCity(costRequest.getSEAddress().getCity());
        seAddress.setPhone(costRequest.getSEAddress().getPhone());

        request.setSEAddress(seAddress);
        request.setSETransactionType(costRequest.getSETransactionType());
        request.setSENumberOfProducts(5);

        return request;
    }

    private PlaceShippingOrderRequest getDefaultOrderRequest() {
        PlaceShippingOrderRequest request = new PlaceShippingOrderRequest();
        request.setOrderNumber(orderRequest.getOrderNumber());
        SEAddress seAddress = new SEAddress();
        seAddress.setAddressLine1(orderRequest.getSEAddress().getAddressLine1());
        seAddress.setCountry(orderRequest.getSEAddress().getCountry());
        seAddress.setState(orderRequest.getSEAddress().getState());
        seAddress.setPostalCode(orderRequest.getSEAddress().getPostalCode());
        seAddress.setCity(orderRequest.getSEAddress().getCity());
        seAddress.setPhone(orderRequest.getSEAddress().getPhone());

        request.setSEAddress(seAddress);
        request.setSETransactionType(orderRequest.getSETransactionType());

        return request;
    }

    @Test
    public void orderRequestValidatorTest() {
        //validate transaction type
        PlaceShippingOrderRequest request = getDefaultOrderRequest();

        request.setSETransactionType("invalid type");
        PlaceShippingOrderResponse response = endpoint.placeShippingOrder(request);
        Assert.assertEquals(true, response.getStatus().contains("ERROR"));

        //validate addressLine1
        request = getDefaultOrderRequest();
        request.getSEAddress().setAddressLine1(createStringWithLength(51));
        response = endpoint.placeShippingOrder(request);
        Assert.assertEquals(true, response.getStatus().contains("ERROR"));

        //validate city
        request = getDefaultOrderRequest();
        request.getSEAddress().setCity(createStringWithLength(26));
        response = endpoint.placeShippingOrder(request);
        Assert.assertEquals(true, response.getStatus().contains("ERROR"));

        //validate state
        request = getDefaultOrderRequest();
        request.getSEAddress().setState(createStringWithLength(11));
        response = endpoint.placeShippingOrder(request);
        Assert.assertEquals(true, response.getStatus().contains("ERROR"));

        //validate country alias
        request = getDefaultOrderRequest();
        request.getSEAddress().setCountry(createStringWithLength(11));
        response = endpoint.placeShippingOrder(request);
        Assert.assertEquals(true, response.getStatus().contains("ERROR"));
        request.getSEAddress().setCountry(createStringWithLength(0));
        response = endpoint.placeShippingOrder(request);
        Assert.assertEquals(true, response.getStatus().contains("ERROR"));
    }

    private String createStringWithLength(int i) {
        StringBuilder builder = new StringBuilder(i);
        for (int j = 0; j < i; j++) {
            builder.append("s");
        }

        return builder.toString();
    }


}
