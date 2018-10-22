package com.excilys.cdb.persistance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCManager {

	private static String KEY_LOGIN = "user";
	private static String KEY_PASS = "password";
	private static String KEY_BDD = "bdd";
	private static String bdd;
	private static String login;
	private static String pass;
	private static Connection connection;
	private static Properties prop;

	private final static JDBCManager jdbcManager = new JDBCManager();

	private JDBCManager() {
		super();
		prop = loadProperties(findSettingsFilePath());

	}

	public static Connection connectionDB() throws SQLException {
		bdd = prop.getProperty(KEY_BDD);
		login = prop.getProperty(KEY_LOGIN);
		pass = prop.getProperty(KEY_PASS);
		connection = DriverManager.getConnection(bdd, login, pass);
		return connection;
	}

	private static Properties loadProperties(String path) {
		Properties settings = new Properties();
		File propfile = new File(path);
		if (propfile.exists()) {
			try {
				FileInputStream in = new FileInputStream(propfile);
				settings.load(in);
			} catch (FileNotFoundException ex) {
				Logger.getLogger(Properties.class.getName()).log(Level.SEVERE,
						"Impossible d'ouvrir le fichier de paramétrage", ex);
			} catch (IOException ioex) {
				Logger.getLogger(Properties.class.getName()).log(Level.SEVERE,
						"Impossible de lire le fichier de paramétrage", ioex);
			}
		}
		return settings;
	}

	private static String findSettingsFilePath() {
		String path;
		String dir = System.getProperty("user.dir", ".");
		String file = JDBCManager.class.getSimpleName() + ".properties.txt";
		String separator = System.getProperty("file.separator", "/");

		path = dir + separator + file;
		return (path);
	}

	public static JDBCManager getInstance() {
		return jdbcManager;
	}

	public static Connection getConnection() {
		return connection;
	}

}
