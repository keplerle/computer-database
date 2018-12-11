package com.excilys.cdb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.model.Company;

@Service
public class CompanyService {
	Logger logger = LoggerFactory.getLogger(CompanyService.class);

	private final CompanyDAO companyDao;
	private final ComputerDAO computerDao;

	public CompanyService(CompanyDAO companyDao, ComputerDAO computerDao) {
		this.companyDao = companyDao;
		this.computerDao = computerDao;

	}

	public List<Company> findAll() {
		List<Company> list;
		list = companyDao.findAll();
		return list;
	}

	@Transactional
	public void delete(long id) {
		computerDao.deleteByCompany(id);
		companyDao.delete(id);
	}

	@Transactional
	public void deleteList(String[] idTab) {
		try {
			for (int i = 0; i < idTab.length; i++) {
				delete(Long.parseLong(idTab[i]));
			}
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
		}
	}

	public void create(Company company) {
		companyDao.create(company);
	}

	public void update(Company company) {
		companyDao.update(company);
	}

}