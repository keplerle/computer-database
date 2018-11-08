package com.excilys.cdb.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.exception.DataBaseException;
import com.excilys.cdb.model.Company;

@Repository
public class CompanyDAO extends JDBCManager implements CompanyDAOInterface<Company> {
	Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	private final static String QUERY_SELECT_ALL = "SELECT id,name FROM company";
	private final static String QUERY_DELETE = "DELETE FROM company WHERE id= :id";
	private CompanyDAO() {
		super();
	}

	@Override
	public List<Company> findAll() throws IOException, DataBaseException {
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        RowMapper<Company> rowMapper = new RowMapper<Company>() {
            public Company mapRow(ResultSet result, int pRowNum) throws SQLException {
            	Company company = new Company(result.getInt("id"), result.getString("name"));
                return company;
            }
        };
        List<Company> list = jdbcTemplate.query(QUERY_SELECT_ALL, rowMapper);
        return list;
		
	}

	@Override
	public void delete(int id) throws IOException, DataBaseException {
		
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        jdbcTemplate.update(QUERY_DELETE,params);
	}
}
