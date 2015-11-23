package com.advantage.online.store.services;

import com.advantage.online.store.dao.deal.DealRepository;
import com.advantage.online.store.model.Deal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DealService {

    @Autowired
    @Qualifier("dealRepository")
    public DealRepository dealRepository;

    @Transactional(readOnly = true)
    public List<Deal> getAllDeals() {

        return dealRepository.getAll();
    }

    @Transactional(readOnly = true)
    public Deal getDealOfTheDay() {

        return dealRepository.getDealOfTheDay();
    }
}