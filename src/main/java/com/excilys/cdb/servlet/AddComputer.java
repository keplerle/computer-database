package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.DataBaseException;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.mapper.MapperComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

public class AddComputer extends HttpServlet {

	Logger logger = LoggerFactory.getLogger(AddComputer.class);
	@Autowired
	CompanyService cpaService;
	@Autowired
	ComputerService cpuService;
	MapperComputerDTO mapper;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
	      ctx.getAutowireCapableBeanFactory().autowireBean(this);
		try {
			mapper=MapperComputerDTO.getInstance();
			List<Company> companies = cpaService.findAll();
			request.setAttribute("companies", companies);
		} catch (DataBaseException dbe) {
			logger.error(dbe.getMessage());
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerDTO computerDto = new ComputerDTO();
		computerDto.name = request.getParameter("computerName");
		computerDto.introduced = request.getParameter("introduced");
		computerDto.discontinued = request.getParameter("discontinued");
		computerDto.companyId = request.getParameter("companyId");
	
		try {
			cpuService.create(mapper.computerDtoToComputer(computerDto));
			response.sendRedirect("dashboard");
		} catch (DataException de) {
			request.setAttribute("internError", de.getMessage());
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
		} 
		 catch (DataBaseException e) {
			 logger.error(e.getMessage());
			 this.getServletContext().getRequestDispatcher("/WEB-INF/views/500.jsp").forward(request, response);
		}
	}
}
