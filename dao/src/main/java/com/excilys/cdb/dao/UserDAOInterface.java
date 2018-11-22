package com.excilys.cdb.dao;

import java.util.Optional;

import com.excilys.cdb.model.User;

public interface UserDAOInterface {
	/**
	 * Méthode de recherche des informations par id
	 * 
	 * @param username
	 * @param saltedPassword
	 * @return T
	 */
	public abstract Optional<User> find(String username, String saltedPassword);
}
