package com.excilys.cdb.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exception.DataBaseException;
import com.excilys.cdb.model.Company;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)

public class CompanyService {
	private static CompanyService companyService = null;
	CompanyDAO companyDao;
	ComputerDAO computerDao;
	@Resource
	private SessionContext context;

	private CompanyService() {
		companyDao = CompanyDAO.getInstance();
		computerDao = ComputerDAO.getInstance();
	}

	public static CompanyService getInstance() {
		if (companyService == null) {
			companyService = new CompanyService();
		}
		return companyService;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <T> List<Company> findAll() throws IOException, DataBaseException {
		List<Company> list;
		try {
			list = companyDao.findAll();
		} catch (DataBaseException dbe) {
			context.setRollbackOnly();
			throw dbe;
		}
		return list;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(int id) throws IOException, DataBaseException {
		try {
			computerDao.deleteByCompany(id);
			companyDao.delete(id);
		} catch (DataBaseException dbe) {
			context.setRollbackOnly();
			throw dbe;
		}
	}

}