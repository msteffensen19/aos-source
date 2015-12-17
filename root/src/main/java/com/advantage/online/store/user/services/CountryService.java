package com.advantage.online.store.user.services;

import com.advantage.online.store.user.dao.CountryRepository;
import com.advantage.online.store.user.dto.CountryResponseStatus;
import com.advantage.online.store.user.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * @author Binyamin Regev on 29/11/2015.
 */
@Service
public class CountryService {

    @Autowired
    @Qualifier("countryRepository")
    public CountryRepository countryRepository;

    @Transactional
    public CountryResponseStatus create(final String name, final int phonePrefix) {
        return countryRepository.create(name, phonePrefix);
    }

    @Transactional
    public CountryResponseStatus create(final String name, final String isoName, final int phonePrefix) {
        return countryRepository.create(name, isoName, phonePrefix);
    }

    @Transactional(readOnly = true)
    public List<Country> getAllCountries() {
        return countryRepository.getAllCountries();
    }

    @Transactional(readOnly = true)
    public List<Country> getCountriesByPartialName(final String countryName) {
        return countryRepository.getCountriesByPartialName(countryName);
    }

    @Transactional(readOnly = true)
    public List<Country> getCountriesByPhonePrefix(final int phonePrefix) {
        return countryRepository.getCountriesByPhonePrefix(phonePrefix);
    }

}
