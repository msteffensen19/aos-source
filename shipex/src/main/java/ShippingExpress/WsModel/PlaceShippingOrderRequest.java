
package ShippingExpress.WsModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SETransactionType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SEAddress" type="{https://www.AdvantageOnlineBanking.com/ShipEx/}SEAddress"/>
 *         &lt;element name="SEProducts" type="{https://www.AdvantageOnlineBanking.com/ShipEx/}SEProducts" form="qualified"/>
 *         &lt;element name="OrderNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Amount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "seTransactionType",
    "seAddress",
    "seProducts",
    "orderNumber",
    "amount",
    "currency"
})
@XmlRootElement(name = "PlaceShippingOrderRequest", namespace = "https://www.AdvantageOnlineBanking.com/ShipEx/")
public class PlaceShippingOrderRequest {

    @XmlElement(name = "SETransactionType", namespace = "https://www.AdvantageOnlineBanking.com/ShipEx/", required = true)
    protected String seTransactionType;
    @XmlElement(name = "SEAddress", namespace = "https://www.AdvantageOnlineBanking.com/ShipEx/", required = true)
    protected SEAddress seAddress;
    @XmlElement(name = "SEProducts", namespace = "https://www.AdvantageOnlineBanking.com/ShipEx/", required = true)
    protected SEProducts seProducts;
    @XmlElement(name = "OrderNumber", namespace = "https://www.AdvantageOnlineBanking.com/ShipEx/", required = true)
    protected String orderNumber;
    @XmlElement(name = "Amount", namespace = "https://www.AdvantageOnlineBanking.com/ShipEx/", required = true)
    protected String amount;
    @XmlElement(name = "Currency", namespace = "https://www.AdvantageOnlineBanking.com/ShipEx/", required = true)
    protected String currency;

    /**
     * Gets the value of the seTransactionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSETransactionType() {
        return seTransactionType;
    }

    /**
     * Sets the value of the seTransactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSETransactionType(String value) {
        this.seTransactionType = value;
    }

    /**
     * Gets the value of the seAddress property.
     * 
     * @return
     *     possible object is
     *     {@link SEAddress }
     *     
     */
    public SEAddress getSEAddress() {
        return seAddress;
    }

    /**
     * Sets the value of the seAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link SEAddress }
     *     
     */
    public void setSEAddress(SEAddress value) {
        this.seAddress = value;
    }

    /**
     * Gets the value of the seProducts property.
     * 
     * @return
     *     possible object is
     *     {@link SEProducts }
     *     
     */
    public SEProducts getSEProducts() {
        return seProducts;
    }

    /**
     * Sets the value of the seProducts property.
     * 
     * @param value
     *     allowed object is
     *     {@link SEProducts }
     *     
     */
    public void setSEProducts(SEProducts value) {
        this.seProducts = value;
    }

    /**
     * Gets the value of the orderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * Sets the value of the orderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderNumber(String value) {
        this.orderNumber = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmount(String value) {
        this.amount = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

}
