package com.advantage.accountsoap.dao;

import com.advantage.accountsoap.model.Account;

public interface AddressRepository extends DefaultCRUDOperations<Account> {
    public long addAddress(long userId, String line1, String line2);
}
