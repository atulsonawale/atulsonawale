package com.spring.security.using.jwt.springsecurityjwtexample.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	private String SECRET_KEY = "secret"; //this needs to be some complex key in real world

//This will extract the userName from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

//This will extract the expiration date from token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { //claimsResolver - used to resolve what all claims are
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //This will takes user detail object and will create jwt
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();	//map of claim, anything that u wanted to include in a payload
        return createToken(claims, userDetails.getUsername()); //passing the username to create the jwt
    }

    private String createToken(Map<String, Object> claims, String subject) {
//subject is a person who has successfully authenticated
//Jwts.builder() calling the jwt builder to create jwt
//setClaims(claims) - setting the claims that we've passed
//setSubject(subject) - setting the subject, subject here is person successfully authenticated
//setIssuedAt(new Date(System.currentTimeMillis())) - jwt issued date
//setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) - how much time jwt is valid
//signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact(); - signing the jwt using SignatureAlgorithm.HS256 and pass SECRET_KEY
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) //current date + 10 hrs
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

//ValidateToken which gets the username and checks if that user name is same as the user name in user detail
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
