package com.hmsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HmsappApplication {

	public static void main(String[] args) {
		SpringApplication.run(HmsappApplication.class, args);
	}

}
