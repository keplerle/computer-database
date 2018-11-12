package com.excilys.cdb.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;

@Controller("companyController")
@RequestMapping("/company")
public class CompanyController {

	
	private final CompanyService companyService;
	
	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}

	@GetMapping(value = "")
	public ResponseEntity<List<Company>> findAll() {
		List<Company> companyList = new ArrayList<Company>();
		companyList = companyService.findAll();
		return new ResponseEntity<List<Company>>(companyList, HttpStatus.OK);
	}

	@DeleteMapping(value = "")
	public ResponseEntity<Void> delete(int id) {
		companyService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.GONE);
	}
}
