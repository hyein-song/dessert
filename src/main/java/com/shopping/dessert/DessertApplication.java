package com.shopping.dessert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DessertApplication {

	public static void main(String[] args) {
		SpringApplication.run(DessertApplication.class, args);
	}

}
