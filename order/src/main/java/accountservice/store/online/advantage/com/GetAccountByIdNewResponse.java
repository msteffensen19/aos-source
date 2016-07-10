
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
 *         &lt;element name="AccountResponse" type="{com.advantage.online.store.accountservice}AccountDtoNew"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "accountResponse"
})
@XmlRootElement(name = "GetAccountByIdNewResponse")
public class GetAccountByIdNewResponse {

    @XmlElement(name = "AccountResponse", required = true)
    protected AccountDtoNew accountResponse;

    /**
     * Gets the value of the accountResponse property.
     *
     * @return possible object is
     * {@link AccountDtoNew }
     */
    public AccountDtoNew getAccountResponse() {
        return accountResponse;
    }

    /**
     * Sets the value of the accountResponse property.
     *
     * @param value allowed object is
     *              {@link AccountDtoNew }
     */
    public void setAccountResponse(AccountDtoNew value) {
        this.accountResponse = value;
    }

}
