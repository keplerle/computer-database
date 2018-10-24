package com.excilys.cdb.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.cdb.model.Company;

public class CompanyDAO implements CompanyDAOInterface<Company> {

	private final static String QUERY_SELECT_ALL = "SELECT * FROM company";
	private static CompanyDAO companyDAO = new CompanyDAO();
	private static Connection connect;

	private CompanyDAO() {
		JDBCManager.getInstance();
		connect = JDBCManager.getConnection();
	}

	public static CompanyDAO getInstance() {
		return companyDAO;
	}

	@Override
	public ArrayList<Company> findAll() throws SQLException ,FileNotFoundException, IOException{
		CompanyDAO.connect = JDBCManager.connectionDB();
		ArrayList<Company> list = new ArrayList<>();
		try (PreparedStatement preparedStatement = CompanyDAO.connect.prepareStatement(QUERY_SELECT_ALL);
				ResultSet result = preparedStatement.executeQuery();) {
			while (result.next()) {
				Company company = new Company(result.getInt("id"), result.getString("name"));
				list.add(company);
			}
		}
		CompanyDAO.connect.close();
		return list;
	}

}
