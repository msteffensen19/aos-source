package com.advantage.accountsoap;

import com.advantage.accountsoap.config.WebServiceConfig;
import com.advantage.accountsoap.dto.account.*;
import com.advantage.accountsoap.dto.country.*;
import com.advantage.accountsoap.dto.address.*;
import com.advantage.accountsoap.dto.payment.PaymentMethodUpdateRequest;
import com.advantage.accountsoap.model.Account;
import com.advantage.accountsoap.model.Country;
import com.advantage.accountsoap.services.AccountService;
import com.advantage.accountsoap.services.AddressService;
import com.advantage.accountsoap.services.CountryService;
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
                account.getAllowOffersPromotion(), account.getInternalUnsuccessfulLoginAttempts(),
                account.getInternalUserBlockedFromLoginUntil(),
                account.getInternalLastSuccesssulLogin());

        return new GetAccountByIdResponse(dto);
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

            return new AccountLoginResponse(response);
        } else {
            return new AccountLoginResponse(response);
        }
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "AccountCreateRequest")
    @ResponsePayload
    public AccountStatusResponse createAccount(@RequestPayload AccountCreateRequest account) {
        return accountService.create(
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
                account.getAllowOffersPromotion());
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "AccountUpdateRequest")
    @ResponsePayload
    public AccountStatusResponse updateAccount(@RequestPayload AccountUpdateRequest account) {
        return accountService.updateAccount(
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
                account.getAllowOffersPromotion());
    }

   /* @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "PaymentMethodUpdateRequest")
    @ResponsePayload
    public AccountStatusResponse updatePaymentMethod(@RequestPayload PaymentMethodUpdateRequest request) {
        return accountService.updatePaymentMethod(request.getAccountId(), request.getPaymentMethod());
    }*/

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "GetCountriesRequest")
    @ResponsePayload
    public GetCountriesResponse getCountries() {
        GetCountriesResponse response = new GetCountriesResponse();
        response.setCountry(countryService.getAllCountries());

        return response;
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "CountryCreateRequest")
    @ResponsePayload
    public CountryStatusResponse createCountry(@RequestPayload CountryCreateRequest country) {
        return countryService.create(country.getName(),
                country.getIsoName(),
                country.getPhonePrefix());
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "CountrySearchRequest")
    @ResponsePayload
    public GetCountriesResponse searchInCountries(@RequestPayload CountrySearchRequest request) {
        GetCountriesResponse response = new GetCountriesResponse();
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
            response.setCountry(countries);
            return response;
        }
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "GetAddressesByAccountIdRequest")
    @ResponsePayload
    public AddressesResponse getAccountShippingAddress(@RequestPayload GetAddressesByAccountIdRequest accountId) {
        AddressesResponse response = new AddressesResponse();
        List<AddressDto> addresses = addressService.getByAccountId(accountId.getId());
        response.setShippingAddress(addresses);

        return response;
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "AddAddressesRequest")
    @ResponsePayload
    public AddressStatusResponse addAddress(@RequestPayload AddAddressesRequest address) {
        return addressService.add(address.getAccountId(), address.getAddresses());
    }


    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "AddressUpdateRequest")
    @ResponsePayload
    public AddressStatusResponse updateAddress(@RequestPayload AddressUpdateRequest address) {
        return addressService.update(address.getAddress());
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "ChangePasswordRequest")
    @ResponsePayload
    public AccountStatusResponse changePassword(@RequestPayload ChangePasswordRequest request) {
        return accountService.changePassword(request.getAccountId(), request.getNewPassword());
    }

    /*protected HttpServletRequest getHttpServletRequest() {
        TransportContext ctx = TransportContextHolder.getTransportContext();
        return ( null != ctx ) ? ((HttpServletConnection) ctx.getConnection()).getHttpServletRequest() : null;
    }

    protected String getHttpHeaderValue( final String headerName ) {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        return ( null != httpServletRequest ) ? httpServletRequest.getHeader( headerName ) : null;
    }*/
}
