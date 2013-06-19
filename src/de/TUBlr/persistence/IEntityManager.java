package de.TUBlr.persistence;

import java.util.List;

public interface IEntityManager {

	public IEntity find(String key,Class<?> classValue);
	public List<IEntity> findAll(Class<?> classValue);
	public void persist(IEntity entity);
	public void remove(IEntity entity);
	
}
