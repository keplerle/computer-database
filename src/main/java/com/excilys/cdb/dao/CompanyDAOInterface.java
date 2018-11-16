package com.excilys.cdb.dao;

import java.io.IOException;
import java.util.List;

import com.excilys.cdb.exception.DataBaseException;

public interface CompanyDAOInterface<T> {

	/**
	 * Méthode de recherche des informations
	 * 
	 * @return ArrayList<T>
	 * @throws IOException 
	 * @throws DataBaseException 
	 */
	public abstract List<T> findAll() throws IOException, DataBaseException;

	/**
	 * Méthode pour effacer par id
	 * 
	 * @param id
	 * @throws IOException 
	 * @throws DataBaseException 
	 */
	public abstract void delete(long id) throws IOException, DataBaseException;
	
}
