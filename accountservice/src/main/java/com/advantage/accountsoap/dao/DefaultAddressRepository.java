package com.advantage.accountsoap.dao;

import com.advantage.accountsoap.model.Account;
import com.advantage.accountsoap.model.ShippingAddress;
import com.advantage.accountsoap.services.AccountService;
import com.advantage.accountsoap.util.ArgumentValidationHelper;
import com.advantage.accountsoap.util.JPAQueryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("addressRepository")
@Repository
public class DefaultAddressRepository extends AbstractRepository implements AddressRepository {
    @Autowired
    AccountService accountService;

    @Override
    public int delete(ShippingAddress... entities) {
        int count = 0;
        for (ShippingAddress entity : entities) {
            if (entityManager.contains(entity)) {
                entityManager.remove(entity);
                count++;
            }
        }

        return count;
    }

    @Override
    public List<ShippingAddress> getAll() {
        List<ShippingAddress> addresses =
                entityManager.createNamedQuery(ShippingAddress.QUERY_GET_ALL, ShippingAddress.class)
                        .getResultList();

        return addresses.isEmpty() ? null : addresses;
    }

    @Override
    public ShippingAddress get(Long entityId) {
        ArgumentValidationHelper.validateArgumentIsNotNull(entityId, "address id");

        return entityManager.find(ShippingAddress.class, entityId);
    }

    @Override
    public long addAddress(long userId, String addressLine1, String addressLine2, String city, String country,
                           String state, String postalCode) {
        Account account = accountService.getById(userId);
        if (account == null) return -1;
        ShippingAddress address = new ShippingAddress(addressLine1, addressLine2, account, city,
                country, state, postalCode);

        return create(address);
    }

    @Override
    public Long create(ShippingAddress entity) {
        entityManager.persist(entity);

        return entity.getId();
    }

    @Override
    public List<ShippingAddress> getByAccountId(Long accountId) {
        List<ShippingAddress> addresses = new ArrayList<>(accountService.getById(accountId).getAddresses());

        return addresses.isEmpty() ? null : addresses;
    }

    @Override
    public ShippingAddress update(ShippingAddress address) {
        entityManager.persist(address);

        return address;
    }
}
