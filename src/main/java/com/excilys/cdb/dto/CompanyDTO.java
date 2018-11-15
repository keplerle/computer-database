package com.excilys.cdb.dto;

import org.springframework.lang.NonNull;


public class CompanyDTO {
	@NonNull
	public String id;
	@NonNull
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
