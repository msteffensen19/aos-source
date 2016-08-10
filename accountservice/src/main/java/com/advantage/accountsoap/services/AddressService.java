package com.advantage.accountsoap.services;

import com.advantage.accountsoap.dao.AccountRepository;
import com.advantage.accountsoap.dao.AddressRepository;
import com.advantage.accountsoap.dao.CountryRepository;
import com.advantage.accountsoap.dto.address.AddAddressDto;
import com.advantage.accountsoap.dto.address.AddressDto;
import com.advantage.accountsoap.dto.address.AddressStatusResponse;
import com.advantage.accountsoap.model.Account;
import com.advantage.accountsoap.model.Country;
import com.advantage.accountsoap.model.ShippingAddress;
import com.advantage.root.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AddressService {
    @Autowired
    @Qualifier("addressRepository")
    private AddressRepository addressRepository;

    @Autowired
    @Qualifier("accountRepository")
    private AccountRepository accountRepository;

    @Autowired
    @Qualifier("countryRepository")
    private CountryRepository countryRepository;


    @Transactional
    public AddressStatusResponse add(long accountId, Collection<AddAddressDto> addresses) {
        if(accountRepository.get(accountId) == null) {
            return new AddressStatusResponse(false, "Account not found");
        }

        boolean updateUserAccountAddress = true;
        for (AddAddressDto address : addresses) {
            //  region Update user-account address
            if (updateUserAccountAddress) {
                Account account = accountRepository.get(accountId);
                if (account != null) {
                    StringBuilder sb = new StringBuilder(address.getAddressLine1() != null ? address.getAddressLine1() : "")
                            .append(address.getAddressLine2() != null ? address.getAddressLine2() : "");

                    account.setAddress(sb.toString());

                    account.setCountry(countryRepository.get(Long.valueOf(address.getCountry())) != null ? countryRepository.get(Long.valueOf(address.getCountry())) : new Country());
                    account.setStateProvince(!StringHelper.isNullOrEmpty(address.getState()) ? address.getState() : "");
                    account.setCityName(!StringHelper.isNullOrEmpty(address.getCity()) ? address.getCity() : "");
                    account.setZipcode(!StringHelper.isNullOrEmpty(address.getPostalCode()) ? address.getPostalCode() : "");

                    accountRepository.updateAppUser(account);
                }

                updateUserAccountAddress = false;
            }
            //  endregion

            addressRepository.addAddress(accountId,
                    address.getAddressLine1(),
                    address.getAddressLine2(),
                    address.getCity(),
                    address.getCountry(),
                    address.getState(),
                    address.getPostalCode());
        }

        return new AddressStatusResponse(true, "Successful");
    }

    @Transactional
    public AddressStatusResponse add(long accountId, AddAddressDto address) {
        List<AddAddressDto> dtos = new ArrayList<>();
        dtos.add(address);

        return add(accountId, dtos);
    }

    @Transactional
    public List<AddressDto> getAll() {
        return fillDtos(addressRepository.getAll());
    }

    @Transactional
    public List<AddressDto> getByAccountId(long accountId) {
        return fillDtos(addressRepository.getByAccountId(accountId));
    }

    @Transactional
    public AddressStatusResponse update(AddressDto address) {
        ShippingAddress shippingAddress = addressRepository.get(address.getId());
        if(shippingAddress == null) return  new AddressStatusResponse(false, "Address not found");

        shippingAddress.setAddressLine1(address.getAddressLine1());
        shippingAddress.setAddressLine2(address.getAddressLine2());
        shippingAddress.setCity(address.getCity());
        shippingAddress.setCountry(address.getCountry());
        shippingAddress.setPostalCode(address.getPostalCode());
        shippingAddress.setState(address.getState());
        addressRepository.update(shippingAddress);

        //  region Update user-account address
        Account account = accountRepository.get(shippingAddress.getAccount().getId());
        if (account != null) {
            StringBuilder sb = new StringBuilder(address.getAddressLine1() != null ? address.getAddressLine1() : "")
                    .append(address.getAddressLine2() != null ? address.getAddressLine2() : "");

            account.setAddress(sb.toString());

            account.setCountry(countryRepository.get(Long.valueOf(address.getCountry())) != null ? countryRepository.get(Long.valueOf(address.getCountry())) : new Country());
            account.setStateProvince(!StringHelper.isNullOrEmpty(address.getState()) ? address.getState() : "");
            account.setCityName(!StringHelper.isNullOrEmpty(address.getCity()) ? address.getCity() : "");
            account.setZipcode(!StringHelper.isNullOrEmpty(address.getPostalCode()) ? address.getPostalCode() : "");

            accountRepository.updateAppUser(account);
        }
        //  endregion

        return new AddressStatusResponse(true, "Successfully");
    }

    private List<AddressDto> fillDtos(Collection<ShippingAddress> addresses) {
        if(addresses == null) return null;

        List<AddressDto> dtos = new ArrayList<>();
        for (ShippingAddress address : addresses) {
            dtos.add(new AddressDto(address.getId(),
                    address.getAddressLine1(),
                    address.getAddressLine2(),
                    address.getCity(),
                    address.getCountry(),
                    address.getState(),
                    address.getPostalCode(),
                    address.getAccount().getId()));
        }

        return dtos;
    }
}
