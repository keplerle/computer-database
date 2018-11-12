package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.NoNextPageException;
import com.excilys.cdb.exception.NoPreviousPageException;
import com.excilys.cdb.mapper.MapperComputerDTO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

	private static final long serialVersionUID = 1L;

	Logger logger = LoggerFactory.getLogger(Dashboard.class);
	MapperComputerDTO computerMapper = MapperComputerDTO.getInstance();
	List<Computer> computers;
	List<Computer> subComputers = new ArrayList<Computer>();
	List<ComputerDTO> subComputersDTO = new ArrayList<ComputerDTO>();
	int counter;
	
	@Autowired
	private ComputerService cpuService;
	



	
	@Override
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		ctx.getAutowireCapableBeanFactory().autowireBean(this);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Page.setPage(request.getParameter("page"), request.getParameter("size"));
			
			if (request.getParameter("search") == null) {
				computers = cpuService.findAll("");
				counter = cpuService.count("");
			} else {
				request.setAttribute("search", request.getParameter("search"));
				computers = cpuService.findAll(request.getParameter("search"));
				counter = cpuService.count(request.getParameter("search"));
			}
			subComputersDTO.clear();
			for (int i = 0; i < computers.size(); i++) {
				subComputersDTO.add(computerMapper.fromComputer(computers.get(i)));
			}
		} catch (NoPreviousPageException nppe) {
			Page.increasePage();
		} catch (NoNextPageException nnpe) {
			Page.decreasePage();
		}
		
		request.setAttribute("counter", counter);
		request.setAttribute("pageIndex", Page.getPage());
		request.setAttribute("pageSize", Page.getPageSize());
		request.setAttribute("computers", subComputersDTO);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String[] checkedIds = request.getParameterValues("selection");
		String[] idTab = checkedIds[0].split(",");
			cpuService.deleteAll(idTab);	

		response.sendRedirect("dashboard");
	}
}
