package com.advantage.online.store.dao.deal;

import com.advantage.online.store.dao.DefaultCRUDOperations;
import com.advantage.online.store.model.Deal;
import com.advantage.online.store.model.DealType;
import com.advantage.online.store.model.product.Product;

import java.util.List;

public interface DealRepository  extends DefaultCRUDOperations<Deal> {
    /**
     * Create Deal entity
     * @param dealType
     * @param description
     * @param promotionHeader
     * @param promotionSubHeader
     * @param staringPrice
     * @param managedImageId
     * @param discount
     * @param dateFrom
     * @param dateTo
     * @param product
     * @return entity reference
     */
	Deal create(DealType dealType, String description, String promotionHeader,
                    String  promotionSubHeader, String staringPrice, String managedImageId,
                    double discount, String dateFrom, String dateTo,
                    Product product);

    /**
     * Get deal of the day
     * @return entity reference
     */
    Deal getDealOfTheDay();
}