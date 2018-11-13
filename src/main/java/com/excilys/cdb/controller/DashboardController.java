package com.excilys.cdb.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.NoNextPageException;
import com.excilys.cdb.exception.NoPreviousPageException;
import com.excilys.cdb.mapper.MapperComputerDTO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

@Controller("dashboardController")
public class DashboardController {

	private final Logger logger = LoggerFactory.getLogger(DashboardController.class);
	@Autowired
	private ComputerService computerService;
	private  MapperComputerDTO computerMapper = MapperComputerDTO.getInstance();

	@GetMapping("dashboard")
	public String getDashboard(ModelMap model,
			@RequestParam(required = false, defaultValue = "") String search,
			@RequestParam(required = false, defaultValue = "1") String page,
			@RequestParam(required = false, defaultValue = "10") String size) {
		List<Computer> computers;
		List<ComputerDTO> subComputersDTO = new ArrayList<ComputerDTO>();
		int counter = 0;
		try {
			Page.setPage(page, size);
			
			if (search == null) {
				computers = computerService.findAll("");
				counter = computerService.count("");
			} else {
				model.addAttribute("search", search);
				computers = computerService.findAll(search);
				counter = computerService.count(search);
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
			model.addAttribute("counter", counter);
			model.addAttribute("pageIndex", Page.getPage());
			model.addAttribute("pageSize", Page.getPageSize());
			model.addAttribute("computers", subComputersDTO);
		return "dashboard";
	}
	
	@PostMapping("dashboard")
	public String postDeleteComputer(ModelMap model, 
			@RequestParam String[] selection) {
		
		String[] idTab = selection[0].split(",");
		computerService.deleteAll(idTab);	

	return "redirect:dashboard";
	}

}