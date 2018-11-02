package com.excilys.cdb.service;

import java.io.IOException;
import java.util.List;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.exception.DataBaseException;
import com.excilys.cdb.model.Company;

public class CompanyService {
	private static CompanyService companyService = null;
	CompanyDAO dc;

	private CompanyService() {
		dc = CompanyDAO.getInstance();
	}

	public static CompanyService getInstance() {
		if (companyService == null) {
			companyService = new CompanyService();
		}
		return companyService;
	}

	public <T> List<Company> findAll() throws IOException, DataBaseException {
		return dc.findAll();
	}

}