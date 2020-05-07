package com.sampleproject.wrenchmarketplace.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*
 * Two datasources being used: this for security
 */

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("securityDataSource")
	private DataSource securityDataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(securityDataSource);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/listings/createNewListing/").hasAnyRole("USER", "ADMIN")
				.antMatchers("/listings/saveNewListing/").hasAnyRole("USER", "ADMIN")
				.antMatchers("/listings/editListing/").hasAnyRole("USER", "ADMIN")
				.antMatchers("/users/createNewUser/").permitAll()
				.antMatchers("/users/editUser/").hasAnyRole("USER", "ADMIN")
				.antMatchers("/showLogin").permitAll()
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/h2/**").permitAll()
				.and()
				.formLogin()
				.loginPage("/showLogin")
				.loginProcessingUrl("/authenticateTheUser") // Spring boot behind the scenes API
				.defaultSuccessUrl("/").permitAll()
				.and()
				.logout()
				.logoutSuccessUrl("/")
				.permitAll();

		/*
		 * Both options disabled because otherwise /h2 path will require constant
		 * authentication even though it is a white listed API.
		 */
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
}
