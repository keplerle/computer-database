package com.excilys.cdb.dto;

public class CompanyDTO {
	private String id;
	private String name;
	
	public CompanyDTO() {
		super();
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
