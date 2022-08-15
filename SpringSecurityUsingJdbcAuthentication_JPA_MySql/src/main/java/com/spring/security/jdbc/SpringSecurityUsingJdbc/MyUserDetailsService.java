package com.spring.security.jdbc.SpringSecurityUsingJdbc;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * The AuthenticationManagerBuilder is gonna call this MyUserDetailsService in order to lookup the user.
 * @author atuls
 *
 */
@Service //In order to make this class work with @autowire into my securityConfiguration class, we use @Service annotation 
public class MyUserDetailsService implements UserDetailsService {	//This is the UserDetailsService's class
	//This class will connect to some other source of user information like JPA or something else, retrieving the user by usernmae and 
	//and giving the UserDetails object and handing it back to spring security
	//This loadUserByUsername needs to call a JPA API to connect to get the user information.

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		//Create the UserDetails implementation class MyUserDetails
		//loadUserByUsername going to return me the instance of MyUserDetails
		return new MyUserDetails(s); //This method is returning the hard coded user details
		//Now in order to use JPA, remove this method and call JPA repository method.
		//I've done this in next version of this program.
		
	}

}
