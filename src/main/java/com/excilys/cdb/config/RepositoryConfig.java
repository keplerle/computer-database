package com.excilys.cdb.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages ="com.excilys.cdb.dao")
public class RepositoryConfig {
	Logger logger = LoggerFactory.getLogger(RepositoryConfig.class);
	@Bean
	public DataSource dataSource() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("db.properties");
		Properties prop = new Properties();
		try {
			prop.load(input);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(prop.getProperty("driverClassName"));
		config.setJdbcUrl(prop.getProperty("jdbcUrl"));
		config.setUsername(prop.getProperty("user"));
		config.setPassword(prop.getProperty("password"));
		HikariDataSource dataSource = new HikariDataSource(config);
		return dataSource;
	}
}
