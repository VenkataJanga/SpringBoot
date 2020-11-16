package com.venkata.sai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/auth")
public class ApplicationController {

	@GetMapping("/greet")
	public String greeting() {
		return "Spring Security Example";
	}
}
