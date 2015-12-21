package advantage.online.store.dto;

/**
 * @author Binyamin Regev on 20/12/2015.
 */
public class MasterCreditResponseStatus {

    private String MCTransactionType;
    private String MCResponse;
    private long MCRefNumber;       //  10 digits
    private String transactionDate; //  DDMMYYYY

    public MasterCreditResponseStatus() { }

    public MasterCreditResponseStatus(String MCTransactionType, String MCResponse, long MCRefNumber, String transactionDate) {
        this.MCTransactionType = MCTransactionType;
        this.MCResponse = MCResponse;
        this.MCRefNumber = MCRefNumber;
        this.transactionDate = transactionDate;
    }

    public String getMCTransactionType() { return this.MCTransactionType; }

    public void setMCTransactionType(String MCTransactionType) { this.MCTransactionType = MCTransactionType; }

    public String getMCResponse() { return this.MCResponse; }

    public void setMCResponse(String MCResponse) { this.MCResponse = MCResponse; }

    public long getMCRefNumber() { return this.MCRefNumber; }

    public void setMCRefNumber(long MCRefNumber) { this.MCRefNumber = MCRefNumber; }

    public String getTransactionDate() { return this.transactionDate; }

    public void setTransactionDate(String transactionDate) { this.transactionDate = transactionDate; }

}
