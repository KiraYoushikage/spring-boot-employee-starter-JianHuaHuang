package com.rest.springbootemployee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.rest.*"})
public class SpringBootEmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootEmployeeApplication.class, args);
	}

}
