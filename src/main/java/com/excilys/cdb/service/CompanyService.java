package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.model.Company;

@Service
public class CompanyService {
	Logger logger = LoggerFactory.getLogger(CompanyService.class);
	
	private final CompanyDAO companyDao;
	private final ComputerDAO computerDao;
	private final PlatformTransactionManager hibernateTransactionManager;

	public CompanyService(CompanyDAO companyDao, ComputerDAO computerDao,
			PlatformTransactionManager hibernateTransactionManager) {
		this.companyDao = companyDao;
		this.computerDao = computerDao;
		this.hibernateTransactionManager = hibernateTransactionManager;
	}

	public <T> List<Company> findAll() {
		List<Company> list = new ArrayList<Company>();
		list = companyDao.findAll();	
		return list;
	}

	public void delete(int id) {
		TransactionTemplate transactionTemplate = new TransactionTemplate(hibernateTransactionManager);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				computerDao.deleteByCompany(id);
				companyDao.delete(id);
			}
		});
	}

}