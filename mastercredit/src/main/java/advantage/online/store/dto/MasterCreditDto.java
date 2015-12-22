package advantage.online.store.dto;

public class MasterCreditDto {

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

    private String MCTransactionType;   //  PAYMENT - TransactioTypeEnum.PAYMENT
    private long MCCardNumber;          //  16 digits
    private String MCExpirationdate;       //   6 digits: MMYYYY
    private String MCCustomerName;      //  2-30 characters ([A-Za-z]{2,30})
    private String Customerphone;       //  0-20 digits and special characters
    private String MCCVVNumber;         //  3 digits
    private String TransactionDate;     //  8 digits: DDMMYYYY
    //private String MCReceivingCard_AccountNumber;       //  fixed 12 digits. String because can start with "0".
    private String AccountNumber;       //  fixed 12 digits. String because can start with "0".

    private ReceivingAmount MCReceivingAmount;

    public MasterCreditDto() { }

    public MasterCreditDto(String MCTransactionType, long MCCardNumber, String MCExpirationdate, String MCCustomerName, String Customerphone, String mcCVVNumber, String transactionDate, String accountNumber, double value, String currency) {
        this.MCTransactionType = MCTransactionType;
        this.MCCardNumber = MCCardNumber;
        this.MCExpirationdate = MCExpirationdate;
        this.MCCustomerName = MCCustomerName;
        this.Customerphone = Customerphone;
        this.MCCVVNumber = mcCVVNumber;
        this.TransactionDate = transactionDate;
        this.AccountNumber = accountNumber;

        this.setReceivingAmount(value, currency);
    }

    public String getMCTransactionType() {
        return MCTransactionType;
    }

    public void setMCTransactionType(String MCTransactionType) {
        this.MCTransactionType = MCTransactionType;
    }

    public long getMCCardNumber() {
        return MCCardNumber;
    }

    public void setMCCardNumber(long MCCardNumber) {
        this.MCCardNumber = MCCardNumber;
    }

    public String getMCExpirationdate() {
        return MCExpirationdate;
    }

    public void setMCExpirationdate(String MCExpirationdate) {
        this.MCExpirationdate = MCExpirationdate;
    }

    public String getMCCustomerName() {
        return MCCustomerName;
    }

    public void setMCCustomerName(String MCCustomerName) {
        this.MCCustomerName = MCCustomerName;
    }

    public String getCustomerphone() {
        return Customerphone;
    }

    public void setCustomerphone(String customerphone) {
        this.Customerphone = customerphone;
    }

    public String getMCCVVNumber() {
        return MCCVVNumber;
    }

    public void setMCCVVNumber(String MCCVVNumber) {
        this.MCCVVNumber = MCCVVNumber;
    }

    public String getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.TransactionDate = transactionDate;
    }

    public String getAccountNumber() {
        return this.AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.AccountNumber = accountNumber;
    }

    public ReceivingAmount getMCReceivingAmount() {
        return this.MCReceivingAmount;
    }

    public void setMCReceivingAmount(ReceivingAmount MCReceivingAmount) {
        this.MCReceivingAmount = MCReceivingAmount;
    }

    public void setReceivingAmount(double value, String currency) {
        this.MCReceivingAmount = new ReceivingAmount(value, currency);
    }

    @Override
    public String toString() {
        return "MasterCreditDto{" +
                "MCTransactionType='" + MCTransactionType + '\'' +
                ", MCCardNumber=" + MCCardNumber +
                ", MCExpirationdate='" + MCExpirationdate + '\'' +
                ", MCCustomerName='" + MCCustomerName + '\'' +
                ", Customerphone='" + Customerphone + '\'' +
                ", MCCVVNumber='" + MCCVVNumber + '\'' +
                ", TransactionDate='" + TransactionDate + '\'' +
                ", MCReceivingCard_AccountNumber='" + AccountNumber + '\'' +
                ", MCReceivingAmount=" + MCReceivingAmount +
                '}';
    }
}