package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.exception.NoNextPageException;
import com.excilys.cdb.exception.NoPreviousPageException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.validator.ComputerValidator;
import com.excilys.cdb.validator.PageValidator;

@Service
public class ComputerService {
	Logger logger = LoggerFactory.getLogger(ComputerService.class);
	private final ComputerDAO computerDao;
	private final ComputerValidator computerValidator;
	private final PageValidator pageValidator;

	public ComputerService(ComputerDAO computerDao, ComputerValidator computerValidator, PageValidator pageValidator) {
		this.computerDao = computerDao;
		this.computerValidator = computerValidator;
		this.pageValidator = pageValidator;
	}

	public Optional<Computer> find(long id) {
		Optional<Computer> computer;
		computer = computerDao.find(id);
		return computer;
	}

	public void create(Computer computer) throws DataException {
		computerValidator.computerValidator(computer);
		computerDao.create(computer);
	}

	public void update(Computer computer) throws DataException {
		computerValidator.computerValidator(computer);
		computerDao.update(computer);
	}

	public void delete(long id) {
		computerDao.delete(id);
	}
	
	@Transactional
	public void deleteAll(String[] idTab) {
				try {
					for (int i = 0; i < idTab.length; i++) {
							delete(Long.parseLong(idTab[i]));
					}
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
				}
	}

	public List<Computer> findAll(String name) throws NoPreviousPageException, NoNextPageException {
		pageValidator.previousPageValidator();
		List<Computer> computerList ;
		computerList = computerDao.findAll(name, Page.getPageNumber(), Page.getPageSize());
		pageValidator.nextPageValidator(computerList);
		return computerList;
	}

	public long count(String name) {
		long count = 0;
		count = computerDao.count(name);
		return count;
	}
}
