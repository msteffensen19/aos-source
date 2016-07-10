
package accountservice.store.online.advantage.com;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cardNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="expirationDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cvvNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="customerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="accountId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="base64Token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "cardNumber",
        "expirationDate",
        "cvvNumber",
        "customerName",
        "accountId",
        "base64Token"
})
@XmlRootElement(name = "AddMasterCreditMethodRequest")
public class AddMasterCreditMethodRequest {

    @XmlElement(required = true)
    protected String cardNumber;
    @XmlElement(required = true)
    protected String expirationDate;
    @XmlElement(required = true)
    protected String cvvNumber;
    @XmlElement(required = true)
    protected String customerName;
    protected long accountId;
    @XmlElement(required = true)
    protected String base64Token;

    /**
     * Gets the value of the cardNumber property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Sets the value of the cardNumber property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCardNumber(String value) {
        this.cardNumber = value;
    }

    /**
     * Gets the value of the expirationDate property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setExpirationDate(String value) {
        this.expirationDate = value;
    }

    /**
     * Gets the value of the cvvNumber property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCvvNumber() {
        return cvvNumber;
    }

    /**
     * Sets the value of the cvvNumber property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCvvNumber(String value) {
        this.cvvNumber = value;
    }

    /**
     * Gets the value of the customerName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the value of the customerName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCustomerName(String value) {
        this.customerName = value;
    }

    /**
     * Gets the value of the accountId property.
     */
    public long getAccountId() {
        return accountId;
    }

    /**
     * Sets the value of the accountId property.
     */
    public void setAccountId(long value) {
        this.accountId = value;
    }

    /**
     * Gets the value of the base64Token property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getBase64Token() {
        return base64Token;
    }

    /**
     * Sets the value of the base64Token property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBase64Token(String value) {
        this.base64Token = value;
    }

}
