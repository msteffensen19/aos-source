package com.advantage.accountsoap.dao;

import com.advantage.accountsoap.model.PaymentPreferences;
import com.advantage.common.dao.DefaultCRUDOperations;

public interface PaymentPreferencesRepository extends DefaultCRUDOperations<PaymentPreferences> {

    PaymentPreferences createMasterCredit(String cardNumber, String expirationDate, String cvvNumber, String customerName, long accountId);

    PaymentPreferences createSafePay(String safePayUsername, long accountId);
}
