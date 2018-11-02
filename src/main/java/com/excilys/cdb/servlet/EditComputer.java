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

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.DataBaseException;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.mapper.MapperComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

public class EditComputer extends HttpServlet {

	Logger logger = LoggerFactory.getLogger(EditComputer.class);
	CompanyService cpaService;
	ComputerService cpuService;
	MapperComputerDTO mapper;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			cpuService = ComputerService.getInstance();
			mapper=MapperComputerDTO.getInstance();
			
			ComputerDTO computerDto = mapper.computerDtoFromOptionalComputer(cpuService.find(Integer.parseInt(request.getParameter("computerId"))));
			request.setAttribute("computerId", computerDto.id);
			request.setAttribute("computerName", computerDto.name);
			request.setAttribute("introduced", computerDto.introduced);
			request.setAttribute("discontinued", computerDto.discontinued);
			request.setAttribute("companyId", computerDto.companyId);
			
			cpaService = CompanyService.getInstance();

			List<Company> companies = cpaService.findAll();
			request.setAttribute("companies", companies);
			
		} catch (DataBaseException dbe) {
			logger.error(dbe.getMessage());
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/500.jsp").forward(request, response);
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ComputerDTO computerDto = new ComputerDTO();
		computerDto.id=request.getParameter("id");
		computerDto.name=request.getParameter("computerName");
		computerDto.introduced=request.getParameter("introduced");
		computerDto.discontinued=request.getParameter("discontinued");
		computerDto.companyId=request.getParameter("companyId");

		cpuService = ComputerService.getInstance();
		try {

			cpuService.update(mapper.computerDtoToComputer(computerDto));

			response.sendRedirect("dashboard");
		} catch (DataException de) {
			request.setAttribute("internError", de.getMessage());
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
		} catch (DataBaseException dbe) {
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/500.jsp").forward(request, response);
		}
	}

}