package com.excilys.cdb.dto;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

public class CompanyDTO {
	@NotNull
	public String id;
	@NotBlank
	public String name;
	
	public CompanyDTO() {
		super();
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
