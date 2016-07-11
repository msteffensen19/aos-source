
package accountservice.store.online.advantage.com;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddressStatusResponse complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="AddressStatusResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="success" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressStatusResponse", propOrder = {
        "reason",
        "success"
})
public class AddressStatusResponse {

    @XmlElement(required = true)
    protected String reason;
    protected boolean success;

    /**
     * Gets the value of the reason property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Gets the value of the success property.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the value of the success property.
     */
    public void setSuccess(boolean value) {
        this.success = value;
    }

}
