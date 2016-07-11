
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
 *         &lt;element name="StatusMessage" type="{com.advantage.online.store.accountservice}AccountStatusResponse"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "statusMessage"
})
@XmlRootElement(name = "AccountLoginResponse")
public class AccountLoginResponse {

    @XmlElement(name = "StatusMessage", required = true)
    protected AccountStatusResponse statusMessage;

    /**
     * Gets the value of the statusMessage property.
     *
     * @return possible object is
     * {@link AccountStatusResponse }
     */
    public AccountStatusResponse getStatusMessage() {
        return statusMessage;
    }

    /**
     * Sets the value of the statusMessage property.
     *
     * @param value allowed object is
     *              {@link AccountStatusResponse }
     */
    public void setStatusMessage(AccountStatusResponse value) {
        this.statusMessage = value;
    }

}
