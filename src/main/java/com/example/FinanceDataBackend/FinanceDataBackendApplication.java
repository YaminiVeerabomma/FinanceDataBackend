package com.example.FinanceDataBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
	    "com.example.FinanceDataBackend",          // your controllers, etc.
	    "com.example.FinanceDataBackendservice"    // your service
	})
public class FinanceDataBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceDataBackendApplication.class, args);
	}

}
