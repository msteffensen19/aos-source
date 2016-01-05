
package AccountServiceClient;

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
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isoName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="phonePrefix" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "name",
    "isoName",
    "phonePrefix"
})
@XmlRootElement(name = "CountryCreateRequest")
public class CountryCreateRequest {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String isoName;
    @XmlElement(required = true)
    protected String phonePrefix;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the isoName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsoName() {
        return isoName;
    }

    /**
     * Sets the value of the isoName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsoName(String value) {
        this.isoName = value;
    }

    /**
     * Gets the value of the phonePrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhonePrefix() {
        return phonePrefix;
    }

    /**
     * Sets the value of the phonePrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhonePrefix(String value) {
        this.phonePrefix = value;
    }

}
