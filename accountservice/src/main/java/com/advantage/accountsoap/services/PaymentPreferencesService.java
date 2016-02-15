package com.advantage.accountsoap.services;

import com.advantage.accountsoap.dao.PaymentPreferencesRepository;
import com.advantage.accountsoap.dto.payment.PaymentPreferencesStatusResponse;
import com.advantage.accountsoap.model.PaymentPreferences;
import com.advantage.accountsoap.model.PaymentPreferencesPK;
import com.advantage.common.enums.PaymentMethodEnum;
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
        if ((safePayUsername.length() < 1) || (safePayUsername.length() > 20)) {
            return new PaymentPreferencesStatusResponse(false, "Failure. Invalid SafePay user name", 0);
        }

        PaymentPreferences paymentPreferences = paymentPreferencesRepository.find(accountId, PaymentMethodEnum.SAFE_PAY.getCode());

        if (paymentPreferences == null) {
            paymentPreferences = paymentPreferencesRepository.createSafePay(safePayUsername, accountId);
        } else {
            paymentPreferences = paymentPreferencesRepository.updateSafePay(accountId, safePayUsername);
        }

        if (paymentPreferences == null ) return new PaymentPreferencesStatusResponse(false, "addSafePayMethod: Failed", -1);

        return new PaymentPreferencesStatusResponse(true, "Successful", 0);
    }

    @Transactional
    public PaymentPreferencesStatusResponse addMasterCreditMethod(String cardNumber, String expirationDate,
                                                                  String cvvNumber, String customerName, long accountId) {

        System.out.println("validate card number = " + cardNumber);
        if(!ValidationHelper.isValidMasterCreditCardNumber(cardNumber)) {
            return new PaymentPreferencesStatusResponse(false, "addMasterCreditMethod. Error: Invalid card number", -1);
        }

        System.out.println("validate CVV number = " + cvvNumber);
        if(!ValidationHelper.isValidMasterCreditCVVNumber(cvvNumber)) {
            return new PaymentPreferencesStatusResponse(false, "addMasterCreditMethod. Error: Invalid CVV number", -1);
        }

        /* convert expiration date "MMYYYY" to date format "DD.MM.YYYY" and validate it.    */
        System.out.println("validate ExpirationDate = \'" + expirationDate);
        StringBuilder sb = new StringBuilder("01.")
                                .append(expirationDate.substring(0, 2))
                                .append('.')
                                .append(expirationDate.substring(2, 6));

        System.out.println("ExpirationDate converted to date format dd.MM.yyyy = \'" + sb.toString() + "\'");
        if(!ValidationHelper.isValidDate(sb.toString())) {
            return new PaymentPreferencesStatusResponse(false, "addMasterCreditMethod. Error: Invalid expiration date format", -1);
        }

        PaymentPreferences paymentPreferences = paymentPreferencesRepository.find(accountId, PaymentMethodEnum.SAFE_PAY.getCode());
        if (paymentPreferences == null) {
            paymentPreferences = paymentPreferencesRepository.createMasterCredit(cardNumber, expirationDate,
                    cvvNumber, customerName, accountId);
        } else {
            paymentPreferences = paymentPreferencesRepository.updateMasterCredit(cardNumber, expirationDate, cvvNumber, customerName, accountId);
        }

        if (paymentPreferences == null) return new PaymentPreferencesStatusResponse(false, "addMasterCreditMethod: Failed", -1);

        return new PaymentPreferencesStatusResponse(true, "Successful", 0);
    }

    @Transactional
    public PaymentPreferencesStatusResponse updateSafePayMethod(long userId, String safePayUsername) {
        PaymentPreferences preferences = paymentPreferencesRepository.updateSafePay(userId, safePayUsername);
        if (preferences == null) return new PaymentPreferencesStatusResponse(false, "updateSafePayMethod: user-id=" + userId + ", Failed", -1);

        return new PaymentPreferencesStatusResponse(true, "Successful", 0);
    }

    @Transactional
    public PaymentPreferencesStatusResponse updateMasterCreditMethod(long userId, String cardNumber, String expirationDate,
                                                                  String cvvNumber, String customerName, long referenceId) {
        if(!ValidationHelper.isValidMasterCreditCardNumber(cardNumber)) {
            return new PaymentPreferencesStatusResponse(false, "updateMasterCreditMethod. Error: Invalid card number", -1);
        }
        if(!ValidationHelper.isValidMasterCreditCVVNumber(cvvNumber)) {
            return new PaymentPreferencesStatusResponse(false, "updateMasterCreditMethod. Error: Invalid CVV number", -1);
        }

        System.out.println("validate ExpirationDate = \'" + expirationDate);
        StringBuilder sb = new StringBuilder("01.")
                .append(expirationDate.substring(0, 2))
                .append('.')
                .append(expirationDate.substring(2, 6));

        System.out.println("ExpirationDate converted to date format dd.MM.yyyy = \'" + sb.toString() + "\'");
        if(!ValidationHelper.isValidDate(sb.toString())) {
            return new PaymentPreferencesStatusResponse(false, "updateMasterCreditMethod. Error: Invalid expiration date format", -1);
        }
        PaymentPreferences preferences = paymentPreferencesRepository.updateMasterCredit(cardNumber, expirationDate,
                cvvNumber, customerName, userId);
        if(preferences == null ) return new PaymentPreferencesStatusResponse(false, "updateMasterCreditMethod: Failed", -1);

        return new PaymentPreferencesStatusResponse(true, "Successful", 0);
    }

    @Transactional
    public PaymentPreferencesStatusResponse deletePaymentPreference(long userId, int paymentMethod) {
        PaymentPreferences paymentPreferences = paymentPreferencesRepository.delete(userId, paymentMethod);
        if(paymentPreferences == null ) return  new PaymentPreferencesStatusResponse(false, "", -1);

        return  new PaymentPreferencesStatusResponse(true, "successful", 0);
    }

    @Transactional
    public boolean isPaymentPreferencesExist(long accountId) {
        PaymentPreferences paymentPreferences = paymentPreferencesRepository.get(accountId);

        return (paymentPreferences != null);
    }
}
