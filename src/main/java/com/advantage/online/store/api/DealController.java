package com.advantage.online.store.api;

import com.advantage.online.store.Constants;
import com.advantage.online.store.model.Category;
import com.advantage.online.store.model.Deal;
import com.advantage.online.store.services.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = Constants.URI_API)
public class DealController {

    @Autowired
    private DealService dealService;

    @RequestMapping(value = "deals", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllDeals(final HttpServletRequest request,
     final HttpServletResponse response) {

        final List<Deal> deals = dealService.getAllDeals();
        return new ResponseEntity<Object>(deals, HttpStatus.OK);
    }

    @RequestMapping(value = "dealOfDay", method = RequestMethod.GET)
    public ResponseEntity<Object> getDealOfTheDay(final HttpServletRequest request,
     final HttpServletResponse response) {

        final Deal dealOfTheDay = dealService.getDealOfTheDay();
        return new ResponseEntity<Object>(dealOfTheDay, HttpStatus.OK);
    }
}