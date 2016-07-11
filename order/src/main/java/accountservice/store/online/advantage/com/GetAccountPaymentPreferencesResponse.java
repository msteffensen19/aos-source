
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
 *         &lt;element name="PaymentPreferences" type="{com.advantage.online.store.accountservice}PaymentPreferencesDto"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "paymentPreferences"
})
@XmlRootElement(name = "GetAccountPaymentPreferencesResponse")
public class GetAccountPaymentPreferencesResponse {

    @XmlElement(name = "PaymentPreferences", required = true)
    protected PaymentPreferencesDto paymentPreferences;

    /**
     * Gets the value of the paymentPreferences property.
     *
     * @return possible object is
     * {@link PaymentPreferencesDto }
     */
    public PaymentPreferencesDto getPaymentPreferences() {
        return paymentPreferences;
    }

    /**
     * Sets the value of the paymentPreferences property.
     *
     * @param value allowed object is
     *              {@link PaymentPreferencesDto }
     */
    public void setPaymentPreferences(PaymentPreferencesDto value) {
        this.paymentPreferences = value;
    }

}
