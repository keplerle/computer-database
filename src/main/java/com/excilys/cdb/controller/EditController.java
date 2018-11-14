package com.excilys.cdb.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.mapper.MapperCompanyDTO;
import com.excilys.cdb.mapper.MapperComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping("edit")
public class EditController {
	Logger logger = LoggerFactory.getLogger(EditController.class);
	
	private final CompanyService companyService;
	private final ComputerService computerService;
	private final MapperComputerDTO computerMapper;
	private final MapperCompanyDTO companyMapper;

	public EditController(CompanyService companyService, ComputerService computerService,
			MapperComputerDTO computerMapper, MapperCompanyDTO companyMapper) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.computerMapper = computerMapper;
		this.companyMapper = companyMapper;
	}

	@GetMapping
	public String getDashboard(ModelMap model, @RequestParam String computerId) {
		ComputerDTO computerDto = computerMapper
				.fromOptionalComputer(computerService.find(Integer.parseInt(computerId)));

		model.addAttribute("computerId", computerDto.id);
		model.addAttribute("computerName", computerDto.name);
		model.addAttribute("introduced", computerDto.introduced);
		model.addAttribute("discontinued", computerDto.discontinued);
		model.addAttribute("companyId", computerDto.companyId);

		List<Company> companies = companyService.findAll();
		List<CompanyDTO> subCompaniesDTO = new ArrayList<CompanyDTO>();
		for (int i = 0; i < companies.size(); i++) {
			subCompaniesDTO.add(companyMapper.fromCompany(companies.get(i)));
		}
		model.addAttribute("companies", companies);

		return "editComputer";
	}

	@PostMapping
	public String postDeleteComputer(ModelMap model, @RequestParam String id, @RequestParam String computerName,
			@RequestParam String introduced, @RequestParam String discontinued, @RequestParam String companyId) {

		ComputerDTO computerDto = new ComputerDTO();
		computerDto.id = id;
		computerDto.name = computerName;
		computerDto.introduced = introduced;
		computerDto.discontinued = discontinued;
		computerDto.companyId = companyId;

		try {

			computerService.update(computerMapper.toComputer(computerDto));

			return "redirect:dashboard";
		} catch (DataException de) {
			model.addAttribute("internError", de.getMessage());
			return "editComputer";
		}
	}
}
