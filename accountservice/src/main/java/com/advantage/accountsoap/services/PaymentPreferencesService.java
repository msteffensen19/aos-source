package com.advantage.accountsoap.services;

import com.advantage.accountsoap.dao.PaymentPreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PaymentPreferencesService {
    @Autowired
    @Qualifier("paymentPreferencesRepository")
    public PaymentPreferencesRepository paymentPreferencesRepository;
}
