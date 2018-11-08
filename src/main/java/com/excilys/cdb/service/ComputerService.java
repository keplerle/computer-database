package com.excilys.cdb.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exception.DataBaseException;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.exception.NoNextPageException;
import com.excilys.cdb.exception.NoPreviousPageException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.validator.ComputerValidator;
import com.excilys.cdb.validator.PageValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComputerService {
	Logger logger = LoggerFactory.getLogger(ComputerService.class);
	
	@Autowired
	private ComputerDAO computerDao;

	 
	@Transactional
	public Optional<Computer> find(int id) throws IOException, DataBaseException {
		Optional<Computer> computer;
		try {
			computer = computerDao.find(id);	
		} catch (DataBaseException dbe) {
			throw dbe;
		}
		return computer;
	}
	 
	 @Transactional
	public void create(Computer computer) throws DataException, IOException, DataBaseException {
		try {
			ComputerValidator.computerValidator(computer);
			computerDao.create(computer);
		} catch (DataBaseException dbe) {
			throw dbe;
		}
	}
	 
	 @Transactional
	public void update(Computer computer) throws DataException, IOException, DataBaseException {
		try {
			ComputerValidator.computerValidator(computer);
			computerDao.update(computer);
		} catch (DataBaseException dbe) {
			throw dbe;
		}
	}

	public void delete(int id) throws IOException, DataBaseException {
		computerDao.delete(id);
	}
	 @Transactional
	public void deleteAll(String[] idTab) throws IOException, DataBaseException {
		try {
			for (int i = 0; i < idTab.length; i++) {
				if (!("".equals(idTab[i])))
					delete(Integer.parseInt(idTab[i]));
			}
		} catch (DataBaseException dbe) {
			throw dbe;
		}
	}

	 @Transactional
	public <T> List<Computer> findAll(String name)
			throws IOException, DataBaseException, NoPreviousPageException, NoNextPageException {
		List<Computer> list;
		try {
			PageValidator.previousPageValidator();
			list = computerDao.findAll(name, Page.getPage(), Page.getPageSize());
			PageValidator.nextPageValidator(list);
		} catch (DataBaseException dbe) {
			throw dbe;
		}
		return list;
	}
	 @Transactional
	public int count(String name) throws IOException, DataBaseException {
		int result = 0;
		try {
			result = computerDao.count(name);
		} catch (DataBaseException dbe) {
			throw dbe;
		}
		return result;
	}
}
