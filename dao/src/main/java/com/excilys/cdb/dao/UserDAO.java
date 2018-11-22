package com.excilys.cdb.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.User;
@Repository
public class UserDAO implements UserDAOInterface {
	Logger logger = LoggerFactory.getLogger(UserDAO.class);

	private final static String HQL_SELECT = "select user from User as user where user.username = :username";

	private final SessionFactory sessionFactory;

	public UserDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public User find(String username) {
		User user;
		try (Session session = sessionFactory.openSession()) {
			Query query = session.createQuery(HQL_SELECT);
			query.setParameter("username", username);
			user = (User) query.list().get(0);
		}
		return user;
	}

}
