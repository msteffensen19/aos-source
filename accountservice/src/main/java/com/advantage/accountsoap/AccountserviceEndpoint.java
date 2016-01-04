package com.advantage.accountsoap;

import com.advantage.accountsoap.config.WebServiceConfig;
import com.advantage.accountsoap.dto.*;
import com.advantage.accountsoap.model.Account;
import com.advantage.accountsoap.services.AccountService;
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

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "AccountsRequest")
    @ResponsePayload
    public AccountsResponse getAllAccounts() {
        List<Account> appUsers = accountService.getAllAppUsers();
        AccountsResponse accountsResponse = new AccountsResponse();
        accountsResponse.setAccount(appUsers);

        return accountsResponse;
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "accountId")
    @ResponsePayload
    public boolean getAccount(@RequestPayload int accountId) {
        return accountService.isExists(accountId);
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

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "AccountUpdateRequest")
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
    }
}
