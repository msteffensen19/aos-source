package com.advantage.online.store.user.api;

import com.advantage.online.store.Constants;
import com.advantage.online.store.user.dto.CountryDto;
import com.advantage.online.store.user.dto.CountryResponseStatus;
import com.advantage.online.store.user.model.Country;
import com.advantage.online.store.user.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Binyamin Regev on 29/11/2015.
 */
@RestController
@RequestMapping(value = Constants.URI_API)
public class CountryController {

    @Autowired
    private CountryService countryService;

    @RequestMapping(value = "/countryData/create", method = RequestMethod.POST)
    public ResponseEntity<CountryResponseStatus> create(@RequestBody Country country) {

        final CountryResponseStatus countryResponseStatus = countryService.create(country.getName(),
                country.getIsoName(),
                country.getPhonePrefix());

        if (countryResponseStatus.isSuccess())
            return new ResponseEntity<>(countryResponseStatus, HttpStatus.OK);
        else
            return new ResponseEntity<>(countryResponseStatus, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/countryData/getAll", method = RequestMethod.POST)
    public ResponseEntity<List<Country>> getAllCountries(HttpServletRequest request, HttpServletResponse response) {
        List<Country> countries = countryService.getAllCountries();

        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @RequestMapping(value = "/countryData/getCountriesByPartialCountryName", method = RequestMethod.POST)
    public ResponseEntity<List<Country>> getCountriesByPartialCountryName(@RequestBody CountryDto country, HttpServletRequest request) {
        List<Country> countries = countryService.getCountriesByPartialName(country.getName());

        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @RequestMapping(value = "/countryData/getCountriesByInternationalCountryCode", method = RequestMethod.POST)
    public ResponseEntity<List<Country>> getCountriesByInternationalCountryCode(@RequestBody CountryDto country, HttpServletRequest request) {
        List<Country> countries = countryService.getCountriesByPhonePrefix(country.getPhonePrefix());

        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

}
