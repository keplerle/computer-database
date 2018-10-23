package com.excilys.cdb.service;

import java.sql.SQLException;
import java.util.List;


import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistance.CompanyDAO;

public class CompanyService {
	private static CompanyService cs = null;
	CompanyDAO dc;

	private CompanyService() {
		dc = CompanyDAO.getInstance();
	}

	public static CompanyService getInstance() {
		if (cs == null) {
			cs = new CompanyService();
		}
		return cs;
	}

	public <T> List<Company> findAll() throws SQLException {
		return dc.findAll();
	}

}