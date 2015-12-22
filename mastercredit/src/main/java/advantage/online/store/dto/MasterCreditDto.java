package advantage.online.store.dto;

public class MasterCreditDto {

    private class ReceivingCard {
        private long accountNumber;     //  fixed 12 digits

        public ReceivingCard(long accountNumber) {
            this.accountNumber = accountNumber;
        }

        public long getAccountNumber() { return this.accountNumber; }

        public void setAccountNumber(long accountNumber) { this.accountNumber = accountNumber; }
    }

    private class ReceivingAmount {
        private double value;           //  Cart total cost: XXXXXXXXXX.XX (12,2)
        private String currency;        //  3 characters. Default "USD"

        public ReceivingAmount(double value, String currency) {
            this.value = value;
            this.currency = currency;
        }

        public double getValue() { return this.value; }

        public void setValue(double value) { this.value = value; }

        public String getCurrency() { return this.currency; }

        public void setCurrency(String currency) { this.currency = currency; }
    }

    private String mcTransactionType;   //  PAYMENT - TransactioTypeEnum.PAYMENT
    private String mcCardNumber;        //  16 digits
    private String mcExpirationdate;    //   6 digits: MMYYYY
    private String mcCustomerName;      //  2-30 characters ([A-Za-z]{2,30})
    private String customerPhone;       //  0-20 digits and special characters
    private String mcCVVNumber;         //  3 digits
    private String transactionDate;     //  8 digits: DDMMYYYY

    private ReceivingCard receivingCard;
    private ReceivingAmount receivingAmount;


    public MasterCreditDto() { }

    public MasterCreditDto(String mcTransactionType, String mcCardNumber, String mcExpirationdate, String mcCustomerName, String customerPhone, String mcCVVNumber, String transactionDate, long accountNumber, double value, String currency) {
        this.mcTransactionType = mcTransactionType;
        this.mcCardNumber = mcCardNumber;
        this.mcExpirationdate = mcExpirationdate;
        this.mcCustomerName = mcCustomerName;
        this.customerPhone = customerPhone;
        this.mcCVVNumber = mcCVVNumber;
        this.transactionDate = transactionDate;

        this.setReceivingCard(accountNumber);
        this.setReceivingAmount(value, currency);
    }

    public String getMcTransactionType() {
        return mcTransactionType;
    }

    public void setMcTransactionType(String mcTransactionType) {
        this.mcTransactionType = mcTransactionType;
    }

    public String getMcCardNumber() {
        return mcCardNumber;
    }

    public void setMcCardNumber(String mcCardNumber) {
        this.mcCardNumber = mcCardNumber;
    }

    public String getMcExpirationdate() {
        return mcExpirationdate;
    }

    public void setMcExpirationdate(String mcExpirationdate) {
        this.mcExpirationdate = mcExpirationdate;
    }

    public String getMcCustomerName() {
        return mcCustomerName;
    }

    public void setMcCustomerName(String mcCustomerName) {
        this.mcCustomerName = mcCustomerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getMcCVVNumber() {
        return mcCVVNumber;
    }

    public void setMcCVVNumber(String mcCVVNumber) {
        this.mcCVVNumber = mcCVVNumber;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public ReceivingCard getReceivingCard() {
        return this.receivingCard;
    }

    public void setReceivingCard(long accountNumber) {
        this.receivingCard = new ReceivingCard(accountNumber);
    }

    public ReceivingAmount getReceivingAmount() {
        return this.receivingAmount;
    }

    public void setReceivingAmount(ReceivingAmount receivingAmount) {
        this.receivingAmount = receivingAmount;
    }

    public void setReceivingAmount(double value, String currency) {
        this.receivingAmount = new ReceivingAmount(value, currency);
    }

}