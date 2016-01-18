package com.advantage.accountsoap.dao.impl;

import com.advantage.accountsoap.dao.AbstractRepository;
import com.advantage.accountsoap.dao.PaymentPreferencesRepository;
import com.advantage.accountsoap.model.Account;
import com.advantage.accountsoap.model.PaymentPreferences;
import com.advantage.accountsoap.services.AccountService;
import com.advantage.accountsoap.util.ArgumentValidationHelper;
import com.advantage.accountsoap.util.JPAQueryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Component
@Qualifier("paymentPreferencesRepository")
@Repository
public class DefaultPaymentPreferencesRepository extends AbstractRepository implements PaymentPreferencesRepository {
    @Autowired
    AccountService accountService;

    @Override
    public int delete(PaymentPreferences... entities) {
        int count = 0;
        for (PaymentPreferences entity : entities) {
            if (entityManager.contains(entity)) {
                entityManager.remove(entity);
                count++;
            }
        }

        return count;
    }

    @Override
    public PaymentPreferences delete(Long id) {
        PaymentPreferences entity = get(id);
        if (entity != null) delete(entity);

        return entity;
    }

    @Override
    public List<PaymentPreferences> getAll() {
        List<PaymentPreferences> accounts = entityManager.createNamedQuery(PaymentPreferences.QUERY_GET_ALL,
                PaymentPreferences.class)
                .getResultList();

        return accounts.isEmpty() ? null : accounts;
    }

    @Override
    public PaymentPreferences get(Long entityId) {
        ArgumentValidationHelper.validateArgumentIsNotNull(entityId, "payment preferences id");
        /*return entityManager.find(PaymentPreferences.class, entityId);*/

        String hql = JPAQueryHelper.getSelectByPkFieldQuery(PaymentPreferences.class, PaymentPreferences.FIELD_ID, entityId);

        Query query = entityManager.createQuery(hql);
        return (PaymentPreferences) query.getSingleResult();

    }

    @Override
    public Long create(PaymentPreferences entity) {
        entityManager.persist(entity);

        return entity.getId();
    }

    @Override
    public PaymentPreferences createMasterCredit(String cardNumber, String expirationDate, String cvvNumber, String customerName, long accountId) {
        PaymentPreferences paymentPreferences = new PaymentPreferences(cardNumber, expirationDate, cvvNumber, customerName);
        Account account = accountService.getById(accountId);
        if (account == null) return null;
        paymentPreferences.setAccount(account);
        entityManager.persist(paymentPreferences);

        return paymentPreferences;
    }

    @Override
    public PaymentPreferences createSafePay(String safePayUsername, long accountId) {
        PaymentPreferences paymentPreferences = new PaymentPreferences(safePayUsername);
        Account account = accountService.getById(accountId);
        if (account == null) return null;
        paymentPreferences.setAccount(account);
        entityManager.persist(paymentPreferences);

        return paymentPreferences;
    }

    @Override
    public PaymentPreferences updateMasterCredit(String cardNumber, String expirationDate,
                                                 String cvvNumber, String customerName, long preferenceId) {
        PaymentPreferences preferences = get(preferenceId);
        if (preferences == null) return null;
        preferences.setCardNumber(cardNumber);
        preferences.setExpirationDate(expirationDate);
        preferences.setCvvNumber(cvvNumber);
        preferences.setCustomerName(customerName);

        entityManager.persist(preferences);

        return preferences;
    }

    @Override
    public PaymentPreferences updateSafePay(String safePayUsername, long preferenceId) {
        PaymentPreferences paymentPreferences = get(preferenceId);
        if (paymentPreferences == null) return null;
        paymentPreferences.setSafePayUsername(safePayUsername);
        entityManager.persist(paymentPreferences);

        return paymentPreferences;
    }
}
