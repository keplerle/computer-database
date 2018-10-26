package com.excilys.cdb.servlet;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.DateException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

public class AddComputer extends HttpServlet{
	
	Logger logger = LoggerFactory.getLogger(AddComputer.class);
	CompanyService cpaService;
	ComputerService cpuService;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		try {
			cpaService= CompanyService.getInstance();
			List<Company> companies = cpaService.findAll();
			request.setAttribute( "companies", companies );
		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
		}	
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/addComputer.jsp" ).forward( request, response );
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		
		String computerName = request.getParameter("computerName");
		String dateIntroduced = request.getParameter("introduced");
		String dateDiscontinued = request.getParameter("discontinued");
		int companyId = Integer.parseInt(request.getParameter("companyId"));

		Computer newComputer = new Computer(computerName);
		Company newCompany = new Company(companyId);

		if (!dateIntroduced.equals("")) {
			newComputer.setIntroduced(Date.valueOf(dateIntroduced).toLocalDate());
		}

		if (!dateDiscontinued.equals("")) {
			newComputer.setDiscontinued(Date.valueOf(dateDiscontinued).toLocalDate());
		}
		
		newComputer.setCompany(newCompany);
		
			cpuService= ComputerService.getInstance();
			try {
				cpuService.create(newComputer);
				response.sendRedirect("dashboard");
			} catch (DateException e) {
				response.sendRedirect("404");
			} catch (SQLException e) {
				response.sendRedirect("500");
			}
	
	}
}
