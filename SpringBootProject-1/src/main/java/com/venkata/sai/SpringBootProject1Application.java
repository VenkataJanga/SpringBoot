package com.venkata.sai;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableOAuth2Sso
@RestController
public class SpringBootProject1Application {

	
	@GetMapping("/")
	public String message(Principal principal) {
		return "Hi"+principal.getName()+" Welcome to Sai's Application";
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootProject1Application.class, args);
	}

}
