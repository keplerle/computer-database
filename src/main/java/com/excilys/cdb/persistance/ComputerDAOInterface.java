package com.excilys.cdb.persistance;

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
	 */
	public abstract boolean create(T obj) throws SQLException, DateException;

	/**
	 * Méthode pour effacer
	 * 
	 * @param obj
	 * @return boolean
	 * @throws SQLException
	 */
	public abstract boolean delete(int id) throws SQLException;

	/**
	 * Méthode de mise à jour
	 * 
	 * @param obj
	 * @return boolean
	 * @throws SQLException
	 * @throws DateException
	 */
	public abstract boolean update(T obj) throws SQLException, DateException;

	/**
	 * Méthode de recherche des informations par id
	 * 
	 * @param id
	 * @return T
	 * @throws SQLException
	 */
	public abstract T find(int id) throws SQLException;

	/**
	 * Méthode de recherche des informations par nom
	 * 
	 * @param name
	 * @return T
	 * @throws SQLException
	 */
	public abstract T find(String name) throws SQLException;

	/**
	 * Méthode de recherche des informations
	 * 
	 * @return ArrayList<T>
	 * @throws SQLException
	 */
	public abstract List<T> findAll() throws SQLException;

}
