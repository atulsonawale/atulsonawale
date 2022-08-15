package com.spring.security.using.jwt.springsecurityjwtexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring.security.using.jwt.springsecurityjwtexample.filter.JwtRequestFilter;
import com.spring.security.using.jwt.springsecurityjwtexample.services.MyUserDetailsService;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailsService myUserDetailsService;
		
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Override	//For authentication
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);
	}
	
	@Override	//for authorization
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
				.authorizeRequests().antMatchers("/authenticate").permitAll().	//permit eveybody for /authenticate
						anyRequest().authenticated().and(). //anyother request needs to be authenticated
						exceptionHandling().and().sessionManagement()	//JWT support  
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);//Here i'm telling spring security, hey dont manage/create session
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); //adding a filter -"jwtRequestFilter"
			//"jwtRequestFilter" this filter gets called before "UsernamePasswordAuthenticationFilter"

	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception { //This is required for backward compatibility of spring boot
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance(); //Its not actually doing any encoding here so that we can make use of a clear text as it is here.
	}

}
