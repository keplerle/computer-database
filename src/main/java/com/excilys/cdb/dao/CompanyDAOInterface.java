package com.excilys.cdb.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface CompanyDAOInterface<T> {

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
