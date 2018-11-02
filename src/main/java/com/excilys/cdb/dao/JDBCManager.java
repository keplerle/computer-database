package com.excilys.cdb.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JDBCManager {
	
	static Logger logger = LoggerFactory.getLogger(JDBCManager.class);
	private static Connection connection;

	private final static JDBCManager jdbcManager = new JDBCManager();

	private JDBCManager() {
		super();
	}

	public static Connection connectionDB() throws IOException {
		Properties props = new Properties();
		try {
			FileInputStream in = new FileInputStream(
					"/home/excilys/eclipse-workspace/computer-database/src/main/resources/db.properties");
			props.load(in);
			in.close();

			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (FileNotFoundException e) {
			logger.error("Le fichier properties est introuvable.");
		} catch (ClassNotFoundException e) {
			logger.error("Le driver est introuvable.");
		}

		String url = props.getProperty("jdbc.url");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");

		try {
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			logger.error("Echec de la connexion.");
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
