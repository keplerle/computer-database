
package com.excilys.cdb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter {
	Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	  throws Exception {
	    auth.authenticationProvider(authenticationProvider(userDetailsService));
	}
	 
	@Bean
	public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
	    DaoAuthenticationProvider authProvider
	      = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(encoder());
	    return authProvider;
	}
	

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	 http.authorizeRequests()
    	 .antMatchers("/login").permitAll()
    	 .antMatchers("/edit","/add").hasRole("ADMIN")
    	 .antMatchers("/dashboard").hasAnyRole("USER", "ADMIN")
    	 .and().exceptionHandling().accessDeniedPage("/accessDenied")
    	 .and().formLogin().defaultSuccessUrl("/dashboard", true)
    	 .and().logout().logoutSuccessUrl("/login").permitAll()
    	 .and().csrf().disable();
    	 
    }

	
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
