package com.excilys.cdb.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.mapper.MapperCompanyDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;

@RestController("companyController")
@RequestMapping("/company")
public class CompanyRESTController {

	private final CompanyService companyService;
	private final MapperCompanyDTO companyMapper;
	
	public CompanyRESTController(CompanyService companyService, MapperCompanyDTO companyMapper) {
		this.companyService = companyService;
		this.companyMapper = companyMapper;
	}

	@GetMapping("/all")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<List<CompanyDTO>> findAll() {
		List<Company> companyList;	
		companyList = companyService.findAll();
		List<CompanyDTO> subCompaniesDTO = companyList.stream().map(
				companyMapper::fromCompany
			).collect(Collectors.toList());	
		return new ResponseEntity<>(subCompaniesDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		companyService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
