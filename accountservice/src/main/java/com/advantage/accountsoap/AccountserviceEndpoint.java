package com.advantage.accountsoap;

import com.advantage.accountsoap.config.WebServiceConfig;
import com.advantage.accountsoap.dto.account.PaymentMethodUpdateResponse;
import com.advantage.accountsoap.dto.account.*;
import com.advantage.accountsoap.dto.country.*;
import com.advantage.accountsoap.dto.address.*;
import com.advantage.accountsoap.dto.payment.*;
import com.advantage.accountsoap.model.Account;
import com.advantage.accountsoap.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class AccountserviceEndpoint {
    @Autowired
    private AccountService accountService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PaymentPreferencesService paymentPreferencesService;

    @Autowired
    private AccountConfigurationService accountConfigurationService;

    //region Account
    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "GetAllAccountsRequest")
    @ResponsePayload
    public GetAllAccountsResponse getAllAccounts() {
        List<AccountDto> appUsers = accountService.getAllAppUsersDto();
        GetAllAccountsResponse getAllAccountsResponse = new GetAllAccountsResponse();
        getAllAccountsResponse.setAccount(appUsers);

        return getAllAccountsResponse;
    }


    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "GetAccountByIdRequest")
    @ResponsePayload
    public GetAccountByIdResponse getAccount(@RequestPayload GetAccountByIdRequest accountId) {
        Account account = accountService.getById(accountId.getId());
        if (account == null) return null;
        AccountDto dto = new AccountDto(account.getId(),
                account.getLastName(),
                account.getFirstName(),
                account.getLoginName(),
                account.getAccountType(),
                account.getCountry().getId(),
                account.getCountry().getName(),
                account.getCountry().getIsoName(),
                account.getStateProvince(),
                account.getCityName(),
                account.getAddress(),
                account.getZipcode(),
                account.getPhoneNumber(),
                account.getEmail(),
                account.getDefaultPaymentMethodId(),
                account.isAllowOffersPromotion(), account.getInternalUnsuccessfulLoginAttempts(),
                account.getInternalUserBlockedFromLoginUntil(),
                account.getInternalLastSuccesssulLogin());

        return new GetAccountByIdResponse(dto);
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "GetAccountByIdNewRequest")
    @ResponsePayload
    public GetAccountByIdNewResponse getAccount(@RequestPayload GetAccountByIdNewRequest accountId) {
        Account account = accountService.getById(accountId.getId());
        if (account == null) return null;
        AccountDtoNew dto = new AccountDtoNew(account.getId(),
                account.getLastName(),
                account.getFirstName(),
                account.getLoginName(),
                account.getAccountType(),
                account.getCountry().getId(),
                account.getCountry().getName(),
                account.getCountry().getIsoName(),
                account.getStateProvince(),
                account.getCityName(),
                account.getAddress(),
                account.getZipcode(),
                account.getPhoneNumber(),
                account.getEmail(),
                account.getDefaultPaymentMethodId(),
                account.isAllowOffersPromotion(), account.getInternalUnsuccessfulLoginAttempts(),
                account.getInternalUserBlockedFromLoginUntil(),
                account.getInternalLastSuccesssulLogin());

        return new GetAccountByIdNewResponse(dto);
    }


    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "AccountLoginRequest")
    @ResponsePayload
    public AccountLoginResponse doLogin(@RequestPayload AccountLoginRequest account) {
        //todo set header

        AccountStatusResponse response = accountService.doLogin(account.getLoginUser(),
                account.getLoginPassword(),
                account.getEmail());

        if (response.isSuccess()) {
            //TODO-ALEX set session
            /*HttpSession session = request.getSession();
            session.setAttribute(Constants.UserSession.TOKEN, response.getToken());
            session.setAttribute(Constants.UserSession.USER_ID, response.getUserId());
            session.setAttribute(Constants.UserSession.IS_SUCCESS, response.isSuccess());

            //  Set SessionID to Response Entity
            //response.getHeader().
            response.setSessionId(session.getId());*/
            response.setSessionId("fake_id");

            return new AccountLoginResponse(response);
        } else {
            return new AccountLoginResponse(response);
        }
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "AccountCreateRequest")
    @ResponsePayload
    public AccountCreateResponse createAccount(@RequestPayload AccountCreateRequest account) {
        AccountStatusResponse response = accountService.create(
                account.getAccountType(),
                account.getLastName(),
                account.getFirstName(),
                account.getLoginName(),
                account.getPassword(),
                account.getCountry(),
                account.getPhoneNumber(),
                account.getStateProvince(),
                account.getCityName(),
                account.getAddress(),
                account.getZipcode(),
                account.getEmail(),
                account.isAllowOffersPromotion());

        return new AccountCreateResponse(response);
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "AccountUpdateRequest")
    @ResponsePayload
    public AccountUpdateResponse updateAccount(@RequestPayload AccountUpdateRequest account) {
        AccountStatusResponse response = accountService.updateAccount(
                account.getAccountId(),
                account.getAccountType(),
                account.getLastName(),
                account.getFirstName(),
                account.getCountry(),
                account.getPhoneNumber(),
                account.getStateProvince(),
                account.getCityName(),
                account.getAddress(),
                account.getZipcode(),
                account.getEmail(),
                account.isAllowOffersPromotion());

        return new AccountUpdateResponse(response);
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "ChangePasswordRequest")
    @ResponsePayload
    public ChangePasswordResponse changePassword(@RequestPayload ChangePasswordRequest request) {
        AccountStatusResponse response = accountService.changePassword(request.getAccountId(), request.getNewPassword());
        return new ChangePasswordResponse(response);
    }
    //endregion

    //region Countries
    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "GetCountriesRequest")
    @ResponsePayload
    public GetCountriesResponse getCountries() {
        GetCountriesResponse response = new GetCountriesResponse();
        response.setCountry(countryService.getAllCountries());

        return response;
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "CountryCreateRequest")
    @ResponsePayload
    public CountryCreateResponse createCountry(@RequestPayload CountryCreateRequest country) {
        CountryStatusResponse response = countryService.create(country.getName(),
                country.getIsoName(),
                country.getPhonePrefix());

        return new CountryCreateResponse(response);
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "CountrySearchRequest")
    @ResponsePayload
    public CountrySearchResponse searchInCountries(@RequestPayload CountrySearchRequest request) {
        List<CountryDto> countries;

        if (request == null) throw new IllegalArgumentException("Not valid parameters");
        if (!request.getStartOfName().isEmpty() && request.getInternationalPhonePrefix() != 0) {
            throw new IllegalArgumentException("Not valid parameters");
        } else if (request.getInternationalPhonePrefix() > 0) {
            countries = countryService.getCountriesByPhonePrefix(request.getInternationalPhonePrefix());
        } else {
            countries = countryService.getCountriesByPartialName(request.getStartOfName().toUpperCase());
        }
        if (countries == null || countries.isEmpty()) {
            return null;
        } else {
            return new CountrySearchResponse(countries);
        }
    }
    //endregion

    //region Address
    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "GetAddressesByAccountIdRequest")
    @ResponsePayload
    public GetAddressesByAccountIdResponse getAccountShippingAddress(@RequestPayload GetAddressesByAccountIdRequest accountId) {
        GetAddressesByAccountIdResponse response = new GetAddressesByAccountIdResponse();
        List<AddressDto> addresses = addressService.getByAccountId(accountId.getId());
        response.setShippingAddress(addresses);

        return response;
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "AddAddressesRequest")
    @ResponsePayload
    public AddAddressesResponse addAddress(@RequestPayload AddAddressesRequest address) {
        AddressStatusResponse response = addressService.add(address.getAccountId(), address.getAddresses());

        return new AddAddressesResponse(response);
    }


    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "AddressUpdateRequest")
    @ResponsePayload
    public AddressUpdateResponse updateAddress(@RequestPayload AddressUpdateRequest address) {
        AddressStatusResponse response = addressService.update(address.getAddress());

        return new AddressUpdateResponse(response);
    }
    //endregion

    //region Payment details
    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "PaymentMethodUpdateRequest")
    @ResponsePayload
    public PaymentMethodUpdateResponse updateDefaultPaymentMethod(@RequestPayload PaymentMethodUpdateRequest request) {
        AccountStatusResponse response =
                accountService.updateDefaultPaymentMethod(request.getAccountId(), request.getPaymentMethod());

        return new PaymentMethodUpdateResponse(response);
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "AddSafePayMethodRequest")
    @ResponsePayload
    public AddSafePayMethodResponse addSafePayMethod(@RequestPayload AddSafePayMethodRequest request) {
        PaymentPreferencesStatusResponse response = paymentPreferencesService.addSafePayMethod(request.getSafePayUsername(),
                request.getAccountId());

        return new AddSafePayMethodResponse(response);
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "AddMasterCreditMethodRequest")
    @ResponsePayload
    public AddMasterCreditMethodResponse addMasterCreditMethod(@RequestPayload AddMasterCreditMethodRequest request) {
        PaymentPreferencesStatusResponse response = paymentPreferencesService.addMasterCreditMethod(request.getCardNumber(),
                request.getExpirationDate(), request.getCvvNumber(), request.getCustomerName(), request.getAccountId());

        return new AddMasterCreditMethodResponse(response);
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "UpdateMasterCreditMethodRequest")
    @ResponsePayload
    public UpdateMasterCreditMethodResponse updateMasterCreditMethod(@RequestPayload UpdateMasterCreditMethodRequest request) {
        PaymentPreferencesStatusResponse response = paymentPreferencesService.updateMasterCreditMethod(request.getCardNumber(),
                request.getExpirationDate(), request.getCvvNumber(), request.getCustomerName(), request.getReferenceId());

        return new UpdateMasterCreditMethodResponse(response);
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "UpdateSafePayMethodRequest")
    @ResponsePayload
    public UpdateSafePayMethodResponse updateSafePayMethod(@RequestPayload UpdateSafePayMethodRequest request) {
        PaymentPreferencesStatusResponse response = paymentPreferencesService.updateSafePayMethod(request.getSafePayUsername(),
                request.getReferenceId());

        return new UpdateSafePayMethodResponse(response);
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "GetAccountPaymentPreferencesRequest")
    @ResponsePayload
    public GetAccountPaymentPreferencesResponse getAccountPaymentPreferences(@RequestPayload GetAccountPaymentPreferencesRequest request) {
        GetAccountPaymentPreferencesResponse response = new GetAccountPaymentPreferencesResponse();
        response.setPreferences(accountService.getPaymentPreferences(request.getId()));

        return response;
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "DeletePaymentPreferenceRequest")
    @ResponsePayload
    public DeletePaymentPreferenceResponse deletePaymentPreference(@RequestPayload DeletePaymentPreferenceRequest request) {
        AccountStatusResponse response = accountService.removePaymentPreferences(request.getAccountId(), request.getId());

        return new DeletePaymentPreferenceResponse(response);
    }


    //endregion


    //region Configuration
    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "AccountConfigurationStatusRequest")
    @ResponsePayload
    public AccountConfigurationStatusResponse getConfigurationParameters() {
        return accountConfigurationService.getAllConfigurationParameters();
    }
    //endregion


    /*protected HttpServletRequest getHttpServletRequest() {
        TransportContext ctx = TransportContextHolder.getTransportContext();
        return ( null != ctx ) ? ((HttpServletConnection) ctx.getConnection()).getHttpServletRequest() : null;
    }

    protected String getHttpHeaderValue( final String headerName ) {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        return ( null != httpServletRequest ) ? httpServletRequest.getHeader( headerName ) : null;
    }*/
}
