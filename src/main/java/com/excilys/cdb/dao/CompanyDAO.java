package com.excilys.cdb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.DataBaseException;
import com.excilys.cdb.model.Company;

public class CompanyDAO implements CompanyDAOInterface<Company> {
	Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	private final static String QUERY_SELECT_ALL = "SELECT id,name FROM company";
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
	public ArrayList<Company> findAll() throws IOException, DataBaseException {
		CompanyDAO.connect = JDBCManager.connectionDB();
		ArrayList<Company> list = new ArrayList<>();

		try (PreparedStatement preparedStatement = CompanyDAO.connect.prepareStatement(QUERY_SELECT_ALL)) {
			CompanyDAO.connect.setAutoCommit(false);
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				Company company = new Company(result.getInt("id"), result.getString("name"));
				list.add(company);
			}
			result.close();
			CompanyDAO.connect.commit();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DataBaseException();
		} finally {
			try {
				CompanyDAO.connect.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new DataBaseException();
			}
		}

		return list;
	}

}
