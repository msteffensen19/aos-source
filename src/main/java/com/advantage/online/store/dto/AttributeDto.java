package com.advantage.online.store.dto;

import java.util.*;

import com.advantage.online.store.model.attribute.Attribute;
//import com.advantage.online.store.model.attribute.EntityAttribute;
import com.advantage.util.ArgumentValidationHelper;

public class AttributeDto {

    private String attributeName;
    private Set<String> attributeValues;

	public AttributeDto() {
	}

	public AttributeDto(String attributeName, Set<String> attributeValues) {
        this.attributeName = attributeName;
        this.attributeValues = attributeValues;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

	public Set<String> getAttributeValues() {
		return attributeValues;
	}

	public void setAttributeValues(Set<String> attributeValues) {
		this.attributeValues = attributeValues;
	}

	/*public static List<AttributeDto> toAttributeDtos(final List<EntityAttribute> entityAttributes) {

		ArgumentValidationHelper.validateArgumentIsNotNull(entityAttributes,
				                                           "entity attributes");
		final int entityAttributesCount = entityAttributes.size();
		final Map<Long, AttributeDto> attributeDtosMap = new HashMap<Long, AttributeDto>(entityAttributesCount);
		
		for (final EntityAttribute entityAttribute : entityAttributes) {

			final Attribute attribute = entityAttribute.getAttribute();
			final Long attributeTitleId = attribute.getId();
			final AttributeDto attributeDto;

			if (attributeDtosMap.containsKey(attributeTitleId)) {

				attributeDto = attributeDtosMap.get(attributeTitleId);
			} else {

				attributeDto = new AttributeDto();
				attributeDtosMap.put(attributeTitleId, attributeDto);
				attributeDto.setAttributeTitleId(attributeTitleId);
				attributeDto.setName(attribute.getName());
			}

			attributeDto.addValue(entityAttribute.getValue());
		}

		return new ArrayList<AttributeDto>(attributeDtosMap.values());
	}*/
}