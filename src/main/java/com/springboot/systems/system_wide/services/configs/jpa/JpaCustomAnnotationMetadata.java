package com.springboot.systems.system_wide.services.configs.jpa;

import com.googlecode.genericdao.search.Metadata;
import com.googlecode.genericdao.search.jpa.JPAAnnotationCollectionMetadata;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class JpaCustomAnnotationMetadata implements Metadata {
	Class<?> klass;
	static Map<Class<?>, JpaCustomAnnotationMetadata> metadataCache;
	Map<String, Property> props;

	private JpaCustomAnnotationMetadata(final Class<?> klass) {
		this.klass = klass;
	}

	public static <T> Metadata getMetadata(Type type) {
		Class<?> klass = null;
		Type[] typeArguments = null;
		if (type instanceof ParameterizedType) {
			typeArguments = ((ParameterizedType) type).getActualTypeArguments();
			type = ((ParameterizedType) type).getRawType();
		}
		if (!(type instanceof Class)) {
			throw new IllegalArgumentException("weirdness");
		}
		klass = (Class<?>) type;
		if (Collection.class.isAssignableFrom(klass)) {
			if (typeArguments == null || typeArguments.length != 1) {
				throw new IllegalArgumentException("weirdness again.");
			}
			if (!(typeArguments[0] instanceof Class)) {
				throw new IllegalArgumentException("weirdness a third time.");
			}
			return (Metadata) new JPAAnnotationCollectionMetadata((Class) typeArguments[0], (Class) klass);
		} else {
			if (klass.isArray()) {
				return (Metadata) new JPAAnnotationCollectionMetadata((Class) klass.getComponentType(), (Class) klass);
			}
			JpaCustomAnnotationMetadata md = JpaCustomAnnotationMetadata.metadataCache.get(klass);
			if (md == null) {
				md = new JpaCustomAnnotationMetadata(klass);
				JpaCustomAnnotationMetadata.metadataCache.put(klass, md);
			}
			return (Metadata) md;
		}
	}

	public Class<?> getCollectionClass() {
		return null;
	}

	public String getEntityName() {
		final Entity annotation = this.klass.getAnnotation(Entity.class);
		if (annotation == null) {
			throw new UnsupportedOperationException("Cannot get Entity Name of non-entity type.");
		}
		if (annotation.name() != null && !annotation.name().isEmpty()) {
			return annotation.name();
		}
		return this.klass.getSimpleName();
	}

	public String getIdProperty() {
		for (final Property prop : this.getProps().values()) {
			if (prop.hasAnnotation(Id.class) || prop.hasAnnotation(EmbeddedId.class)) {
				return prop.name;
			}
		}
		return null;
	}

	public Metadata getIdType() {
		final String idProp = this.getIdProperty();
		if (idProp != null) {
			return this.getPropertyType(idProp);
		}
		return null;
	}

	public Serializable getIdValue(final Object object) {
		final String idProp = this.getIdProperty();
		if (idProp != null) {
			return (Serializable) this.getPropertyValue(object, idProp);
		}
		return null;
	}

	public Class<?> getJavaClass() {
		return this.klass;
	}

	public String[] getProperties() {
		final String[] array = new String[this.getProps().size()];
		int i = 0;
		for (final String prop : this.getProps().keySet()) {
			array[i++] = prop;
		}
		return array;
	}

	public Metadata getPropertyType(final String property) {
		final Property prop = this.getProps().get(property);
		if (prop == null) {
			return null;
		}
		return getMetadata(prop.getType());
	}

	public Object getPropertyValue(final Object object, final String property) {
		final Property prop = this.getProps().get(property);
		if (prop == null) {
			return null;
		}
		return prop.getValue(object);
	}

	public boolean isCollection() {
		return false;
	}

	public boolean isEmeddable() {
		return null != this.klass.getAnnotation(Embeddable.class);
	}

	public boolean isEntity() {
		return null != this.klass.getAnnotation(Entity.class);
	}

	public boolean isNumeric() {
		return Number.class.isAssignableFrom(this.klass);
	}

	public boolean isString() {
		return String.class.equals(this.klass);
	}

	public synchronized Map<String, Property> getProps() {
		if (this.props != null) {
			return this.props;
		}
		this.props = new TreeMap<String, Property>();
		if (!this.isEntity() && !this.isEmeddable()) {
			return this.props;
		}
		for (final Field field : this.klass.getFields()) {
			if (null != field.getDeclaringClass().getAnnotation(Entity.class)
					|| null != field.getDeclaringClass().getAnnotation(Embeddable.class)
					|| null != field.getDeclaringClass().getAnnotation(MappedSuperclass.class)) {
				this.props.put(field.getName(), new Property(field));
			}
		}
		for (final Method method : this.klass.getMethods()) {
			final String[] name = getterName(method);
			if (name != null && (null != method.getDeclaringClass().getAnnotation(Entity.class)
					|| null != method.getDeclaringClass().getAnnotation(Embeddable.class)
					|| null != method.getDeclaringClass().getAnnotation(MappedSuperclass.class))) {
				Property property = this.props.get(name[0]);
				if (property == null) {
					property = new Property(name[0]);
					this.props.put(name[0], property);
				}
				property.getter = method;
				if (property.field == null) {
					try {
						property.field = this.klass.getDeclaredField(name[0]);
						if (!property.field.getGenericType().equals(property.getter.getGenericReturnType())) {
							property.field = null;
						}
					} catch (SecurityException ex) {
					} catch (NoSuchFieldException ex2) {
					}
				}
				try {
					property.setter = this.klass.getMethod("set" + name[1], method.getReturnType());
				} catch (SecurityException ex3) {
				} catch (NoSuchMethodException ex4) {
				}
			}
		}
		final Iterator<Map.Entry<String, Property>> iterator = this.props.entrySet().iterator();
		while (iterator.hasNext()) {
			final Property property2 = iterator.next().getValue();
			if (property2.hasAnnotation(Transient.class)) {
				iterator.remove();
			}
		}
		return this.props;
	}

	public static String[] getterName(final Method method) {
		if (method.getParameterTypes().length != 0 || method.getReturnType() == null) {
			return null;
		}
		String name = method.getName();
		if (name.length() > 3 && name.startsWith("get")) {
			name = name.substring(3);
		} else {
			if (name.length() <= 2
					|| (!method.getReturnType().equals(Boolean.class) && !method.getReturnType().equals(Boolean.TYPE))
					|| !name.startsWith("is")) {
				return null;
			}
			name = name.substring(2);
		}
		if (Character.isUpperCase(name.charAt(0))) {
			return new String[] { name.substring(0, 1).toLowerCase() + name.substring(1), name };
		}
		return null;
	}

	static {
		JpaCustomAnnotationMetadata.metadataCache = new HashMap<Class<?>, JpaCustomAnnotationMetadata>();
	}

	private static class Property {
		String name;
		Field field;
		Method getter;
		Method setter;

		public Property(final String name) {
			this.name = name;
		}

		public Property(final Field field) {
			this.name = field.getName();
			this.field = field;
		}

		public Type getType() {
			if (this.getter != null) {
				return this.getter.getGenericReturnType();
			}
			if (this.field != null) {
				return this.field.getGenericType();
			}
			if (this.setter != null) {
				return this.setter.getGenericParameterTypes()[0];
			}
			return null;
		}

		public Object getValue(final Object o) {
			try {
				if (this.getter != null) {
					return this.getter.invoke(o, new Object[0]);
				}
				if (this.field != null) {
					return this.field.get(o);
				}
			} catch (IllegalArgumentException e) {
				throw new RuntimeException("Unexpected error getting value of property");
			} catch (IllegalAccessException e2) {
				throw new RuntimeException("Unexpected error getting value of property");
			} catch (InvocationTargetException e3) {
				throw new RuntimeException("Unexpected error getting value of property");
			}
			return null;
		}

		public <T extends Annotation> boolean hasAnnotation(final Class<T> annotationClass) {
			return this.getAnnotation(annotationClass) != null;
		}

		public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
			T val = null;
			if (this.getter != null) {
				val = this.getter.getAnnotation(annotationClass);
			}
			if (val == null && this.field != null) {
				val = this.field.getAnnotation(annotationClass);
			}
			if (val == null && this.setter != null) {
				val = this.setter.getAnnotation(annotationClass);
			}
			return val;
		}
	}
}
