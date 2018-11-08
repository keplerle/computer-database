package com.excilys.cdb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.exception.DataBaseException;
import com.excilys.cdb.model.Company;

@Repository
public class CompanyDAO extends JDBCManager implements CompanyDAOInterface<Company> {
	Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	private final static String QUERY_SELECT_ALL = "SELECT id,name FROM company";
	private final static String QUERY_DELETE = "DELETE FROM company WHERE id= ?";
	private static Connection connect;

	private CompanyDAO() {
		super();
	}

	@Override
	public ArrayList<Company> findAll() throws IOException, DataBaseException {

		CompanyDAO.connect = JDBCManager.getConnexion();

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
			throw new DataBaseException("Erreur dû à la base de données");
		} finally {
			try {
				CompanyDAO.connect.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new DataBaseException("Erreur dû à la base de données");
			}
		}

		return list;
	}

	@Override
	public void delete(int id) throws IOException, DataBaseException {
		CompanyDAO.connect = JDBCManager.getConnexion();
		try (PreparedStatement preparedStatement = CompanyDAO.connect.prepareStatement(QUERY_DELETE)) {
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DataBaseException("Erreur interne à la base de données");
		} finally {
			try {
				CompanyDAO.connect.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new DataBaseException("Echec de la fermeture de la connexion à la BDD");
			}
		}
	}
}
