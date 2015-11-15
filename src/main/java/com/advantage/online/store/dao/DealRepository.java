package com.advantage.online.store.dao;

import com.advantage.online.store.model.Deal;
import com.advantage.online.store.model.DealType;
import com.advantage.online.store.model.product.Product;

import java.util.List;

public interface DealRepository {

<<<<<<< HEAD
	Deal createDeal(DealType dealType, String name, String description, double discount,
			String dateFrom, String dateTo, Product product, String managedImageId);
=======
	Deal createDeal(DealType dealType, String description, String promotionHeader,
                    String  promotionSubHeader, String staringPrice, String managedImageId, Product product);
>>>>>>> e91b911e98c2286ef0a2c3026cb95336d9b8ce0f
	int deleteDeal(Deal deal);
    List<Deal> getAllDeals();
    Deal getDealOfTheDay();
}