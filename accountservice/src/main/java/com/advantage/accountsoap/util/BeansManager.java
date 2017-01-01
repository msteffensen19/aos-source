package com.advantage.accountsoap.util;

import com.advantage.accountsoap.dao.AddressRepository;
import com.advantage.accountsoap.services.AccountService;
import com.advantage.accountsoap.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kubany on 1/1/2017.
 */
public class BeansManager {

    @Autowired
    private Set<Injectable> injectables = new HashSet();

    @Autowired
    private AccountService accountService;

    @Autowired
    private AddressService addressService;

    @Autowired
    @Qualifier("addressRepository")
    private AddressRepository addressRepository;

    public AddressService getAddressService() {
        return addressService;
    }

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    @PostConstruct
    private void inject() {
        for (Injectable injectableItem : injectables) {
            injectableItem.inject(this);
        }
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public AddressRepository getAddressRepository() {
        return addressRepository;
    }

    public void setAddressRepository(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
}
