package com.advantage.online.store.dao.attribute;

import java.util.List;

import com.advantage.online.store.model.attribute.Attribute;
import com.advantage.online.store.model.category.Category;

public interface AttributeRepository {

	Attribute createAttribute(String name);
	int deleteAttribute(Attribute attribute);
	int deleteAttributes(Attribute... attributes);
	List<Attribute> getAllAttributes();
	Attribute getAttribute(Long id);
}