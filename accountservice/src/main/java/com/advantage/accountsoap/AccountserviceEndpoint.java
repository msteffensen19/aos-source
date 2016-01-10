package com.advantage.accountsoap;

import com.advantage.accountsoap.config.WebServiceConfig;
import com.advantage.accountsoap.dto.*;
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
    public AccountDto getAccount(@RequestPayload GetAccountByIdRequest accountId) {
        Account account = accountService.getById(accountId.getId());
        if(account == null) return null;
        AccountDto response = new AccountDto(account.getId(),
                account.getLastName(),
                account.getFirstName(),
                account.getLoginName(),
                account.getAccountType(),
                account.getCountry(),
                account.getStateProvince(),
                account.getCityName(),
                account.getAddress(),
                account.getZipcode(),
                account.getPhoneNumber(),
                account.getEmail(),
                account.getAllowOffersPromotion(),account.getInternalUnsuccessfulLoginAttempts(),
                account.getInternalUserBlockedFromLoginUntil(),
                account.getInternalLastSuccesssulLogin());

        return response;
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "AccountLoginRequest")
    @ResponsePayload
    public AccountStatusResponse doLogin(@RequestPayload AccountLoginRequest account) {
        //todo set header

        AccountStatusResponse response = accountService.doLogin(account.getLoginUser(),
                account.getLoginPassword(),
                account.getEmail());

        if (response.isSuccess()) {
            //todo set session
            /*HttpSession session = request.getSession();
            session.setAttribute(Constants.UserSession.TOKEN, response.getToken());
            session.setAttribute(Constants.UserSession.USER_ID, response.getUserId());
            session.setAttribute(Constants.UserSession.IS_SUCCESS, response.isSuccess());

            //  Set SessionID to Response Entity
            //response.getHeader().
            response.setSessionId(session.getId());*/

            return response;
        } else {
            return response;
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

    /*@PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "AccountUpdateRequest")
    @ResponsePayload
    public AccountStatusResponse updateAccount(@RequestPayload AccountUpdateRequest account) {
        return accountService.updateAccount(
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
    }*/

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "GetCountriesRequest")
    @ResponsePayload
    public GetCountriesResponse getCountries() {
        List<Country> countries = countryService.getAllCountries();
        GetCountriesResponse response = new GetCountriesResponse();
        response.setCountry(countries);

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
        List<Country> countries;

        if(request == null) throw new IllegalArgumentException("Not valid parameters");
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
}
