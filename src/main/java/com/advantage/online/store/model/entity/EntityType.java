package com.advantage.online.store.model.entity;

public enum EntityType {

	CATEGORY (Integer.valueOf(10)),
	PRODUCT (Integer.valueOf(20)),
	DEAL (Integer.valueOf(30)),
	ATTRIBUTE_TITLE (Integer.valueOf(40));

	private Integer entityTypeCode;

	private EntityType(final Integer entityTypeCode) {

		this.entityTypeCode = entityTypeCode;
	}

	public Integer getEntityTypeCode() {

		return entityTypeCode;
	}
}