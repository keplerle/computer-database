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

public class EditComputer extends HttpServlet {
	
	Logger logger = LoggerFactory.getLogger(EditComputer.class);
	CompanyService cpaService;
	ComputerService cpuService;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {	
			
			cpuService = ComputerService.getInstance();
			Computer computer=cpuService.find(Integer.parseInt(request.getParameter("computerId")));
			
			request.setAttribute( "computerId",  computer.getId());
			request.setAttribute( "computerName", computer.getName());
			request.setAttribute( "companyId", computer.getCompany().getId());
			request.setAttribute( "introduced", computer.getIntroduced());
			request.setAttribute( "discontinued", computer.getDiscontinued());
			
			cpaService= CompanyService.getInstance();
			
			List<Company> companies = cpaService.findAll();
			request.setAttribute( "companies", companies );
		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
		}	
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String computerName = request.getParameter("computerName");
		String dateIntroduced = request.getParameter("introduced");
		String dateDiscontinued = request.getParameter("discontinued");
		int companyId = Integer.parseInt(request.getParameter("companyId"));
		int computerId = Integer.parseInt(request.getParameter("id"));
		
		cpuService = ComputerService.getInstance();
		
		Computer updatedComputer = new Computer(computerId, computerName);
		Company updatedCompany = new Company();
		updatedComputer.setCompany(updatedCompany);
		
		if (!dateIntroduced.equals("")) {
			updatedComputer.setIntroduced(Date.valueOf(dateIntroduced).toLocalDate());
		}
		if (!dateDiscontinued.equals("")) {
			updatedComputer.setDiscontinued(Date.valueOf(dateDiscontinued).toLocalDate());
		}
		if (companyId > 0) {
			updatedComputer.getCompany().setId(companyId);
		}
		try {
			cpuService.update(updatedComputer);
			response.sendRedirect("dashboard");
			
		} catch (DateException de) {
			response.sendRedirect("500");
		} catch (SQLException e) {
			response.sendRedirect("404");
		}
	}

}