package com.trainting.MyBoutique.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HomeEndpoint {

	@PreAuthorize("hasRole('USER')")
	 @GetMapping("/user")
	    public String user() {
	        return ("<h1>Welcome User</h1>");
	    }
	

}
