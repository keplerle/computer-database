package com.excilys.cdb.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.NoNextPageException;
import com.excilys.cdb.exception.NoPreviousPageException;
import com.excilys.cdb.mapper.MapperComputerDTO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

public class Dashboard extends HttpServlet {

	private static final long serialVersionUID = 1L;

	Logger logger = LoggerFactory.getLogger(Dashboard.class);
	ComputerService cpuService;
	MapperComputerDTO mapper;
	List<Computer> computers;
	List<Computer> subComputers= new ArrayList<Computer>();
	List<ComputerDTO> subComputersDTO = new ArrayList<ComputerDTO>();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			cpuService = ComputerService.getInstance();
			mapper=MapperComputerDTO.getInstance();
			
			
			
			if (request.getParameter("search") == null || request.getParameter("search").equals("")) {
				computers = cpuService.findAll();
				request.setAttribute("search", "");

			} else {
				computers = cpuService.findAll(request.getParameter("search"));
				request.setAttribute("search", request.getParameter("search"));
				
			}
			
			if (request.getParameter("page") != null) {
				Page.setPage(Integer.parseInt(request.getParameter("page")));
			}else {
				Page.setPage(1);
			}
			if (request.getParameter("size") != null) {
				Page.setPageSize(Integer.parseInt(request.getParameter("size")));
			}
			
			subComputers = Page.pagination(computers);
			
			subComputersDTO.clear();
			for(int i=0;i<subComputers.size();i++) {
				subComputersDTO.add(mapper.computerToComputerDto(subComputers.get(i)));
			}

		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/404.jsp").forward(request, response);
		} catch (NoPreviousPageException nppe) {
			Page.setPage(Integer.parseInt(request.getParameter("page")) + 1);

		} catch (NoNextPageException nnpe) {
			Page.setPage(Integer.parseInt(request.getParameter("page")) - 1);
		}
		request.setAttribute("computers", subComputersDTO);
		request.setAttribute("counter", computers.size());
		request.setAttribute("pageIndex", Page.getPage());
		request.setAttribute("pageSize", Page.getPageSize());

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		cpuService = ComputerService.getInstance();

		String[] checkedIds = request.getParameterValues("selection");
		String[] idTab = checkedIds[0].split(",");

		for (int i = 0; i < idTab.length; i++) {

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
