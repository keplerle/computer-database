package com.excilys.cdb.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public String getEditComputer(ModelMap model, @RequestParam Long computerId) {
		ComputerDTO computerDto = computerMapper
				.fromOptionalComputer(computerService.find(computerId));
		model.addAttribute("computerDto", computerDto);

		List<Company> companies = companyService.findAll();
		List<CompanyDTO> subCompaniesDTO = companies.stream().map(
			companyMapper::fromCompany
		).collect(Collectors.toList());	
		model.addAttribute("companies", subCompaniesDTO);
		return "editComputer";
	}

	@PostMapping
	public String postEditComputer(@Validated @ModelAttribute("computerDto") ComputerDTO computerDto, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "500";
        }
		try {

			computerService.update(computerMapper.toComputer(computerDto));

			return "redirect:dashboard";
		} catch (DataException de) {
			model.addAttribute("internError", de.getMessage());
			return "editComputer";
		}
	}
}
