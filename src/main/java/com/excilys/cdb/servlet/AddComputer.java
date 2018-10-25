package com.excilys.cdb.servlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

public class AddComputer extends HttpServlet{
	
	Logger logger = LoggerFactory.getLogger(AddComputer.class);
	CompanyService cpaService;
	
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
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/dashboard.jsp" ).forward( request, response );
	}
}
