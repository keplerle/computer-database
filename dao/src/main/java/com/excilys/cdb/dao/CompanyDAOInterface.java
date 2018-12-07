package com.excilys.cdb.dao;

import java.util.List;
public interface CompanyDAOInterface<T> {

	/**
	 * Méthode de recherche des informations
	 * 
	 * @return ArrayList<T>
	 */
	public abstract List<T> findAll();

	/**
	 * Méthode pour effacer par id
	 * 
	 * @param id
	 */
	public abstract void delete(long id);
	
	
	/**
	 * Méthode de mise à jour
	 * 
	 * @param obj
	 */
	public abstract void update(T obj);
	
	/**
	 * Méthode de création
	 * 
	 * @param obj
	 */
	public abstract void create(T obj);
}
