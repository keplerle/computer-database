package com.excilys.cdb.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.excilys.cdb.exception.DateException;

public interface ComputerDAOInterface<T> {

	/**
	 * Méthode de création
	 * 
	 * @param obj
	 * @return boolean
	 * @throws SQLException
	 * @throws DateException
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public abstract boolean create(T obj) throws SQLException, DateException, FileNotFoundException, IOException;

	/**
	 * Méthode pour effacer
	 * 
	 * @param obj
	 * @return boolean
	 * @throws SQLException
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public abstract boolean delete(int id) throws SQLException, FileNotFoundException, IOException;

	/**
	 * Méthode de mise à jour
	 * 
	 * @param obj
	 * @return boolean
	 * @throws SQLException
	 * @throws DateException
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public abstract boolean update(T obj) throws SQLException, DateException, FileNotFoundException, IOException;

	/**
	 * Méthode de recherche des informations par id
	 * 
	 * @param id
	 * @return T
	 * @throws SQLException
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public abstract T find(int id) throws SQLException, FileNotFoundException, IOException;

	/**
	 * Méthode de recherche des informations par nom
	 * 
	 * @param name
	 * @return T
	 * @throws SQLException
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public abstract T find(String name) throws SQLException, FileNotFoundException, IOException;

	/**
	 * Méthode de recherche des informations
	 * 
	 * @return ArrayList<T>
	 * @throws SQLException
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public abstract List<T> findAll() throws SQLException, FileNotFoundException, IOException;

}
