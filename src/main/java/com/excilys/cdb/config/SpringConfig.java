package com.excilys.cdb.config;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({ServiceConfig.class,RepositoryConfig.class})

public class SpringConfig extends AnnotationConfigApplicationContext{

}