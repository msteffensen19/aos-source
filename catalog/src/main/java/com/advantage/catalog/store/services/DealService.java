package com.advantage.catalog.store.services;

import com.advantage.catalog.store.dao.deal.DealRepository;
import com.advantage.catalog.store.model.deal.Deal;
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

    public Deal getDealOfTheDay(Long categoryId) {

        return dealRepository.getDealOfTheDay(categoryId);
    }
}