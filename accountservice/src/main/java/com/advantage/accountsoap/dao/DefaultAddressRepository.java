package com.advantage.accountsoap.dao;

import com.advantage.accountsoap.model.Account;

import java.util.List;

public class DefaultAddressRepository implements AddressRepository {
    @Override
    public int delete(Account... entities) {
        return 0;
    }

    @Override
    public List<Account> getAll() {
        return null;
    }

    @Override
    public Account get(Long entityId) {
        return null;
    }

    @Override
    public long addAddress(long userId, String line1, String line2) {
        return 0;
    }
}
