package com.spring.security.using.jwt.springsecurityjwtexample.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.security.using.jwt.springsecurityjwtexample.services.MyUserDetailsService;
import com.spring.security.using.jwt.springsecurityjwtexample.util.JwtUtil;
/**
 * This filter is used to intercept the request, look at the header(for jwt)
 * @author atuls
 *
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter { //intercept every request only once by extending OncePerRequestFilter

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override	//this method will actually do the job, takes the request and response and passing it to other filter using filter chain
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
//Examine the incoming request for the jwt in the header and see if the jwt is valid. If found the valid jwt, get the user details and save it in the security context.
    //get the header for authorization
        final String authorizationHeader = request.getHeader("Authorization"); //get the authrorization header that we passed from postman's header-Authorization
        //authorizationHeader string should contain "Bearer <jwt>"
        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) { //It means we have JWT in auth header which starts with "Bearer "
            jwt = authorizationHeader.substring(7);	//REMOVE "Bearer " and get the actual jwt
            username = jwtUtil.extractUsername(jwt);
        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//if username is not null, get the user details from loadUserByUsername method
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
         //if we have the JWT and username, validate the jwt for that user using validateToken method
            if (jwtUtil.validateToken(jwt, userDetails)) {
         //UsernamePasswordAuthenticationToken which spring security uses for authentication of username and password
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response); //Now I'm done with my job and handeling you filters to do the rest
    }

}