package com.excilys.cdb.dao;

import java.util.Optional;

import com.excilys.cdb.model.User;

public interface UserDAOInterface {
	/**
	 * 
	 * @param username
	 * @return T
	 */
	public abstract User find(String username);
}
