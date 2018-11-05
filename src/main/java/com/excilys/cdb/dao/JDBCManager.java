package com.excilys.cdb.dao;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class JDBCManager {
	
	static Logger logger = LoggerFactory.getLogger(JDBCManager.class);
	private static Connection connection;

	private final static JDBCManager jdbcManager = new JDBCManager();
	static HikariConfig config ;
	static HikariDataSource ds ;
	private JDBCManager() {
		super();
		 config = new HikariConfig("/home/excilys/eclipse-workspace/computer-database/src/main/resources/db.properties");
		 ds = new HikariDataSource(config);
	}

	public static Connection connectionDB() throws IOException {

		try {
			Connection connect = ds.getConnection();
			return connect;
		} catch (SQLException e) {
			logger.error("Echec de la connexion.");
			ds.close();
			return null;
		}
	}
	
	public static JDBCManager getInstance() {
		return jdbcManager;
	}

	public static Connection getConnection() {
		return connection;
	}

}
