package advantage.online.store.model;

/**
 * Not in use at this point.
 * @author Binyamin Regev on 21/12/2015.
 */
public class MasterCredit {

    private long id;    //  Auto Increment
    private String cardNumber;
    private String validThru;   //  mm/yyyy
    private String cardHolderName;
    private int bankNumber;
    private int branchNumber;
    private long accountNumber;
    private int cvv1;
    private int cvv2;   // 3-4 digits. On the cart
    private int cvv3;
    private int smartCartCode;  //  To type on smart-deals

    public MasterCredit(String cardNumber, String validThru, String cardHolderName) {
        this.cardNumber = cardNumber;
        this.validThru = validThru;
        this.cardHolderName = cardHolderName;
    }

    public MasterCredit(String cardNumber, String validThru, String cardHolderName, int bankNumber, int branchNumber, long accountNumber, int cvv2) {
        this.cardNumber = cardNumber;
        this.validThru = validThru;
        this.cardHolderName = cardHolderName;
        this.bankNumber = bankNumber;
        this.branchNumber = branchNumber;
        this.accountNumber = accountNumber;
        this.cvv2 = cvv2;
    }

    public long getId() { return id; }

    public String getCardNumber() { return cardNumber; }

    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getValidThru() { return validThru; }

    public void setValidThru(String validThru) { this.validThru = validThru; }

    public String getCardHolderName() { return cardHolderName; }

    public void setCardHolderName(String cardHolderName) { this.cardHolderName = cardHolderName; }

    public int getBankNumber() { return bankNumber; }

    public void setBankNumber(int bankNumber) { this.bankNumber = bankNumber; }

    public int getBranchNumber() { return branchNumber; }

    public void setBranchNumber(int branchNumber) { this.branchNumber = branchNumber; }

    public long getAccountNumber() { return accountNumber; }

    public void setAccountNumber(long accountNumber) { this.accountNumber = accountNumber; }

    public int getCvv1() { return cvv1; }

    public void setCvv1(int cvv1) { this.cvv1 = cvv1; }

    public int getCvv2() { return cvv2; }

    public void setCvv2(int cvv2) { this.cvv2 = cvv2; }

    public int getCvv3() { return cvv3; }

    public void setCvv3(int cvv3) { this.cvv3 = cvv3; }

    public int getSmartCartCode() { return smartCartCode; }

    public void setSmartCartCode(int smartCartCode) { this.smartCartCode = smartCartCode; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MasterCredit that = (MasterCredit) o;

        if (getId() != that.getId()) return false;
        if (getBankNumber() != that.getBankNumber()) return false;
        if (getBranchNumber() != that.getBranchNumber()) return false;
        if (getAccountNumber() != that.getAccountNumber()) return false;
        if (getCvv1() != that.getCvv1()) return false;
        if (getCvv2() != that.getCvv2()) return false;
        if (getCvv3() != that.getCvv3()) return false;
        if (getSmartCartCode() != that.getSmartCartCode()) return false;
        if (!getCardNumber().equals(that.getCardNumber())) return false;
        if (!getValidThru().equals(that.getValidThru())) return false;
        return getCardHolderName().equals(that.getCardHolderName());

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getCardNumber().hashCode();
        result = 31 * result + getValidThru().hashCode();
        result = 31 * result + getCardHolderName().hashCode();
        result = 31 * result + getBankNumber();
        result = 31 * result + getBranchNumber();
        result = 31 * result + (int) (getAccountNumber() ^ (getAccountNumber() >>> 32));
        result = 31 * result + getCvv1();
        result = 31 * result + getCvv2();
        result = 31 * result + getCvv3();
        result = 31 * result + getSmartCartCode();
        return result;
    }

}