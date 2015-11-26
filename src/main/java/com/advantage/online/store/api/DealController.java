package com.advantage.online.store.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.advantage.online.store.Constants;
import com.advantage.online.store.model.deal.Deal;
import com.advantage.online.store.services.DealService;
import com.advantage.util.ArgumentValidationHelper;

@RestController
@RequestMapping(value = Constants.URI_API)
public class DealController {

    @Autowired
    private DealService dealService;

    @RequestMapping(value = "deals", method = RequestMethod.GET)
    public ResponseEntity<List<Deal>> getAllDeals(final HttpServletRequest request,
     final HttpServletResponse response) {

    	ArgumentValidationHelper.validateArgumentIsNotNull(request, "http servlet request");
        ArgumentValidationHelper.validateArgumentIsNotNull(response, "http servlet response");
        final List<Deal> deals = dealService.getAllDeals();

        return new ResponseEntity<>(deals, HttpStatus.OK);
    }

    @RequestMapping(value = "dealOfDay", method = RequestMethod.GET)
    public ResponseEntity<Deal> getDealOfTheDay(final HttpServletRequest request,
     final HttpServletResponse response) {
    	ArgumentValidationHelper.validateArgumentIsNotNull(request, "http servlet request");
        ArgumentValidationHelper.validateArgumentIsNotNull(response, "http servlet response");
        final Deal dealOfTheDay = dealService.getDealOfTheDay();

        return new ResponseEntity<>(dealOfTheDay, HttpStatus.OK);
    }
}