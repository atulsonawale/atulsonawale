package com.spring.security.jdbc.SpringSecurityUsingJdbc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity	//it tells the spring security that it is a web security configuration class. Another way is method level security
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService; //We need to create the implementation of UserDetailsService.
	
	@Override //AuthenticationManagerBuilder is for Authentication
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//We have out of the box implementation for jdbcAuthentication, ldapAuthentication, inMemoryAuthentication. But we dont have out of the box implementation for JPA for authentication.
//So the way to have spring security to get the data from database using JPA is actually have nothing to do with JPA at all.
//AuthenticationManager's authenticate() -> AuthenticationProvider's authenticate() and supports() -> userDetailsService's loadUserByUserName()
//loadUserByUserName() which gets the user information based on user name.
//Spring security has a way to provide the userDetail's service, and you can say, hey i'm going to create the user details service and give it to you
//This service is going to take a user name and return me the user object.	

		//In order to have spring security work with JPA, create the instance of userDetailsService and have a logic to lookup the user using JPA.
		
		auth.userDetailsService(userDetailsService);
	}

	@Override //HttpSecurity is for authorization
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()  //here we can specify mapping between the path to role.
			.antMatchers("/admin").hasRole("ADMIN")	//Hierarchy should be - most restricted to least restricted URL
			.antMatchers("/user").hasRole("USER")
			.antMatchers("/adminuser").hasAnyRole("ADMIN","USER")
			.antMatchers("/").permitAll()
			//.antMatchers("/**")		  //here we can specify the path. /** means all paths in current level and below this level  -  wildcard
	 		//.hasRole("ADMIN") 		  //here we can specify the role to above path. i.e. all URLS accessible to the users with role as USER.
			.and()					  //end the method chaining by using and() method
			.formLogin();			  //here we are specifying the type of login eg. formLogin. Spring will give default login and logout functionality with formLogin();

		//Following two line required to access h2 console else you will get 403 forbidden error
		//and error related to csrf - Invalid CSRF token found for http://localhost:8080/h2-console/login.do
//		http.csrf().disable();
//        http.headers().frameOptions().disable();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance(); //Its not actually doing any encoding here so that we can make use of a clear text as it is here.
	}
}