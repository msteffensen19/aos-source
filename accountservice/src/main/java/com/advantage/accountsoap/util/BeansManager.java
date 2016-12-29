package com.advantage.accountsoap.util;

import com.advantage.accountsoap.config.Injectable;
import com.advantage.accountsoap.dao.PaymentPreferencesRepository;
import com.advantage.accountsoap.services.PaymentPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kubany on 12/28/2016.
 */
public class BeansManager {

    //This line will guarantee the BeansManager class will be injected last
    @Autowired
    private Set<Injectable> injectables = new HashSet();

    //This method will make sure all the injectable classes will get the BeansManager in its steady state,
    //where it's class members are ready to be set
    @PostConstruct
    private void inject() {
        for (Injectable injectableItem : injectables) {
            injectableItem.inject(this);
        }
    }

    @Autowired
    @Qualifier("paymentPreferencesRepository")
    public PaymentPreferencesRepository paymentPreferencesRepository;

    @Autowired
    @Qualifier("paymentPreferencesService")
    public PaymentPreferencesService paymentPreferencesService;

    public PaymentPreferencesRepository getPaymentPreferencesRepository() {
        return paymentPreferencesRepository;
    }

    public void setPaymentPreferencesRepository(PaymentPreferencesRepository paymentPreferencesRepository) {
        this.paymentPreferencesRepository = paymentPreferencesRepository;
    }

    public PaymentPreferencesService getPaymentPreferencesService() {
        return paymentPreferencesService;
    }

    public void setPaymentPreferencesService(PaymentPreferencesService paymentPreferencesService) {
        this.paymentPreferencesService = paymentPreferencesService;
    }
}
