package de.TUBlr.persistence;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class EntityManager implements IEntityManager {

	private DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	private <T> T mapTo(Entity dataStoreEntity, Class<T> classValue) {
		try {
			Object obj = Class.forName(dataStoreEntity.getKind()).newInstance();
			if (obj.getClass().equals(classValue)) {
				T entity = (T) obj;
				Field[] fields = entity.getClass().getDeclaredFields();
				for (Field field : fields) {
					String propertyName = field.getName();
					Object value = dataStoreEntity.getProperty(propertyName);
					String property = field.getName().substring(0, 1)
							.toUpperCase()
							+ field.getName().substring(1);
					Method setter = entity.getClass().getMethod(
							"set" + property, field.getType());
					setter.invoke(entity, value);

				}
				return entity;
			} else {
				throw new IllegalArgumentException("Illegal kind of Object "
						+ obj + " it doesn't implements IEntity");
			}
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| InstantiationException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private <T> Entity mapTo(T entity) {
		String kind = entity.getClass().getName();
		Entity dataStoreEntity;
		Field[] fields = entity.getClass().getDeclaredFields();
		HashMap<String, Object> values = new HashMap<String, Object>();
		Class<?> parentClass = null;
		String ancestor = "";
		for (Field field : fields) {
			Method getter;
			String propertyName = field.getName();
			try {
				propertyName = propertyName.substring(0, 1).toUpperCase()
						+ propertyName.substring(1);
				getter = entity.getClass()
						.getMethod("get" + propertyName, null);
				Object value = getter.invoke(entity, null);
				values.put(field.getName(), value);
				if (field.isAnnotationPresent(Ancestor.class)) {
					ancestor = field.getName();
					parentClass = field.getAnnotation(Ancestor.class)
							.reference();
				}
			} catch (NoSuchMethodException | SecurityException
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Initialize with ancestor if it is possible
		if (ancestor.isEmpty()) {
			dataStoreEntity = new Entity(kind);
		} else {
			String keyValue = (String) values.get(ancestor);
			Key parentKey = KeyFactory.createKey(parentClass.getName(), "key");
			Key key = KeyFactory.createKey(parentKey, kind, ancestor);
			dataStoreEntity = new Entity(kind, key);
		}

		for (String propertyName : values.keySet()) {
			dataStoreEntity.setProperty(propertyName, values.get(propertyName));
		}

		return dataStoreEntity;

	}

	@Override
	public <T> T find(String key, Class<T> classValue) {
		FilterPredicate filter = new FilterPredicate("key",
				FilterOperator.EQUAL, key);
		;
		Query query = new Query(classValue.getName());
		query.setFilter(filter);
		PreparedQuery pq = datastore.prepare(query);
		return this.mapTo(pq.asSingleEntity(), classValue);
	}

	@Override
	public <T> List<T> findAll(Class<T> classValue) {
		List<T> result = new ArrayList<T>();
		Query query = new Query(classValue.getName());
		PreparedQuery pq = datastore.prepare(query);
		Iterable<Entity> iterator = pq.asIterable();
		for (Entity dataStoreEntity : iterator) {
			if (dataStoreEntity != null) {
				result.add(this.mapTo(dataStoreEntity, classValue));
			}
		}
		return result;
	}

	@Override
	public void persist(Object entity) {
		Entity dataStoreEntity = this.mapTo(entity);
		this.datastore.put(dataStoreEntity);
	}

	@Override
	public void remove(Object entity) {
		Entity dataStoreEntity = this.mapTo(entity);
		this.datastore.delete(dataStoreEntity.getKey());
	}

	private Map<String, Object> getAncestor(Class<?> classValue) {
		Map<String, Object> property = new HashMap<String, Object>();
		Field[] fields = classValue.getDeclaredFields();
		Class<?> parentClass = null;
		for (Field field : fields) {
			if (field.isAnnotationPresent(Ancestor.class)) {
				parentClass = field.getAnnotation(Ancestor.class).reference();
			}
		}
		return property;
	}
}
