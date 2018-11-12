package com.excilys.cdb.dto;

import org.springframework.format.annotation.DateTimeFormat;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

public class ComputerDTO {

	public String id;
	@NotBlank
	public String name;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public String introduced;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public String discontinued;

	public String companyId;

	public String companyName;

	public ComputerDTO() {
		super();
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
