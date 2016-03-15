
package AccountServiceClient;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="parameters" maxOccurs="unbounded" form="qualified">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="parameterName" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *                   &lt;element name="attributeTools" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *                   &lt;element name="parameterValue" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "parameters"
})
@XmlRootElement(name = "DemoAppConfigGetParametersByToolResponse")
public class DemoAppConfigGetParametersByToolResponse {

    @XmlElement(required = true)
    protected List<DemoAppConfigGetParametersByToolResponse.Parameters> parameters;

    /**
     * Gets the value of the parameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DemoAppConfigGetParametersByToolResponse.Parameters }
     * 
     * 
     */
    public List<DemoAppConfigGetParametersByToolResponse.Parameters> getParameters() {
        if (parameters == null) {
            parameters = new ArrayList<DemoAppConfigGetParametersByToolResponse.Parameters>();
        }
        return this.parameters;
    }


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
     *         &lt;element name="parameterValue" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
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
        "parameterValue"
    })
    public static class Parameters {

        @XmlElement(required = true)
        protected String parameterName;
        @XmlElement(required = true)
        protected String attributeTools;
        @XmlElement(required = true)
        protected String parameterValue;

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
         * Gets the value of the parameterValue property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getParameterValue() {
            return parameterValue;
        }

        /**
         * Sets the value of the parameterValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setParameterValue(String value) {
            this.parameterValue = value;
        }

    }

}
