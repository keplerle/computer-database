package com.excilys.cdb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.exception.DataBaseException;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Repository
public class ComputerDAO extends JDBCManager implements ComputerDAOInterface<Computer> {

	private final static String QUERY_INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
	private final static String QUERY_UPDATE = "UPDATE computer SET name = :name, introduced = :introduced, discontinued = :discontinued, company_id = :company_id WHERE id = :id";
	private final static String QUERY_DELETE = "DELETE FROM computer WHERE id= :id";
	private final static String QUERY_SELECT_BY_NAME = "SELECT cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpu.company_id,cpa.name FROM computer AS cpu LEFT JOIN company AS cpa ON cpu.company_id = cpa.id WHERE UPPER(cpu.name) LIKE UPPER(:name) OR UPPER(cpa.name) LIKE UPPER(:name) ORDER BY cpu.name LIMIT :limit OFFSET :offset";
	private final static String QUERY_SELECT_BY_ID = "SELECT cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpu.company_id,cpa.name FROM computer AS cpu LEFT JOIN company AS cpa ON cpu.company_id = cpa.id WHERE cpu.id = :id";
	private final static String QUERY_COUNT = "SELECT COUNT(cpu.id) FROM computer AS cpu LEFT JOIN company AS cpa ON cpu.company_id = cpa.id WHERE UPPER(cpu.name) LIKE UPPER(:name) OR UPPER(cpa.name) LIKE UPPER(:name) ";
	private final static String QUERY_DELETE_COMPANY = "DELETE FROM computer WHERE company_id= :company_id";
	
	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	@Override
	public void create(Computer computer) throws DataException, IOException, DataBaseException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        Object[] params = {
                new SqlParameterValue(Types.VARCHAR, computer.getName()),
                new SqlParameterValue(Types.DATE, computer.getIntroduced() == null ? null:computer.getIntroduced()),
                new SqlParameterValue(Types.DATE, computer.getDiscontinued() == null ? null:computer.getDiscontinued()),
                new SqlParameterValue(Types.LONGNVARCHAR, computer.getCompany().getId() == 0 ? null:computer.getCompany().getId())
            };
		jdbcTemplate.update(QUERY_INSERT, params);
	}

	@Override
	public void update(Computer computer) throws DataException, IOException, DataBaseException {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
        MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", computer.getName(), Types.VARCHAR);
		params.addValue("introduced",computer.getIntroduced() == null ? null:computer.getIntroduced(), Types.DATE);
		params.addValue("discontinued",computer.getDiscontinued() == null ? null:computer.getDiscontinued(),Types.DATE);
		params.addValue("company_id",computer.getCompany().getId() == 0 ? null:computer.getCompany().getId(),Types.LONGNVARCHAR);
		params.addValue("id",computer.getId(), Types.LONGNVARCHAR);
		jdbcTemplate.update(QUERY_UPDATE, params);
	}

	@Override
	public void delete(int id) throws IOException, DataBaseException {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        jdbcTemplate.update(QUERY_DELETE,params);
	}

	@Override
	public Optional<Computer> find(int id) throws IOException, DataBaseException {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        RowMapper<Computer> rowMapper = new RowMapper<Computer>() {
            public Computer mapRow(ResultSet result, int pRowNum) throws SQLException {
            	Computer computer = new Computer(result.getInt("id"), result.getString("name"));
				if (result.getDate("introduced") != null) {
					computer.setIntroduced(result.getDate("introduced").toLocalDate());
				}
				if (result.getDate("discontinued") != null) {
					computer.setDiscontinued(result.getDate("discontinued").toLocalDate());
				}
				computer.setCompany(new Company());
				if (result.getInt("company_id") != 0) {
					computer.getCompany().setId(result.getInt("company_id"));
					computer.getCompany().setName(result.getString("cpa.name"));
				}
                return computer;
            }
        };
        return Optional.ofNullable(jdbcTemplate.queryForObject(QUERY_SELECT_BY_ID, params, rowMapper));
	}

	@Override
	public List<Computer> findAll(String name, int page, int size) throws IOException, DataBaseException {
		
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", "%" + name + "%");
        params.addValue("limit",size);
        params.addValue("offset", (page - 1) * size);
        RowMapper<Computer> rowMapper = new RowMapper<Computer>() {
            public Computer mapRow(ResultSet result, int pRowNum) throws SQLException {
            	Computer computer = new Computer(result.getInt("id"), result.getString("name"));
				if (result.getDate("introduced") != null) {
					computer.setIntroduced(result.getDate("introduced").toLocalDate());
				}
				if (result.getDate("discontinued") != null) {
					computer.setDiscontinued(result.getDate("discontinued").toLocalDate());
				}
				computer.setCompany(new Company());
				if (result.getInt("company_id") != 0) {
					computer.getCompany().setId(result.getInt("company_id"));
					computer.getCompany().setName(result.getString("cpa.name"));
				}
                return computer;
            }
        };
        List<Computer> list = jdbcTemplate.query(QUERY_SELECT_BY_NAME, params, rowMapper);
        return list;
	}

	@Override
	public int count(String name) throws IOException, DataBaseException {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", "%" + name + "%");
		int count = jdbcTemplate.queryForObject(
	            QUERY_COUNT,
	            params,
	            Integer.class
	            );
		return count;
	}

	@Override
	public void deleteByCompany(int companyId) throws IOException, DataBaseException {	
		
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("company_id", companyId);
        jdbcTemplate.update(QUERY_DELETE_COMPANY,params);
	}
}
