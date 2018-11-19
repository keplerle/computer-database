package com.excilys.cdb.dao;

import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Computer;

@Repository
public class ComputerDAO implements ComputerDAOInterface<Computer> {
	Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	private final static String HQL_UPDATE = "update Computer set name = :name, introduced = :introduced, discontinued = :discontinued, company = :companyId where id = :id";
	private final static String HQL_SELECT_BY_ID = "select cpu from Computer as cpu left join Company as cpa with cpu.company = cpa.id where cpu.id = :id";
	private final static String HQL_SELECT_BY_NAME = "select cpu from Computer as cpu left join Company as cpa with cpu.company = cpa.id where upper(cpu.name) like upper(:name) or upper(cpa.name) like upper(:name) order by cpu.name ";
	private final static String HQL_COUNT = "select count(cpu.id) from Computer as cpu left join Company as cpa with cpu.company = cpa.id where upper(cpu.name) like upper(:name) or upper(cpa.name) like upper(:name) ";
	private final static String HQL_DELETE_COMPANY = "delete Computer where company= :companyId";
	private final static String HQL_DELETE = "delete Computer where id= :id";

	private final SessionFactory sessionFactory;

	public ComputerDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void create(Computer computer) {
		try (Session session = sessionFactory.openSession()) {
			if(computer.getCompany().getId()==0) {
				computer.setCompany(null);
			}
			session.save(computer);
		}
	}

	@Override
	public void update(Computer computer) {
		try (Session session = sessionFactory.openSession()) {
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(HQL_UPDATE);
			query.setParameter("id", computer.getId());
			query.setParameter("name", computer.getName());
			query.setParameter("introduced", computer.getIntroduced());
			query.setParameter("discontinued", computer.getDiscontinued());			
			query.setParameter("companyId", computer.getCompany().getId() == 0 ? null : computer.getCompany());		
			query.executeUpdate();
			tx.commit();
		}
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
	public Optional<Computer> find(long id) {
		Computer computer;
		try (Session session = sessionFactory.openSession()) {
			computer = session.createQuery(HQL_SELECT_BY_ID, Computer.class).setParameter("id", id).list().get(0);
		}
		return Optional.ofNullable(computer);
	}

	@Override
	public List<Computer> findAll(String name, int page, int size) {
		List<Computer> list;
		try (Session session = sessionFactory.openSession()) {
			Query query = session.createQuery(HQL_SELECT_BY_NAME);
			query.setParameter("name", "%" + name + "%");
			query.setMaxResults(size);
			query.setFirstResult((page - 1) * size);
			list = query.list();
		}
		return list;
	}

	@Override
	public long count(String name) {
		long count = 0;
		try (Session session = sessionFactory.openSession()) {
			count = session.createQuery(HQL_COUNT, Long.class).setParameter("name", "%" + name + "%").uniqueResult();
		}
		return count;
	}

	@Override
	public void deleteByCompany(long companyId) {
		try (Session session = sessionFactory.openSession()) {
			session.createQuery(HQL_DELETE_COMPANY).setParameter("companyId", companyId).executeUpdate();
		}
	}
}
