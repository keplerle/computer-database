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
	
}
