package com.example.springbootsecurity.example1;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@EnableWebSecurity	//it tells the spring that it is a web security configuration class. Another way is method level security
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance(); //Its not actually not doing any encoding here so that we can make use of a clear text as it is here.
	}
	
	/*
	 * public PasswordEncoder passwordEncoder() { return new
	 * Pbkdf2PasswordEncoder(); }
	 */
}