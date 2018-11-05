package com.excilys.cdb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.DataBaseException;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerDAO implements ComputerDAOInterface<Computer> {

	private final static String QUERY_INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private final static String QUERY_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private final static String QUERY_DELETE = "DELETE FROM computer WHERE id= ?";
	private final static String QUERY_DELETE_COMPANY = "DELETE FROM computer WHERE company_id= ?";
	private final static String QUERY_SELECT_BY_NAME = "SELECT cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpu.company_id,cpa.name FROM computer AS cpu LEFT JOIN company AS cpa ON cpu.company_id = cpa.id WHERE UPPER(cpu.name) LIKE UPPER(?) OR UPPER(cpa.name) LIKE UPPER(?) ORDER BY cpu.name LIMIT ? OFFSET ?";
	private final static String QUERY_SELECT_BY_ID = "SELECT cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpu.company_id,cpa.name FROM computer AS cpu LEFT JOIN company AS cpa ON cpu.company_id = cpa.id WHERE cpu.id = ?";
	private final static String QUERY_COUNT = "SELECT COUNT(cpu.id)  FROM computer AS cpu LEFT JOIN company AS cpa ON cpu.company_id = cpa.id WHERE UPPER(cpu.name) LIKE UPPER(?) OR UPPER(cpa.name) LIKE UPPER(?) ";

	private static ComputerDAO computerDAO = new ComputerDAO();
	private static Connection connect;
	Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	private ComputerDAO() {
		JDBCManager.getInstance();
		connect = JDBCManager.getConnection();
	}

	public static ComputerDAO getInstance() {
		return computerDAO;
	}

	@Override
	public void create(Computer computer) throws DataException, IOException, DataBaseException {

		ComputerDAO.connect = JDBCManager.connectionDB();
		try (PreparedStatement preparedStatement = ComputerDAO.connect.prepareStatement(QUERY_INSERT)) {

			preparedStatement.setString(1, computer.getName());

			if (computer.getIntroduced() == null) {
				preparedStatement.setDate(2, null);
			} else {
				preparedStatement.setDate(2, Date.valueOf(computer.getIntroduced()));
			}

			if (computer.getDiscontinued() == null) {
				preparedStatement.setDate(3, null);
			} else {
				preparedStatement.setDate(3, Date.valueOf(computer.getDiscontinued()));
			}

			if (computer.getCompany().getId() != 0) {
				preparedStatement.setLong(4, computer.getCompany().getId());
			} else {
				preparedStatement.setBinaryStream(4, null);
			}

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DataBaseException("Erreur interne à la base de données");

		} finally {
			try {
				ComputerDAO.connect.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new DataBaseException("Echec de la fermeture de la connexion à la BDD");
			}
		}
	}

	@Override
	public void update(Computer computer) throws DataException, IOException, DataBaseException {

		ComputerDAO.connect = JDBCManager.connectionDB();
		try (PreparedStatement preparedStatement = ComputerDAO.connect.prepareStatement(QUERY_UPDATE);) {

			preparedStatement.setString(1, computer.getName());
			if (computer.getIntroduced() == null) {
				preparedStatement.setDate(2, null);
			} else {
				preparedStatement.setDate(2, Date.valueOf(computer.getIntroduced()));
			}

			if (computer.getDiscontinued() == null) {
				preparedStatement.setDate(3, null);
			} else {
				preparedStatement.setDate(3, Date.valueOf(computer.getDiscontinued()));
			}

			if (computer.getCompany().getId() != 0) {
				preparedStatement.setLong(4, computer.getCompany().getId());
			} else {
				preparedStatement.setBinaryStream(4, null);
			}
			preparedStatement.setLong(5, computer.getId());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DataBaseException("Erreur interne à la base de données");
		} finally {
			try {
				ComputerDAO.connect.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new DataBaseException("Echec de la fermeture de la connexion à la BDD");
			}
		}
	}

	@Override
	public void delete(int id) throws IOException, DataBaseException {
		ComputerDAO.connect = JDBCManager.connectionDB();
		try (PreparedStatement preparedStatement = ComputerDAO.connect.prepareStatement(QUERY_DELETE);) {

			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DataBaseException("Erreur interne à la base de données");
		} finally {
			try {
				ComputerDAO.connect.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new DataBaseException("Echec de la fermeture de la connexion à la BDD");
			}
		}
	}

	@Override
	public Optional<Computer> find(int id) throws IOException, DataBaseException {
		ComputerDAO.connect = JDBCManager.connectionDB();
		Computer computer = null;
		try (PreparedStatement preparedStatement = ComputerDAO.connect.prepareStatement(QUERY_SELECT_BY_ID)) {
	
			preparedStatement.setLong(1, id);
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				computer = new Computer(result.getInt("id"), result.getString("cpu.name"));
				if (result.getDate("introduced") != null) {
					computer.setIntroduced(result.getDate("introduced").toLocalDate());
				}
				if (result.getDate("discontinued") != null) {
					computer.setDiscontinued(result.getDate("discontinued").toLocalDate());
				}
				computer.setCompany(new Company());
				if (result.getInt("company_id") != 0) {
					computer.getCompany().setId(result.getInt("company_id"));
					computer.getCompany().setName(result.getString("cpa.name"));
				}
			}
			result.close();
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DataBaseException("Erreur interne à la base de données");
		} finally {
			try {
				ComputerDAO.connect.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new DataBaseException("Echec de la fermeture de la connexion à la BDD");
			}
		}
		return Optional.ofNullable(computer);
	}

	@Override
	public ArrayList<Computer> findAll(String name, int page, int size) throws IOException, DataBaseException {
		ComputerDAO.connect = JDBCManager.connectionDB();
		ArrayList<Computer> list = new ArrayList<>();
		try (PreparedStatement preparedStatement = ComputerDAO.connect.prepareStatement(QUERY_SELECT_BY_NAME)) {
			
			preparedStatement.setNString(1, "%" + name + "%");
			preparedStatement.setNString(2, "%" + name + "%");
			preparedStatement.setInt(3, size);
			preparedStatement.setInt(4, (page - 1) * size);
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				Computer computer = new Computer(result.getInt("id"), result.getString("name"));
				if (result.getDate("introduced") != null) {
					computer.setIntroduced(result.getDate("introduced").toLocalDate());
				}
				if (result.getDate("discontinued") != null) {
					computer.setDiscontinued(result.getDate("discontinued").toLocalDate());
				}
				computer.setCompany(new Company());
				if (result.getInt("company_id") != 0) {
					computer.getCompany().setId(result.getInt("company_id"));
					computer.getCompany().setName(result.getString("cpa.name"));
				}
				list.add(computer);
			}
			result.close();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DataBaseException("Erreur interne à la base de données");
		} finally {
			try {
				ComputerDAO.connect.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new DataBaseException("Echec de la fermeture de la connexion à la BDD");
			}
		}
		return list;
	}

	@Override
	public int count(String name) throws IOException, DataBaseException {
		ComputerDAO.connect = JDBCManager.connectionDB();
		int count = 0;
		try (PreparedStatement preparedStatement = ComputerDAO.connect.prepareStatement(QUERY_COUNT)) {
			preparedStatement.setNString(1, "%" + name + "%");
			preparedStatement.setNString(2, "%" + name + "%");
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				count = result.getInt(1);
			}
			result.close();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DataBaseException("Erreur interne à la base de données");

		} finally {
			try {
				ComputerDAO.connect.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new DataBaseException("Echec de la fermeture de la connexion à la BDD");
			}
		}
		return count;
	}

	@Override
	public void deleteByCompany(int companyId) throws IOException, DataBaseException {
		ComputerDAO.connect = JDBCManager.connectionDB();
		try (PreparedStatement preparedStatement = ComputerDAO.connect.prepareStatement(QUERY_DELETE_COMPANY)) {
			preparedStatement.setInt(1, companyId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DataBaseException("Erreur interne à la base de données");
		} finally {
			try {
				ComputerDAO.connect.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new DataBaseException("Echec de la fermeture de la connexion à la BDD");
			}
		}
	}
}
