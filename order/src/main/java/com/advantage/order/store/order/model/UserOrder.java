package com.advantage.order.store.order.model;

import java.util.List;

/**
 * @author Binyamin Regev on 06/01/2016.
 */
public class UserOrder {

    public class UserOrderLines {
        private Long productId;             //  From Product table in CATALOG schema
        private String productName;         //  From Product table in CATALOG schema
        private int productColor;           //  RGB decimal value
        private double pricePerItem;        //  From Product table in CATALOG schema
        private int quantity;               //  From ShoppingCart table in ORDER schema

        public UserOrderLines() { }

        public UserOrderLines(Long productId, String productName, int productColor, double pricePerItem, int quantity) {
            this.productId = productId;
            this.productName = productName;
            this.productColor = productColor;
            this.pricePerItem = pricePerItem;
            this.quantity = quantity;
        }

        public Long getProductId() { return productId; }

        public String getProductName() { return productName; }

        public int getProductColor() { return productColor; }

        public double getPricePerItem() { return pricePerItem; }

        public int getQuantity() { return quantity;  }
    }

    private long userId;
    private long orderNumber;
    private long orderTimestamp;

    /*  ShippingInformation - Begin */
    private int numberOfProducts;       //  Numeric. 1-5 digits
    private double shippingCost;        //  "##.##"

    /*  ShippingAddress - Begin */
    private String shippingAddress;     //  0-100 characters (Street name, house number, floor number, apartment number)
    private String shippingCity;        //  0-25 characters
    private String shippingPostalCode;	//  0-10 characters
    private String shippingState;       //  0-10 characters
    private String shippingCountry;     //  2 characters
    /*  ShippingAddress - End   */
    /*  ShippingInformation - End */

    private String paymentMethod;       //  from PaymentMethodEnum: "MasterCredit" / "SafePay"

    /*  paymentDetails  */
    private String transactionType;     //  from TransactionTypeEnum: "Payment" / "Refund"
    private long paymentConfirmationNumber;
    private String cardNumber;          //  MasterCredit ONLY. MCCardNumber (can be null in SafePay)
    private String expirationDate; 		//  MasterCredit ONLY. MCExpirationDate (can be null in SafePay)
    private String customerName;		//  MasterCredit ONLY. MCCustomerName (can be null in SafePay)
    private String username;            //  SafePay ONLY. SafePay.username (can be null in MasterCredit)
    private String customerPhone;       //  International phone number
    private String cvvNumber;           //  MasterCredit ONLY. MCCVVNumber  (can be null in SafePay).
    private String transactionDate;     //  TransactionDate
    private String accountNumber;       //  ReceivingCard.AccountNumber
    private double amount;              //  ReceivingAmount.Value
    private String currency;            //  ReceivingAmount.Currency

    List<UserOrderLines> orderLines;

    public UserOrder() { }

    public UserOrder(long userId, long orderNumber, long orderTimestamp) {
        this.userId = userId;
        this.orderNumber = orderNumber;
        this.orderTimestamp = orderTimestamp;
    }

    public UserOrder(long userId, long orderNumber, long orderTimestamp, int numberOfProducts, double shippingCost, String shippingAddress, String shippingCity, String shippingPostalCode, String shippingState, String shippingCountry, String paymentMethod, String transactionType, String cardNumber, String expirationDate, String customerName, String username, String customerPhone, String cvvNumber, String transactionDate, String accountNumber, double amount, String currency) {
        this.userId = userId;
        this.orderNumber = orderNumber;
        this.orderTimestamp = orderTimestamp;
        this.numberOfProducts = numberOfProducts;
        this.shippingCost = shippingCost;
        this.shippingAddress = shippingAddress;
        this.shippingCity = shippingCity;
        this.shippingPostalCode = shippingPostalCode;
        this.shippingState = shippingState;
        this.shippingCountry = shippingCountry;
        this.paymentMethod = paymentMethod;
        this.transactionType = transactionType;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.customerName = customerName;
        this.username = username;
        this.customerPhone = customerPhone;
        this.cvvNumber = cvvNumber;
        this.transactionDate = transactionDate;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.currency = currency;
    }

    public UserOrder(long userId, long orderNumber, long orderTimestamp, int numberOfProducts, double shippingCost, String shippingAddress, String shippingCity, String shippingPostalCode, String shippingState, String shippingCountry, String paymentMethod, String transactionType, String cardNumber, String expirationDate, String customerName, String username, String customerPhone, String cvvNumber, String transactionDate, String accountNumber, double amount, String currency, List<UserOrderLines> orderLines) {
        this.userId = userId;
        this.orderNumber = orderNumber;
        this.orderTimestamp = orderTimestamp;
        this.numberOfProducts = numberOfProducts;
        this.shippingCost = shippingCost;
        this.shippingAddress = shippingAddress;
        this.shippingCity = shippingCity;
        this.shippingPostalCode = shippingPostalCode;
        this.shippingState = shippingState;
        this.shippingCountry = shippingCountry;
        this.paymentMethod = paymentMethod;
        this.transactionType = transactionType;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.customerName = customerName;
        this.username = username;
        this.customerPhone = customerPhone;
        this.cvvNumber = cvvNumber;
        this.transactionDate = transactionDate;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.currency = currency;
        this.orderLines = orderLines;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public long getOrderTimestamp() {
        return orderTimestamp;
    }

    public void setOrderTimestamp(long orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public String getShippingPostalCode() {
        return shippingPostalCode;
    }

    public void setShippingPostalCode(String shippingPostalCode) {
        this.shippingPostalCode = shippingPostalCode;
    }

    public String getShippingState() {
        return shippingState;
    }

    public void setShippingState(String shippingState) {
        this.shippingState = shippingState;
    }

    public String getShippingCountry() {
        return shippingCountry;
    }

    public void setShippingCountry(String shippingCountry) {
        this.shippingCountry = shippingCountry;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public long getPaymentConfirmationNumber() {
        return paymentConfirmationNumber;
    }

    public void setPaymentConfirmationNumber(long paymentConfirmationNumber) {
        this.paymentConfirmationNumber = paymentConfirmationNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCvvNumber() {
        return cvvNumber;
    }

    public void setCvvNumber(String cvvNumber) {
        this.cvvNumber = cvvNumber;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<UserOrderLines> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<UserOrderLines> orderLines) {
        this.orderLines = orderLines;
    }

    /**
     * Create User order line from {@code UserOrderLines} class.
     */
    public void createOrderLine(UserOrderLines orderLine) { orderLines.add(orderLine); }

    /**
     * Create User order line from {@code UserOrderLines} properties.
     */
    public void createOrderLine(Long productId, String productName, int productColor, double pricePerItem, int quantity) {
        createOrderLine(new UserOrderLines(productId, productName, productColor, pricePerItem, quantity));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserOrder userOrder = (UserOrder) o;

        if (getUserId() != userOrder.getUserId()) return false;
        if (getOrderNumber() != userOrder.getOrderNumber()) return false;
        if (getOrderTimestamp() != userOrder.getOrderTimestamp()) return false;
        if (getNumberOfProducts() != userOrder.getNumberOfProducts()) return false;
        if (Double.compare(userOrder.getShippingCost(), getShippingCost()) != 0) return false;
        if (Double.compare(userOrder.getAmount(), getAmount()) != 0) return false;
        if (!getShippingAddress().equals(userOrder.getShippingAddress())) return false;
        if (!getShippingCity().equals(userOrder.getShippingCity())) return false;
        if (!getShippingPostalCode().equals(userOrder.getShippingPostalCode())) return false;
        if (!getShippingState().equals(userOrder.getShippingState())) return false;
        if (!getShippingCountry().equals(userOrder.getShippingCountry())) return false;
        if (!getPaymentMethod().equals(userOrder.getPaymentMethod())) return false;
        if (!getTransactionType().equals(userOrder.getTransactionType())) return false;
        if (getCardNumber() != null ? !getCardNumber().equals(userOrder.getCardNumber()) : userOrder.getCardNumber() != null)
            return false;
        if (getExpirationDate() != null ? !getExpirationDate().equals(userOrder.getExpirationDate()) : userOrder.getExpirationDate() != null)
            return false;
        if (getCustomerName() != null ? !getCustomerName().equals(userOrder.getCustomerName()) : userOrder.getCustomerName() != null)
            return false;
        if (getUsername() != null ? !getUsername().equals(userOrder.getUsername()) : userOrder.getUsername() != null)
            return false;
        if (!getCustomerPhone().equals(userOrder.getCustomerPhone())) return false;
        if (getCvvNumber() != null ? !getCvvNumber().equals(userOrder.getCvvNumber()) : userOrder.getCvvNumber() != null)
            return false;
        if (!getTransactionDate().equals(userOrder.getTransactionDate())) return false;
        if (!getAccountNumber().equals(userOrder.getAccountNumber())) return false;
        if (!getCurrency().equals(userOrder.getCurrency())) return false;
        return getOrderLines().equals(userOrder.getOrderLines());

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (getUserId() ^ (getUserId() >>> 32));
        result = 31 * result + (int) (getOrderNumber() ^ (getOrderNumber() >>> 32));
        result = 31 * result + (int) (getOrderTimestamp() ^ (getOrderTimestamp() >>> 32));
        result = 31 * result + getNumberOfProducts();
        temp = Double.doubleToLongBits(getShippingCost());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getShippingAddress().hashCode();
        result = 31 * result + getShippingCity().hashCode();
        result = 31 * result + getShippingPostalCode().hashCode();
        result = 31 * result + getShippingState().hashCode();
        result = 31 * result + getShippingCountry().hashCode();
        result = 31 * result + getPaymentMethod().hashCode();
        result = 31 * result + getTransactionType().hashCode();
        result = 31 * result + (getCardNumber() != null ? getCardNumber().hashCode() : 0);
        result = 31 * result + (getExpirationDate() != null ? getExpirationDate().hashCode() : 0);
        result = 31 * result + (getCustomerName() != null ? getCustomerName().hashCode() : 0);
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + getCustomerPhone().hashCode();
        result = 31 * result + (getCvvNumber() != null ? getCvvNumber().hashCode() : 0);
        result = 31 * result + getTransactionDate().hashCode();
        result = 31 * result + getAccountNumber().hashCode();
        temp = Double.doubleToLongBits(getAmount());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getCurrency().hashCode();
        result = 31 * result + getOrderLines().hashCode();
        return result;
    }
}
