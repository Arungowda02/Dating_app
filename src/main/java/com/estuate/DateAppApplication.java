package com.estuate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class DateAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DateAppApplication.class, args);
	}

}
