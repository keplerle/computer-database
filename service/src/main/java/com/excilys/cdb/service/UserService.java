package com.excilys.cdb.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.UserDAO;
import com.excilys.cdb.model.User;

@Service
public class UserService {
	Logger logger = LoggerFactory.getLogger(UserService.class);
	private final UserDAO userDao;


	public UserService(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	public Optional<User> find(String username, String saltedPassword) {
		Optional<User> user;
		user = userDao.find(username, saltedPassword);
		return user;
	}
}
