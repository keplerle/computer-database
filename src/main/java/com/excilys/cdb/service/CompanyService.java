package com.excilys.cdb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exception.DataBaseException;
import com.excilys.cdb.model.Company;

@Service
public class CompanyService {
	Logger logger = LoggerFactory.getLogger(CompanyService.class);
	@Autowired
	private CompanyDAO companyDao;
	@Autowired
	private ComputerDAO computerDao;
	@Autowired
	private PlatformTransactionManager transactionManager;

	public <T> List<Company> findAll() {
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		List<Company> list = new ArrayList<Company>();
		list = transactionTemplate.execute(new TransactionCallback<List<Company>>() {
			@Override
			public List<Company> doInTransaction(TransactionStatus transactionStatus) {
				List<Company> listCompany = new ArrayList<Company>();
				listCompany = companyDao.findAll();
				return listCompany;
			}
		});
		return list;
	}

	public void delete(int id) {
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				computerDao.deleteByCompany(id);
				companyDao.delete(id);
			}
		});
	}

}