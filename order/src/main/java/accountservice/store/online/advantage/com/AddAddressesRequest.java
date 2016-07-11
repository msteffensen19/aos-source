
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
 *         &lt;element name="address" type="{com.advantage.online.store.accountservice}AddAddressDto" maxOccurs="unbounded" form="qualified"/>
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
        "address",
        "accountId",
        "base64Token"
})
@XmlRootElement(name = "AddAddressesRequest")
public class AddAddressesRequest {

    @XmlElement(required = true)
    protected List<AddAddressDto> address;
    protected long accountId;
    @XmlElement(required = true)
    protected String base64Token;

    /**
     * Gets the value of the address property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the address property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddress().add(newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AddAddressDto }
     */
    public List<AddAddressDto> getAddress() {
        if (address == null) {
            address = new ArrayList<AddAddressDto>();
        }
        return this.address;
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
