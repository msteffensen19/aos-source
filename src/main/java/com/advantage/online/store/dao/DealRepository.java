package com.advantage.online.store.dao;

import com.advantage.online.store.model.Deal;
import com.advantage.online.store.model.DealType;
import com.advantage.online.store.model.product.Product;

import java.util.List;

public interface DealRepository {

	Deal createDeal(DealType dealType, String description, String promotionHeader,
                    String  promotionSubHeader, String staringPrice, String managedImageId, Product product);
	int deleteDeal(Deal deal);
    List<Deal> getAllDeals();
    Deal getDealOfTheDay();
}