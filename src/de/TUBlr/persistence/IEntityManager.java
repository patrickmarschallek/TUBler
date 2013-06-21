package de.TUBlr.persistence;

import java.util.List;

public interface IEntityManager {

	public <T> T find(String key,Class<T> classValue);
	public <T> List<T> findAll(Class<T> classValue);
	public void persist(Object entity);
	public void remove(Object entity);
	
}
