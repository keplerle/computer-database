package com.excilys.cdb.controller;

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
public class CompanyRESTController {

	
	private final CompanyService companyService;
	
	public CompanyRESTController(CompanyService companyService) {
		this.companyService = companyService;
	}

	@GetMapping(value = "")
	public ResponseEntity<List<Company>> findAll() {
		List<Company> companyList;
		companyList = companyService.findAll();
		return new ResponseEntity<>(companyList, HttpStatus.OK);
	}

	@DeleteMapping(value = "")
	public ResponseEntity<Void> delete(int id) {
		companyService.delete(id);
		return new ResponseEntity<>(HttpStatus.GONE);
	}
}
