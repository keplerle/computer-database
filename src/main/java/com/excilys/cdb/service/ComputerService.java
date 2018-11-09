package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
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

	@Autowired
	private ComputerDAO computerDao;
	@Autowired
	private PlatformTransactionManager transactionManager;

	public Optional<Computer> find(int id) {
		Optional<Computer> computer;
		computer = computerDao.find(id);
		return computer;
	}

	public void create(Computer computer) throws DataException {
		ComputerValidator.computerValidator(computer);
		computerDao.create(computer);
	}

	public void update(Computer computer) throws DataException {
		ComputerValidator.computerValidator(computer);
		computerDao.update(computer);
	}

	public void delete(int id) {
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
							delete(Integer.parseInt(idTab[i]));
					}
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
				}
			}
		});
	}

	public <T> List<Computer> findAll(String name) throws NoPreviousPageException, NoNextPageException {
		PageValidator.previousPageValidator();
		List<Computer> computerList = new ArrayList<Computer>();
		computerList = computerDao.findAll(name, Page.getPage(), Page.getPageSize());
		PageValidator.nextPageValidator(computerList);
		return computerList;
	}

	public int count(String name) {
		int count = 0;
		count = computerDao.count(name);
		return count;
	}
}
