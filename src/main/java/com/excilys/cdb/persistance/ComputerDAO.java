package com.excilys.cdb.persistance;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.cdb.exception.DateException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerDAO implements ComputerDAOInterface<Computer> {

	private final static String QUERY_INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private final static String QUERY_UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	private final static String QUERY_DELETE = "DELETE FROM computer WHERE id= ?";
	private final static String QUERY_SELECT_BY_NAME = "SELECT cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpu.company_id,cpa.name FROM computer AS cpu LEFT JOIN company AS cpa ON cpu.company_id = cpa.id WHERE cpu.name = ?";
	private final static String QUERY_SELECT_BY_ID = "SELECT cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpu.company_id,cpa.name FROM computer AS cpu LEFT JOIN company AS cpa ON cpu.company_id = cpa.id WHERE cpu.id = ?";
	private final static String QUERY_SELECT_ALL = "SELECT id,name FROM computer";

	private static ComputerDAO computerDAO = new ComputerDAO();
	private static Connection connect;

	private ComputerDAO() {
		JDBCManager.getInstance();
		connect = JDBCManager.getConnection();
	}

	public static ComputerDAO getInstance() {
		return computerDAO;
	}

	@Override
	public boolean create(Computer computer) throws SQLException, DateException {

		ComputerDAO.connect = JDBCManager.connectionDB();

		try (PreparedStatement preparedStatement = ComputerDAO.connect.prepareStatement(QUERY_INSERT);) {
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

			if (computer.getDiscontinued() != null && computer.getIntroduced() != null
					&& computer.getDiscontinued().isBefore(computer.getIntroduced())) {
				throw new DateException();
			}
			preparedStatement.executeUpdate();

		}

		ComputerDAO.connect.close();
		return true;
	}

	@Override
	public boolean update(Computer computer) throws SQLException, DateException {
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

			if (computer.getDiscontinued() != null && computer.getIntroduced() != null
					&& computer.getDiscontinued().isBefore(computer.getIntroduced())) {
				throw new DateException();
			}

			preparedStatement.executeUpdate();
		}

		ComputerDAO.connect.close();
		return true;
	}

	@Override
	public boolean delete(int id) throws SQLException {
		ComputerDAO.connect = JDBCManager.connectionDB();
		try (PreparedStatement preparedStatement = ComputerDAO.connect.prepareStatement(QUERY_DELETE);) {
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();
		}
		ComputerDAO.connect.close();
		return true;
	}

	@Override
	public Computer find(int id) throws SQLException {
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
		}

		ComputerDAO.connect.close();
		return computer;
	}

	@Override
	public Computer find(String name) throws SQLException {
		ComputerDAO.connect = JDBCManager.connectionDB();
		Computer computer = null;
		try (PreparedStatement preparedStatement = ComputerDAO.connect.prepareStatement(QUERY_SELECT_BY_NAME)) {
			preparedStatement.setNString(1, name);
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				computer = new Computer(result.getInt("id"), result.getString("name"));
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
		}

		ComputerDAO.connect.close();
		return computer;
	}

	@Override
	public ArrayList<Computer> findAll() throws SQLException {
		ComputerDAO.connect = JDBCManager.connectionDB();
		ArrayList<Computer> list = new ArrayList<>();
		try (PreparedStatement preparedStatement = ComputerDAO.connect.prepareStatement(QUERY_SELECT_ALL);
				ResultSet result = preparedStatement.executeQuery()) {
			while (result.next()) {
				Computer computer = new Computer(result.getInt("id"), result.getString("name"));
				list.add(computer);
			}
		}

		ComputerDAO.connect.close();

		return list;
	}
}
