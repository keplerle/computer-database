package com.excilys.cdb.model;

public class Company {

	// Variable d'instance
	private long id;
	private String name;

	// Construteur
	public Company() {
		super();

	}

	/**
	 * 
	 * @param id
	 * @param name
	 */
	public Company(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Company(int id) {
		super();
		this.id = id;
	}

	// Getter & setter
	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "id=" + id + ",\n name=" + name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	

}
