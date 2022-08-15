package com.spring.security.using.jwt.springsecurityjwtexample;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.using.jwt.springsecurityjwtexample.model.AuthenticationRequest;
import com.spring.security.using.jwt.springsecurityjwtexample.model.AuthenticationResponse;
import com.spring.security.using.jwt.springsecurityjwtexample.services.MyUserDetailsService;
import com.spring.security.using.jwt.springsecurityjwtexample.util.JwtUtil;

@RestController
public class HelloResource {

	@Autowired
	private AuthenticationManager authenticationManager; //for authenticating the user and pass
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello";
	}
	
//Endpoint which does authentication
//The problem is I can not call this without authenticatin first, because spring security is around everything, hence need to skip auth.
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticateToken(
			@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		//will get the username and password from AuthenticationRequest and this needs to be authenticated and in order to authenticate, 
		//I will need authentication manager instance - authenticationManager 
		try { //try catch is used if the auth fail the i'm going to catch it.
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
//If authentication is successful, i need to create the JWT and return the jwt
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		
		final String jwt = jwtTokenUtil.generateToken(userDetails);

//retuns a new authentication response of jwt
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
}
