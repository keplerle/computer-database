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

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;

public class Dashboard extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	Logger logger = LoggerFactory.getLogger(Dashboard.class);
	ComputerService cpuService;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		try {
			cpuService= ComputerService.getInstance();
			List<Computer> computers = cpuService.findAll();		
			request.setAttribute( "computers", computers );
			request.setAttribute( "counter", computers.size());
		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		cpuService= ComputerService.getInstance();
		
		String[] checkedIds = request.getParameterValues("selection");
		String[] idTab=checkedIds[0].split(",");
		
		for(int i = 0;i<idTab.length;i++) {
	
			try {
				cpuService.delete(Integer.parseInt(idTab[i]));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		response.sendRedirect("dashboard");
	}
}
