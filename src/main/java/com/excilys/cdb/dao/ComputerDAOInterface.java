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
	 * @return boolean
	 * @throws DataException
	 * @throws IOException 
	 * @throws DataBaseException 
	 */
	public abstract void create(T obj) throws DataException, IOException, DataBaseException;

	/**
	 * Méthode pour effacer
	 * 
	 * @param obj
	 * @return boolean
	 * @throws IOException 
	 * @throws DataBaseException 
	 */
	public abstract void delete(int id) throws IOException, DataBaseException;

	/**
	 * Méthode de mise à jour
	 * 
	 * @param obj
	 * @return boolean
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
	public abstract Optional<Computer> find(int id) throws IOException, DataBaseException;

	/**
	 * Méthode de recherche des informations par nom
	 * 
	 * @param name
	 * @return ArrayList<T>
	 * @throws IOException 
	 * @throws DataBaseException 
	 */
	public abstract List<T> findAll(String name, int page, int size) throws IOException, DataBaseException;

	/**
	 * Méthode de recherche des informations
	 * 
	 * @return ArrayList<T>
	 * @throws IOException 
	 * @throws DataBaseException 
	 */
	public abstract List<T> findAll(int page, int size) throws IOException, DataBaseException;
	/**
	 * Méthode pour compter le nombre total de données
	 * 
	 * @param name
	 * @return int
	 * @throws IOException 
	 * @throws DataBaseException 
	 */
	int count(String name) throws IOException, DataBaseException;


}
