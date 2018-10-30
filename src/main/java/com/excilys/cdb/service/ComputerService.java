package com.excilys.cdb.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.exception.Validator;
import com.excilys.cdb.model.Computer;


public class ComputerService {

	private static ComputerService computerService = null;
	ComputerDAO computerDao;

	private ComputerService() {
		computerDao = ComputerDAO.getInstance();
	}

	public static ComputerService getInstance() {
		if (computerService == null) {
			computerService = new ComputerService();
		}
		return computerService;
	}

	public Computer find(int id) throws SQLException, FileNotFoundException, IOException {
		return computerDao.find(id);
	}



	public boolean create(Computer computer) throws DataException, SQLException, FileNotFoundException, IOException {
		if(!Validator.computerValidator(computer)) {
			throw new DataException();
		}
		return computerDao.create(computer);
	}

	public boolean update(Computer computer) throws DataException, SQLException, FileNotFoundException, IOException {
		if(!Validator.computerValidator(computer)) {
			throw new DataException();
		}
		return computerDao.update(computer);
	}

	public boolean delete(int id) throws SQLException, FileNotFoundException, IOException {
		return computerDao.delete(id);
	}

	public <T> List<Computer> findAll() throws SQLException, FileNotFoundException, IOException {
		return computerDao.findAll();
	}
	
	public <T> List<Computer> findAll(String name) throws SQLException, FileNotFoundException, IOException {
		return computerDao.findAll(name);
	}
}
