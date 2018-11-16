package com.excilys.cdb.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.exception.DataBaseException;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.model.Computer;

public interface ComputerDAOInterface<T> {

	/**
	 * Méthode de création
	 * 
	 * @param obj
	 * @throws DataException
	 * @throws IOException 
	 * @throws DataBaseException 
	 */
	public abstract void create(T obj) throws DataException, IOException, DataBaseException;

	/**
	 * Méthode pour effacer par id
	 * 
	 * @param id
	 * @throws IOException 
	 * @throws DataBaseException 
	 */
	public abstract void delete(int id) throws IOException, DataBaseException;
	
	/**
	 * Méthode pour effacer par company_id
	 * 
	 * @param id
	 * @throws IOException 
	 * @throws DataBaseException 
	 */
	public abstract void deleteByCompany(int id) throws IOException, DataBaseException;

	/**
	 * Méthode de mise à jour
	 * 
	 * @param obj
	 * @throws DataException
	 * @throws IOException 
	 * @throws DataBaseException 
	 */
	public abstract void update(T obj) throws DataException, IOException, DataBaseException;

	/**
	 * Méthode de recherche des informations par id
	 * 
	 * @param id
	 * @return T
	 * @throws IOException 
	 * @throws DataBaseException 
	 */
	public abstract Optional<Computer> find(long id) throws IOException, DataBaseException;

	/**
	 * Méthode de recherche des informations
	 * 
	 * @param name
	 * @return ArrayList<T>
	 * @throws IOException 
	 * @throws DataBaseException 
	 */
	public abstract List<T> findAll(String name, int page, int size) throws IOException, DataBaseException;

	/**
	 * Méthode pour compter le nombre total de données
	 * 
	 * @param name
	 * @return int
	 * @throws IOException 
	 * @throws DataBaseException 
	 */
	public abstract long count(String name) throws IOException, DataBaseException;


}
