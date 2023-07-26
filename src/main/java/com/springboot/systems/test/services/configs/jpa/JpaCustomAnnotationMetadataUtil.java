package com.springboot.systems.test.services.configs.jpa;

import com.googlecode.genericdao.search.Metadata;
import com.googlecode.genericdao.search.MetadataUtil;

import javax.persistence.Entity;
import java.io.Serializable;

public class JpaCustomAnnotationMetadataUtil implements MetadataUtil {
	public Metadata get(final Class<?> klass) throws IllegalArgumentException {
		return JpaCustomAnnotationMetadata.getMetadata(klass);
	}

	public Metadata get(final Class<?> rootEntityClass, final String propertyPath) throws IllegalArgumentException {
		Metadata md = this.get(rootEntityClass);
		if (propertyPath == null || propertyPath.equals("")) {
			return md;
		}
		for (final String prop : propertyPath.split("\\.")) {
//			System.out.println("Property path " + propertyPath);
			if ("id".equals(prop)) {
				md = md.getIdType();
			} else {
				md = md.getPropertyType(prop);
			}
			if (md == null) {
				throw new IllegalArgumentException(
						"Property path '" + propertyPath + "' invalid for type " + rootEntityClass.getName());
			}
		}
		return md;
	}

	public Serializable getId(final Object object) {
		final Metadata md = this.get(object.getClass());
		return md.getIdValue(object);
	}

	public boolean isId(final Class<?> rootClass, final String propertyPath) {
		if (propertyPath == null || "".equals(propertyPath)) {
			return false;
		}
		// with hibernate, "id" always refers to the id property, no matter what
		// that property is named. just make sure the segment before this "id"
		// refers to an entity since only entities have ids.
		if (propertyPath.equals("id") || (propertyPath.endsWith(".id")
				&& this.get(rootClass, propertyPath.substring(0, propertyPath.length() - 3)).isEntity())) {
			return true;
		}
		// see if the property is the identifier property of the entity it
		// belongs to.
		final int pos = propertyPath.lastIndexOf(".");
		if (pos != -1) {
			final Metadata parentType = this.get(rootClass, propertyPath.substring(0, pos));
			return parentType.isEntity() && propertyPath.substring(pos + 1).equals(parentType.getIdProperty());
		}
		return propertyPath.equals(this.get(rootClass).getIdProperty());
	}

	@SuppressWarnings("unchecked")
	public <T> Class<T> getUnproxiedClass(Class<?> klass) {
		while (klass.getAnnotation(Entity.class) == null) {
			klass = klass.getSuperclass();
			if (Object.class.equals(klass)) {
				return null;
			}
		}
		return (Class<T>) klass;
	}

	public <T> Class<T> getUnproxiedClass(final Object entity) {
		return this.getUnproxiedClass(entity.getClass());
	}
}
