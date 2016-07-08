
package accountservice.store.online.advantage.com;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="ShippingAddress" type="{com.advantage.online.store.accountservice}AccountDto" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "shippingAddress"
})
@XmlRootElement(name = "GetAddressesByAccountIdResponse")
public class GetAddressesByAccountIdResponse {

    @XmlElement(name = "ShippingAddress")
    protected List<AccountDto> shippingAddress;

    /**
     * Gets the value of the shippingAddress property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shippingAddress property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getShippingAddress().add(newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AccountDto }
     */
    public List<AccountDto> getShippingAddress() {
        if (shippingAddress == null) {
            shippingAddress = new ArrayList<AccountDto>();
        }
        return this.shippingAddress;
    }

}
