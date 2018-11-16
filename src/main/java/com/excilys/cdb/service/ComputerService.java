package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

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
	private final PlatformTransactionManager transactionManager;


	public ComputerService(ComputerDAO computerDao, ComputerValidator computerValidator, PageValidator pageValidator,
			PlatformTransactionManager transactionManager) {
		this.computerDao = computerDao;
		this.computerValidator = computerValidator;
		this.pageValidator = pageValidator;
		this.transactionManager = transactionManager;
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
	
	public void deleteAll(String[] idTab) {
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				try {
					for (int i = 0; i < idTab.length; i++) {
						if (!("".equals(idTab[i])))
							delete(Long.parseLong(idTab[i]));
					}
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
				}
			}
		});
	}

	public <T> List<Computer> findAll(String name) throws NoPreviousPageException, NoNextPageException {
		pageValidator.previousPageValidator();
		List<Computer> computerList = new ArrayList<Computer>();
		computerList = computerDao.findAll(name, Page.getPage(), Page.getPageSize());
		pageValidator.nextPageValidator(computerList);
		return computerList;
	}

	public long count(String name) {
		long count = 0;
		count = computerDao.count(name);
		return count;
	}
}
