package com.excilys.cdb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Repository
public class ComputerDAO implements ComputerDAOInterface<Computer> {
	Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	private final static String QUERY_INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private final static String QUERY_UPDATE = "UPDATE computer SET name = :name, introduced = :introduced, discontinued = :discontinued, company_id = :company_id WHERE id = :id";
	private final static String QUERY_DELETE = "DELETE FROM computer WHERE id= :id";
	private final static String QUERY_SELECT_BY_NAME = "SELECT cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpu.company_id,cpa.name FROM computer AS cpu LEFT JOIN company AS cpa ON cpu.company_id = cpa.id WHERE UPPER(cpu.name) LIKE UPPER(:name) OR UPPER(cpa.name) LIKE UPPER(:name) ORDER BY cpu.name LIMIT :limit OFFSET :offset";
	private final static String QUERY_SELECT_BY_ID = "SELECT cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpu.company_id,cpa.name FROM computer AS cpu LEFT JOIN company AS cpa ON cpu.company_id = cpa.id WHERE cpu.id = :id";
	private final static String QUERY_COUNT = "SELECT COUNT(cpu.id) FROM computer AS cpu LEFT JOIN company AS cpa ON cpu.company_id = cpa.id WHERE UPPER(cpu.name) LIKE UPPER(:name) OR UPPER(cpa.name) LIKE UPPER(:name) ";
	private final static String QUERY_DELETE_COMPANY = "DELETE FROM computer WHERE company_id= :company_id";
	
	private final static String HQL_INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private final static String HQL_UPDATE = "UPDATE computer SET name = :name, introduced = :introduced, discontinued = :discontinued, company_id = :company_id WHERE id = :id";
	
	private final static String HQL_SELECT_BY_ID = "select cpu from Computer as cpu left join Company as cpa with cpu.company = cpa.id where cpu.id = :id";
	private final static String HQL_SELECT_BY_NAME = "select cpu from Computer as cpu left join Company as cpa with cpu.company = cpa.id where upper(cpu.name) like upper(:name) or upper(cpa.name) like upper(:name) order by cpu.name ";
	private final static String HQL_COUNT = "select count(cpu.id) from Computer as cpu left join Company as cpa with cpu.company = cpa.id where upper(cpu.name) like upper(:name) or upper(cpa.name) like upper(:name) ";
	private final static String HQL_DELETE_COMPANY = "delete Computer where company= :companyId";
	private final static String HQL_DELETE = "delete Computer where id= :id";
	
	private final DataSource dataSource;
	private final SessionFactory sessionFactory;
	public ComputerDAO(DataSource dataSource,SessionFactory sessionFactory) {
		this.sessionFactory =sessionFactory;
		this.dataSource =dataSource;
	}

	@Override
	public void create(Computer computer){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] params = { new SqlParameterValue(Types.VARCHAR, computer.getName()),
				new SqlParameterValue(Types.DATE, computer.getIntroduced()),
				new SqlParameterValue(Types.DATE,
						computer.getDiscontinued()),
				new SqlParameterValue(Types.LONGNVARCHAR,
						computer.getCompany().getId() == 0 ? null : computer.getCompany().getId()) };
		jdbcTemplate.update(QUERY_INSERT, params);
	}

	@Override
	public void update(Computer computer){
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", computer.getName(), Types.VARCHAR);
		params.addValue("introduced", computer.getIntroduced(), Types.DATE);
		params.addValue("discontinued", computer.getDiscontinued(),
				Types.DATE);
		params.addValue("company_id", computer.getCompany().getId() == 0 ? null : computer.getCompany().getId(),
				Types.LONGNVARCHAR);
		params.addValue("id", computer.getId(), Types.LONGNVARCHAR);
		jdbcTemplate.update(QUERY_UPDATE, params);
	}

	@Override
	public void delete(int id) {
		try (Session session = sessionFactory.openSession()) {
			   session.createQuery(HQL_DELETE).setParameter("id", id).executeUpdate();
		}
	}

	@Override
	public Optional<Computer> find(long id) {
		final Computer computer;
		try (Session session = sessionFactory.openSession()) {
			computer = session.createQuery(HQL_SELECT_BY_ID,Computer.class).setParameter("id", id).list().get(0);
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
			  count = session.createQuery(HQL_COUNT,Long.class).setParameter("name", "%" + name + "%").uniqueResult();
		}
		return count;
	}

	@Override
	public void deleteByCompany(int companyId) {
		try (Session session = sessionFactory.openSession()) {
			   session.createQuery(HQL_DELETE_COMPANY).setParameter("companyId", companyId).executeUpdate();
		}
	}
}
