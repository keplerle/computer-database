package com.excilys.cdb.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exception.DataBaseException;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.exception.NoNextPageException;
import com.excilys.cdb.exception.NoPreviousPageException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.validator.ComputerValidator;
import com.excilys.cdb.validator.PageValidator;


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

	public void create(Computer computer) throws DataException, IOException, DataBaseException {
		ComputerValidator.computerValidator(computer);
		computerDao.create(computer);
	}

	public void update(Computer computer) throws DataException, IOException, DataBaseException {
		ComputerValidator.computerValidator(computer);
		computerDao.update(computer);
	}

	public void delete(int id) throws IOException, DataBaseException {
		computerDao.delete(id);
	}
	
	public void deleteAll(String[] idTab) throws IOException, DataBaseException {
		for (int i = 0; i < idTab.length; i++) {
			if(!("".equals(idTab[i])))
			delete(Integer.parseInt(idTab[i]));
		}
	}

	public <T> List<Computer> findAll() throws IOException, DataBaseException, NoPreviousPageException, NoNextPageException {
		PageValidator.previousPageValidator();
		List<Computer> list = computerDao.findAll(Page.getPage(),Page.getPageSize());
		PageValidator.nextPageValidator(list);
		return list;
	}
	
	public <T> List<Computer> findAll(String name) throws  IOException, DataBaseException, NoPreviousPageException, NoNextPageException {
		PageValidator.previousPageValidator();
		List<Computer> list = computerDao.findAll(name,Page.getPage(),Page.getPageSize());
		PageValidator.nextPageValidator(list);
		return list;
	}
	public int count() throws  IOException, DataBaseException{
		return computerDao.count();
	}
}
