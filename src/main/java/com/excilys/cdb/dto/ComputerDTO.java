package com.excilys.cdb.dto;

import java.time.LocalDate;

import com.excilys.cdb.model.Company;

public class ComputerDTO {
	
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;
	
	
	public ComputerDTO() {
		super();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getIntroduced() {
		return introduced;
	}
	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}
	public LocalDate getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
}
