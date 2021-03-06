package com.excilys.cdb.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Company;

@Repository
public class CompanyDAO implements CompanyDAOInterface<Company> {
	Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	private static final String HQL_SELECT_ALL = "from Company";
	private static final String HQL_DELETE = "delete Company where id= :id";
	private static final String HQL_UPDATE = "update Company set name = :name where id = :id";
	
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
			Transaction tx = session.beginTransaction();
			session.createQuery(HQL_DELETE).setParameter("id", id).executeUpdate();
			tx.commit();
		}
	}

	@Override
	public void update(Company company) {
		try (Session session = sessionFactory.openSession()) {
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(HQL_UPDATE);
			query.setParameter("id", company.getId());
			query.setParameter("name", company.getName());
			query.executeUpdate();
			tx.commit();
		}	
	}

	@Override
	public void create(Company company) {
		try (Session session = sessionFactory.openSession()) {
			session.save(company);
		}
	}
}
