package ShippingExpress.util;

import ShippingExpress.WsModel.PlaceShippingOrderRequest;
import ShippingExpress.WsModel.ShippingCostRequest;
import ShippingExpress.WsModel.ShippingCostResponse;

public class ArgumentValidationHelper {

    public static final String STATUS_OK = "OK";
    public static final String STATUS_ERROR_COUNTRY_CODE = "ERROR. Country code is empty or not valid";
    public static final String STATUS_ERROR_CITY_VALUE = "ERROR. City value is empty or not valid";
    public static final String STATUS_ERROR_STATE_VALUE = "ERROR. State value is empty or not valid";
    public static final String STATUS_ERROR_ADDRESS_LINE1 = "ERROR. Address Line1 is empty or not valid";
    public static final String STATUS_ERROR_ADDRESS_LINE_2 = "ERROR. Address Line 2 is too long";
    public static final String STATUS_ERROR_AMOUNT_VALUE = "ERROR. Amount value is not valid";
    public static final String STATUS_ERROR_ORDER_NUMBER = "ERROR. OrderNumber value is not valid";
    public static final String ERROR_TRANSACTION_TYPE = "ERROR. Transaction type is not valid";
    public static final String TRANSACTION_TYPE_PLACE_ORDER = "PlaceShippingOrder";

    public static String shippingCostRequestValidation(ShippingCostRequest request) {
        if(!countryValidation(request.getSEAddress().getCountry())) {
            return STATUS_ERROR_COUNTRY_CODE;
        }
        if (!cityValidation(request.getSEAddress().getCity())) {
            return STATUS_ERROR_CITY_VALUE;
        }
        if (!stateValidation(request.getSEAddress().getState())) {
            return STATUS_ERROR_STATE_VALUE;
        }
        if (!addressLineValidation(request.getSEAddress().getAddressLine1())) {
            return STATUS_ERROR_ADDRESS_LINE1;
        }
        if (!addressLine2Validation(request.getSEAddress().getAddressLine2())) {
            return STATUS_ERROR_ADDRESS_LINE_2;
        }

        return STATUS_OK;
    }

    public static String shippingCostResponseValidation(ShippingCostResponse response) {
        if(!doubleTryParse(response.getAmount())) return STATUS_ERROR_AMOUNT_VALUE;

        return STATUS_OK;
    }

    public static String placeShippingOrderRequestValidation(PlaceShippingOrderRequest request) {
        if(!orderNumberValidation(request.getOrderNumber())) {
            return STATUS_ERROR_ORDER_NUMBER;
        }
        if (!cityValidation(request.getSEAddress().getCity())) {
            return STATUS_ERROR_CITY_VALUE;
        }
        if (!stateValidation(request.getSEAddress().getState())) {
            return STATUS_ERROR_STATE_VALUE;
        }
        if (!addressLineValidation(request.getSEAddress().getAddressLine1())) {
            return STATUS_ERROR_ADDRESS_LINE1;
        }
        if (!addressLine2Validation(request.getSEAddress().getAddressLine2())) {
            return STATUS_ERROR_ADDRESS_LINE_2;
        }
        if(!doubleTryParse(request.getAmount())) {
            return STATUS_ERROR_AMOUNT_VALUE;
        }
        if(!request.getSETransactionType().equalsIgnoreCase(TRANSACTION_TYPE_PLACE_ORDER)) {
            return ERROR_TRANSACTION_TYPE;
        }

        return STATUS_OK;
    }

    private static boolean orderNumberValidation(String value) {
        return value != null && value.length() == 10;
    }

    public static boolean doubleTryParse(String value) {
        try {
            double d = Double.parseDouble(value);
        }
        catch (Exception e)  {
            return false;
        }

        return true;
    }

    private static boolean addressLine2Validation(String addressLine) {
        return addressLine != null && addressLine.length() <= 50;
    }

    private static boolean addressLineValidation(String addressLine) {
        return addressLine != null && addressLine.length() <= 50 && !addressLine.isEmpty();
    }

    private static boolean stateValidation(String state) {
        return state != null && state.length() <= 10 && !state.isEmpty();
    }

    private static boolean cityValidation(String city) {
        return city != null && city.length() <= 25 && !city.isEmpty();
    }

    private static boolean countryValidation(String country) {
        return country != null && country.length() == 2;
    }
}
