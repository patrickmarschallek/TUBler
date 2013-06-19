package de.TUBlr.persistence;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class EntityManager implements IEntityManager{

	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	private IEntity mapTo(com.google.appengine.api.datastore.Entity dataStoreEntity) {
		return null;
	}
	
	private com.google.appengine.api.datastore.Entity mapTo(IEntity entity) {
		return null;
	}
	
	@Override
	public IEntity find(String key, Class<?> classValue) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<IEntity> findAll(Class<?> classValue) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void persist(IEntity entity) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void remove(IEntity entity) {
		// TODO Auto-generated method stub
		
	}

}
