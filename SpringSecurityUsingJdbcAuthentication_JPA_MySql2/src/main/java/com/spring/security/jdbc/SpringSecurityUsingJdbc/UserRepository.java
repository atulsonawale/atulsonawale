package com.spring.security.jdbc.SpringSecurityUsingJdbc;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.security.jdbc.SpringSecurityUsingJdbc.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	//Hey JPA, i need a method which finds by user name
	Optional<User> findByUserName(String userName);//This tells spring JPA that this particular method needs to be implementation which needs to 
												  //work on user entity, JPA will create implementation which finds a given user.
	
}
