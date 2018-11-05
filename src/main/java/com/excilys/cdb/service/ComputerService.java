package com.excilys.cdb.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

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

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)

public class ComputerService {
	Logger logger = LoggerFactory.getLogger(ComputerService.class);
	private static ComputerService computerService = null;
	ComputerDAO computerDao;
	@Resource
	   private SessionContext context;

	private ComputerService() {
		computerDao = ComputerDAO.getInstance();
	}

	public static ComputerService getInstance() {
		if (computerService == null) {
			computerService = new ComputerService();
		}
		return computerService;
	}
	 @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Optional<Computer> find(int id) throws IOException, DataBaseException {
		Optional<Computer> computer;
		try {
			computer = computerDao.find(id);	
		} catch (DataBaseException dbe) {
			context.setRollbackOnly();
			throw dbe;
		}
		return computer;
	}
	 
	 @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void create(Computer computer) throws DataException, IOException, DataBaseException {
		try {
			ComputerValidator.computerValidator(computer);
			computerDao.create(computer);
		} catch (DataBaseException dbe) {
			context.setRollbackOnly();
			throw dbe;
		}
	}
	 
	 @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void update(Computer computer) throws DataException, IOException, DataBaseException {
		try {
			ComputerValidator.computerValidator(computer);
			computerDao.update(computer);
		} catch (DataBaseException dbe) {
			context.setRollbackOnly();
			throw dbe;
		}
	}

	public void delete(int id) throws IOException, DataBaseException {
		computerDao.delete(id);
	}
	 @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteAll(String[] idTab) throws IOException, DataBaseException {
		try {
			for (int i = 0; i < idTab.length; i++) {
				if (!("".equals(idTab[i])))
					delete(Integer.parseInt(idTab[i]));
			}
		} catch (DataBaseException dbe) {
			context.setRollbackOnly();
			throw dbe;
		}
	}

	 @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <T> List<Computer> findAll(String name)
			throws IOException, DataBaseException, NoPreviousPageException, NoNextPageException {
		List<Computer> list;
		try {
			PageValidator.previousPageValidator();
			list = computerDao.findAll(name, Page.getPage(), Page.getPageSize());
			PageValidator.nextPageValidator(list);
		} catch (DataBaseException dbe) {
			context.setRollbackOnly();
			throw dbe;
		}
		return list;
	}
	 @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int count(String name) throws IOException, DataBaseException {
		int result = 0;
		try {
			result = computerDao.count(name);
		} catch (DataBaseException dbe) {
			context.setRollbackOnly();
			throw dbe;
		}
		return result;
	}
}
