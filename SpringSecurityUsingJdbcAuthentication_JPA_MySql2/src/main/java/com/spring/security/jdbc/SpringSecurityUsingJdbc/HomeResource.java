package com.spring.security.jdbc.SpringSecurityUsingJdbc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResource {
	
	@GetMapping("/") //available for all
	public String home() {
		return ("<h1>Welcome home</h1>");
	}
	
	@GetMapping("/admin")//available for authenticated users only with role as "ADMIN"
	public String admin() {
		return ("<h1>Welcome Admin</h1>");
	}
	
	@GetMapping("/user") //available for authenticated users only with role as "USER"
	public String user() {
		return ("<h1>Welcome user</h1>");
	}
	
	@GetMapping("/adminuser")//available for authenticated users only with role as "USER" or "ADMIN"
	public String accessToAdminAndUser() {
		return ("<h1>Welcome admin or user</h1>");
	}
}
