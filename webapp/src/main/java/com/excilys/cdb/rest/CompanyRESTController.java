package com.excilys.cdb.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.mapper.MapperCompanyDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;
@CrossOrigin(origins="*")
@RestController("companyController")
@RequestMapping("/api/company")
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

	@DeleteMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> delete(@RequestParam String[] idTab) {
		companyService.deleteList(idTab);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<CompanyDTO> create(@RequestBody CompanyDTO companyDto) {
			companyService.create(companyMapper.toCompany(companyDto));
		return new ResponseEntity<>(companyDto, HttpStatus.CREATED);

	}

	@PutMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<CompanyDTO> update(@RequestBody CompanyDTO companyDto) {
			
			companyService.update(companyMapper.toCompany(companyDto));
		
		return new ResponseEntity<>(companyDto, HttpStatus.OK);
	}
}
