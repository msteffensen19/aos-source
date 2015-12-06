package com.advantage.online.store.user.api;

import com.advantage.online.store.Constants;
import com.advantage.online.store.user.dto.CountryDto;
import com.advantage.online.store.user.dto.CountryResponseStatus;
import com.advantage.online.store.user.model.Country;
import com.advantage.online.store.user.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/account/countries", method = RequestMethod.POST)
    public ResponseEntity<CountryResponseStatus> create(@RequestBody Country country) {

        final CountryResponseStatus countryResponseStatus = countryService.create(country.getName(),
                country.getIsoName(),
                country.getPhonePrefix());

        if (countryResponseStatus.isSuccess())
            return new ResponseEntity<>(countryResponseStatus, HttpStatus.OK);
        else
            return new ResponseEntity<>(countryResponseStatus, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/account/countries", method = RequestMethod.GET)
    public ResponseEntity<List<Country>> getAllCountries() {
        List<Country> countries = countryService.getAllCountries();

        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @RequestMapping(value = "/account/countries/from_name/{start_of_name}", method = RequestMethod.GET)
    public ResponseEntity<List<Country>> getCountriesByPartialCountryName(@PathVariable("start_of_name") String partialCountryName) {

        List<Country> countries = countryService.getCountriesByPartialName(partialCountryName);

        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @RequestMapping(value = "/account/countries/phone_prefix/{international_phone_prefix}", method = RequestMethod.GET)
    public ResponseEntity<List<Country>> getCountriesByInternationalCountryCode(@PathVariable("international_phone_prefix") Integer phonePrefix) {
        List<Country> countries = countryService.getCountriesByPhonePrefix(phonePrefix);

        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

}
