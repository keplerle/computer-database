package com.excilys.cdb.mapper;

import com.excilys.cdb.dto.CompanyDTO;

import com.excilys.cdb.model.Company;

public class MapperCompanyDTO {
	private static MapperCompanyDTO mapperCompanyDTO = new MapperCompanyDTO();

	private MapperCompanyDTO() {
	}

	public static MapperCompanyDTO getInstance() {
		return mapperCompanyDTO;
	}

	public Company toCompany(CompanyDTO companyDto) {
		Company company = new Company();
		company.setId(Integer.parseInt(companyDto.id));
		company.setName(companyDto.name);
		return company;
	}

	public CompanyDTO fromCompany(Company company) {
		CompanyDTO companyDto = new CompanyDTO();
		companyDto.id = company.getId() + "";
		companyDto.name = company.getName();
		return companyDto;

	}
}
