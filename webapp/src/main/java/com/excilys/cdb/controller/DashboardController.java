package com.excilys.cdb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.NoNextPageException;
import com.excilys.cdb.exception.NoPreviousPageException;
import com.excilys.cdb.mapper.MapperComputerDTO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping("dashboard")
public class DashboardController {

	private final ComputerService computerService;
	private final MapperComputerDTO computerMapper;

	public DashboardController(ComputerService computerService, MapperComputerDTO computerMapper) {
		this.computerService = computerService;
		this.computerMapper = computerMapper;
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public String getDashboard(ModelMap model, @RequestParam(required = false, defaultValue = "") String search,
			@RequestParam(required = false, defaultValue = "1") String page,
			@RequestParam(required = false, defaultValue = "10") String size) {
		List<Computer> computers;
		List<ComputerDTO> subComputersDTO = new ArrayList<>();
		long counter = 0;
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
			subComputersDTO = computers.stream().map(computerMapper::fromComputer).collect(Collectors.toList());

		} catch (NoPreviousPageException nppe) {
			Page.increasePage();
		} catch (NoNextPageException nnpe) {
			Page.decreasePage();
		}
		model.addAttribute("counter", counter);
		model.addAttribute("pageIndex", Page.getPageNumber());
		model.addAttribute("pageSize", Page.getPageSize());
		model.addAttribute("computers", subComputersDTO);
		return "dashboard";
	}

	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String postDashboard(ModelMap model, @RequestParam String[] selection) {
		computerService.deleteAll(selection);
		return "redirect:dashboard";
	}
}