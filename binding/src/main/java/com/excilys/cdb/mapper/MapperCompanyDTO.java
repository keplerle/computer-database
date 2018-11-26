package com.excilys.cdb.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.model.Company;
@Component
public class MapperCompanyDTO {


	public Company toCompany(CompanyDTO companyDto) {
		Company company = new Company();
		company.setId(Long.parseLong(companyDto.getId()));
		company.setName(companyDto.getName());
		return company;
	}

	public CompanyDTO fromCompany(Company company) {
		CompanyDTO companyDto = new CompanyDTO();
		companyDto.setId(company.getId() + "");
		companyDto.setName(company.getName());
		return companyDto;

	}
}
