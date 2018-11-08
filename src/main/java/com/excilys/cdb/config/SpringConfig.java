package com.excilys.cdb.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan("com.excilys.cdb")
@ImportResource("classpath:/applicationContext.xml")
public class SpringConfig {

	@Bean
	public DataSource dataSource() {
		HikariConfig config = new HikariConfig(
				"/home/excilys/eclipse-workspace/computer-database/src/main/resources/db.properties");
		HikariDataSource dataSource = new HikariDataSource(config);
		return dataSource;
	}

}