package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
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

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.mapper.MapperCompanyDTO;
import com.excilys.cdb.mapper.MapperComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

public class EditComputer extends HttpServlet {

	Logger logger = LoggerFactory.getLogger(EditComputer.class);
	@Autowired
	CompanyService cpaService;
	@Autowired
	ComputerService cpuService;
	MapperComputerDTO computerMapper=MapperComputerDTO.getInstance();
	MapperCompanyDTO companyMapper=MapperCompanyDTO.getInstance();
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
	      ctx.getAutowireCapableBeanFactory().autowireBean(this);
			
			ComputerDTO computerDto = computerMapper.fromOptionalComputer(cpuService.find(Integer.parseInt(request.getParameter("computerId"))));
			request.setAttribute("computerId", computerDto.id);
			request.setAttribute("computerName", computerDto.name);
			request.setAttribute("introduced", computerDto.introduced);
			request.setAttribute("discontinued", computerDto.discontinued);
			request.setAttribute("companyId", computerDto.companyId);

			List<Company> companies = cpaService.findAll();
			List<CompanyDTO> subCompaniesDTO = new ArrayList<CompanyDTO>();
			for (int i = 0; i < companies.size(); i++) {
				subCompaniesDTO.add(companyMapper.fromCompany(companies.get(i)));
			}
			request.setAttribute("companies", companies);

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ComputerDTO computerDto = new ComputerDTO();
		computerDto.id=request.getParameter("id");
		computerDto.name=request.getParameter("computerName");
		computerDto.introduced=request.getParameter("introduced");
		computerDto.discontinued=request.getParameter("discontinued");
		computerDto.companyId=request.getParameter("companyId");

		try {

			cpuService.update(computerMapper.toComputer(computerDto));

			response.sendRedirect("dashboard");
		} catch (DataException de) {
			request.setAttribute("internError", de.getMessage());
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
		} 
	}

}