package com.excilys.cdb.config;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan("com.excilys.cdb")
@ImportResource("classpath:/applicationContext.xml")
public class SpringConfig {

}
