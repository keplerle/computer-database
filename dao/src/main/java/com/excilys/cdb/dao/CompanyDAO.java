package com.excilys.cdb.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Company;

@Repository
public class CompanyDAO implements CompanyDAOInterface<Company> {
	Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	private static final String HQL_SELECT_ALL = "from Company";
	private static final String HQL_DELETE = "delete Company where id= :id";

	private final SessionFactory sessionFactory;

	public CompanyDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Company> findAll() {
		List<Company> list = new ArrayList<>();
		try (Session session = sessionFactory.openSession()) {
			list = session.createQuery(HQL_SELECT_ALL).list();
		}
		return list;
	}

	@Override
	public void delete(long id) {
		try (Session session = sessionFactory.openSession()) {
			session.createQuery(HQL_DELETE).setParameter("id", id).executeUpdate();
		}
	}

}
