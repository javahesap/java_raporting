package com.example.jasper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan(basePackages = "com.example.jasper") 
public class JasperApplication {

	public static void main(String[] args) {
		SpringApplication.run(JasperApplication.class, args);
	}

}
