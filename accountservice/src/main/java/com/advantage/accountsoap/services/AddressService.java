package com.advantage.accountsoap.services;

import com.advantage.accountsoap.dao.AccountRepository;
import com.advantage.accountsoap.dao.AddressRepository;
import com.advantage.accountsoap.dto.AddAddressDto;
import com.advantage.accountsoap.dto.AddressDto;
import com.advantage.accountsoap.dto.AddressStatusResponse;
import com.advantage.accountsoap.model.ShippingAddress;
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

    @Transactional
    public AddressStatusResponse add(long accountId, Collection<AddAddressDto> addresses) {
        if(accountRepository.get(accountId) == null) {
            return new AddressStatusResponse(false, "Account not found");
        }

        for (AddAddressDto address : addresses) {
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
