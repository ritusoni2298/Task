package com.emailpart.emaildemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class EmaildemoApplication {


	public static void main(String[] args) {
		SpringApplication.run(EmaildemoApplication.class, args);
	}

}
