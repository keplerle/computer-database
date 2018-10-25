package com.excilys.cdb.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCManager {

	private static Connection connection;

	private final static JDBCManager jdbcManager = new JDBCManager();

	private JDBCManager() {
		super();
	}

	public static Connection connectionDB() throws SQLException, FileNotFoundException, IOException {
		Properties props = new Properties();
        FileInputStream in = new FileInputStream("/home/excilys/eclipse-workspace/computer-database/src/main/resources/db.properties");
        props.load(in);
        in.close();
        
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password= props.getProperty("jdbc.password");
        
        return DriverManager.getConnection(url, username, password);
        
	}

	public static JDBCManager getInstance() {
		return jdbcManager;
	}

	public static Connection getConnection() {
		return connection;
	}

}
