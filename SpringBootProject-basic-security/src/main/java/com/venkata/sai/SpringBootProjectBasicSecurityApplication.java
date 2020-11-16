package com.venkata.sai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class SpringBootProjectBasicSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootProjectBasicSecurityApplication.class, args);
	}

}
