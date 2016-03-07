
package AccountServiceClient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountConfigurationStatusResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountConfigurationStatusResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="emailAddressInLogin" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="loginBlockingIntervalInMilliSeconds" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="numberOfFailedLoginAttemptsBeforeBlocking" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="productInStockDefaultValue" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="userSecondWsdl" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="userLoginTimeout" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="allowUserConfiguration" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountConfigurationStatusResponse", propOrder = {
    "emailAddressInLogin",
    "loginBlockingIntervalInMilliSeconds",
    "numberOfFailedLoginAttemptsBeforeBlocking",
    "productInStockDefaultValue",
    "userSecondWsdl",
    "userLoginTimeout",
    "allowUserConfiguration"
})
public class AccountConfigurationStatusResponse {

    protected boolean emailAddressInLogin;
    protected long loginBlockingIntervalInMilliSeconds;
    protected int numberOfFailedLoginAttemptsBeforeBlocking;
    protected int productInStockDefaultValue;
    protected boolean userSecondWsdl;
    protected int userLoginTimeout;
    protected boolean allowUserConfiguration;

    /**
     * Gets the value of the emailAddressInLogin property.
     * 
     */
    public boolean isEmailAddressInLogin() {
        return emailAddressInLogin;
    }

    /**
     * Sets the value of the emailAddressInLogin property.
     * 
     */
    public void setEmailAddressInLogin(boolean value) {
        this.emailAddressInLogin = value;
    }

    /**
     * Gets the value of the loginBlockingIntervalInMilliSeconds property.
     * 
     */
    public long getLoginBlockingIntervalInMilliSeconds() {
        return loginBlockingIntervalInMilliSeconds;
    }

    /**
     * Sets the value of the loginBlockingIntervalInMilliSeconds property.
     * 
     */
    public void setLoginBlockingIntervalInMilliSeconds(long value) {
        this.loginBlockingIntervalInMilliSeconds = value;
    }

    /**
     * Gets the value of the numberOfFailedLoginAttemptsBeforeBlocking property.
     * 
     */
    public int getNumberOfFailedLoginAttemptsBeforeBlocking() {
        return numberOfFailedLoginAttemptsBeforeBlocking;
    }

    /**
     * Sets the value of the numberOfFailedLoginAttemptsBeforeBlocking property.
     * 
     */
    public void setNumberOfFailedLoginAttemptsBeforeBlocking(int value) {
        this.numberOfFailedLoginAttemptsBeforeBlocking = value;
    }

    /**
     * Gets the value of the productInStockDefaultValue property.
     * 
     */
    public int getProductInStockDefaultValue() {
        return productInStockDefaultValue;
    }

    /**
     * Sets the value of the productInStockDefaultValue property.
     * 
     */
    public void setProductInStockDefaultValue(int value) {
        this.productInStockDefaultValue = value;
    }

    /**
     * Gets the value of the userSecondWsdl property.
     * 
     */
    public boolean isUserSecondWsdl() {
        return userSecondWsdl;
    }

    /**
     * Sets the value of the userSecondWsdl property.
     * 
     */
    public void setUserSecondWsdl(boolean value) {
        this.userSecondWsdl = value;
    }

    /**
     * Gets the value of the userLoginTimeout property.
     * 
     */
    public int getUserLoginTimeout() {
        return userLoginTimeout;
    }

    /**
     * Sets the value of the userLoginTimeout property.
     * 
     */
    public void setUserLoginTimeout(int value) {
        this.userLoginTimeout = value;
    }

    /**
     * Gets the value of the allowUserConfiguration property.
     * 
     */
    public boolean isAllowUserConfiguration() {
        return allowUserConfiguration;
    }

    /**
     * Sets the value of the allowUserConfiguration property.
     * 
     */
    public void setAllowUserConfiguration(boolean value) {
        this.allowUserConfiguration = value;
    }

}
