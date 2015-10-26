package com.advantage.util;

/**
 * A helper class for JPA queries.
 */
public abstract class JPAQueryHelper {

	private JPAQueryHelper() {

		throw new UnsupportedOperationException();
	}

	/**
	 * Get a HQL query for a deletion of a JPA entity, by it's primary key field.
	 * @param entityClass the class of the JPA entity to delete.
	 * @param pkFieldName the name of the primary key field, of the entity to delete.
	 * @param pkFieldValue (the value of) the primary key, of the entity to delete.
	 * @return a HQL string.
	 */
	public static String getDeleteByPkFieldQuery(final Class<?> entityClass,
	 final String pkFieldName, final Object pkFieldValue) {

		ArgumentValidationHelper.validateArgumentIsNotNull(entityClass, "entity class");
		ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(pkFieldName,
				                                                            "pk field name");
		ArgumentValidationHelper.validateArgumentIsNotNull(pkFieldValue, "pk field value");
		final StringBuilder hql = new StringBuilder("DELETE FROM ");
		final String entityClassName = entityClass.getName();
		hql.append(entityClassName);
		hql.append(" WHERE ");
		hql.append(pkFieldName);
		hql.append(" = ");
		hql.append(pkFieldValue);
		return hql.toString();
	}
}