package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public abstract class JDBCManager {
	
	private static Logger logger = LoggerFactory.getLogger(JDBCManager.class);
	private static HikariConfig config = new HikariConfig("/home/excilys/eclipse-workspace/computer-database/src/main/resources/db.properties") ;
	private static HikariDataSource dataSource = new HikariDataSource(config);
	
    protected static HikariDataSource getDataSource() {
        return dataSource;
    }
    
    protected static Connection getConnexion() {
    	 Connection connect = null;
		try {
			connect = dataSource.getConnection();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
        return connect;
    }
	
}
