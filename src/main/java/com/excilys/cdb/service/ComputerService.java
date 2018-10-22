package com.excilys.cdb.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.cdb.exception.DateException;
import com.excilys.cdb.exception.NoNextPageException;
import com.excilys.cdb.exception.NoPreviousPageException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistance.ComputerDAO;

public class ComputerService {

	private static ComputerService cs = null;
	ComputerDAO dc;

	private ComputerService() {
		dc = ComputerDAO.getInstance();
	}

	public static ComputerService getInstance() {
		if (cs == null) {
			cs = new ComputerService();
		}
		return cs;
	}

	public Computer find(int id) throws SQLException {
		return dc.find(id);
	}

	public Computer find(String name) throws SQLException {
		return dc.find(name);
	}

	public boolean create(Computer computer) throws DateException, SQLException {
		return dc.create(computer);
	}

	public boolean update(Computer computer) throws DateException, SQLException {
		return dc.update(computer);
	}

	public boolean delete(int id) throws SQLException {
		return dc.delete(id);
	}

	public <T> List<Computer> findAll() throws SQLException, NoNextPageException, NoPreviousPageException {
		return Page.pagination(dc.findAll(), Page.getPage(), Page.getPageSize());
	}
}
