package com.excilys.cdb.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exception.DataBaseException;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.validator.ComputerValidator;


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

	public Optional<Computer> find(int id) throws IOException, DataBaseException {
		return computerDao.find(id);
	}

	public boolean create(Computer computer) throws DataException, IOException, DataBaseException {
		ComputerValidator.computerValidator(computer);
		return computerDao.create(computer);
	}

	public boolean update(Computer computer) throws DataException, IOException, DataBaseException {
		ComputerValidator.computerValidator(computer);
		return computerDao.update(computer);
	}

	public boolean delete(int id) throws IOException, DataBaseException {
		return computerDao.delete(id);
	}
	
	public void deleteAll(String[] idTab) throws IOException, DataBaseException {
		for (int i = 0; i < idTab.length; i++) {
			if(!("".equals(idTab[i])))
			delete(Integer.parseInt(idTab[i]));
		}
	}

	public <T> List<Computer> findAll() throws IOException, DataBaseException {
		return computerDao.findAll();
	}
	
	public <T> List<Computer> findAll(String name) throws  IOException, DataBaseException {
		return computerDao.findAll(name);
	}
}
