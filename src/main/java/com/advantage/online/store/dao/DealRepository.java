package com.advantage.online.store.dao;

import com.advantage.online.store.model.Deal;

import java.util.List;

public interface DealRepository {

    List<Deal> getAllDeals();
    Deal getDealOfTheDay();
}