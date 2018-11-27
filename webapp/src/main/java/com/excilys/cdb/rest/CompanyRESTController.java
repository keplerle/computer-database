package com.excilys.cdb.rest;

import java.util.List;

import javax.ws.rs.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;

@RestController("companyController")
@RequestMapping("/company")
public class CompanyRESTController {

	
	private final CompanyService companyService;
	
	public CompanyRESTController(CompanyService companyService) {
		this.companyService = companyService;
	}
	@GetMapping
	public ResponseEntity<List<Company>> findAll() {
		List<Company> companyList;
		companyList = companyService.findAll();
		return new ResponseEntity<>(companyList, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<Void> delete(@PathParam("id") int id) {
		companyService.delete(id);
		return new ResponseEntity<>(HttpStatus.GONE);
	}
}
