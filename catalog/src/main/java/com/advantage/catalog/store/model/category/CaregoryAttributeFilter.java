package com.advantage.catalog.store.model.category;

import javax.persistence.*;

/**
 * Created by ostrovsm on 31/01/2016.
 */

@Entity
@Table(name = "caregory_attribute_filter")
@IdClass(CategoryAttributeFilterPK.class)

public class CaregoryAttributeFilter {

    @Id
    @Column(name = FIELD_CATEGORY_ID)
    private long categoryId;

    @Id
    @Column(name = FIELD_ATTRIBUTE_NUMBER)
    private long attributeId;

    @Column(name = FIELD_ORDER_TIMESTAMP)
    private boolean inFilter;
}
