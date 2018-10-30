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
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.mapper.MapperComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
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
			
			ComputerDTO computerDto = mapper.computerToComputerDto(cpuService.find(Integer.parseInt(request.getParameter("computerId"))));
			request.setAttribute("computerId", computerDto.getId());
			request.setAttribute("computerName", computerDto.getName());
			request.setAttribute("introduced", computerDto.getIntroduced());
			request.setAttribute("discontinued", computerDto.getDiscontinued());
			request.setAttribute("companyId", computerDto.getCompanyId());
			
			cpaService = CompanyService.getInstance();

			List<Company> companies = cpaService.findAll();
			request.setAttribute("companies", companies);
			
		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ComputerDTO computerDto = new ComputerDTO();
		computerDto.setId(request.getParameter("id"));
		computerDto.setName(request.getParameter("computerName"));
		computerDto.setIntroduced(request.getParameter("introduced"));
		computerDto.setDiscontinued(request.getParameter("discontinued"));
		computerDto.setCompanyId(request.getParameter("companyId"));

		cpuService = ComputerService.getInstance();
		try {

			cpuService.update(mapper.computerDtoToComputer(computerDto));

			response.sendRedirect("dashboard");
		} catch (DataException de) {
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/500.jsp").forward(request, response);
		} catch (SQLException e) {
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/404.jsp").forward(request, response);
		}
	}

}