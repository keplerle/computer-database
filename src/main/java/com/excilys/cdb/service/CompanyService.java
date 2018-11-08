package com.excilys.cdb.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exception.DataBaseException;
import com.excilys.cdb.model.Company;

@Service
public class CompanyService {

	@Autowired
	private CompanyDAO companyDao;
	@Autowired
	private ComputerDAO computerDao;

	@Transactional
	public <T> List<Company> findAll() throws IOException, DataBaseException {
		List<Company> list;
		try {
			list = companyDao.findAll();
		} catch (DataBaseException dbe) {

			throw dbe;
		}
		return list;
	}

	@Transactional
	public void delete(int id) throws IOException, DataBaseException {
		try {
			computerDao.deleteByCompany(id);
			companyDao.delete(id);
		} catch (DataBaseException dbe) {
			throw dbe;
		}
	}

}