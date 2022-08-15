/*
 *This project talks about basic classes required for JPA to work with Spring
 *This project is not yet using JPA to communicate with DB. READ HELP.md
 *Here we have hard coded the password as "pass" and any username will work here.
 */

package com.spring.security.jdbc.SpringSecurityUsingJdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityUsingJdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityUsingJdbcApplication.class, args);
	}

}
