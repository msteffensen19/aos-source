
package AccountServiceClient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the AccountServiceClient package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetCountriesRequest_QNAME = new QName("com.advantage.online.store.accountservice", "GetCountriesRequest");
    private final static QName _AccountsRequest_QNAME = new QName("com.advantage.online.store.accountservice", "AccountsRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: AccountServiceClient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetCountriesResponse }
     * 
     */
    public GetCountriesResponse createGetCountriesResponse() {
        return new GetCountriesResponse();
    }

    /**
     * Create an instance of {@link Country }
     * 
     */
    public Country createCountry() {
        return new Country();
    }

    /**
     * Create an instance of {@link GetAccountByIdRequest }
     * 
     */
    public GetAccountByIdRequest createGetAccountByIdRequest() {
        return new GetAccountByIdRequest();
    }

    /**
     * Create an instance of {@link CountryCreateRequest }
     * 
     */
    public CountryCreateRequest createCountryCreateRequest() {
        return new CountryCreateRequest();
    }

    /**
     * Create an instance of {@link GetAccountByIdResponse }
     * 
     */
    public GetAccountByIdResponse createGetAccountByIdResponse() {
        return new GetAccountByIdResponse();
    }

    /**
     * Create an instance of {@link AccountsResponse }
     * 
     */
    public AccountsResponse createAccountsResponse() {
        return new AccountsResponse();
    }

    /**
     * Create an instance of {@link Account }
     * 
     */
    public Account createAccount() {
        return new Account();
    }

    /**
     * Create an instance of {@link AccountCreateRequest }
     * 
     */
    public AccountCreateRequest createAccountCreateRequest() {
        return new AccountCreateRequest();
    }

    /**
     * Create an instance of {@link AccountUpdateRequest }
     * 
     */
    public AccountUpdateRequest createAccountUpdateRequest() {
        return new AccountUpdateRequest();
    }

    /**
     * Create an instance of {@link CountrySearchRequest }
     * 
     */
    public CountrySearchRequest createCountrySearchRequest() {
        return new CountrySearchRequest();
    }

    /**
     * Create an instance of {@link AccountLoginRequest }
     * 
     */
    public AccountLoginRequest createAccountLoginRequest() {
        return new AccountLoginRequest();
    }

    /**
     * Create an instance of {@link AccountStatusResponse }
     * 
     */
    public AccountStatusResponse createAccountStatusResponse() {
        return new AccountStatusResponse();
    }

    /**
     * Create an instance of {@link CountryStatusResponse }
     * 
     */
    public CountryStatusResponse createCountryStatusResponse() {
        return new CountryStatusResponse();
    }

    /**
     * Create an instance of {@link AccountConfigurationResponseStatus }
     * 
     */
    public AccountConfigurationResponseStatus createAccountConfigurationResponseStatus() {
        return new AccountConfigurationResponseStatus();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "com.advantage.online.store.accountservice", name = "GetCountriesRequest")
    public JAXBElement<Object> createGetCountriesRequest(Object value) {
        return new JAXBElement<Object>(_GetCountriesRequest_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "com.advantage.online.store.accountservice", name = "AccountsRequest")
    public JAXBElement<Object> createAccountsRequest(Object value) {
        return new JAXBElement<Object>(_AccountsRequest_QNAME, Object.class, null, value);
    }

}
