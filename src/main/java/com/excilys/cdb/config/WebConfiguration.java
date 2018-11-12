package com.excilys.cdb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("com.excilys.cdb.config, " + "com.excilys.cdb.mapper, " + "com.excilys.cdb.dao, "
		+ "com.excilys.cdb.service, " + "com.excilys.cdb.controller")
public class WebConfiguration implements WebMvcConfigurer{
	
	Logger logger = LoggerFactory.getLogger(WebConfiguration.class);
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/lib/js/");
		registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/lib/css/");
		registry.addResourceHandler("/fonts/**").addResourceLocations("/WEB-INF/lib/fonts/");
	}
	
//	@Bean("messageSource")
//	public MessageSource messageSource() {
//	    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//	    messageSource.setBasenames("languages/messages");
//	    messageSource.setDefaultEncoding("UTF-8");
//	    return messageSource;
//	}
//	
//	@Bean
//	public LocaleResolver localeResolver() {
//	    return new CookieLocaleResolver();
//	}
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//	    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//	    localeChangeInterceptor.setParamName("lang");
//	    registry.addInterceptor(localeChangeInterceptor);
//	}
	
}
