/*
 *This project talks about basic classes required for JPA to work with Spring
 *This project is not yet using JPA to communicate with DB. READ HELP.md
 *Here we have hard coded the password as "pass" and any username will work here.
 */

package com.spring.security.jdbc.SpringSecurityUsingJdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class) //This does the classpath scan, and allows JPA framework to create the implementation 
public class SpringSecurityUsingJdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityUsingJdbcApplication.class, args);
	}

}
