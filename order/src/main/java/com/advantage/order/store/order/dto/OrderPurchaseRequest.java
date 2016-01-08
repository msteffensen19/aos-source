package com.advantage.order.store.order.dto;

import java.util.List;

/**
 * @author Binyamin Regev on 24/12/2015.
 */
public class OrderPurchaseRequest {

    private String paymentMethod;       //  MasterCredit / SafePay

    private ShippingInformation shippingInformation;

    private PaymentInformation paymentInformation;

    private List<PurchasedProductInformation> purchasedProducts;

    public OrderPurchaseRequest() { }

    public OrderPurchaseRequest(String paymentMethod, ShippingInformation shippingInformation, PaymentInformation paymentInformation) {
        this.paymentMethod = paymentMethod;
        this.shippingInformation = shippingInformation;
        this.paymentInformation = paymentInformation;
    }

    public OrderPurchaseRequest(String paymentMethod, ShippingInformation shippingInformation, PaymentInformation paymentInformation, List<PurchasedProductInformation> purchasedProducts) {
        this.paymentMethod = paymentMethod;
        this.shippingInformation = shippingInformation;
        this.paymentInformation = paymentInformation;
        this.purchasedProducts = purchasedProducts;
    }

    /**
     * Add {@link PurchasedProductInformation} with {@code productId}, {@code product name} and {@code quantity}.
     * @param productId
     * @param productName
     * @param pricePerItem
     * @param quantity
     * @return
     */
    public boolean addProductToPurchase(Long productId, String productName, String hexColor, double pricePerItem, int quantity) {
        boolean result = purchasedProducts.add(new PurchasedProductInformation(productId, productName, hexColor, pricePerItem, quantity));
        return result;
    }

    /**
     * Add {@link PurchasedProductInformation} with {@code productId} and {@code quantity}.
     * @param productId
     * @param pricePerItem
     * @param quantity
     * @return
     */
    public boolean addProductToPurchase(Long productId, double pricePerItem, String hexColor, int quantity) {
        boolean result = purchasedProducts.add(new PurchasedProductInformation(productId, "", hexColor, pricePerItem, quantity));
        return result;
    }

}
