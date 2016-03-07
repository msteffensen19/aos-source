
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
 *         &lt;element name="parameterName" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *         &lt;element name="attributeTools" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *         &lt;element name="parameterNewValue" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
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
    "parameterName",
    "attributeTools",
    "parameterNewValue"
})
@XmlRootElement(name = "UpdateDemoAppConfigParameterRequest")
public class UpdateDemoAppConfigParameterRequest {

    @XmlElement(required = true)
    protected String parameterName;
    @XmlElement(required = true)
    protected String attributeTools;
    @XmlElement(required = true)
    protected String parameterNewValue;

    /**
     * Gets the value of the parameterName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParameterName() {
        return parameterName;
    }

    /**
     * Sets the value of the parameterName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParameterName(String value) {
        this.parameterName = value;
    }

    /**
     * Gets the value of the attributeTools property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeTools() {
        return attributeTools;
    }

    /**
     * Sets the value of the attributeTools property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeTools(String value) {
        this.attributeTools = value;
    }

    /**
     * Gets the value of the parameterNewValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParameterNewValue() {
        return parameterNewValue;
    }

    /**
     * Sets the value of the parameterNewValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParameterNewValue(String value) {
        this.parameterNewValue = value;
    }

}
