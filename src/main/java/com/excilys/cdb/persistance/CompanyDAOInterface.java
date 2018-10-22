package com.excilys.cdb.persistance;

import java.sql.SQLException;
import java.util.List;

public interface CompanyDAOInterface<T> {

	/**
	 * Méthode de recherche des informations
	 * 
	 * @return ArrayList<T>
	 * @throws SQLException
	 */
	public abstract List<T> findAll() throws SQLException;

}
