package com.spring.security.using.jwt.springsecurityjwtexample.services;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Override //spring framework is going to call 'loadUserByUsername' method in order to load the user by user name
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return new User("user", "pass", new ArrayList<>()); //not dealing with authorities right now.. hence empty list
		//if the credentials mentions here matches with the credentials entered the authentication is successful
	}

}
