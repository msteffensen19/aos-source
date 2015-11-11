package com.advantage.online.store.dao;

import com.advantage.online.store.model.Deal;
import com.advantage.online.store.model.DealType;
import com.advantage.online.store.model.Product;

import java.util.List;

public interface DealRepository {

	Deal createDeal(DealType dealType, String name, String description, double discount,
			String dateFrom, String dateTo, Product product, String managedImageId);
	int deleteDeal(Deal deal);
    List<Deal> getAllDeals();
    Deal getDealOfTheDay();
}