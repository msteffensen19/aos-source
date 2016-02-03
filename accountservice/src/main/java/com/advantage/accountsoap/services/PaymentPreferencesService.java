package com.advantage.accountsoap.services;

import com.advantage.accountsoap.dao.PaymentPreferencesRepository;
import com.advantage.accountsoap.dto.payment.PaymentPreferencesStatusResponse;
import com.advantage.accountsoap.model.PaymentPreferences;
import com.advantage.root.util.ValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentPreferencesService {
    @Autowired
    @Qualifier("paymentPreferencesRepository")
    public PaymentPreferencesRepository paymentPreferencesRepository;

    @Transactional
    public PaymentPreferencesStatusResponse addSafePayMethod(String safePayUsername, long accountId) {
        PaymentPreferences preferences = paymentPreferencesRepository.createSafePay(safePayUsername, accountId);
        if(preferences == null ) return new PaymentPreferencesStatusResponse(false, "", -1);

        return new PaymentPreferencesStatusResponse(true, "Successfully", preferences.getId());
    }

    @Transactional
    public PaymentPreferencesStatusResponse addMasterCreditMethod(String cardNumber, String expirationDate,
                                                                  String cvvNumber, String customerName, long accountId) {
        if(!ValidationHelper.isValidMasterCreditCardNumber(cardNumber)) {
            return new PaymentPreferencesStatusResponse(false, "Invalid card number", -1);
        }
        if(!ValidationHelper.isValidMasterCreditCVVNumber(cvvNumber)) {
            return new PaymentPreferencesStatusResponse(false, "Invalid CVV number", -1);
        }

        /* convert expiration date "MMYYYY" to date format "DD.MM.YYYY" and validate it.    */
        StringBuilder sb = new StringBuilder("01.")
                                .append(expirationDate.substring(0, 2))
                                .append('.')
                                .append(expirationDate.substring(2, 6));
        if(!ValidationHelper.isValidDate(sb.toString())) {
            return new PaymentPreferencesStatusResponse(false, "Invalid expiration date format", -1);
        }

        PaymentPreferences preferences = paymentPreferencesRepository.createMasterCredit(cardNumber, expirationDate,
                cvvNumber, customerName, accountId);
        if(preferences == null ) return new PaymentPreferencesStatusResponse(false, "", -1);

        return new PaymentPreferencesStatusResponse(true, "Successfully", preferences.getId());
    }

    @Transactional
    public PaymentPreferencesStatusResponse updateSafePayMethod(String safePayUsername, long preferenceId) {
        PaymentPreferences preferences = paymentPreferencesRepository.updateSafePay(safePayUsername, preferenceId);
        if(preferences == null ) return new PaymentPreferencesStatusResponse(false, "", -1);

        return new PaymentPreferencesStatusResponse(true, "Successfully", preferences.getId());
    }

    @Transactional
    public PaymentPreferencesStatusResponse updateMasterCreditMethod(String cardNumber, String expirationDate,
                                                                  String cvvNumber, String customerName, long preferenceId) {
        if(!ValidationHelper.isValidMasterCreditCardNumber(cardNumber)) {
            return new PaymentPreferencesStatusResponse(false, "Invalid card number", -1);
        }
        if(!ValidationHelper.isValidMasterCreditCVVNumber(cvvNumber)) {
            return new PaymentPreferencesStatusResponse(false, "Invalid CVV number", -1);
        }
        if(!ValidationHelper.isValidDate(expirationDate)) {
            return new PaymentPreferencesStatusResponse(false, "Invalid expiration date format", -1);
        }
        PaymentPreferences preferences = paymentPreferencesRepository.updateMasterCredit(cardNumber, expirationDate,
                cvvNumber, customerName, preferenceId);
        if(preferences == null ) return new PaymentPreferencesStatusResponse(false, "", -1);

        return new PaymentPreferencesStatusResponse(true, "Successfully", preferences.getId());
    }

    @Transactional
    public PaymentPreferencesStatusResponse deletePaymentPreference(long preferenceId) {
        PaymentPreferences paymentPreferences = paymentPreferencesRepository.delete(preferenceId);
        if(paymentPreferences == null ) return  new PaymentPreferencesStatusResponse(false, "", -1);

        return  new PaymentPreferencesStatusResponse(true, "succssefully", paymentPreferences.getId());
    }

    @Transactional
    public boolean isPaymentPreferencesExist(long preferenceId) {
        PaymentPreferences paymentPreferences = paymentPreferencesRepository.get(preferenceId);

        return (paymentPreferences != null);
    }
}
