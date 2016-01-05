
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
 *         &lt;element name="internationalPhonePrefix" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="startOfName" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "internationalPhonePrefix",
    "startOfName"
})
@XmlRootElement(name = "CountrySearchRequest")
public class CountrySearchRequest {

    protected int internationalPhonePrefix;
    @XmlElement(required = true)
    protected String startOfName;

    /**
     * Gets the value of the internationalPhonePrefix property.
     * 
     */
    public int getInternationalPhonePrefix() {
        return internationalPhonePrefix;
    }

    /**
     * Sets the value of the internationalPhonePrefix property.
     * 
     */
    public void setInternationalPhonePrefix(int value) {
        this.internationalPhonePrefix = value;
    }

    /**
     * Gets the value of the startOfName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartOfName() {
        return startOfName;
    }

    /**
     * Sets the value of the startOfName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartOfName(String value) {
        this.startOfName = value;
    }

}
