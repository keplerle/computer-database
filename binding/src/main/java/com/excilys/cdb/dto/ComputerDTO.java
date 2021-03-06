package com.excilys.cdb.dto;

import org.springframework.format.annotation.DateTimeFormat;

public class ComputerDTO {

	String id;
	String name;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public String introduced;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public String discontinued;

	String companyId;

	String companyName;

	public ComputerDTO() {
		super();
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

}
