package de.TUBlr.persistence;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class EntityManager implements IEntityManager {

	private DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	private IEntity mapTo(Entity dataStoreEntity) {
		try {
			Object obj = Class.forName(dataStoreEntity.getKind()).newInstance();
			if (obj instanceof IEntity) {
				IEntity entity = (IEntity) obj;
				Field[] fields = entity.getClass().getDeclaredFields();
				for (Field field : fields) {
					Object value = dataStoreEntity.getProperty(field.getName());

					Method setter = entity.getClass().getMethod(
							"set" + field.getName(), field.getClass());
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

	private Entity mapTo(IEntity entity) {
		String kind = entity.getClass().toString();
		Entity dataStoreEntity = new Entity(kind);
		Field[] fields = entity.getClass().getDeclaredFields();
		for (Field field : fields) {
			Method getter;
			try {
				getter = entity.getClass().getMethod("get" + field.getName(),
						null);
				Object value = getter.invoke(entity, null);
				dataStoreEntity.setProperty(field.getName(), value);
			} catch (NoSuchMethodException | SecurityException
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dataStoreEntity;

	}

	@Override
	public IEntity find(String key, Class<?> classValue) {
		FilterPredicate filter = new FilterPredicate("key",
				FilterOperator.EQUAL, key);
		;
		Query query = new Query(classValue.toString());
		query.setFilter(filter);
		PreparedQuery pq = datastore.prepare(query);
		return this.mapTo(pq.asSingleEntity());
	}

	@Override
	public List<IEntity> findAll(Class<?> classValue) {
		List<IEntity> result = new ArrayList<IEntity>();
		Query query = new Query(classValue.toString());
		PreparedQuery pq = datastore.prepare(query);
		Iterable<Entity> iterator = pq.asIterable();
		for(Entity dataStoreEntity:iterator){
			result.add(this.mapTo(dataStoreEntity));
		}
		return result;
	}

	@Override
	public void persist(IEntity entity) {
		Entity dataStoreEntity = this.mapTo(entity);
		this.datastore.put(dataStoreEntity);

	}

	@Override
	public void remove(IEntity entity) {
		// TODO Auto-generated method stub
	}

}
