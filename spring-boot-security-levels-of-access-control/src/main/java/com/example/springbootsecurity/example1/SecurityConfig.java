package com.example.springbootsecurity.example1;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity	//it tells the spring that it is a web security configuration class. Another way is method level security
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	//Authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//set your authentication to auth object
		auth.inMemoryAuthentication()	//pass username, password and roles
			.withUser("abc")
			.password("abc")
			.roles("USER")
			.and()			//builder pattern, method chaining
			.withUser("def")
			.password("def")
			.roles("ADMIN");
	}
	
	//authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()  //here we can specify mapping between the path to role.
		.antMatchers("/admin").hasRole("ADMIN")	//Hierarchy should be - most restricted to least restricted URL
		.antMatchers("/user").hasRole("USER")
		.antMatchers("/adminuser").hasAnyRole("ADMIN","USER")
		.antMatchers("/").permitAll()
//		.antMatchers("/**")		  //here we can specify the path. /** means all paths in current level and below this level  -  wildcard
//		.hasRole("ADMIN") 		  //here we can specify the role to above path. i.e. all URLS accessible to the users with role as USER.
		.and()					  //end the method chaining by using and() method
		.formLogin();			  //here we are specifying the type of login eg. formLogin. Spring will give default login and logout functionality with formLogin();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance(); //Its not actually doing any encoding here so that we can make use of a clear text as it is here.
	}


}