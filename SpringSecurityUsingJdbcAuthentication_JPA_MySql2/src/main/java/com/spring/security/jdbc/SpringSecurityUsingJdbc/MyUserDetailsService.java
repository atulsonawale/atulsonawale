package com.spring.security.jdbc.SpringSecurityUsingJdbc;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.security.jdbc.SpringSecurityUsingJdbc.model.User;
/**
 * The AuthenticationManagerBuilder is gonna call this MyUserDetailsService in order to lookup the user.
 * @author atuls
 *
 */
@Service //In order to make this class work with @autowire into my securityConfiguration class, we use @Service annotation 
public class MyUserDetailsService implements UserDetailsService {	//This is the UserDetailsService's class

	//get the users information 
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUserName(userName); //passing the user name to user repository to get the user object
		//Now convert the above 'user' object to myUserDetails instance.
		
		user.orElseThrow(()->new UsernameNotFoundException("Not found: "+userName));
		return user.map(MyUserDetails::new).get(); //passing the user object to MyUserDetails constructor that will map userDetail to MyUserDetails
	}

}
