package com.spring.security.using.jwt.springsecurityjwtexample.model;
import java.io.Serializable;
/**
 * This class is used as output structure for authenticate method
 * @author atuls
 *
 */
public class AuthenticationResponse implements Serializable {

    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}