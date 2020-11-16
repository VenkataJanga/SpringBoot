package com.venkata.sai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/noauth/api")
public class NoAuthControler {

		@GetMapping("/noSecurity")
		public String sayHi() {
			return "Hello this is without Security Implementation";
		}
	
}
