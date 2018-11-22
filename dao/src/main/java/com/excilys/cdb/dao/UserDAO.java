package com.excilys.cdb.dao;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.User;

public class UserDAO implements UserDAOInterface {
	Logger logger = LoggerFactory.getLogger(UserDAO.class);

	private final static String HQL_SELECT = "select user from User as user where user.username = :username and user.saltedPassword=:saltedPassword";

	private final SessionFactory sessionFactory;

	public UserDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Optional<User> find(String username, String saltedPassword) {
		User user;
		try (Session session = sessionFactory.openSession()) {
			Query query = session.createQuery(HQL_SELECT);
			query.setParameter("username", username);
			query.setParameter("saltedPassword", saltedPassword);
			user = (User) query.list().get(0);
		}
		return Optional.ofNullable(user);
	}

}
