package gr.teonapster.eres.rest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityParameterConverterProvider implements ParamConverterProvider {
	private final class EntityConverter<T> implements ParamConverter<T> {
		private final Class<T> rawType;

		private EntityConverter(Class<T> rawType) {
			this.rawType = rawType;
		}

		@Override
		public T fromString(String value) {
			if (value == null || value.length() == 0
					|| !value.matches("^\\d+$"))
				return null;
			return em.find(rawType, Integer.valueOf(value));
		}

		@Override
		public String toString(T value) {
			if (value == null)
				return null;
			Object identifier = em.getEntityManagerFactory()
					.getPersistenceUnitUtil().getIdentifier(value);
			return identifier == null ? null : identifier.toString();
		}
	}

	@Inject
	private EntityManager em;

	@Override
	public <T> ParamConverter<T> getConverter(final Class<T> rawType,
			Type genericType, Annotation[] annotations) {
		if (rawType.getAnnotation(Entity.class) == null
				|| rawType.getAnnotation(Entity.class) == null)
			return null;
		for (Annotation a : annotations) {
			if (Identified.class.isAssignableFrom(a.getClass()))
				return new EntityConverter<T>(rawType);
		}
		return null;
	}
}
